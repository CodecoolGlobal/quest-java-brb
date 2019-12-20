package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Fireball;

import java.time.Duration;
import java.time.Instant;

public class Mage extends Player{


    public Mage(Cell cell) {
        super(cell);
        setMaxHealth(50);
        setBasePower(20);
        setPower(getBasePower());
        setHealth(getMaxHealth());
        setSpellCooldown(15000);
    }

    @Override
    public void castSpell() {
        if(Duration.between(getSpellLastUsed(), Instant.now()).toMillis() >= getSpellCooldown()){
            shootFireball(-1,0,11);
            shootFireball(0,1,11);
            shootFireball(0,-1,11);
            shootFireball(1,0,11);
            setSpellLastUsed(Instant.now());
        }
    }

    public void shootFireball(int dx,int dy,int dmg){
        if(!getCell().getNeighbor(dx,dy).getTileName().equals("wall")) new Fireball(getCell().getNeighbor(dx,dy),dx,dy,dmg);

    }

    @Override
    public String getTileName() {
        return isWeaponed() && isArmored() ? "fullSetMage" : isWeaponed() ? "weaponedMage" : isArmored() ? "armoredMage" : "mage";

    }
}
