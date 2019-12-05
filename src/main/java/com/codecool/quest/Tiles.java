package com.codecool.quest;

import com.codecool.quest.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.*;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
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
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(25, 0));
        tileMap.put("weaponedPlayer", new Tile(27, 0));
        tileMap.put("armoredPlayer", new Tile(30, 0));
        tileMap.put("fullSetPlayer", new Tile(31, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("monster", new Tile(28, 6));
        tileMap.put("sword", new Tile(4, 28));
        tileMap.put("axe", new Tile(10, 30));
        tileMap.put("shield", new Tile(8,26));
        tileMap.put("helmet", new Tile(2,22));
        tileMap.put("closeddoor", new Tile(3, 3));
        tileMap.put("opendoor", new Tile(4, 3));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("stairs", new Tile(4, 6));
        tileMap.put("heart", new Tile(23, 22));
        tileMap.put("torch", new Tile(4, 15));
        tileMap.put("healthPotion", new Tile(17, 25));
        tileMap.put("laserpistol", new Tile (6, 31));
        tileMap.put("golem", new Tile(30, 6));
        tileMap.put("banshee", new Tile(27, 6));
        tileMap.put("wateredge", new Tile(9, 5));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile;
        if (d.getTileName().equals("greenery")) {
            tile = new Tile(iterator.next(), iterator.next());
        } else {
            tile = tileMap.get(d.getTileName());
        }
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);

    }

    public static double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    public static void generateGreenery(){
        for (int i = 0;i<500;i++){
            greenery.add((int) getRandomIntegerBetweenRange(0,7));
            greenery.add((int) getRandomIntegerBetweenRange(0,2));
        }
    }
}
