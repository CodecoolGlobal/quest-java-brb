package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.actors.Player;

public class Heart extends Item {
    private int healPower = 30;

    public Heart(Cell cell) {
        super(cell);
    }

    public void heal(Actor actor){
        actor.setHealth(actor.getHealth()+this.healPower);
        if(actor.getMaxHealth() < actor.getHealth()) actor.setHealth(actor.getMaxHealth());
    }

    @Override
    public void pickedUp(Player player) {
        this.heal(player);
        player.removeFromInventory(this);
    }

    @Override
    public String getTileName() {
        return "heart";
    }
}
