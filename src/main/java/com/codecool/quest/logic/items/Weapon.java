package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.actors.Player;

public class Weapon extends Item{
    int power;

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
        player.setPower(player.getBasePower()+this.power);
        player.setTileName("weaponedPlayer");
    }

    @Override
    public void use(Player player) {

    }

    @Override
    public String getTileName() {
        return "weapon";
    }
}
