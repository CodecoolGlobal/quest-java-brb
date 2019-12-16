package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Arrow extends Ammo{

    public Arrow(Cell cell, int dx, int dy){
        super(cell,dx,dy);
        setDamage(10);

    }

    @Override
    public String getTileName() {
        return "arrow";
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
