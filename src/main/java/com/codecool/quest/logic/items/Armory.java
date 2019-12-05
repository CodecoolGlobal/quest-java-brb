package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public class Armory extends Item{
    int durability;

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public Armory(Cell cell) {
        super(cell);
    }

    @Override
    public void pickedUp(Player player) {

    }


    @Override
    public String getTileName() {
        return null;
    }
}
