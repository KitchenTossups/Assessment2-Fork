package com.oshewo.panic.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.base.BaseActor;
import com.oshewo.panic.sprites.Food;

import static com.oshewo.panic.sprites.Food.foodArray;
//import com.eng1.non_actor.*;

public class Chef extends BaseActor {

    private Object inventoryItem;
    public boolean isHolding = false;

    public Chef(float x, float y, Stage s, int chefNumber) {
        super(x, y, s);

        this.loadTexture("ChefB" + chefNumber + ".png");
    }

    public Object getInventoryItem() {
        return this.inventoryItem;
    }

    public Food nearestFood(float distance) {
        Food nearestFood = null;
        float nearestDistance = Float.MAX_VALUE;
        for (Food food : foodArray) {
            float currentDistance = food.getPosition().dst(this.getX(), this.getY());
//             this.getPosition().dst(food.getPosition());
            if (currentDistance <= distance && currentDistance < nearestDistance) {
                System.out.println(currentDistance + " " + nearestDistance);
                nearestDistance = currentDistance;
                nearestFood = food;
                System.out.println(nearestFood);
            }
        }
        return nearestFood;
    }

//    public void setInventoryItem(Object inventoryItem) {
//        if (inventoryItem instanceof Food || inventoryItem instanceof Ingredient || inventoryItem == null)
//            this.inventoryItem = inventoryItem;
//    }
}
