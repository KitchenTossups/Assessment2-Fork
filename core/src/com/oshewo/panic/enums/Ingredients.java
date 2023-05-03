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
    PEPERONI;

    public IngredientState getDefaultState() {
        switch (this) {
            case TOP_BUN:
            case BOTTOM_BUN:
            case BEANS:
            case PIZZA_BASE:
                return IngredientState.UNCOOKED;
            case PATTY:
                return IngredientState.UNCUT_UNCOOKED;
            case CHEDDAR:
                return IngredientState.NOT_APPLICABLE;
            case LETTUCE:
            case TOMATO:
            case ONION:
            case PEPERONI:
                return IngredientState.UNCUT;
            case JACKET:
//                return IngredientState.UNCOOKED_UNCUT;
            default:
                return null;
        }
    }

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
            case PIZZA_BASE:
                return "Pizza Base";
            default:
                return null;
        }
    }
}
