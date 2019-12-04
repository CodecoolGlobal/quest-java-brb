package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.items.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private GameMap gameMap;
    private Item item;
    private int x, y;

    public Item getItem() {
        return item;
    }

    public boolean isObstacle() {
        return this.getTileName().equals("wall")
                || this.getActor() != null
                || this.getTileName().equals("closeddoor");
    }

    public void setItem(Item item) {
        this.item = item;
    }

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    public List<Actor> getAdjacentEnemies(){
        List<Cell> cells = getAllNeighbors();
        List<Actor> enemies = new ArrayList<>();
        for (Cell cell : cells){
            if (cell.getActor() != null) enemies.add(cell.getActor());
        }
        return enemies;
    }

    public List<Cell> getAllNeighbors(){
        return new ArrayList<>(){{
            add(getNeighbor(1,0));
            add(getNeighbor(0,1));
            add(getNeighbor(-1,0));
            add(getNeighbor(0,-1));
        }};
    }

    public void checkDoors(){
        for (Cell cell: getAllNeighbors()) {
            if(cell.getTileName().equals("closeddoor") || cell.getTileName().equals("opendoor")) cell.unlockDoor();
        }
    }

    private void unlockDoor() {
        this.setType(this.getTileName().equals("closeddoor") ? CellType.OPENDOOR: CellType.CLOSEDDOOR);
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
