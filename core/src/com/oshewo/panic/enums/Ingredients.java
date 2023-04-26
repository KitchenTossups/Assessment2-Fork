package com.oshewo.panic.enums;

public enum Ingredients {
    LETTUCE,
    TOMATO,
    ONION,
    PATTY,
    CHEDDAR,
    BOTTOM_BUN,
    TOP_BUN,
    BUN,
    JACKET,
    BEANS,
    PIZZA_BASE,
    MOZZARELLA,
    PEPERONI;

    @Override
    public String toString() {
        switch (this) {
            case TOP_BUN:
                return "Top Bun";
            case BOTTOM_BUN:
                return "Bottom Bun";
            case PATTY:
                return "Patty";
            case CHEDDAR:
                return "Cheddar";
            case LETTUCE:
                return "Lettuce";
            case TOMATO:
                return "Tomato";
            case ONION:
                return "Onion";
            case BEANS:
                return "Beans";
            case JACKET:
                return "Jacket";
            case PEPERONI:
                return "Peperoni";
            case MOZZARELLA:
                return "Mozzarella";
            case PIZZA_BASE:
                return "Pizza Base";
            default:
                return null;
        }
    }
}
