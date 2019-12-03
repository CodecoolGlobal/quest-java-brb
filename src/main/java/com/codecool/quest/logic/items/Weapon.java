package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.actors.Player;

public class Weapon extends Item{
    int power = 2;

    public int getPower() {
        return power;
    }

    public Weapon(Cell cell) {
        super(cell);
    }


    public void pickedUp(Player player){
        player.setPower(player.getPower()+this.power);
        player.setTileName("weaponedPlayer");
    }

    @Override
    public String getTileName() {
        return "weapon";
    }
}
