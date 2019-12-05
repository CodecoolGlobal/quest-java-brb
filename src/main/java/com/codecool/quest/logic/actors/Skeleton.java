package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Skeleton extends Enemy {
    public Skeleton(Cell cell) {
        super(cell);
        setHealth(50);
        setPower(20);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
