package com.codecool.quest.logic.items;

import com.codecool.quest.Main;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.actors.Actor;
import com.codecool.quest.logic.actors.Enemy;


public abstract class Ammo implements Drawable {
    int damage;

    public Cell getCell() {
        return cell;
    }

    int dx;
    int dy;
    Cell cell;
    double direction;

    public double getDirection() {
        return direction;
    }

    public Ammo(Cell cell, int dx, int dy,int damage){
        this.cell = cell;
        this.cell.setAmmo(this);
        this.dx = dx;
        this.dy = dy;
        this.damage = damage;
        switch (dx + String.valueOf(dy)){
            case "0-1":
                direction = 0;
                break;
            case "01":
                direction = 180;
                break;
            case "-10":
                direction = -90;
                break;
            case "10":
                direction = 90;
                break;
        }
    }
    public void moveBullet(){
        Cell nextCell = cell.getNeighbor(dx, dy);
        cell.setAmmo(null);

        nextCell.setAmmo(this);
        cell = nextCell;

       if(!this.getCell().getTileName().equals("floor") || this.getCell().getActor() != null){
           this.getCell().setAmmo(null);
           if(this.getCell().getActor() instanceof Enemy){
               Actor enemy = this.getCell().getActor();

               enemy.setTileName("hurt"+enemy.getBaseTileName());
               Main.setTimeout(()->enemy.setTileName(enemy.getBaseTileName()),250);

               enemy.setHealth(enemy.getHealth()-damage);
               if(enemy.isDead()) enemy.die();
           }
       }
    }

    public abstract String getTileName();
}
