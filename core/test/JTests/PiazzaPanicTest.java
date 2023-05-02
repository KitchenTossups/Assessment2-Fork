package JTests;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.*;
import com.oshewo.panic.Calculation;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.scenes.Hud;
import org.junit.jupiter.api.Test;
import com.oshewo.panic.screens.PlayScreen;

class PiazzaPanicTest {
    @Test
    public void SmoothGameTest() {
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        HeadlessApplication headlessApplication = new HeadlessApplication(new PiazzaPanic(true), config);
        assertTrue(headlessApplication.getGraphics().getFramesPerSecond() >= 24);
        // same issue with running the game?
    }

    @Test
    public void StartReputationPointsTest() {
        int getReputationPointsPlaceholder = 3;
        assertEquals(getReputationPointsPlaceholder, 3);
        // getter in Hud
    }

    @Test
    public void LoseReputationPointTest() {
        // oldest in Hud update
        // orderSystem -> update for time limit and flagging code
        // LISTENING
    }

    @Test
    public void GameOverTest() {
        // getLives in Hud
        // game.getScreen
        // check label somewhere to differentiate win/loss
        // LISTENER
    }

    @Test
    public void GameWinTest() {
        // if no customers remain and rep points > 0, pass iff game won
        // customers in lists
        // getLives in hud
        // check label somewhere to differentiate win/loss
        // LISTENER
    }

    @Test
    public void PlaytimeTest() {
        // start loop
        // has game been won? pass
        // has timer gone over 5 mins? fail
        // otherwise, loop again
        // Hud.getLapseTime
        // LISTENER
    }

    @Test
    public void FastInteractTest() {
        // listen to console output?
    }

    @Test
    public void SaveStateTest() {
        // record all state variables
        // save state, quit game, reload state
        // check variables all match
    }

}