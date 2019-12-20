package com.codecool.quest;

import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.items.Ammo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import java.util.*;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles8.png", 543 * 2, 543 * 2, true, false);

    private static Map<String, Tile> tileMap = new HashMap<>();
    public static ArrayList<Integer> greenery = new ArrayList<>();
    public static ListIterator<Integer> iterator = greenery.listIterator();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        //ENVIROMENT
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("closeddoor", new Tile(3, 3));
        tileMap.put("opendoor", new Tile(4, 3));
        tileMap.put("stairs", new Tile(4, 6));
        tileMap.put("torch", new Tile(4, 15));
        tileMap.put("wateredge", new Tile(9, 5));
        tileMap.put("objective", new Tile(12, 24));
        tileMap.put("placeholder", new Tile(12, 10));
        tileMap.put("diggable", new Tile(12, 18));
        tileMap.put("centerwater", new Tile(8, 5));
        tileMap.put("topleftwater", new Tile(20, 30));
        tileMap.put("toprightwater", new Tile(21, 30));
        tileMap.put("bottomleftwater", new Tile(20, 31));
        tileMap.put("bottomrightwater", new Tile(21, 31));
        tileMap.put("topwateredge", new Tile(23, 31));
        tileMap.put("leftwateredge", new Tile(22, 30));
        tileMap.put("rightwateredge", new Tile(23, 30));
        tileMap.put("bottomwateredge", new Tile(22, 31));
        tileMap.put("darkness", new Tile(0, 0));
        tileMap.put("centercave", new Tile(28, 30));
        tileMap.put("exitcave", new Tile(28, 31));
        tileMap.put("topleftcave", new Tile(24, 30));
        tileMap.put("toprightcave", new Tile(25, 30));
        tileMap.put("bottomleftcave", new Tile(24, 31));
        tileMap.put("bottomrightcave", new Tile(25, 31));
        tileMap.put("topedgecave", new Tile(27, 31));
        tileMap.put("leftedgecave", new Tile(26, 30));
        tileMap.put("rightedgecave", new Tile(27, 30));
        tileMap.put("bottomedgecave", new Tile(26, 31));

        //CHARACTERS
        tileMap.put("warrior", new Tile(25, 0));
        tileMap.put("weaponedWarrior", new Tile(27, 0));
        tileMap.put("armoredWarrior", new Tile(30, 0));
        tileMap.put("fullSetWarrior", new Tile(31, 0));
        tileMap.put("mage", new Tile(24, 0));
        tileMap.put("weaponedMage", new Tile(25, 1));
        tileMap.put("armoredMage", new Tile(24, 2));
        tileMap.put("fullSetMage", new Tile(24, 1));

        //ENEMIES
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("monster", new Tile(28, 6));
        tileMap.put("golem", new Tile(30, 6));
        tileMap.put("banshee", new Tile(27, 6));
        tileMap.put("hurtskeleton", new Tile(24, 8));
        tileMap.put("hurtmonster", new Tile(25, 8));
        tileMap.put("hurtbanshee", new Tile(26, 8));
        tileMap.put("hurtgolem", new Tile(25, 9));

        //EQUIPMENTS
        tileMap.put("shield", new Tile(8,26));
        tileMap.put("helmet", new Tile(1,22));

        //WEAPONS
        tileMap.put("arrow", new Tile(15, 18));
        tileMap.put("axe", new Tile(10, 30));
        tileMap.put("bow", new Tile(5, 28));
        tileMap.put("sword", new Tile(4, 28));
        tileMap.put("crossbow", new Tile(7, 27));
        tileMap.put("laserpistol", new Tile (6, 31));
        tileMap.put("laser", new Tile (13, 31));
        tileMap.put("fireball", new Tile (14, 31));

        //ITEMS
        tileMap.put("heart", new Tile(23, 22));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("healthPotion", new Tile(17, 25));
        tileMap.put("shovel", new Tile(10, 27));
    }


    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile;
        if (d.getTileName().equals("greenery")) {
            tile = new Tile(iterator.next(), iterator.next());
        } else {
            tile = tileMap.get(d.getTileName());
        }

        if(d instanceof Ammo){
            double angle = ((Ammo) d).getDirection();
            context.save();
            Rotate r = new Rotate(angle, x*TILE_WIDTH + TILE_WIDTH/2, y*TILE_WIDTH + TILE_WIDTH/2);
            context.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        }
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
        context.restore();
    }

    public static double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    public static void generateGreenery(){
        for (int i = 0;i<600;i++){
            greenery.add((int) getRandomIntegerBetweenRange(0,7));
            greenery.add((int) getRandomIntegerBetweenRange(0,2));
        }
    }
}
