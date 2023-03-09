package com.oshewo.panic.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.enums.Item;
import com.oshewo.panic.screens.PlayScreen;
import com.oshewo.panic.sprites.Chef;
import com.oshewo.panic.sprites.Food;

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
    private final Item item;

    /**
     * Instantiates a new Food crate.
     *
     * @param bounds the bounds
     * @param item the ingredient
     */
    public FoodCrate(Rectangle bounds, Item item, PlayScreen playScreen, PiazzaPanic game) {
        this.game = game;
        this.playScreen = playScreen;
        this.bounds = bounds;
        this.item = item;
    }

    /**
     * Checks whether chef is nearby and has free hands to get ingredient
     *
     * @return boolean
     */
    public boolean checkForChef() {
        return bounds.contains(this.playScreen.chefs[this.playScreen.getChefSelector()].getX() + this.playScreen.chefs[this.playScreen.getChefSelector()].getWidth() / 2, this.playScreen.chefs[this.playScreen.getChefSelector()].getY()) && !this.playScreen.chefs[this.playScreen.getChefSelector()].isHolding;
    }

    /**
     * Sets texture of ingredient according to ID of food
     *
     * @param playScreen active play screen
     */
//    @Override
    public void onUse(PlayScreen playScreen) {
        updatePlayScreen(playScreen);
        if (checkForChef()) {
            String texture;
            if (item == Item.LETTUCE) {
                texture = "lettuce.png";
            } else if (item == Item.TOMATO) {
                texture = "tomato.png";
            } else if (item == Item.ONION) {
                texture = "onion.png";
            } else if (item == Item.PATTY) {
                texture = "meat.png";
            } else if (item == Item.TOP_BUN) {
                texture = "bun.png";
            } else {
                return;
            }
            Food gen = new Food(new Texture(texture), item, this.playScreen, game);
            gen.onUse();
        }
    }

    public void updatePlayScreen(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }
}
