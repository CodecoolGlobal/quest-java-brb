package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public class Key extends Consumable{

    public Key(Cell cell) {
        super(cell);
    }


    @Override
    public void use(Player player) {
        player.getCell().checkDoors();
    }

    @Override
    public String getTileName() {
        return "key";
    }
}