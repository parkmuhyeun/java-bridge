package bridge;

import bridge.model.Bridge;
import bridge.model.Path;
import bridge.model.User;

import static bridge.model.Case.getPictogram;
import static bridge.model.Command.isRetry;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {
    private int count = 1;
    private int round = 0;
    private boolean end;
    private boolean success;

    public int getCount() {
        return count;
    }

    public boolean isEnd() {
        return end;
    }

    public boolean isSuccess() {
        return success;
    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public boolean move(Bridge bridge, String inputMoving, User user) {
        boolean isPass = bridge.isPassable(round++, inputMoving);
        user.addPath(new Path(inputMoving, getPictogram(isPass)));
        return isPass;
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public boolean retry(String gameCommand) {
        boolean retry = isRetry(gameCommand);
        processCondition(retry);
        return retry;
    }

    private void processCondition(boolean retry) {
        processRetry(retry);
        processExit(retry);
    }

    private void processExit(boolean retry) {
        if(!retry){
            updateEnd();
        }
    }

    private void processRetry(boolean retry) {
        if (retry) {
            count++;
            round = 0;
        }
    }

    public void updateEnd() {
        end = !end;
    }

    public void updateSuccess() {
        success = !success;
    }
}
