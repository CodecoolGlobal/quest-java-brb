package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

import java.util.Random;

public enum ShopItems {
    HEALTHPOTION("HealthPotion", 3),
    HEART("Heart",2),
    SWORD("Sword",2),
    HELMET("Helmet",2),
    SHIELD("Shield",3),
    AXE("Axe",5),
    BOW("Bow",4),
    CROSSBOW("Crossbow",5);


    private final String itemName;
    private final int worth;


    ShopItems(String itemName, int worth) {
        this.itemName = itemName;
        this.worth = worth;
    }

    public String getItemName() {
        return itemName;
    }

    public int getWorth() {
        return worth;
    }

    public static String getRandomShopItem(){
        Random random = new Random();
        int rndnum = random.nextInt(values().length);
        return values()[rndnum].getWorth() +" Gold"+"   "+values()[rndnum].getItemName();

    }
}