package com.oshewo.panic.non_actor;

import com.oshewo.panic.enums.*;
import com.oshewo.panic.actor.Food;

import java.util.*;

public class Recipe {
    private final Product endProduct;
    private final List<Ingredient> ingredients;

    public Recipe(Product endProduct) {
        this.endProduct = endProduct;
        this.ingredients = this.generateIngredientList();
    }

    public Product getEndProduct() {
        return this.endProduct;
    }

    public List<Ingredient> getIngredientsRaw() {
        return this.ingredients;
    }

    public boolean satisfied(Food food) { // To be coming soon
//        if (food.getCurrentIngredients().size() != this.ingredients.size())
//            return false;
//        for (int i = 0; i < this.ingredients.size(); i++)
//            if (this.ingredients.get(i).notMatches(food.getCurrentIngredients().get(i)))
//                return false;
//        return true;
        return false;
    }

    public String getIngredients() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Ingredient ingredient : this.ingredients)
            stringBuilder.append("    ").append(ingredient.getItem().toString()).append("\n");
        return stringBuilder.toString();
    }

    private List<Ingredient> generateIngredientList() {
        List<Ingredient> ingredients = new ArrayList<>();
        switch (this.endProduct) {
            case CHEESEBURGER:
                ingredients.add(new Ingredient(Ingredients.BOTTOM_BUN, IngredientState.NOT_APPLICABLE));
                ingredients.add(new Ingredient(Ingredients.PATTY, IngredientState.COOKED));
                ingredients.add(new Ingredient(Ingredients.CHEDDAR, IngredientState.NOT_APPLICABLE));
                ingredients.add(new Ingredient(Ingredients.TOP_BUN, IngredientState.NOT_APPLICABLE));
                break;
            case DOUBLE_CHEESEBURGER:
                ingredients.add(new Ingredient(Ingredients.BOTTOM_BUN, IngredientState.NOT_APPLICABLE));
                ingredients.add(new Ingredient(Ingredients.PATTY, IngredientState.COOKED));
                ingredients.add(new Ingredient(Ingredients.CHEDDAR, IngredientState.NOT_APPLICABLE));
                ingredients.add(new Ingredient(Ingredients.PATTY, IngredientState.COOKED));
                ingredients.add(new Ingredient(Ingredients.CHEDDAR, IngredientState.NOT_APPLICABLE));
                ingredients.add(new Ingredient(Ingredients.TOP_BUN, IngredientState.NOT_APPLICABLE));
                break;
            case BURGER:
                ingredients.add(new Ingredient(Ingredients.BOTTOM_BUN, IngredientState.NOT_APPLICABLE));
                ingredients.add(new Ingredient(Ingredients.PATTY, IngredientState.COOKED));
                ingredients.add(new Ingredient(Ingredients.TOP_BUN, IngredientState.NOT_APPLICABLE));
                break;
            case SALAD:
                ingredients.add(new Ingredient(Ingredients.LETTUCE, IngredientState.CUT));
                ingredients.add(new Ingredient(Ingredients.TOMATO, IngredientState.CUT));
                ingredients.add(new Ingredient(Ingredients.ONION, IngredientState.CUT));
                break;
            case JACKET_PLAIN:
                ingredients.add(new Ingredient(Ingredients.JACKET, IngredientState.COOKED));
                break;
            case JACKET_BEANS:
                ingredients.add(new Ingredient(Ingredients.JACKET, IngredientState.COOKED));
                ingredients.add(new Ingredient(Ingredients.BEANS, IngredientState.COOKED));
                break;
            case JACKET_CHEESE:
                ingredients.add(new Ingredient(Ingredients.JACKET, IngredientState.COOKED));
                ingredients.add(new Ingredient(Ingredients.CHEDDAR, IngredientState.NOT_APPLICABLE));
                break;
            case JACKET_CHEESE_BEANS:
                ingredients.add(new Ingredient(Ingredients.JACKET, IngredientState.COOKED));
                ingredients.add(new Ingredient(Ingredients.BEANS, IngredientState.COOKED));
                ingredients.add(new Ingredient(Ingredients.CHEDDAR, IngredientState.NOT_APPLICABLE));
                break;
            case PIZZA_MARGARITA:
                ingredients.add(new Ingredient(Ingredients.PIZZA_BASE, IngredientState.COOKED));
                ingredients.add(new Ingredient(Ingredients.MOZZARELLA, IngredientState.PREPARED));
                break;
            case PIZZA_PEPERONI:
                ingredients.add(new Ingredient(Ingredients.PIZZA_BASE, IngredientState.COOKED));
                ingredients.add(new Ingredient(Ingredients.MOZZARELLA, IngredientState.PREPARED));
                ingredients.add(new Ingredient(Ingredients.PEPERONI, IngredientState.CUT));
                break;
        }
        return ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "endProduct=" + endProduct.toString() +
                ", ingredients=" + ingredients.toString() +
                '}';
    }
}

