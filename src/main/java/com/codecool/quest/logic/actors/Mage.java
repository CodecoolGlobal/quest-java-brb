package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Fireball;

public class Mage extends Player{
    public Mage(Cell cell) {
        super(cell);
        setMaxHealth(45);
        setBasePower(20);
        setPower(getBasePower());
        setHealth(getMaxHealth());
    }

    @Override
    public void castSpell() {
        new Fireball(getCell(),-1,0,11);
        new Fireball(getCell(),0,-1,11);
        new Fireball(getCell(),1,0,11);
        new Fireball(getCell(),0,1,11);
    }

    @Override
    public String getTileName() {
        return isWeaponed() && isArmored() ? "fullSetMage" : isWeaponed() ? "weaponedMage" : isArmored() ? "armoredMage" : "mage";

    }
}
