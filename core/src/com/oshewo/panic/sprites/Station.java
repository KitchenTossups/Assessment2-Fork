package com.oshewo.panic.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.oshewo.panic.enums.Ingredients;
import com.oshewo.panic.enums.StationType;

import java.util.ArrayList;

import static com.oshewo.panic.scenes.Hud.hudStartTime;
import static com.oshewo.panic.screens.PlayScreen.*;
import static com.oshewo.panic.sprites.Food.foodArray;
import static com.oshewo.panic.sprites.CountdownTimer.timerArray;

/**
 * The type Station.
 * Sets methods for all stations - prep stations and servery
 *
 * @author Oshewo
 */
public class Station {
    private final StationType type;
    private final Rectangle bounds;
    private Ingredients ingredient;
    private CountdownTimer timer;


    /**
     * Instantiates a new Station.
     *
     * @param stationType   the station type
     * @param bounds the bounds
     */
    public Station(StationType stationType, Rectangle bounds) {
        this.type = stationType;
        this.bounds = bounds;
    }

    /**
     * Updates whether food has been served and if it is being prepped
     */
    public void update() {
        if (foodId < 0) {
            checkForFood();
        } else if (timer.isComplete()) {
            output();
            foodId = -1;
            timerArray.remove(timer);
        } else {
            showProgress();
        }
    }

    /**
     * Check whether food has been placed on a specific station then if so, starts timer for prepping
     */
    public void checkForFood() {
        for (Food food : new ArrayList<>(foodArray)) {
            if (bounds.contains(food.getX(), food.getY()) && !food.followingChef) {
                foodId = food.getId();
                if (food.isChoppable() && this.type == StationType.CHOPPING_BOARD) {
                    foodArray.remove(food);
                    timer = new CountdownTimer(15, bounds);
                } else if (food.isGrillable() && this.type == StationType.STOVE) {
                    foodArray.remove(food);
                    timer = new CountdownTimer(15, bounds);
                } else if (foodId == currentOrder.getOrderId() && this.type == StationType.SERVING) {
                    foodArray.remove(food);
                    timer = new CountdownTimer(0, bounds);
                    submitOrder();
                } else {
                    foodId = -1;
                }
            }
        }

    }

    /**
     * Submit order which finishes current order and restarts hud timer
     */
    public void submitOrder() {
        currentOrder = null;
        hudStartTime = TimeUtils.millis();
        ordersCompleted++;
    }

    /**
     * Show progress of timer.
     */
    public void showProgress() {
        float progress = timer.getProgressPercent();
        float x = this.bounds.x;
        float y = this.bounds.y + this.bounds.height;
        float width = 32;
        float height = 5;
    }

    /**
     * Outputs finished food and the correct texture of cooked food.
     */
    public void output() {
        String texture = "";
        if (type == StationType.CHOPPING_BOARD) {
            texture = choppingOutput();
        } else if (type == StationType.STOVE) {
            texture = cookingOutput();
        } else {
            return;
        }
        Food gen = new Food(new Texture(texture), foodId);
        gen.setX(bounds.getX() - 10);
        gen.setY(bounds.getY() - 10);
    }

    /**
     * Returns correct string for the png depending on ID of the foods which can be chopped
     *
     * @return the string for the png of the food
     */
    public String choppingOutput() {
        if (foodId == 1) {
            return "lettuce_chopped.png";
        } else if (foodId == 2) {
            return "tomato_chopped.png";
        } else if (foodId == 3) {
            return "onion_chopped.png";
        } else if (foodId == 4) {
            return "patty.png";
        }
        return null;
    }

    /**
     * Returns correct string for the png depending on ID of the foods which can be cooked
     *
     * @return the string for the png of the food
     */
    public String cookingOutput() {
        if (ingredient == Ingredients.BUN) {
            return "bun_toasted.png";
        } else if (ingredient == Ingredients.PATTY) {
            return "patty_cooked.png";
        }
        return null;
    }
}
