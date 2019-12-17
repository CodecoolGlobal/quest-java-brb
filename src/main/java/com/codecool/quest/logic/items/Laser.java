package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Laser extends Ammo{

    public Laser(Cell cell, int dx, int dy, int damage) {
        super(cell, dx, dy, damage);
    }

    @Override
    public String getTileName() {
        return "laser";
    }
}
