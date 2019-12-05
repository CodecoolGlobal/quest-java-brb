package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Monster extends Enemy{
    public Monster(Cell cell) {
        super(cell);
        setHealth(6);
        setPower(5);
    }

    @Override
    public String getTileName() {
        return "monster";
    }
}