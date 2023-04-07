package com.oshewo.panic;

import com.oshewo.panic.base.BaseGame;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.screens.*;

/**
 * The type Piazza panic.
 * Sets dimensions of game and initial screen when game loads
 */
public class PiazzaPanic extends BaseGame {
    // screen dimensions
    public final float V_WIDTH = 1280;
    public final float V_HEIGHT = 720;
    public final boolean VERBOSE;
    public boolean RUNNING = true;

    public long worldTimer = 0;

    public GameMode MODE = GameMode.SCENARIO;
    public Difficulty DIFFICULTY = Difficulty.HARD;

//    public SpriteBatch batch;

    /**
     * When game loads, sets screen to main menu screen
     */
//    @Override
    public void create() {
        super.create();
//        batch = new SpriteBatch();
        setActiveScreen(new MainMenu(this));
    }

    public boolean isVerbose() {
        return this.verbose;
    }

//    @Override
//    public void render() {
//        super.render();
//    }

//    @Override
//    public void dispose() {
//        batch.dispose();
//    }
}
