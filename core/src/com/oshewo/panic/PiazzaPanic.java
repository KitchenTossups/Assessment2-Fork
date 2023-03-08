package com.oshewo.panic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.oshewo.panic.enums.Difficulty;
import com.oshewo.panic.enums.GameMode;
import com.oshewo.panic.screens.MainMenu;

/**
 * The type Piazza panic.
 * Sets dimensions of game and initial screen when game loads
 */
public class PiazzaPanic extends Game {
    // screen dimensions and zoom
    public static final float V_ZOOM = 1.42f;
    public static final float V_WIDTH = 1280;
    public static final float V_HEIGHT = 720;
    private final boolean verbose = true;

    public long worldTimer = 0;

    public GameMode MODE = GameMode.SCENARIO;
    public Difficulty DIFFICULTY = Difficulty.HARD;

    public SpriteBatch batch;

    /**
     * When game loads, sets screen to main menu screen
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MainMenu(this));
    }

    public boolean isVerbose() {
        return this.verbose;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
