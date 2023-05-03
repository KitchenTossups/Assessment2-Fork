package com.oshewo.panic.stations;

import com.badlogic.gdx.math.Rectangle;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.screens.PlayScreen;

public class FoodCrateBox {     //Initialises food crate

    private final PiazzaPanic game;
    private final PlayScreen playScreen;
    private final Rectangle bounds;

    public FoodCrateBox(Rectangle bounds, PlayScreen playScreen, PiazzaPanic game) {
        this.game = game;
        this.playScreen = playScreen;
        this.bounds = bounds;
    }

    public boolean checkForChef() {
        if (this.game.VERBOSE) {
            System.out.println(4);
            System.out.println(this.bounds);
            System.out.println(this.playScreen.chefs[this.playScreen.getChefSelector()].getX() + " " + this.playScreen.chefs[this.playScreen.getChefSelector()].getY());
        }
        return this.bounds.contains(this.playScreen.chefs[this.playScreen.getChefSelector()].getX() + this.playScreen.chefs[this.playScreen.getChefSelector()].getWidth() / 2, this.playScreen.chefs[this.playScreen.getChefSelector()].getY()) &&
                !this.playScreen.chefs[this.playScreen.getChefSelector()].isHolding;
    }
}
