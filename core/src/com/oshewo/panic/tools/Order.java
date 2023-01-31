package com.oshewo.panic.tools;

import java.util.HashMap;
import java.util.List;

public class Order {
    private String recipeType;
    public HashMap<String, List<String>> recipes;
    private List<String> ingredients;
    private int orderId = -1;
    private static int nextId = 1;

    public Order(String recipeType) {
        this.recipeType = recipeType;
        if(recipeType.toString() == "Burger"){
            this.orderId = 450;
        } else if (recipeType.toString() == "Salad") {
            this.orderId = 60;
        }

    }

    public String getRecipeType() {
        return recipeType;
    }

    public int getOrderId() {
        return orderId;
    }
}

