package com.oshewo.panic.enums;

public enum Ingredients {
    LETTUCE,
    TOMATO,
    ONION,
    PATTY,
    CHEDDAR,
//    BOTTOM_BUN,
//    TOP_BUN,
    BUN,
    JACKET,
    BEANS,
    PIZZA_BASE,
    PEPERONI;

    public IngredientState getDefaultState() {
        switch (this) {
//            case TOP_BUN:
//            case BOTTOM_BUN:
            case BUN:
            case BEANS:
            case PIZZA_BASE:
                return IngredientState.UNCOOKED;
            case PATTY:
                return IngredientState.UNCUT_UNCOOKED;
            case CHEDDAR:
            case LETTUCE:
            case TOMATO:
            case ONION:
            case PEPERONI:
                return IngredientState.UNCUT;
            case JACKET:
                return IngredientState.UNCOOKED_UNCUT;
            default:
                return null;
        }
    }

    public String getString() {
        switch (this) {
            case BUN:
                return "BUN";
            case JACKET:
                return "JACKET";
            case BEANS:
                return "BEANS";
            case PATTY:
                return "PATTY";
            case ONION:
                return "ONION";
            case TOMATO:
                return "TOMATO";
            case CHEDDAR:
                return "CHEDDAR";
            case LETTUCE:
                return "LETTUCE";
            case PEPERONI:
                return "PEPERONI";
            case PIZZA_BASE:
                return "PIZZA_BASE";
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        switch (this) {
//            case TOP_BUN:
//                return "Top Bun";
//            case BOTTOM_BUN:
//                return "Bottom Bun";
            case BUN:
                return "Bun";
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
            case PIZZA_BASE:
                return "Pizza Base";
            default:
                return null;
        }
    }
}
