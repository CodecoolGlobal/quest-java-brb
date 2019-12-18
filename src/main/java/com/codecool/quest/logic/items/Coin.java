package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public class Coin extends Item{
    private int worth = 1;

    public Coin(Cell cell) {
        super(cell);
    }

    @Override
    public void pickedUp(Player player) {
        player.addToBank(worth);
    }

    @Override
    public String getTileName() {
        return "coin";
    }
}
