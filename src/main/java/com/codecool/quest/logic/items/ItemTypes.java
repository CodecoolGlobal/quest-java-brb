package com.codecool.quest.logic.items;

import java.util.Random;

public enum ItemTypes {
    HEALTHPOTION(HealthPotion.class),
    HEART(Heart.class),
    AXE(Axe.class);


    private final Class itemName;

    ItemTypes(Class<?> itemName) {
        this.itemName = itemName;
    }

    public Class getItemName() {
        return itemName;
    }

    public static Class getRandomItem(){
        Random random = new Random();
        return values()[random.nextInt(values().length)].getItemName();

    }
}
