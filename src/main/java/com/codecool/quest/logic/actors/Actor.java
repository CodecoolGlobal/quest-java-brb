package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.items.Item;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class Actor implements Drawable {
    protected List<Item> inventory = new ArrayList<>();
    protected String tileName;
    private Cell cell;
    private int health;

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
    }

    public void move(int dx, int dy) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getActor() != null) this.attack(nextCell);
        if (!nextCell.isObstacle()) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public void attack(Cell cell) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Actor enemy = cell.getActor();
        enemy.setHealth(enemy.getHealth() - this.getPower());
        this.setHealth(this.getHealth() - enemy.getPower());
        if(this.isDead()) this.die();
        if(enemy.isDead()) enemy.die();
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

    public void die() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
