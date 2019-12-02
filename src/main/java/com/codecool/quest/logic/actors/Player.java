package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.items.Item;
import com.codecool.quest.logic.items.Key;
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
        return !this.hasItem(Weapon.class) ? "player" : "weaponedPlayer";
    }

    public void pickUp(){
        this.inventory.add(this.getCell().getItem());
        this.getCell().setItem(null);
    }

    public boolean hasItem(Class<?> type){
        for (Item item : this.inventory) {
            if (type.isInstance(item)) return true;
        }
        return false;
    }
}
