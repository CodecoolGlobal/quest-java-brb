package com.codecool.quest.logic;

import com.codecool.quest.Tiles;
import com.codecool.quest.logic.actors.*;
import com.codecool.quest.logic.items.*;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String file,Class choosenCast) {
        InputStream is = MapLoader.class.getResourceAsStream(file);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line
        Tiles.generateGreenery();
        GameMap map = new GameMap(width, height, CellType.GREENERY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {

                        // tiles
                        case ' ':
                            cell.setType(CellType.GREENERY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'D':
                            cell.setType(CellType.CLOSEDDOOR);
                            break;
                        case 'E':
                            cell.setType(CellType.STAIRS);
                            break;
                        case 'T':
                            cell.setType(CellType.TORCH);
                            break;
                        case 'F':
                            cell.setType(CellType.PLACEHOLDER);
                            break;
                        case '-':
                            cell.setType(CellType.WATEREDGE);
                            break;
                        case '$':
                            cell.setType(CellType.SHOP);
                            break;
                        case '_':
                            cell.setType(CellType.DIGGABLE);
                            break;

                         // actors
                        case '@':
                            cell.setType(CellType.FLOOR);
                            try {
                                map.setPlayer((Player) choosenCast.getConstructor(Cell.class).newInstance(cell));
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 'S':
                            cell.setType(CellType.FLOOR);
                            map.setSkeleton(new Skeleton(cell));
                            break;
                        case 'M':
                            cell.setType(CellType.FLOOR);
                            map.setMonster(new Monster(cell));
                            break;
                        case 'G':
                            cell.setType(CellType.FLOOR);
                            map.setGolem(new Golem(cell));
                            break;
                        case 'B':
                            cell.setType(CellType.FLOOR);
                            map.setBanshee(new Banshee(cell, map));
                            break;
                        case 'Ő':
                            cell.setType(CellType.FLOOR);
                            map.setSpider(new Spider(cell));
                            break;

                        // items
                        case 'K':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'P':
                            cell.setType(CellType.FLOOR);
                            new HealthPotion(cell);
                            break;
                        case 'H':
                            cell.setType(CellType.FLOOR);
                            new Heart(cell);
                            break;
                        case 'O':
                            cell.setType(CellType.FLOOR);
                            new Shovel(cell);
                            break;

                        // weapons
                        case '|':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'Á':
                            cell.setType(CellType.FLOOR);
                            new Shield(cell);
                            break;
                        case 'A':
                            cell.setType(CellType.FLOOR);
                            new Axe(cell);
                            break;
                        case 'L':
                            cell.setType(CellType.FLOOR);
                            new LaserPistol(cell);
                            break;
                        case 'I':
                            cell.setType(CellType.FLOOR);
                            new Bow(cell);
                            break;

                            // lake
                        case '0':
                            cell.setType(CellType.CENTERWATER);
                            break;
                        case '1':
                            cell.setType(CellType.TOPLEFTWATER);
                            break;
                        case '2':
                            cell.setType(CellType.TOPRIGHTWATER);
                            break;
                        case '3':
                            cell.setType(CellType.BOTTOMLEFTWATER);
                            break;
                        case '4':
                            cell.setType(CellType.BOTTOMRIGHTWATER);
                            break;
                        case '5':
                            cell.setType(CellType.TOPWATEREDGE);
                            break;
                        case '6':
                            cell.setType(CellType.LEFTWATEREDGE);
                            break;
                        case '7':
                            cell.setType(CellType.RIGHTWATEREDGE);
                            break;
                        case '8':
                            cell.setType(CellType.BOTTOMWATEREDGE);
                            break;

                            // cave
                        case '+':
                            cell.setType(CellType.DARKNESS);
                            break;
                        case '?':
                            cell.setType(CellType.CENTERCAVE);
                            break;
                        case '!':
                            cell.setType(CellType.EXITCAVE);
                            break;
                        case '%':
                            cell.setType(CellType.TOPLEFTCAVE);
                            break;
                        case '=':
                            cell.setType(CellType.TOPRIGHTCAVE);
                            break;
                        case '(':
                            cell.setType(CellType.BOTTOMLEFTCAVE);
                            break;
                        case ')':
                            cell.setType(CellType.BOTTOMRIGHTCAVE);
                            break;
                        case ':':
                            cell.setType(CellType.TOPEDGECAVE);
                            break;
                        case ';':
                            cell.setType(CellType.LEFTEDGECAVE);
                            break;
                        case '¤':
                            cell.setType(CellType.RIGHTEDGECAVE);
                            break;
                        case '×':
                            cell.setType(CellType.BOTTOMEDGECAVE);
                            break;

                           // objective
                        case 'W':
                            cell.setType(CellType.OBJECTIVE);
                            break;

                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
