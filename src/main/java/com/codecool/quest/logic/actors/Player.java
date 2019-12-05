package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.Item;
import java.util.List;

public class Player extends Actor {
    public List<Item> getInventory() {
        return inventory;
    }

    public Item inventoryGetItem(Class<?> type){
        for(Item item : getInventory()){
            if(item.getClass().equals(type)) return item;
        }
        return null;
    }

    public Player(Cell cell) {
        super(cell);
        setHealth(10);
        setPower(3);
        setMaxHealth(15);
        setBasePower(2);
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
        if(type == null) return false;
        for (Item item : this.inventory) {
            if (type.isInstance(item)) return true;
        }
        return false;
    }

    public void removeFromInventory(Item item){
        this.inventory.removeIf(deleteItem -> deleteItem.hashCode() == item.hashCode());
    }

    public void useItem(Class<?> type){
        if(this.hasItem(type)){
            inventoryGetItem(type).use(this);
        }
    }

    public boolean isStairs() {
        return this.getCell().getTileName().equals("stairs");
    }

}
