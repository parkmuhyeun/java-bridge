package bridge.controller;

import bridge.BridgeGame;
import bridge.BridgeMaker;
import bridge.BridgeRandomNumberGenerator;
import bridge.dto.ResultDTO;
import bridge.model.Bridge;
import bridge.model.User;
import bridge.util.Converter;
import bridge.util.Validator;
import bridge.view.InputView;
import bridge.view.OutputView;
import java.util.ArrayList;

public class BridgeController {
    private final OutputView outputView;
    private final InputView inputView;
    private final Validator validator;
    private final BridgeMaker bridgeMaker;
    private final BridgeGame bridgeGame;
    private final Converter converter;

    public BridgeController() {
        outputView = new OutputView();
        inputView = new InputView();
        validator = new Validator();
        bridgeMaker = new BridgeMaker(new BridgeRandomNumberGenerator());
        bridgeGame = new BridgeGame();
        converter = new Converter();
    }

    public void run() {
        outputView.printStartNotice();
        int size = inputBridgeSize();
        Bridge bridge = new Bridge(bridgeMaker.makeBridge(size));

        User user = new User(new ArrayList<>());
        while(true){
            for (int round = 0; round < size; round++) {
                String inputMoving = inputMoving();
                boolean isPass = bridgeGame.move(bridge, inputMoving, user);
                //출력
                outputView.printMap(converter.convertToMapDTO(user));
                //pass한지 아닌지
                if (!isPass) {
                    break;
                }
                //성공했다면
                if (round == size - 1) {
                    bridgeGame.updateSuccess();
                    bridgeGame.updateEnd();
                }
            }
            //재입력
            if (bridgeGame.isEnd() || !bridgeGame.retry(inputGameCommand())) {
                break;
            }
            user.clear();
        }

        outputView.printResult(new ResultDTO(converter.convertToMapDTO(user), bridgeGame.isSuccess(), bridgeGame.getCount()));
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

    private String inputGameCommand() {
        try {
            String input = inputView.readGameCommand();
            validator.validateGameCommand(input);
            return input;
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return inputGameCommand();
        }
    }
}
