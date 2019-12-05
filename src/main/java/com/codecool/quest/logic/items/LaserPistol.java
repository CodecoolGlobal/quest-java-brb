package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class LaserPistol extends Weapon {
    public LaserPistol(Cell cell) {
        super(cell);
        setPower(999);
    }

    @Override
    public String getTileName() {
        return "laserpistol";
    }

}
