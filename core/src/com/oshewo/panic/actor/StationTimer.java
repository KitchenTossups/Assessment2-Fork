package com.oshewo.panic.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.oshewo.panic.base.BaseActor;
import com.oshewo.panic.enums.IngredientState;
import com.oshewo.panic.non_actor.Food;

import java.util.Date;

public class StationTimer extends BaseActor {

    private final long timeStarted;
    private final TimerSub timerSub;
    private final float duration;
    private final String stationId;
    private final Food heldFood;
    private final float foodX, foodY;

    public StationTimer(float x, float y, int width, int height, String stationId, Food heldFood, float foodX, float foodY, Stage s, float seconds) {
        super(x, y, s);
        this.stationId = stationId;
        this.duration = seconds;
        this.timeStarted = new Date().getTime();
        this.timerSub = new TimerSub(x + 2, y + 2, width - 4, height - 4, s);
        this.heldFood = heldFood;
        switch (this.heldFood.getState()) {
            case UNCUT:
                this.heldFood.setState(IngredientState.CUT);
                break;
            case UNCUT_UNCOOKED:
                this.heldFood.setState(IngredientState.UNCOOKED);
                break;
            case UNCOOKED:
                this.heldFood.setState(IngredientState.COOKED);
                break;
            case HALF_COOKED:
                this.heldFood.setState(IngredientState.COOKED);
                break;
            case UNPREPARED:
                this.heldFood.setState(IngredientState.PREPARED);
                break;
            default:
                break;
        }
        this.loadTexture("progressGrey.png", width, height);
        this.foodX = foodX;
        this.foodY = foodY;
    }

    public float getProgressPercent() {
        return (float) TimeUtils.timeSinceMillis(this.timeStarted) / (this.duration * 1000);
    }

    public float getHeldFoodX() {
        return this.foodX;
    }

    public float getHeldFoodY() {
        return this.foodY;
    }

    public Food getHeldFood() {
        return this.heldFood;
    }

    public void delete() {
        this.timerSub.remove();
        this.remove();
    }

    public String getStationId() {
        return stationId;
    }

    public void setInnerWidth(float w) {
        this.timerSub.setWidth(w);
    }

    static class TimerSub extends BaseActor {
        public TimerSub(float x, float y, int width, int height, Stage s) {
            super(x, y, s);
            this.loadTexture("progressRed.png", width, height);
        }
    }
}
