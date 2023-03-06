package com.oshewo.panic.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.oshewo.panic.enums.Ingredients;
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
    private final Ingredients ingredient;

    /**
     * Instantiates a new Food crate.
     *
     * @param bounds the bounds
     * @param ingredient the ingredient
     */
    public FoodCrate(Rectangle bounds, Ingredients ingredient) {
        this.bounds = bounds;
        this.ingredient = ingredient;
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
            String texture;
            if (ingredient == Ingredients.LETTUCE) {
                texture = "lettuce.png";
            } else if (ingredient == Ingredients.TOMATO) {
                texture = "tomato.png";
            } else if (ingredient == Ingredients.ONION) {
                texture = "onion.png";
            } else if (ingredient == Ingredients.PATTY) {
                texture = "meat.png";
            } else if (ingredient == Ingredients.BUN) {
                texture = "bun.png";
            } else {
                return;
            }
            Food gen = new Food(new Texture(texture), ingredient);
            gen.onUse(activePlayer);
        }
    }
}
