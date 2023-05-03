package com.oshewo.panic.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.BaseActor;
import com.oshewo.panic.enums.IngredientState;
import com.oshewo.panic.non_actor.Food;
import com.oshewo.panic.screens.PlayScreen;

import java.util.Date;

import static com.oshewo.panic.lists.Lists.*;

public class StationTimer extends BaseActor {       //Initialises all timers

    private final long timeStarted;
    private final TimerSub timerSub;
    private final float duration;
    private final String stationId;
    private final Food heldFood;
    private final float foodX, foodY;
    private final boolean interactionRequired;
    private boolean interactionNeeded = false, halfwayInteraction = false, finalInteraction = false;
    private long timeSinceHalfwayInteractionRequired = -1, timeSinceFinalInteractionRequired = -1;
    private float timeDeductionFromHalf = -1;

    public StationTimer(float x, float y, int width, int height, String stationId, Food heldFood, float foodX, float foodY, Stage s, float seconds, boolean interactionRequired) {
        super(x, y, s);                     // Sets how long it takes for each cooking step to take
        this.stationId = stationId;
        this.duration = seconds;
        this.timeStarted = new Date().getTime();
        this.timerSub = new TimerSub(x + 2, y + 2, width - 4, height - 4, s);
        this.heldFood = heldFood;
        this.interactionRequired = interactionRequired;
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
        System.out.println(this);
        if (interactionRequired) {
            FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("arcadeclassic.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
            fontParameters.size = 36;
            fontParameters.color = Color.WHITE;
            fontParameters.borderWidth = 2;
            fontParameters.borderColor = Color.BLACK;
            fontParameters.borderStraight = true;
            fontParameters.minFilter = Texture.TextureFilter.Linear;
            fontParameters.magFilter = Texture.TextureFilter.Linear;

            BitmapFont bitmap = fontGenerator.generateFont(fontParameters);

            Label.LabelStyle style = new Label.LabelStyle(bitmap, Color.WHITE);
            attentionLabels.put(stationId, new Label("Attention Required!", style));
//            attentionLabels.get(stationId).setVisible(false);
            attentionLabels.get(stationId).setPosition(x, y);
            s.addActor(attentionLabels.get(stationId));
        }
    }

    public void update(Stage s, PlayScreen playScreen, PiazzaPanic game) {   // // Called whenever frames are refreshed and updates anything during runtime for StationTimer
        float percent = getPercentage();
        if (!interactionRequired && getPercentage() >= 1) {
            System.out.println(1);
            foodActors.add(new FoodActor(this.foodX, this.foodY, s, this.heldFood, playScreen, game, -1));
            this.delete();
        } else if (percent >= 0.5 && !this.halfwayInteraction && this.interactionRequired) {
            System.out.println(2);
            if (this.timeSinceHalfwayInteractionRequired == -1) {
                System.out.println(3);
                interactionNeeded = true;
                this.timeSinceHalfwayInteractionRequired = new Date().getTime();
                this.timerSub.setVisible(false);
                this.setVisible(false);
                attentionLabels.get(this.stationId).setVisible(true);
            }
            if ((int) TimeUtils.timeSinceMillis(this.timeSinceHalfwayInteractionRequired) / 1000 >= 15) {
                System.out.println(4);
                this.heldFood.setState(IngredientState.OVERCOOKED);
                foodActors.add(new FoodActor(this.foodX, this.foodY, s, this.heldFood, playScreen, game, -1));
                this.delete();
                attentionLabels.get(this.stationId).remove();
                attentionLabels.remove(this.stationId);
            }
        } else if (percent >= 1 && this.interactionRequired) {
            System.out.println(5);
            if (this.timeSinceFinalInteractionRequired == -1) {
                System.out.println(6);
                interactionNeeded = true;
                this.timeSinceFinalInteractionRequired = new Date().getTime();
                this.timerSub.setVisible(false);
                this.setVisible(false);
                attentionLabels.get(this.stationId).setVisible(true);
            }
            if ((int) TimeUtils.timeSinceMillis(this.timeSinceFinalInteractionRequired) / 1000 >= 15) {
                System.out.println(7);
                this.heldFood.setState(IngredientState.OVERCOOKED);
                foodActors.add(new FoodActor(this.foodX, this.foodY, s, this.heldFood, playScreen, game, -1));
                this.delete();
                attentionLabels.get(this.stationId).remove();
                attentionLabels.remove(this.stationId);
            }
        } else {
            System.out.println(8);
            this.timerSub.setWidth(36 * percent);
        }
    }

    private float getPercentage() {
        if (this.timeSinceHalfwayInteractionRequired == -1)
            return (float) TimeUtils.timeSinceMillis(this.timeStarted) / (this.duration * 1000);
        else if (this.timeDeductionFromHalf != -1) {
            return (TimeUtils.timeSinceMillis(this.timeStarted) - this.timeDeductionFromHalf) / (this.duration * 1000);
        }
        return (float) (TimeUtils.timeSinceMillis(this.timeStarted) - TimeUtils.timeSinceMillis(this.timeSinceHalfwayInteractionRequired)) / (this.duration * 1000);
    }

    public void interacted(Stage s, PlayScreen playScreen, PiazzaPanic game) {
        System.out.println(10);
        if ((getPercentage() >= 0.5 && getPercentage() < 1 && !this.halfwayInteraction) && this.interactionRequired) {
            System.out.println(11);
            this.timerSub.setVisible(true);
            this.setVisible(true);
            this.interactionNeeded = false;
            this.halfwayInteraction = true;
            this.timeDeductionFromHalf = TimeUtils.timeSinceMillis(this.timeSinceHalfwayInteractionRequired);
            attentionLabels.get(this.stationId).setVisible(false);
        } else if (getPercentage() >= 1 && !this.finalInteraction && this.halfwayInteraction) {
            System.out.println(12);
            this.finalInteraction = true;
            foodActors.add(new FoodActor(this.foodX, this.foodY, s, this.heldFood, playScreen, game, playScreen.getChefSelector()));
            this.delete();
            attentionLabels.get(this.stationId).remove();
            attentionLabels.remove(this.stationId);
        }
        System.out.println(13);
    }

    private void delete() {
        timers.remove(this);
        this.timerSub.remove();
        this.remove();
    }

    public String getStationId() {
        return stationId;
    }

    static class TimerSub extends BaseActor {
        public TimerSub(float x, float y, int width, int height, Stage s) {
            super(x, y, s);
            this.loadTexture("progressRed.png", width, height);
        }
    }

    public boolean isInteractionRequired() {
        return interactionRequired;
    }

    @Override
    public String toString() {
        return "StationTimer{" +
                "timeStarted=" + timeStarted +
                ", timerSub=" + timerSub +
                ", duration=" + duration +
                ", stationId='" + stationId + '\'' +
                ", heldFood=" + heldFood +
                ", foodX=" + foodX +
                ", foodY=" + foodY +
                ", interactionRequired=" + interactionRequired +
                ", interactionNeeded=" + interactionNeeded +
                ", halfwayInteraction=" + halfwayInteraction +
                ", finalInteraction=" + finalInteraction +
                ", timeSinceHalfwayInteractionRequired=" + timeSinceHalfwayInteractionRequired +
                ", timeSinceFinalInteractionRequired=" + timeSinceFinalInteractionRequired +
                '}';
    }
}
