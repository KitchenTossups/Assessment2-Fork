package com.oshewo.panic.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.base.BaseActor;

import static com.oshewo.panic.lists.Lists.foods;

public class ChefActor extends BaseActor {

    public boolean isHolding = false;

    public ChefActor(float x, float y, Stage s, int chefNumber) {
        super(x, y, s);

        this.loadTexture("ChefB" + (chefNumber + 1) + ".png");
    }

    public Food nearestFood(float distance) {
        Food nearestFood = null;
        float nearestDistance = Float.MAX_VALUE;
        for (Food food : foods) {
            float currentDistance = food.getPosition().dst(this.getX(), this.getY());
            if (currentDistance <= distance && currentDistance < nearestDistance) {
                System.out.println(currentDistance + " " + nearestDistance);
                nearestDistance = currentDistance;
                nearestFood = food;
                System.out.println(nearestFood);
            }
        }
        return nearestFood;
    }
}
