package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Golem extends Enemy{
    public Golem(Cell cell) {
        super(cell, "still");
        setHealth(200);
        setPower(50);
        setTileName("golem");
        setBaseTileName(tileName);
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    @Override
    public void move(int dx, int dy) {
    }
}
