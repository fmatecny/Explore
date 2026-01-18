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
public class Wasp extends Entity{
    public Wasp(int id, float x, float y, float scale) {
        super(id, x, y, Constants.typeOfEntity.wasp, scale); 
        System.out.println("Wasp " + id + " has position " + x + "|" + y);
        animations.get(Constants.typeOfDirection.Left.ordinal()).get(Constants.typeOfMovement.Stand.ordinal()).setFrameDuration(0.03f);
        animations.get(Constants.typeOfDirection.Right.ordinal()).get(Constants.typeOfMovement.Stand.ordinal()).setFrameDuration(0.03f);
        timeMove = (int )(Math.random() * 1000) + 10;
    } 
}
