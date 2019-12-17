package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.*;
import com.codecool.quest.logic.items.Ammo;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private Player player;
    private Skeleton skeleton;
    private Monster monster;
    private Golem golem;
    private Banshee banshee;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public List<Actor> getSlowEnemies(){
        List<Actor> slowEnemies = new ArrayList<>();
        for(int row = 0;row<width;row++){
            for(int col = 0;col<height;col++){
                if(getCell(row,col).getActor() instanceof Enemy && isSlowEnemy(row, col)) slowEnemies.add(getCell(row,col).getActor());
            }
        }
        return slowEnemies;
    }

    public List<Actor> getFastEnemies(){
        List<Actor> fastEnemies = new ArrayList<>();
        for(int row = 0;row<width;row++){
            for(int col = 0;col<height;col++){
                if(getCell(row,col).getActor() instanceof Enemy && !isSlowEnemy(row, col)) fastEnemies.add(getCell(row,col).getActor());
            }
        }
        return fastEnemies;
    }

    private boolean isSlowEnemy(int row, int col) {
        return ((Enemy) getCell(row, col).getActor()).getMovementSpeed().equals("slow");
    }

    public List<Ammo> getAllAmmos(){
        List<Ammo> ammos = new ArrayList<>();
        for(int row = 0;row<width;row++){
            for(int col = 0;col<height;col++){
                if(getCell(row,col).getAmmo() != null) ammos.add(getCell(row,col).getAmmo());
            }
        }
        return ammos;
    }

    public Player getPlayer() {
        return player;
    }

    public Skeleton getSkeleton() { return skeleton; }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setSkeleton(Skeleton skeleton) { this.skeleton = skeleton; }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public void setGolem(Golem golem) {
        this.golem = golem;
    }

    public void setBanshee(Banshee banshee) {
        this.banshee = banshee;
    }
}
