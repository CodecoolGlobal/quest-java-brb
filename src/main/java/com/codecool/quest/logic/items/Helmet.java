package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public class Helmet extends Armor {
    public Helmet(Cell cell) {
        super(cell);
        setMaxDurability(3);
        setDurability(getMaxDurability());
        setResi(0.15);
    }

    @Override
    public void destroyArmory() {
        getPlayer().setResi(getPlayer().getResi()-this.getResi());
        getPlayer().setHelmet(null);

    }

    @Override
    public void pickedUp(Player player) {
        this.setPlayer(player);
        if(player.getHelmet() != null) player.setResi(player.getResi()-player.getHelmet().getResi()+this.getResi());
        else player.setResi(player.getResi()+this.getResi());
        player.setHelmet(this);
    }


    @Override
    public String getTileName() {
        return "helmet";
    }

}
