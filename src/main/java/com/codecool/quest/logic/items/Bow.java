package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Bow extends RangedWeapon {
    public Bow(Cell cell) {
        super(cell,Arrow.class);
        setPower(4);
        setMaxDurability(4);
        setDurability(getMaxDurability());
        setWorth(4);
    }

    @Override
    public String getTileName() {
        return "bow";
    }
}
