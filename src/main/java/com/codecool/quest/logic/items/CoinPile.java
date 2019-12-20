package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public class CoinPile extends Item {
    private int worth = 5;
    public CoinPile(Cell cell) {
        super(cell);
    }

    @Override
    public void pickedUp(Player player) {
        player.addToBank(worth);
    }

    @Override
    public String getTileName() {
        return "coinPile";
    }
}
