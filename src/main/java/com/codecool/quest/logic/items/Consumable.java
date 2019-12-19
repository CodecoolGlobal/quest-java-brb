package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public abstract class Consumable extends Item{
    public Consumable(Cell cell) {
        super(cell);
    }


    public abstract void use(Player player);

    @Override
    public void pickedUp(Player player) {
        player.getInventory().add(this);
    }

}
