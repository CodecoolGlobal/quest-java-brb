package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Skeleton extends Enemy {
    public Skeleton(Cell cell) {
        super(cell);
        setHealth(8);
        setPower(2);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
