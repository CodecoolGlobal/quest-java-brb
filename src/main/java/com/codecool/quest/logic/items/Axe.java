package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Axe extends Weapon{
    public Axe(Cell cell) {
        super(cell);
        setPower(4);
        setDurability(5);
    }

    @Override
    public String getTileName() {
        return "axe";
    }
}
