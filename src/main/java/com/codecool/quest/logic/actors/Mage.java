package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Mage extends Player{
    public Mage(Cell cell) {
        super(cell);
        setMaxHealth(45);
        setBasePower(20);
        setPower(getBasePower());
        setHealth(getMaxHealth());
    }
}
