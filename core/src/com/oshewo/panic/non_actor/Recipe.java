package com.oshewo.panic.non_actor;

import com.oshewo.panic.enums.*;
import com.oshewo.panic.actor.FoodActor;

import java.util.*;

public class Recipe {       // Constructs recipes based on fully prepared ingredients.
    private final Product endProduct;
    private final List<Food> foods;

    public Recipe(Product endProduct) {
        this.endProduct = endProduct;
        this.foods = this.generateIngredientList();
    }

    public Product getEndProduct() {
        return this.endProduct;
    }

    public List<Food> getIngredientsRaw() {
        return this.foods;
    }

    public boolean satisfied(List<FoodActor> list) {
        if (list.size() < this.foods.size())
            return false;
//        List<Food> foundFoodList = new ArrayList<>();
//        for (FoodActor foodActor : new ArrayList<>(list)) {
//            Food food = foodActor.getFood();
//            if (this.foods.contains(food)) {
//                list.remove(foodActor);
//                foundFoodList.add(foodActor.getFood());
//            }
//        }

        for (Food food : this.foods) {
            boolean found = false;
            for (FoodActor foodActor : new ArrayList<>(list)) {
                Food food1 = foodActor.getFood();
                if (food.equals(food1)) {
                    list.remove(foodActor);
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }
        return true;




//        if (food.getCurrentIngredients().size() != this.foods.size())
//            return false;
//        for (int i = 0; i < this.foods.size(); i++)
//            if (this.foods.get(i).notMatches(food.getCurrentIngredients().get(i)))
//                return false;
//        return true;
//        return false;
    }

    public String getIngredients() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Food food : this.foods)
            stringBuilder.append("    ").append(food.getItem().toString()).append("\n");
        return stringBuilder.toString();
    }

    private List<Food> generateIngredientList() {
        List<Food> foods = new ArrayList<>();
        switch (this.endProduct) {
            case CHEESEBURGER:
                foods.add(new Food(Ingredients.BUN, IngredientState.COOKED));
                foods.add(new Food(Ingredients.PATTY, IngredientState.COOKED));
                foods.add(new Food(Ingredients.CHEDDAR, IngredientState.CUT));
//                foods.add(new Food(Ingredients.TOP_BUN, IngredientState.COOKED));
                break;
            case DOUBLE_CHEESEBURGER:
                foods.add(new Food(Ingredients.BUN, IngredientState.COOKED));
                foods.add(new Food(Ingredients.PATTY, IngredientState.COOKED));
                foods.add(new Food(Ingredients.CHEDDAR, IngredientState.CUT));
                foods.add(new Food(Ingredients.PATTY, IngredientState.COOKED));
                foods.add(new Food(Ingredients.CHEDDAR, IngredientState.CUT));
//                foods.add(new Food(Ingredients.TOP_BUN, IngredientState.COOKED));
                break;
            case BURGER:
                foods.add(new Food(Ingredients.BUN, IngredientState.COOKED));
                foods.add(new Food(Ingredients.PATTY, IngredientState.COOKED));
//                foods.add(new Food(Ingredients.TOP_BUN, IngredientState.COOKED));
                break;
            case SALAD:
                foods.add(new Food(Ingredients.LETTUCE, IngredientState.CUT));
                foods.add(new Food(Ingredients.TOMATO, IngredientState.CUT));
                foods.add(new Food(Ingredients.ONION, IngredientState.CUT));
                break;
            case JACKET_PLAIN:
                foods.add(new Food(Ingredients.JACKET, IngredientState.COOKED));
                break;
            case JACKET_BEANS:
                foods.add(new Food(Ingredients.JACKET, IngredientState.COOKED));
                foods.add(new Food(Ingredients.BEANS, IngredientState.COOKED));
                break;
            case JACKET_CHEESE:
                foods.add(new Food(Ingredients.JACKET, IngredientState.COOKED));
                foods.add(new Food(Ingredients.CHEDDAR, IngredientState.CUT));
                break;
            case JACKET_CHEESE_BEANS:
                foods.add(new Food(Ingredients.JACKET, IngredientState.COOKED));
                foods.add(new Food(Ingredients.BEANS, IngredientState.COOKED));
                foods.add(new Food(Ingredients.CHEDDAR, IngredientState.CUT));
                break;
            case PIZZA_MARGARITA:
                foods.add(new Food(Ingredients.PIZZA_BASE, IngredientState.COOKED));
                foods.add(new Food(Ingredients.CHEDDAR, IngredientState.CUT));
                break;
            case PIZZA_PEPERONI:
                foods.add(new Food(Ingredients.PIZZA_BASE, IngredientState.COOKED));
                foods.add(new Food(Ingredients.CHEDDAR, IngredientState.PREPARED));
                foods.add(new Food(Ingredients.PEPERONI, IngredientState.CUT));
                break;
        }
        return foods;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "endProduct=" + this.endProduct.toString() +
                ", ingredients=" + this.foods.toString() +
                '}';
    }
}

