package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Spider extends Enemy {
    int CHANCETOAPPLYVENOM = 20;
    public Spider(Cell cell) {
        super(cell, "fast");
        setHealth(35);
        setPower(25);
        setTileName("spider");
        setBaseTileName(tileName);
    }

    public void chanceToApplyVenom() {

    }

    @Override
    public String getTileName() {
        return tileName;
    }
}
