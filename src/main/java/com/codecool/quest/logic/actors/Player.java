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

    private String tileName;

    public Player(Cell cell) {
        super(cell);
        this.tileName = "player";
    }

    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {
        this.tileName = tileName;
    }

    public void pickUp(){
        if(this.getCell().getItem() != null){
            this.getCell().getItem().pickedUp(this);
            this.getCell().setItem(null);

        }
    }

    public boolean hasItem(Class<?> type){
        for (Item item : this.inventory) {
            if (type.isInstance(item)) return true;
        }
        return false;
    }


    public void useKey(){
        this.getCell().checkDoors();
    }

    public void useItem(Class<?> type){
        if(this.hasItem(type)){
            if (Key.class.equals(type)) {
                useKey();
            }

        }

    }
}
