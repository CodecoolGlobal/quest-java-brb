package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.actors.Player;

public class HealthPotion extends Item{
    private int healPower = 3;

    public HealthPotion(Cell cell) {
        super(cell);
    }

    public void heal(Actor actor){
        actor.setHealth(actor.getHealth()+this.healPower);
        if(actor.getMaxHealth() < actor.getHealth()) actor.setHealth(actor.getMaxHealth());
    }
    @Override
    public void pickedUp(Player player) {
        player.getInventory().add(this);
    }

    @Override
    public void use(Player player) {
        this.heal(player);
        player.removeFromInventory(this);
    }

    @Override
    public String getTileName() {
        return "healthPotion";
    }
}
