package com.oshewo.panic.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.base.BaseActor;
import com.oshewo.panic.enums.PowerUps;
import com.oshewo.panic.screens.PlayScreen;

public class PowerUpActor extends BaseActor {
    PowerUps powerUpType;
    PlayScreen playScreen;
    public boolean listenerInit = false;

    public PowerUpActor(float x, float y, Stage s, PlayScreen playScreen, PowerUps powerUpType){
        super(x, y, s);
        this.powerUpType = powerUpType;
        this.playScreen = playScreen;

        switch (powerUpType){
            case EXTRA_LIFE:
                loadTexture("heart.png", 60, 60);
                break;
        }

    }

    public void activate() {
        switch (this.powerUpType) {
            case EXTRA_LIFE:
                this.activateExtraLife();
                break;
        }
    }

    private void activateExtraLife(){
        playScreen.hud.increaseLives();
    }
}
