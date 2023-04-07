package com.oshewo.panic.stations;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.enums.Ingredients;
import com.oshewo.panic.screens.PlayScreen;

/**
 * The type Food crate.
 * Crates to get ingredients for the recipes
 *
 * @author Oshewo
 */
public class FoodCrate {

    private final PiazzaPanic game;
    private PlayScreen playScreen;
    private final Rectangle bounds;
    private final Ingredients ingredients;

    /**
     * Instantiates a new Food crate.
     *
     * @param bounds the bounds
     * @param ingredients   the ingredient
     */
    public FoodCrate(Rectangle bounds, Ingredients ingredients, PlayScreen playScreen, PiazzaPanic game) {
        this.game = game;
        this.playScreen = playScreen;
        this.bounds = bounds;
        this.ingredients = ingredients;
    }

    /**
     * Checks whether chef is nearby and has free hands to get ingredient
     *
     * @return boolean
     */
    public boolean checkForChef() {
        if (game.VERBOSE) {
            System.out.println(4);
            System.out.println(bounds);
            System.out.println(this.playScreen.chefs[this.playScreen.getChefSelector()].getX() + " " + this.playScreen.chefs[this.playScreen.getChefSelector()].getY());
        }
        return bounds.contains(this.playScreen.chefs[this.playScreen.getChefSelector()].getX() + this.playScreen.chefs[this.playScreen.getChefSelector()].getWidth() / 2, this.playScreen.chefs[this.playScreen.getChefSelector()].getY()) &&
                !this.playScreen.chefs[this.playScreen.getChefSelector()].isHolding;
//        return false;
    }

    /**
     * Sets texture of ingredient according to ID of food
     *
     * @param playScreen active play screen
     */
//    @Override
    public void onUse(PlayScreen playScreen, Stage s) {
        updatePlayScreen(playScreen);
        if (checkForChef()) {
            if (game.VERBOSE)
                System.out.println(3);
            String texture;
            if (ingredients == Ingredients.LETTUCE) {
                texture = "lettuce.png";
            } else if (ingredients == Ingredients.TOMATO) {
                texture = "tomato.png";
            } else if (ingredients == Ingredients.ONION) {
                texture = "onion.png";
            } else if (ingredients == Ingredients.PATTY) {
                texture = "meat.png";
            } else if (ingredients == Ingredients.BUN) {
                texture = "bun.png";
            } else {
                return;
            }
//            Food gen = new Food(0, 0, s, new Texture(texture), ingredients, this.playScreen, game);
//            gen.onUse();
        }
    }

    public void updatePlayScreen(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    @Override
    public String toString() {
        return "FoodCrate{" +
                "bounds=" + bounds +
                ", item=" + ingredients +
                '}';
    }
}
