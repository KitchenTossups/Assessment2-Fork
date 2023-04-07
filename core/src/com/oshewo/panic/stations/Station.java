package com.oshewo.panic.stations;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.actor.*;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.enums.Ingredients;
import com.oshewo.panic.non_actor.*;
import com.oshewo.panic.screens.*;

import java.util.*;

import static com.oshewo.panic.lists.Lists.*;

/**
 * The type Station.
 * Sets methods for all stations - prep stations and servery
 *
 * @author Oshewo
 */
public class Station {

    private final PiazzaPanic game;
    private PlayScreen playScreen;
    private final StationType type;
    private final Rectangle bounds;
    private Ingredients ingredients;
//    private StationTimer timer;
    private final Stage s;


    /**
     * Instantiates a new Station.
     *
     * @param stationType   the station type
     * @param bounds the bounds
     */
    public Station(StationType stationType, Rectangle bounds, PlayScreen playScreen, PiazzaPanic game, Stage s) {
        this.type = stationType;
        this.bounds = bounds;
        this.playScreen = playScreen;
        this.game = game;
        this.s = s;
    }

    /**
     * Updates whether food has been served and if it is being prepped
     */
    public void update(PlayScreen playScreen) {
        updatePlayScreen(playScreen);
        if (this.ingredients == null) {
//        if (foodId < 0) {
//        if (false) {
            checkForFood();
//        } else if (timer.isComplete()) {
//            output();
//            this.item = null;
//            timerArray.remove(timer);
//        } else {
//            showProgress();
        }
    }

    /**
     * Check whether food has been placed on a specific station then if so, starts timer for prepping
     */
    public void checkForFood() {
        for (Food food : new ArrayList<>(foods)) {
            if (bounds.contains(food.getX(), food.getY()) && !food.isCarried()) {
                if (food.getIngredient().getState() == IngredientState.UNCUT && this.type == StationType.CHOPPING_BOARD) {
                    foods.remove(food);
                    timers.add(new StationTimer(bounds.x + (bounds.getWidth() - 18) / 2, bounds.y + bounds.getHeight(), 40, 10, this.s, 15));
                } else if (food.getIngredient().getState() == IngredientState.UNCOOKED && this.type == StationType.STOVE) {
                    foods.remove(food);
                    timers.add(new StationTimer(bounds.x + (bounds.getWidth() - 18) / 2, bounds.y + bounds.getHeight(), 40, 10, this.s, 15));
                } else if (this.type == StationType.SERVING)
                    for (Customer customer : new ArrayList<>(customers))
                        if (customer.getOrder().satisfied(food)) {
                            customers.remove(customer);
                            foods.remove(food);
                            submitOrder();
                            break;
                        }
                else {
                    ingredients = null;
                }
            }
        }

    }

    /**
     * Submit order which finishes current order and restarts hud timer
     */
    public void submitOrder() {
        this.playScreen.incrementOrderCompleted();
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
        Food gen = new Food(null, null, playScreen, game);
//        Food gen = new Food(new Texture(texture), foodId);
        gen.setX(bounds.getX() - 10);
        gen.setY(bounds.getY() - 10);
    }

    /**
     * Returns correct string for the png depending on ID of the foods which can be chopped
     *
     * @return the string for the png of the food
     */
    public String choppingOutput() {
//        if (foodId == 1) {
//            return "lettuce_chopped.png";
//        } else if (foodId == 2) {
//            return "tomato_chopped.png";
//        } else if (foodId == 3) {
//            return "onion_chopped.png";
//        } else if (foodId == 4) {
//            return "patty.png";
//        }
        return null;
    }

    /**
     * Returns correct string for the png depending on ID of the foods which can be cooked
     *
     * @return the string for the png of the food
     */
    public String cookingOutput() {
//        if (item)
        if (item == Item.TOP_BUN) {
            return "top_bun_toasted.png";
        } else if (item == Item.PATTY) {
            return "patty_cooked.png";
        }
        return null;
    }

    public void updatePlayScreen(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
