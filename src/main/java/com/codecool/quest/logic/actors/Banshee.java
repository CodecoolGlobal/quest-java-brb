package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.GameMap;

import java.util.List;
import java.util.Random;

public class Banshee extends Enemy {
    private GameMap map;

    public Banshee(Cell cell, GameMap map) {
        super(cell, "slow");
        setHealth(6);
        setBasePower(2);
        setTileName("banshee");
        setBaseTileName(tileName);
        this.map = map;
    }

    private Cell getRandomFloor() {
        List<Cell> walls = map.getAllWalls();
        while (true) {
            Cell wall = walls.get(new Random().nextInt(walls.size()));
            List<Cell> cells = wall.getAllNeighbors();
            for (Cell cell : cells) {
                if (cell.getTileName().equals("floor")) return cell;
            }
        }
    }



    @Override
    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getActor() == null) {
            battle(nextCell);
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
            if (this.getCell().getTileName().equals("wall")) {
                Cell floor = getRandomFloor();
                cell.setActor(null);
                floor.setActor(this);
                cell = floor;
            }
        }
    }
}
