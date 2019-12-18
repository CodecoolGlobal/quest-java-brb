package com.codecool.quest.logic.actors;

import com.codecool.quest.Main;
import com.codecool.quest.logic.Cell;

import java.util.ArrayList;
import java.util.List;

public class Warrior extends Player{
    public Warrior(Cell cell) {
        super(cell);
        setMaxHealth(75);
        setBasePower(10);
        setPower(getBasePower());
        setHealth(getMaxHealth());
    }

    @Override
    public String getTileName() {
        return isWeaponed() && isArmored() ? "fullSetWarrior" : isWeaponed() ? "weaponedWarrior" : isArmored() ? "armoredWarrior" : "warrior";
    }

    @Override
    public void castSpell() {
        List<Enemy> enemies = new ArrayList<>();
        List<String> names = new ArrayList<>();

        getCell().getAllNeighbors().forEach((cell -> {
            if (cell.getActor() instanceof Enemy) {
                Actor enemy = cell.getActor();
                enemies.add((Enemy) enemy);
                names.add(enemy.getTileName());
                enemy.setTileName("hurt" + enemy.getTileName());

                enemy.setHealth(enemy.getHealth() - getPower()/2);
                if (enemy.isDead()) enemy.die();
            }

        }));
        Main.setTimeout(() -> {
            for (int i = 0; i < enemies.size(); i++) {
                enemies.get(i).setTileName(names.get(i));
            }
        }, 250);
    }
}
