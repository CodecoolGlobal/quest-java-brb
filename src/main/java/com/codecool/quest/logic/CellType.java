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
    DIGGABLE("diggable"),
    CENTERWATER("centerwater"),
    TOPLEFTWATER("topleftwater"),
    TOPRIGHTWATER("toprightwater"),
    BOTTOMLEFTWATER("bottomleftwater"),
    BOTTOMRIGHTWATER("bottomrightwater"),
    TOPWATEREDGE("topwateredge"),
    LEFTWATEREDGE("leftwateredge"),
    RIGHTWATEREDGE("rightwateredge"),
    BOTTOMWATEREDGE("bottomwateredge"),
    DARKNESS("darkness"),
    CENTERCAVE("centercave"),
    EXITCAVE("exitcave"),
    TOPLEFTCAVE("topleftcave"),
    TOPRIGHTCAVE("toprightcave"),
    BOTTOMLEFTCAVE("bottomleftcave"),
    BOTTOMRIGHTCAVE("bottomrightcave"),
    TOPEDGECAVE("topedgecave"),
    LEFTEDGECAVE("leftedgecave"),
    RIGHTEDGECAVE("rightedgecave"),
    BOTTOMEDGECAVE("bottomedgecave");


    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
