package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public class Shovel extends Consumable {
    public Shovel(Cell cell) {
        super(cell);
    }

    @Override
    public void use(Player player) {
        if(player.getCell().getTileName().equals("diggable")) {
            player.removeFromInventory(this);
        }
    }

    @Override
    public String getTileName() {
        return "shovel";
    }
}
