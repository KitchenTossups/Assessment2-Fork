package com.oshewo.panic.tools;

import java.util.HashMap;
import java.util.List;

public class Order {
    private String recipeType;
    public HashMap<String, List<String>> recipes;
    private List<String> ingredients;
    private int orderId;
    private static int nextId = 1;

    public Order(String recipeType) {
        this.recipeType = recipeType;
        this.orderId = nextId++;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public int getOrderId() {
        return orderId;
    }
}

