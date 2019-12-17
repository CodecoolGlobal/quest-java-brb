package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Skeleton extends Enemy {
    public Skeleton(Cell cell) {
        super(cell, "fast");
        setHealth(40);
        setPower(10);
        setTileName("skeleton");
    }

    @Override
    public String getTileName() {
        return tileName;
    }
}
