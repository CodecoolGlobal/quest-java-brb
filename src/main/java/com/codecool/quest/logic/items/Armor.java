package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public abstract class Armor extends Armory{
    private double resi;

    public double getResi() {
        return resi;
    }

    public void setResi(double resi) {
        this.resi = resi;
    }

    public Armor(Cell cell) {
        super(cell);
    }


}
