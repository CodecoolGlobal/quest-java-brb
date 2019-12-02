package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Weapon;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {

    public List<Item> getInventory() {
        return inventory;
    }

    private List<Item> inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return !this.hasWeapon() ? "player" : "weaponedPlayer";
    }

    public void pickUp(){
        this.inventory.add(this.getCell().getItem());
        this.getCell().setItem(null);
    }

    public boolean hasWeapon(){
        for (Item item : this.inventory){
            if(item instanceof Weapon) return true;
        }
        return false;
    }
}
