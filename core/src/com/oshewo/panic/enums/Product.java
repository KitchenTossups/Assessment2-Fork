package com.oshewo.panic.enums;

import java.util.Random;

public enum Product {
    BURGER,
    CHEESEBURGER,
    DOUBLE_CHEESEBURGER,
    SALAD,
    JACKET_CHEESE,
    JACKET_BEANS,
    JACKET_CHEESE_BEANS,
    PIZZA_MARGARITA,
    PIZZA_PEPERONI;

    private static final Random RANDOM = new Random();

    public static Product getRandomProduct() {
        Product[] directions = values();
        return directions[RANDOM.nextInt(directions.length)];
    }

    @Override
    public String toString() {
        switch (this) {
            case BURGER:
                return "Burger";
            case CHEESEBURGER:
                return "Cheeseburger";
            case DOUBLE_CHEESEBURGER:
                return "Double Cheeseburger";
            case SALAD:
                return "Salad";
            case JACKET_CHEESE:
                return "Cheese Jacket Potato";
            case JACKET_BEANS:
                return "Beans Jacket Potato";
            case JACKET_CHEESE_BEANS:
                return "Cheese and Beans Jacket Potato";
            case PIZZA_MARGARITA:
                return "Margarita Pizza";
            case PIZZA_PEPERONI:
                return "Peperoni Pizza";
            default:
                return null;
        }
    }
}
