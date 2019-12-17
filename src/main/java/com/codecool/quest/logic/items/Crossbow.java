package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Crossbow extends RangedWeapon {

    public Crossbow(Cell cell) {
        super(cell, Arrow.class);
        setPower(5);
        setMaxDurability(5);
        setDurability(getMaxDurability());
    }

    @Override
    public String getTileName() {
        return "crossbow";
    }
}
