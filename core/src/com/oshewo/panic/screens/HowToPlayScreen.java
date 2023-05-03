package com.oshewo.panic.screens;

import com.badlogic.gdx.Gdx;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.*;

public class HowToPlayScreen extends BaseScreen {  // Instruction screen

    private final PiazzaPanic game;

    public HowToPlayScreen(PiazzaPanic game) {
        BaseActor background = new BaseActor(0, 0, this.mainStage);
        background.loadTexture("loadscreenfinal.png");
        background.setSize(game.V_WIDTH, game.V_HEIGHT);
        this.game = game;
    }

    public void update(float dt) {
        if (Gdx.input.isTouched()) {
            this.dispose();
            this.game.setActiveScreen(new MainMenu(this.game));
        }
    }

    public void resizing(int width, int height) {

    }

    public void disposing() {

    }
}
