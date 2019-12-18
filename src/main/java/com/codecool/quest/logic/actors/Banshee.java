package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Banshee extends Enemy {
    public Banshee(Cell cell) {
        super(cell, "slow");
        setHealth(6);
        setBasePower(2);
        setTileName("banshee");
        setBaseTileName(tileName);
    }



        @Override
    public String getTileName() {
        return tileName;
    }
}
