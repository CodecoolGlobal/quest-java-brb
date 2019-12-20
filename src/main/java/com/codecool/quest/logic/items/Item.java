package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.actors.Player;

public abstract class Item implements Drawable {
    private Cell cell;
    private int worth;

    public int getWorth() {
        return worth;
    }

    public void setWorth(int worth) {
        this.worth = worth;
    }

    public Item(Cell cell){
        this.cell = cell;
        this.cell.setItem(this);
    }

    public abstract void pickedUp(Player player);
}
