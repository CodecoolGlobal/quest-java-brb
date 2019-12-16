package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Bow extends RangedWeapon {

    public Bow(Cell cell) {
        super(cell,Arrow.class);
    }

    @Override
    public String getTileName() {
        return "bow";
    }
}
