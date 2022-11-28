package bridge;

import bridge.model.Bridge;
import bridge.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static bridge.model.Command.RETRY;
import static org.junit.jupiter.api.Assertions.*;

class BridgeGameTest {
    private BridgeGame bridgeGame;

    @BeforeEach
    void setUp() {
        bridgeGame = new BridgeGame();
    }

    @Test
    @DisplayName("사용자가 칸을 이동 - Pass")
    void moveWithPass() {
        User user = new User(new ArrayList<>());
        boolean isPass = bridgeGame.move(new Bridge(List.of("U", "D", "U")), "U", user);

        assertTrue(isPass);
        assertEquals(1, user.getPath().size());
    }

    @Test
    @DisplayName("사용자가 칸을 이동 - Fail")
    void moveWithFail() {
        User user = new User(new ArrayList<>());
        boolean isPass = bridgeGame.move(new Bridge(List.of("U", "D", "U")), "D", user);

        assertFalse(isPass);
        assertEquals(1, user.getPath().size());
    }


    @Test
    @DisplayName("사용자가 게임을 다시 시도")
    void retry() {
        String input = RETRY.getName();

        assertTrue(bridgeGame.retry(input));
    }
}