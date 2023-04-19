package com.oshewo.panic.non_actor;

import com.oshewo.panic.enums.*;

public class Food {

    private final Ingredients ingredients;
    private IngredientState state;

    public Food(Ingredients ingredients, IngredientState state) {
        this.ingredients = ingredients;
        this.state = state;
    }

    public Ingredients getItem() {
        return this.ingredients;
    }

    public IngredientState getState() {
        return this.state;
    }

    public void setState(IngredientState state) {
        this.state = state;
    }

    public boolean notMatches(Food food) {
        return this.state != food.getState() || this.ingredients != food.getItem();
    }

    @Override
    public String toString() {
        return "Food{" +
                "ingredients=" + ingredients +
                ", state=" + state +
                '}';
    }
}
