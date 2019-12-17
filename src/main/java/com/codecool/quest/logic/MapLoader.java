package com.codecool.quest.logic;

import com.codecool.quest.Tiles;
import com.codecool.quest.logic.actors.Player;
import com.codecool.quest.logic.actors.Skeleton;
import com.codecool.quest.logic.actors.Monster;
import com.codecool.quest.logic.actors.Golem;
import com.codecool.quest.logic.actors.Banshee;
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
                        case '-':
                            cell.setType(CellType.WATEREDGE);
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
                            map.setBanshee(new Banshee(cell));
                            break;

                        // items
                        case 'K':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'I':
                            cell.setType(CellType.FLOOR);
                            new Bow(cell);
                            break;
                        case 'P':
                            cell.setType(CellType.FLOOR);
                            new HealthPotion(cell);
                            break;
                        case 'H':
                            cell.setType(CellType.FLOOR);
                            new Heart(cell);
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
