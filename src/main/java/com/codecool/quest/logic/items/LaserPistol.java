package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class LaserPistol extends RangedWeapon {
    public LaserPistol(Cell cell) {
        super(cell,Laser.class);
        setPower(6);
        setMaxDurability(5);
        setDurability(getMaxDurability());
    }

    @Override
    public String getTileName() {
        return "laserpistol";
    }

}
