package com.oshewo.panic.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.oshewo.panic.enums.State;
import com.oshewo.panic.interfaces.Interactable;
import com.oshewo.panic.sprites.Chef;
import com.oshewo.panic.sprites.Food;


import static com.oshewo.panic.screens.PlayScreen.activePlayer;


/**
 * The type Food crate.
 * Crates to get ingredients for the recipes
 *
 * @author Oshewo
 */
public class FoodCrate implements Interactable {
    private final Rectangle bounds;
    private final int foodId;
    private final State state;

    /**
     * Instantiates a new Food crate.
     *
     * @param bounds the bounds
     * @param foodId the food id
     */
    public FoodCrate(Rectangle bounds, int foodId, State state) {
        this.bounds = bounds;
        this.foodId = foodId;
        this.state = state;
    }

    /**
     * Checks whether chef is nearby and has free hands to get ingredient
     *
     * @return the chef
     */
    public Chef checkForChef() {
        if (bounds.contains(activePlayer.getX() + activePlayer.getWidth() / 2, activePlayer.getY()) && !activePlayer.isHolding) {
            return activePlayer;
        } else {
            return null;
        }
    }

    /**
     * Sets texture of ingredient according to ID of food
     *
     * @param chefInUse chef in use
     */
    @Override
    public void onUse(Chef chefInUse) {
        if (checkForChef() != null) {
            String tex;
            if (foodId == 1) {
                tex = "lettuce.png";
            } else if (foodId == 2) {
                tex = "tomato.png";
            } else if (foodId == 3) {
                tex = "onion.png";
            } else if (foodId == 4) {
                tex = "meat.png";
            } else if (foodId == 5) {
                tex = "bun.png";
            } else {
                return;
            }
            Food gen = new Food(new Texture(tex), foodId);
            gen.onUse(activePlayer);
        }
    }
}
