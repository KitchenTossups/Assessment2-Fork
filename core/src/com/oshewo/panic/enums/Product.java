package com.oshewo.panic.enums;

import java.util.Random;

public enum Product {
    BURGER,
    CHEESEBURGER,
    DOUBLE_CHEESEBURGER,
    SALAD,
    JACKET_PLAIN,
    JACKET_CHEESE,
    JACKET_BEANS,
    JACKET_CHEESE_BEANS,
    PIZZA_MARGARITA,
    PIZZA_PEPPERONI;

    private static final Random RANDOM = new Random();

    public static Product getRandomProduct() {
        Product[] directions = values();
        return directions[RANDOM.nextInt(directions.length)];
    }

    public String getString() {
        switch (this) {
            case BURGER:
                return "BURGER";
            case CHEESEBURGER:
                return "CHEESEBURGER";
            case DOUBLE_CHEESEBURGER:
                return "DOUBLE_CHEESEBURGER";
            case SALAD:
                return "SALAD";
            case JACKET_PLAIN:
                return "JACKET_PLAIN";
            case JACKET_CHEESE:
                return "JACKET_CHEESE";
            case JACKET_BEANS:
                return "JACKET_BEANS";
            case JACKET_CHEESE_BEANS:
                return "JACKET_CHEESE_BEANS";
            case PIZZA_MARGARITA:
                return "PIZZA_MARGARITA";
            case PIZZA_PEPPERONI:
                return "PIZZA_PEPPERONI";
            default:
                return null;
        }
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
            case JACKET_PLAIN:
                return "Plain Jacket Potato";
            case JACKET_CHEESE:
                return "Cheese Jacket Potato";
            case JACKET_BEANS:
                return "Beans Jacket Potato";
            case JACKET_CHEESE_BEANS:
                return "Cheese and Beans Jacket Potato";
            case PIZZA_MARGARITA:
                return "Margarita Pizza";
            case PIZZA_PEPPERONI:
                return "Pepperoni Pizza";
            default:
                return null;
        }
    }
}
