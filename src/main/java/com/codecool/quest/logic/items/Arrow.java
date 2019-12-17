package com.codecool.quest.logic.items;

import com.codecool.quest.logic.Cell;

public class Arrow extends Ammo{

    public Arrow(Cell cell, int dx, int dy,int damage){
        super(cell,dx,dy,damage);
    }

    @Override
    public String getTileName() {
        return "arrow";
    }
}
