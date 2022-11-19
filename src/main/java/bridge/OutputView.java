package bridge;

import java.util.List;

import static bridge.Direction.DOWN;
import static bridge.Direction.UP;
import static bridge.NoticeMessage.START;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {
    private static final String START_BOUNDARY_MAP = "[";
    private static final String END_BOUNDARY_MAP = "]";
    private static final String MID_BOUNDARY_MAP = "|";
    private static final String SPACE = " ";
    private static final int SPACE_COUNT = 3;

    public void printStartNotice() {
        System.out.println(START);
        System.out.println();
    }

    /**
     * 현재까지 이동한 다리의 상태를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printMap(MapDTO mapDTO) {
        StringBuilder upBridge = new StringBuilder(START_BOUNDARY_MAP);
        StringBuilder downBridge = new StringBuilder(START_BOUNDARY_MAP);
        addBridge(mapDTO, upBridge, downBridge);
        System.out.println(upBridge);
        System.out.println(downBridge);
        System.out.println();
    }

    private void addBridge(MapDTO mapDTO, StringBuilder upBridge, StringBuilder downBridge) {
        List<PathDTO> path = mapDTO.getPath();
        for (int round = 0; round < path.size(); round++) {
            addMidBoundary(upBridge, downBridge, round);
            addPath(upBridge, downBridge, path.get(round));
        }
        addEndBoundary(upBridge, downBridge);
    }

    private void addEndBoundary(StringBuilder upBridge, StringBuilder downBridge) {
        upBridge.append(END_BOUNDARY_MAP);
        downBridge.append(END_BOUNDARY_MAP);
    }

    private void addPath(StringBuilder upBridge, StringBuilder downBridge, PathDTO pathDTO) {
        if (isUpwardDirection(pathDTO)) {
            upBridge.append(SPACE + pathDTO.getPass() + SPACE);
            processUnselectedPath(downBridge);
        }
        if (isDownwardDirection(pathDTO)) {
            downBridge.append(SPACE + pathDTO.getPass() + SPACE);
            processUnselectedPath(upBridge);
        }
    }

    private boolean isDownwardDirection(PathDTO pathDTO) {
        return pathDTO.getDirection().equals(DOWN.getName());
    }

    private boolean isUpwardDirection(PathDTO pathDTO) {
        return pathDTO.getDirection().equals(UP.getName());
    }

    private void addMidBoundary(StringBuilder upBridge, StringBuilder downBridge, int round) {
        if (round != 0){
            upBridge.append(MID_BOUNDARY_MAP);
            downBridge.append(MID_BOUNDARY_MAP);
        }
    }

    private void processUnselectedPath(StringBuilder bridge) {
        bridge.append(SPACE.repeat(SPACE_COUNT));
    }

    /**
     * 게임의 최종 결과를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printResult() {
    }
}
