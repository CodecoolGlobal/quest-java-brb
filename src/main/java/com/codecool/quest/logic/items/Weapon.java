package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.actors.Player;

public class Weapon extends Armory{
    private int power;

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public Weapon(Cell cell) {
        super(cell);
    }


    public void pickedUp(Player player){
        this.setPlayer(player);
        player.setWeapon(this);
        player.setPower(player.getBasePower()+this.power);
    }

    @Override
    public void destroyArmory() {
        getPlayer().setPower(getPlayer().getPower()-this.getPower());
        getPlayer().setWeapon(null);
    }


    @Override
    public String getTileName() {
        return "weapon";
    }
}
