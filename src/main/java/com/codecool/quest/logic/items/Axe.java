package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Axe extends Weapon{
    public Axe(Cell cell) {
        super(cell);
        setPower(20);
        setMaxDurability(3);
        setDurability(getMaxDurability());
        setWorth(5);
    }


    @Override
    public String getTileName() {
        return "axe";
    }
}
