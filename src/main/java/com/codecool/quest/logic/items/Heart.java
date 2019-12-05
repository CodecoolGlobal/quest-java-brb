package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public class Heart extends Item {
    public Heart(Cell cell) {
        super(cell);
    }

    @Override
    public void pickedUp(Player player) {
        player.setHealth(player.getHealth() + 10);
    }

    @Override
    public String getTileName() {
        return "heart";
    }
}
