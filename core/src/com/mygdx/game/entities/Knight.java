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
public class Knight extends Entity{
    
    public Knight(int id, float x, float y) {
        super(id, x, y, Constants.typeOfEntity.knight); 
        System.out.println("Knight " + id + " has position " + x + "|" + y);
    }
    
}
