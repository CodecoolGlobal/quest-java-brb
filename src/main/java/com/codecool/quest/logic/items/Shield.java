package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public class Shield extends Armor {

    public Shield(Cell cell) {
        super(cell, 4, 0.2, Player::getShield, Player::setShield);
    }

    @Override
    public String getTileName() {
        return "shield";
    }
}
