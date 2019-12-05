package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.items.*;
import java.util.List;

public class Player extends Actor {

    private Armory weapon;
    private Armory helmet;
    private Armory shield;
    private Armory boots;
    private Armory gloves;
    private Armory plate;

    public boolean isArmored() {
        return getShield() != null || getBoots() != null || getGloves() != null || getPlate() != null || getHelmet() != null;
    }

    public boolean isWeaponed() {
        return getWeapon() != null;
    }

    public void damageEquipment(){
        if(getHelmet() != null) getHelmet().loseDurability();
        if(getShield() != null) getShield().loseDurability();
    }

    public Armory getWeapon() {
        return weapon;
    }

    public void setWeapon(Armory weapon) {
        this.weapon = weapon;
    }

    public Armory getHelmet() {
        return helmet;
    }

    public void setHelmet(Armory helmet) {
        this.helmet = helmet;
    }

    public Armory getShield() {
        return shield;
    }

    public void setShield(Armory shield) {
        this.shield = shield;
    }

    public Armory getBoots() {
        return boots;
    }

    public void setBoots(Armory boots) {
        this.boots = boots;
    }

    public Armory getGloves() {
        return gloves;
    }

    public void setGloves(Armory gloves) {
        this.gloves = gloves;
    }

    public Armory getPlate() {
        return plate;
    }

    public void setPlate(Armory plate) {
        this.plate = plate;
    }

    public List<Consumable> getInventory() {
        return inventory;
    }

    public Consumable inventoryGetItem(Class<?> type){
        for(Consumable item : getInventory()){
            if(item.getClass().equals(type)) return item;
        }
        return null;
    }

    public Player(Cell cell) {
        super(cell);
        setMaxHealth(75);
        setBasePower(10);
        setPower(getBasePower());
        setHealth(getMaxHealth());
    }

    public String getTileName() {
        return isWeaponed() && isArmored() ? "fullSetPlayer" : isWeaponed() ? "weaponedPlayer" : isArmored() ? "armoredPlayer" : "player";
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
