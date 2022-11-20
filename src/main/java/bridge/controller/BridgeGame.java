package bridge.controller;

import bridge.model.Bridge;
import bridge.BridgeMaker;
import bridge.BridgeRandomNumberGenerator;
import bridge.dto.MapDTO;
import bridge.dto.PathDTO;
import bridge.dto.ResultDTO;
import bridge.util.Validator;
import bridge.view.InputView;
import bridge.view.OutputView;

import java.util.ArrayList;
import java.util.List;

import static bridge.model.Command.END;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {
    private static final String PASS = "O";
    private static final String FAIL = "X";

    private final OutputView outputView;
    private final InputView inputView;
    private final Validator validator;
    private final BridgeMaker bridgeMaker;

    public BridgeGame() {
        outputView = new OutputView();
        inputView = new InputView();
        validator = new Validator();
        bridgeMaker = new BridgeMaker(new BridgeRandomNumberGenerator());
    }

    public void run() {
        outputView.printStartNotice();
        int size = inputBridgeSize();
        Bridge bridge = new Bridge(bridgeMaker.makeBridge(size));
        ResultDTO resultDTO = crossBridge(size, bridge);
        outputView.printResult(resultDTO);
    }

    private ResultDTO crossBridge(int size, Bridge bridge) {
        int count = 1;
        while (true) {
            List<PathDTO> pathDTO = new ArrayList<>();
            boolean end = move(size, bridge, pathDTO);
            if (isEnd(end)) {
                return new ResultDTO(new MapDTO(pathDTO), end, count);
            }
            count++;
        }
    }

    private boolean isEnd(boolean end) {
        return end || retry().equals(END.getName());
    }

    private int inputBridgeSize() {
        try {
            String input = inputView.readBridgeSize();
            validator.validateBridgeSize(input);
            return Integer.parseInt(input);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return inputBridgeSize();
        }
    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public boolean move(int size, Bridge bridge, List<PathDTO> pathDTO) {
        boolean end = false;
        for (int round = 0; round < size; round++) {
            if (getPassable(bridge, pathDTO, round).equals(FAIL)) {
                break;
            }
            end = isEnd(size, round);
        }
        return end;
    }

    private String getPassable(Bridge bridge, List<PathDTO> pathDTO, int round) {
        String moving = inputMoving();
        String pass = getPass(bridge, round, moving);
        pathDTO.add(new PathDTO(moving, pass));
        outputView.printMap(new MapDTO(pathDTO));
        return pass;
    }

    private boolean isEnd(int size, int round) {
        return round == size - 1;
    }

    private String getPass(Bridge bridge, int round, String moving) {
        if (bridge.isPassable(round, moving)){
            return PASS;
        }
        return FAIL;
    }

    private String inputMoving() {
        try {
            String moving = inputView.readMoving();
            validator.validateMoving(moving);
            return moving;
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return inputMoving();
        }
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public String retry() {
        try {
            String input = inputView.readGameCommand();
            validator.validateGameCommand(input);
            return input;
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return retry();
        }
    }
}
