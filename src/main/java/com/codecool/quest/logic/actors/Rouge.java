package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Rouge extends Player{
    public Rouge(Cell cell) {
        super(cell);
        setMaxHealth(50);
        setBasePower(17);
        setPower(getBasePower());
        setHealth(getMaxHealth());
    }

    @Override
    public void castSpell() {

    }

    @Override
    public String getTileName() {
        return "rouge";
    }
}
