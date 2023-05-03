package com.oshewo.panic.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.BaseActor;

import static com.oshewo.panic.lists.Lists.foodActors;

public class ChefActor extends BaseActor {          // ChefActor initialises chefs controlled by player, interacts with all other actors

    public boolean isHolding = false;

    private final PiazzaPanic game;

    public ChefActor(float x, float y, Stage s, PiazzaPanic game, int chefNumber) {     //Initialises the chef
        super(x, y, s);
        this.game = game;
        this.loadTexture("ChefB" + (chefNumber + 1) + ".png");
    }

    public FoodActor nearestFood(float distance) {      //FoodActor appears on the screen and implements non-actor food
        FoodActor nearestFoodActor = null;
        float nearestDistance = Float.MAX_VALUE;
        for (FoodActor foodActor : foodActors) {
            float currentDistance = foodActor.getPosition().dst(this.getX(), this.getY());
            if (currentDistance <= distance && currentDistance < nearestDistance) {
                if (this.game.VERBOSE)
                    System.out.println(currentDistance + " " + nearestDistance);
                nearestDistance = currentDistance;
                nearestFoodActor = foodActor;
                if (this.game.VERBOSE)
                    System.out.println(nearestFoodActor);
            }
        }
        return nearestFoodActor;
    }
}
