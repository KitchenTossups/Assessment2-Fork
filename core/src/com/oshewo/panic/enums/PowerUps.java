package com.oshewo.panic.enums;

import java.util.Random;

public enum PowerUps {
    EXTRA_LIFE,
    INCREASE_CHEF_SPEED,
    CLEAR_NEXT_ORDER;

    private static final Random RANDOM = new Random();

    public static PowerUps getRandomPowerUp() {
        PowerUps[] powerUps = values();
        return powerUps[RANDOM.nextInt(powerUps.length)];
    }
}
