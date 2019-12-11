package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class Armor extends Armory{
    private double resi;

    public double getResi() {
        return resi;
    }

    public void setResi(double resi) {
        this.resi = resi;
    }

    private final Function<Player, Armor> getArmor;
    private final BiConsumer<Player, Armor> setArmor;


    public void pickedUp(Player player){
        this.setPlayer(player);
        if (getArmor.apply(player) != null) player.setResi(player.getResi()+getArmor.apply(player).getResi()-this.getResi());
        else player.setResi(player.getResi()-this.getResi());
        setArmor.accept(player,this);
    }

    public void destroyArmory() {
        getPlayer().setResi(getPlayer().getResi() - this.getResi());
        setArmor.accept(this.getPlayer(),null);
    }

    public Armor(Cell cell, int maxDurability, double resi, Function<Player, Armor> getArmor, BiConsumer<Player, Armor> setArmor) {
        super(cell);
        this.getArmor = getArmor;
        this.setArmor = setArmor;
        setMaxDurability(maxDurability);
        setDurability(getMaxDurability());
        setResi(resi);
    }
}
