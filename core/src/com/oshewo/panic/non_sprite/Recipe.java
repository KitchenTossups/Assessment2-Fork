package com.oshewo.panic.non_sprite;

import com.oshewo.panic.enums.*;
import com.oshewo.panic.sprites.Food;

import java.util.*;

public class Recipe {
    private final Product endProduct;
    private final List<Ingredient> items;

    public Recipe(Product endProduct) {
        this.endProduct = endProduct;
        this.items = this.generateIngredientList();
    }

    public Product getEndProduct() {
        return this.endProduct;
    }

    public List<Ingredient> getIngredientsRaw() {
        return this.items;
    }

    public boolean satisfied(Food food) {
//        if (food.getCurrentIngredients().size() != this.items.size())
//            return false;
//        for (int i = 0; i < this.items.size(); i++)
//            if (this.items.get(i).notMatches(food.getCurrentIngredients().get(i)))
//                return false;
//        return true;
        return false;
    }

    public String getIngredients() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Ingredient ingredient : this.items)
            stringBuilder.append("    ").append(ingredient.getItem().toString()).append("\n");
        return stringBuilder.toString();
    }

    private List<Ingredient> generateIngredientList() {
        List<Ingredient> items = new ArrayList<>();
        switch (this.endProduct) {
            case CHEESEBURGER:
//                items.add(new Ingredient(Item.BOTTOM_BUN, IngredientState.NOT_APPLICABLE));
                items.add(new Ingredient(Item.PATTY, IngredientState.COOKED));
                items.add(new Ingredient(Item.CHEESE, IngredientState.NOT_APPLICABLE));
//                items.add(new Ingredient(Item.TOP_BUN, IngredientState.NOT_APPLICABLE));
                break;
            case DOUBLE_CHEESEBURGER:
//                items.add(new Ingredient(Item.BOTTOM_BUN, IngredientState.NOT_APPLICABLE));
                items.add(new Ingredient(Item.PATTY, IngredientState.COOKED));
                items.add(new Ingredient(Item.CHEESE, IngredientState.NOT_APPLICABLE));
                items.add(new Ingredient(Item.PATTY, IngredientState.COOKED));
                items.add(new Ingredient(Item.CHEESE, IngredientState.NOT_APPLICABLE));
//                items.add(new Ingredient(Item.TOP_BUN, IngredientState.NOT_APPLICABLE));
                break;
            case BURGER:
//                items.add(new Ingredient(Item.BOTTOM_BUN, IngredientState.NOT_APPLICABLE));
                items.add(new Ingredient(Item.PATTY, IngredientState.COOKED));
//                items.add(new Ingredient(Item.TOP_BUN, IngredientState.NOT_APPLICABLE));
                break;
            case SALAD:
                items.add(new Ingredient(Item.LETTUCE, IngredientState.CUT));
                items.add(new Ingredient(Item.TOMATO, IngredientState.CUT));
                items.add(new Ingredient(Item.ONION, IngredientState.CUT));
                break;
            case JACKET_BEANS:
                items.add(new Ingredient(Item.JACKET, IngredientState.COOKED));
                items.add(new Ingredient(Item.BEANS, IngredientState.COOKED));
                break;
            case JACKET_CHEESE:
                items.add(new Ingredient(Item.JACKET, IngredientState.COOKED));
                items.add(new Ingredient(Item.CHEESE, IngredientState.NOT_APPLICABLE));
                break;
            case JACKET_CHEESE_BEANS:
                items.add(new Ingredient(Item.JACKET, IngredientState.COOKED));
                items.add(new Ingredient(Item.BEANS, IngredientState.COOKED));
                items.add(new Ingredient(Item.CHEESE, IngredientState.NOT_APPLICABLE));
                break;
        }
        return items;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "endProduct=" + endProduct.toString() +
                ", ingredients=" + items.toString() +
                '}';
    }
}

