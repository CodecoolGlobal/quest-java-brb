package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Sword extends Weapon{
    public Sword(Cell cell) {
        super(cell);
        setPower(2);
        setDurability(10);
    }

    @Override
    public String getTileName() {
        return "sword";
    }
}
