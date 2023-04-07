package com.oshewo.panic.non_actor;

import com.oshewo.panic.enums.*;

public class Ingredient {

    private final Ingredients ingredients;
    private IngredientState state;

    public Ingredient(Ingredients ingredients, IngredientState state) {
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

    public boolean notMatches(Ingredient ingredient) {
        return this.state != ingredient.getState() || this.ingredients != ingredient.getItem();
    }

    @Override
    public String toString() {
        return this.ingredients.toString();
    }
}
