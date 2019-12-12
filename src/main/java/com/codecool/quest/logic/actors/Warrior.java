package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Warrior extends Player{
    public Warrior(Cell cell) {
        super(cell);
        setMaxHealth(75);
        setBasePower(10);
        setPower(getBasePower());
        setHealth(getMaxHealth());
    }


}
