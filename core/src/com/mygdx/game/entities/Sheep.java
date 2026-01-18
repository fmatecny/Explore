/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.entities;

import com.mygdx.game.Constants;

/**
 *
 * @author Fery
 */
public class Sheep extends Entity {
    
    public Sheep(int id, float x, float y, float scale) {
        super(id, x, y, Constants.typeOfEntity.sheep, scale, true); 
        System.out.println("Sheep " + id + " has position " + x + "|" + y);
    }   
}
