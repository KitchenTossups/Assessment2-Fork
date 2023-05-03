package com.oshewo.panic.enums;

import java.util.Random;

public enum PowerUps {          // Initialises base power-ups
    EXTRA_LIFE,
    INCREASE_CHEF_SPEED,
    CLEAR_NEXT_ORDER,
    DECREASE_CHOPPING_TIME,
    DECREASE_COOKING_TIME;

    private static final Random RANDOM = new Random();

    public static PowerUps getRandomPowerUp() {
        PowerUps[] powerUps = values();
        return powerUps[RANDOM.nextInt(powerUps.length)];
    }

    public String toString() {
        switch (this) {
            case EXTRA_LIFE:
                return "EXTRA_LIFE";
            case CLEAR_NEXT_ORDER:
                return "CLEAR_NEXT_ORDER";
            case INCREASE_CHEF_SPEED:
                return "INCREASE_CHEF_SPEED";
            case DECREASE_COOKING_TIME:
                return "DECREASE_COOKING_TIME";
            case DECREASE_CHOPPING_TIME:
                return "DECREASE_CHOPPING_TIME";
            default:
                return null;
        }
    }
}
