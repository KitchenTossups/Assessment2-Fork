package com.oshewo.panic.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.base.BaseActor;
import com.oshewo.panic.enums.PowerUps;
import com.oshewo.panic.lists.Lists;
import com.oshewo.panic.screens.PlayScreen;

import java.util.Date;

public class PowerUpActor extends BaseActor {
    PowerUps powerUpType;
    PlayScreen playScreen;
    public boolean listenerInit = false;
    private long timeUntilResetChefSpeed;

    public PowerUpActor(float x, float y, Stage s, PlayScreen playScreen, PowerUps powerUpType){
        super(x, y, s);
        this.powerUpType = powerUpType;
        this.playScreen = playScreen;

        switch (powerUpType){
            case EXTRA_LIFE:
                loadTexture("heart.png", 60, 60);
                break;
            case INCREASE_CHEF_SPEED:
                loadTexture("bolt-icon-button-yellow.png", 60, 60);
                break;
            case CLEAR_NEXT_ORDER:
                loadTexture("green-tick-icon.jpg", 60, 60);
        }

    }

    public void activate() {
        switch (this.powerUpType) {
//            case EXTRA_LIFE:
//                this.activateExtraLife();
//                break;
//            case INCREASE_CHEF_SPEED:
//                this.activeIncreaseChefSpeed();
//                break;
            case CLEAR_NEXT_ORDER:
                this.activeClearNextOrder();
                break;

        }
    }

    private void activeIncreaseChefSpeed(){
        playScreen.movementMultiplier = 1.25F;
        this.timeUntilResetChefSpeed = new Date().getTime() + 30 * 1000;
        //Need to implement a while loop using delta time and reset chef speed after given time
        playScreen.movementMultiplier = 1;
    }

    private void activeClearNextOrder(){
        Lists.customers.remove(0);
        this.playScreen.incrementOrderCompleted();
    }
    private void activateExtraLife(){
        playScreen.hud.increaseLives();
    }
}
