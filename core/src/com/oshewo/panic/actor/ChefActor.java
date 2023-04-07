package com.oshewo.panic.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.BaseActor;

import static com.oshewo.panic.lists.Lists.foods;

public class ChefActor extends BaseActor {

    public boolean isHolding = false;

    private final PiazzaPanic game;

    public ChefActor(float x, float y, Stage s, PiazzaPanic game, int chefNumber) {
        super(x, y, s);
        this.game = game;
        this.loadTexture("ChefB" + (chefNumber + 1) + ".png");
    }

    public Food nearestFood(float distance) {
        Food nearestFood = null;
        float nearestDistance = Float.MAX_VALUE;
        for (Food food : foods) {
            float currentDistance = food.getPosition().dst(this.getX(), this.getY());
            if (currentDistance <= distance && currentDistance < nearestDistance) {
                if (this.game.VERBOSE)
                    System.out.println(currentDistance + " " + nearestDistance);
                nearestDistance = currentDistance;
                nearestFood = food;
                if (this.game.VERBOSE)
                    System.out.println(nearestFood);
            }
        }
        return nearestFood;
    }
}
