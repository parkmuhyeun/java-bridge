package bridge;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {

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
    public void move() {
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void retry() {
    }
}
