package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.ItemTypes;

import java.lang.reflect.InvocationTargetException;

public abstract class Enemy extends Actor{
    public Enemy(Cell cell) {
        super(cell);
    }

    public void dropLoot() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class randomItem = ItemTypes.getRandomItem();
        randomItem.getConstructor(Cell.class).newInstance(this.getCell());
    }


}
