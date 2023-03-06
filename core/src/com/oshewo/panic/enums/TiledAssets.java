package com.oshewo.panic.enums;

public enum TiledAssets {

    WALLS("walls"),
    STOVES("stoves"),
    CHOPPING_BOARD("chopping_board"),
    SERVING_STATION("serving_station"),
    LETTUCE("lettuce"),
    TOMATO("tomato"),
    ONION("onion"),
    PATTY("patty"),
    BUNS("buns");
    private final String layerName;

    TiledAssets(String layerName) {
        this.layerName = layerName;
    }

    public String getLayerName() {
        return layerName;
    }
}