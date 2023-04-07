package com.oshewo.panic.enums;

public enum TiledAssets {

    WALLS("Wall object layer"),
    STOVES("Stove object layer"),
    CHOPPING_BOARD("Chopping object layer"),
    SERVING_STATION("Serving object layer"),
    LETTUCE("Lettuce object layer"),
    TOMATO("Tomato object layer"),
    ONION("Onion object layer"),
    PATTY("Patty object layer"),
    BUNS("Buns object layer"),
    NULL("");
    private final String layerName;

    TiledAssets(String layerName) {
        this.layerName = layerName;
    }

    public static TiledAssets getValueOf(String layerName) {
        for (TiledAssets t : TiledAssets.values())
            if (t.layerName.equals(layerName))
                return t;
        return NULL;
    }
}
