package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public class Helmet extends Armor {

    public Helmet(Cell cell) {
        super(cell, 3, 0.15, Player::getHelmet, Player::setHelmet);
        setWorth(4);

    }

    @Override
    public String getTileName() {
        return "helmet";
    }

}
