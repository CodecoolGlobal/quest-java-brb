package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Monster extends Enemy{
    String movementSpeed = "slow";

    public Monster(Cell cell) {
        super(cell, "slow");
        setHealth(35);
        setPower(25);
        setTileName("monster");
    }

    @Override
    public String getTileName() {
        return tileName;
    }
}