package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public class Shield extends Armor {
    public Shield(Cell cell) {
        super(cell);
        setMaxDurability(4);
        setDurability(getMaxDurability());
        setResi(0.2);
    }


    @Override
    public void destroyArmory() {
        getPlayer().setResi(getPlayer().getResi() - this.getResi());
        getPlayer().setShield(null);
    }

    @Override
    public void pickedUp(Player player) {
        this.setPlayer(player);
        if (player.getShield() != null) player.setResi(player.getResi()+player.getShield().getResi()-this.getResi());
        else player.setResi(player.getResi()-this.getResi());
        player.setShield(this);
    }

    @Override
    public String getTileName() {
        return "shield";
    }
}
