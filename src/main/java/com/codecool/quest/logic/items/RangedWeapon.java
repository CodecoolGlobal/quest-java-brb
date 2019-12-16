package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

import java.lang.reflect.InvocationTargetException;

public abstract class RangedWeapon extends Weapon {
    Class ammo;

    public RangedWeapon(Cell cell,Class ammo) {
        super(cell);
        this.ammo = ammo;
    }

    public Class getAmmo() {
        return ammo;
    }

    public void setAmmo(Class ammo) {
        this.ammo = ammo;
    }

    public void shoot(Cell cell,int dx, int dy){
        try {
            ammo.getConstructor(Cell.class,int.class,int.class).newInstance(cell, dx, dy);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
