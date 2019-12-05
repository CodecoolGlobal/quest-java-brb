package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public class Shield extends Armor {
    public Shield(Cell cell) {
        super(cell);
        setResi(0.2);
        setMaxDurability(4);
        setDurability(getMaxDurability());
    }

    @Override
    public void destroyArmory() {
        getPlayer().setResi(getPlayer().getResi() - this.getResi());
        getPlayer().setShield(null);
    }

    @Override
    public void pickedUp(Player player) {
        this.setPlayer(player);
        player.setResi(player.getResi()+this.getResi());
        player.setShield(this);
    }

    @Override
    public String getTileName() {
        return "shield";
    }
}
