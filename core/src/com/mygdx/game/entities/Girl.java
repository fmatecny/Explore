/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.entities;

import com.mygdx.game.Constants;

/**
 *
 * @author Krtkosaurus
 */
public class Girl extends Entity{

    public Girl(int id, float x, float y) {
        super(id, x, y, Constants.typeOfEntity.girl); 
        System.out.println("Girl " + id + " has position " + x + "|" + y);
    }
}
