package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.items.Consumable;
import com.codecool.quest.logic.items.Item;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Actor implements Drawable {
    protected List<Consumable> inventory = new ArrayList<>();
    protected String tileName;
    public void setCell(Cell cell) {
        this.cell = cell;
    }
    private Cell cell;
    private int health;
    private double resi;
    private double baseResi = 1;

    public double getBaseResi() {
        return baseResi;
    }

    public double getResi() {
        return resi;
    }

    public void setResi(double resi) {
        this.resi = resi;
    }

    public int getBasePower() {
        return basePower;
    }

    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }

    private int power;
    private int basePower;

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    private int maxHealth;

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        this.tileName = "player";
        this.setResi(getBaseResi());
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (this instanceof Player && nextCell.getActor() instanceof Enemy ){
            this.attack(nextCell);
            ((Player) this).getWeapon().loseDurability();

        }
        else if (this instanceof Enemy && nextCell.getActor() instanceof Player){
            this.attack(nextCell);
            ((Player) nextCell.getActor()).damageEquipment();

        }
        if (!nextCell.isObstacle()) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public void attack(Cell cell){
        Actor enemy = cell.getActor();
        enemy.setHealth((int) (enemy.getHealth() - this.getPower() * enemy.getResi()));
        if(enemy.isDead()) enemy.die();
        else{
            this.setHealth((int) (this.getHealth() - enemy.getPower() * this.getResi()));
            if(this instanceof Player) ((Player) this).damageEquipment();
        }
        if(this.isDead()) this.die();

    }

    public int getHealth() {
        return health;
    }


    public Cell getCell() {
        return cell;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "health=" + health +
                ", power=" + power +
                '}';
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public void die() {
        this.getCell().setActor(null);
        if(this instanceof Enemy) ((Enemy) this).dropLoot();

    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}
