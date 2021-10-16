/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class EntitiesManager {

    private int id = 0;
    
    private ArrayList<Villager> villagerList;
    private Golem golem;
    
    public EntitiesManager() {
        id++;
        golem = new Golem(id);
    }
    
}
