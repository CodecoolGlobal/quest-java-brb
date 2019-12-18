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
        shootFireball(-1,0,11);
        shootFireball(0,1,11);
        shootFireball(0,-1,11);
        shootFireball(1,0,11);
    }

    public void shootFireball(int dx,int dy,int dmg){
        new Fireball(getCell().getNeighbor(dx,dy),dx,dy,dmg);

    }

    @Override
    public String getTileName() {
        return isWeaponed() && isArmored() ? "fullSetMage" : isWeaponed() ? "weaponedMage" : isArmored() ? "armoredMage" : "mage";

    }
}
