package com.oshewo.panic.enums;

public enum TiledAssets {       //Sets the individual layers of the .tmx map file and lays them out into usable java layers

    WALLS("Wall object layer"),
    STOVES("Stove object layer"),
    CHOPPING_BOARD("Chopping object layer"),
    SERVING_STATION("Serving object layer"),
    LETTUCE("Lettuce object layer"),
    TOMATO("Tomato object layer"),
    ONION("Onion object layer"),
    PATTY("Patty object layer"),
    BUN("Bun object layer"),
    JACKET("Potato object layer"),
    BEANS("Beans object layer"),
    PIZZA_BASE("Pizza base object layer"),
    PEPERONI("Pepperoni object layer"),
    FOOD_CRATE("Food crate object layer"),
    CHEDDAR("Cheddar object layer"),
    OVEN("Oven object layer"),
    TEXT("Text object layer"),
    BIN("Bin object layer"),
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
