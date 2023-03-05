package com.oshewo.panic.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * The type Order.
 * Generates order and its ID according to recipe
 *
 * @author sl3416, Oshewo
 */
public class Order {
    private final String recipeType;
    public HashMap<String, List<String>> recipes;
    private List<String> ingredients;
    private int orderId = -1;
    private static final int nextId = 1;

    /**
     * Instantiates a new Order.
     *
     * @param recipeType the recipe type
     */
    public Order(String recipeType) {
        this.recipeType = recipeType;
        if (Objects.equals(recipeType, "Burger"))
            this.orderId = 450;
        else if (Objects.equals(recipeType, "Salad"))
            this.orderId = 60;
    }

    /**
     * Gets recipe type.
     *
     * @return the recipe type
     */
    public String getRecipeType() {
        return recipeType;
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public int getOrderId() {
        return orderId;
    }
}

