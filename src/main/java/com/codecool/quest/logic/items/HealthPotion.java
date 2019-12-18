package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public class HealthPotion extends Consumable implements HealingItem{
    private int healPower = 25;

    public HealthPotion(Cell cell) {
        super(cell);
    }



    @Override
    public void use(Player player) {
        this.heal(player,this.healPower);
        player.removeFromInventory(this);
    }

    @Override
    public String getTileName() {
        return "healthPotion";
    }
}
