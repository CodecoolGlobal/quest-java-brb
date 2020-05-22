package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.ItemTypes;

import java.lang.reflect.InvocationTargetException;

public abstract class Enemy extends Actor{
    String movementSpeed;

    public String getMovementSpeed() {
        return movementSpeed;
    }

    public Enemy(Cell cell, String movementSpeed) {
        super(cell);
        this.movementSpeed = movementSpeed;
    }

    public void dropLoot(){
        try {
            ItemTypes.getRandomItem().getConstructor(Cell.class).newInstance(this.getCell());
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            System.err.println("Couldnt create loot: "+e.getMessage());
        }
    }

    @Override
    public String getTileName() {
        return tileName;
    }
}
