package bridge;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static bridge.Direction.DOWN;
import static bridge.Direction.UP;
import static org.junit.jupiter.api.Assertions.*;

class BridgeMakerTest {

    @Test
    @DisplayName("다리 생성")
    void makeBridge() {
        int size = 5;
        BridgeMaker bridgeMaker = new BridgeMaker(new BridgeRandomNumberGenerator());
        List<String> bridge = bridgeMaker.makeBridge(size);

        assertEquals(size, bridge.size());
        for (int round = 0; round < size; round++) {
            assertTrue(isDirection(bridge, round));
        }
    }

    private boolean isDirection(List<String> bridge, int round) {
        return bridge.get(round).contains(DOWN.getName()) || bridge.get(round).contains(UP.getName());
    }
}