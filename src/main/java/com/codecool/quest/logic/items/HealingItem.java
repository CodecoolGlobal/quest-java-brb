package com.codecool.quest.logic.items;

import com.codecool.quest.logic.actors.Actor;

public interface HealingItem {

    default void heal(Actor actor,int healPower){
        actor.setHealth(actor.getHealth()+healPower);
        if(actor.getMaxHealth() < actor.getHealth()) actor.setHealth(actor.getMaxHealth());
    }
}
