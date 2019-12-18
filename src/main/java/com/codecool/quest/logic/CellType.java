package com.codecool.quest.logic;

public enum CellType {
    GREENERY("greenery"),
    FLOOR("floor"),
    CLOSEDDOOR("closeddoor"),
    OPENDOOR("opendoor"),
    TORCH("torch"),
    STAIRS("stairs"),
    WATEREDGE("wateredge"),
    OBJECTIVE("objective"),
    WALL("wall"),
    SHOP("shop"),
    PLACEHOLDER("placeholder"),
    DIGGABLE("diggable");


    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
