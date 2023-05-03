package com.oshewo.panic.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.base.BaseActor;
import com.oshewo.panic.enums.PowerUps;
import com.oshewo.panic.lists.Lists;
import com.oshewo.panic.screens.PlayScreen;

import java.util.Date;

public class PowerUpActor extends BaseActor {       // Initialises PowerUpActor
    PowerUps powerUpType;
    PlayScreen playScreen;
    public boolean listenerInit = false;
    private long timeUntilResetChefSpeed;

    public PowerUpActor(Stage s, PlayScreen playScreen, PowerUps powerUpType) {     // Sets the base icons and functionality of each power-up
        super(700, 340, s);
        this.powerUpType = powerUpType;
        this.playScreen = playScreen;

        switch (powerUpType) {
            case EXTRA_LIFE:
                loadTexture("heart.png", 40, 40);
                break;
            case INCREASE_CHEF_SPEED:
                loadTexture("bolt-icon-button-yellow.png", 60, 60);
                break;
            case CLEAR_NEXT_ORDER:
                loadTexture("green-tick-icon.jpg", 60, 60);
                break;
            case DECREASE_CHOPPING_TIME:
                loadTexture("Knife.png", 60, 60);
                break;
            case DECREASE_COOKING_TIME:
                loadTexture("frying-pan.png", 60, 60);
                break;
        }

    }

    public void activate() {        // Activates the power-ups when collected by chef
        switch (this.powerUpType) {
            case EXTRA_LIFE:
                this.activateExtraLife();
                break;
            case INCREASE_CHEF_SPEED:
                this.activateIncreaseChefSpeed();
                break;
            case CLEAR_NEXT_ORDER:
                this.activateClearNextOrder();
                break;
            case DECREASE_CHOPPING_TIME:
                this.activateDecreaseChoppingTime();
                break;
            case DECREASE_COOKING_TIME:
                this.activateDecreaseCookingTime();
                break;

        }
    }

    private void activateIncreaseChefSpeed() {      // Increases chefs speed when collected
        playScreen.movementMultiplier = 1.5F;
        playScreen.timeUntilResetChefSpeed = new Date().getTime() + 30 * 1000;
    }

    private void activateClearNextOrder() {         // Clears next order when collected
        Lists.customers.remove(0);
        this.playScreen.incrementOrderCompleted();
    }

    private void activateExtraLife() {
        playScreen.hud.increaseLives();
    }  // Increases lives when collected

    private void activateDecreaseChoppingTime() {           // Decreases how long chopping ingredients takes when collected
        this.playScreen.choppingTimerMultiplier = 0.5F;
        playScreen.timeUntilResetChoppingMultiplier = new Date().getTime() + 30 * 1000;
    }

    private void activateDecreaseCookingTime() {        //Decreases how long it takes to cook ingredients when collected
        this.playScreen.cookingTimerMultiplier = 0.5F;
        playScreen.timeUntilResetCookingMultiplier = new Date().getTime() + 30 * 1000;
    }

    @Override
    public String toString() {
        return "PowerUpActor{" +
                "powerUpType=" + powerUpType +
                ", playScreen=" + playScreen +
                ", listenerInit=" + listenerInit +
                ", timeUntilResetChefSpeed=" + timeUntilResetChefSpeed +
                '}';
    }
}
