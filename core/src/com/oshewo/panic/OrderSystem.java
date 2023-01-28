package com.oshewo.panic;

import com.badlogic.gdx.math.RandomXS128;

import java.util.*;

import static com.badlogic.gdx.math.MathUtils.random;

public class OrderSystem {
    private RandomXS128 random;
    public static HashMap<String, List<String>> recipes;
    public Queue<Object> orders = new LinkedList<>();
    private final int orderId;

    public OrderSystem() {
        random = new RandomXS128();
        recipes = new HashMap<String, List<String>>();

        // burger
        recipes.put("Burger", Arrays.asList("bun", "beef", "bun"));

        // salad
        recipes.put("Salad", Arrays.asList("lettuce", "tomato", "onion"));
        orderId = 1;
    }

    public static HashMap<String, List<String>> getRecipes() {
        return recipes;
    }

    public Order generateOrder() {
        Set<String> recipeTypes = recipes.keySet();
        int recipeSize = recipeTypes.size();
        int randomIndex = new Random().nextInt(recipeSize);
        String recipeType = (String)recipeTypes.toArray()[randomIndex];
        return new Order(recipeType);
}
}
