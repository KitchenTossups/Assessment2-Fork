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
    BUNS("Buns object layer");
    private final String layerName;

    TiledAssets(String layerName) {
        this.layerName = layerName;
    }

    public String getLayerName() {
        return layerName;
    }
}
