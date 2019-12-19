package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.actors.Player;

public class Heart extends Item implements HealingItem{
    private int healPower = 30;


    public Heart(Cell cell) {
        super(cell);
        setWorth(3);
    }

    @Override
    public void pickedUp(Player player) {
        this.heal(player,this.healPower);
        player.removeFromInventory(this);
    }

    @Override
    public String getTileName() {
        return "heart";
    }
}
