package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Player;

public abstract class Armory extends Item{
    int durability;
    int maxDurability;
    Player player;
    private int worth;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public Armory(Cell cell) {
        super(cell);
    }

    public abstract void pickedUp(Player player);
    public abstract void destroyArmory();

    @Override
    public String getTileName() {
        return null;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }

    public void loseDurability(){
        this.setDurability(getDurability()-1);
        if(this.getDurability() <= 0) this.destroyArmory();
    }
}
