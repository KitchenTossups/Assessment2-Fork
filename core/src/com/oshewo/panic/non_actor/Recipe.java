package com.oshewo.panic.non_actor;

import com.oshewo.panic.enums.*;
import com.oshewo.panic.actor.FoodActor;

import java.util.*;

public class Recipe {
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
//        System.out.println(10);
        if (list.size() < this.foods.size())
            return false;

//        System.out.println(20);

        for (Food food : this.foods) {
//            System.out.println(30);
            boolean found = false;
            for (FoodActor foodActor : new ArrayList<>(list)) {
//                System.out.println(40);
                Food food1 = foodActor.getFood();
//                System.out.println(food1);
//                System.out.println(food);
                if (food.toString().equals(food1.toString())) {
//                    System.out.println(50);
                    list.remove(foodActor);
                    foodActor.remove();
                    found = true;
                    break;
                }
            }
//            System.out.println(60);
            if (!found)
                return false;
//            System.out.println(70);
        }
//        System.out.println(80);
        return true;
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
            case PIZZA_PEPPERONI:
                foods.add(new Food(Ingredients.PIZZA_BASE, IngredientState.COOKED));
                foods.add(new Food(Ingredients.CHEDDAR, IngredientState.PREPARED));
                foods.add(new Food(Ingredients.PEPPERONI, IngredientState.CUT));
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

