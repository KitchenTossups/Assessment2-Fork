package com.oshewo.panic.stations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.oshewo.panic.interfaces.IInteractable;
import com.oshewo.panic.sprites.Chef;
import com.oshewo.panic.sprites.Food;


import static com.oshewo.panic.screens.PlayScreen.activePlayer;


public class FoodCrate implements IInteractable {
    private Rectangle bounds;
    private int foodId;
    public FoodCrate(Rectangle bounds, int foodId) {
        this.bounds = bounds;
        this.foodId = foodId;
    }
    public Chef checkForChef() {
        if (bounds.contains(activePlayer.getX()+activePlayer.getWidth()/2, activePlayer.getY()) && !activePlayer.isHolding) {
            return activePlayer;
        }else{
            return null;
        }
    }

    @Override
    public void onUse(Chef chefInUse) {
        if(checkForChef()!=null) {
            String tex = "";
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
            }
            if (tex == "" || tex == null) {
                return;
            }
            Food gen = new Food(new Texture(tex), foodId);
            gen.onUse(activePlayer);
        }
    }
}
