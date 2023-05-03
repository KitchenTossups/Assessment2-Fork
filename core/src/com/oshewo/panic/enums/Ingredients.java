package com.oshewo.panic.enums;

public enum Ingredients {
    LETTUCE,
    TOMATO,
    ONION,
    PATTY,
    CHEDDAR,
    BUN,
    JACKET,
    BEANS,
    PIZZA_BASE,
    PEPPERONI;

    public IngredientState getDefaultState() {
        switch (this) {
            case BUN:
            case BEANS:
            case PIZZA_BASE:
            case JACKET:
                return IngredientState.UNCOOKED;
            case PATTY:
                return IngredientState.UNCUT_UNCOOKED;
            case CHEDDAR:
            case LETTUCE:
            case TOMATO:
            case ONION:
            case PEPPERONI:
                return IngredientState.UNCUT;
//                return IngredientState.UNCOOKED_UNCUT;
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
            case PEPPERONI:
                return "PEPPERONI";
            case PIZZA_BASE:
                return "PIZZA_BASE";
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        switch (this) {
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
            case PEPPERONI:
                return "Pepperoni";
            case PIZZA_BASE:
                return "Pizza Base";
            default:
                return null;
        }
    }
}
