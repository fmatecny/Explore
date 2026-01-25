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
public class Wasp extends EntityFlying{
    
    public Wasp(Builder b){
        super(b);
    }
    
    public static class Builder extends Entity.Builder<Builder>{

        @Override
        public Wasp build(int id) {
            //this.setHeightInBlocks(1.2f);
            //this.setEntityTextureSizeRatio(87f/205f);
            this.setTypeOfEntity(Constants.typeOfEntity.wasp);
            this.setId(id);
            return new Wasp(this);
        }

        @Override
        protected Builder self() {
            return this;
        }    
    }
    
    
    
    /*public Wasp(int id, float x, float y, float scale) {
        super(id, x, y, Constants.typeOfEntity.wasp); 
        System.out.println("Wasp " + id + " has position " + x + "|" + y);
        animations.get(Constants.typeOfDirection.Left.ordinal()).get(Constants.typeOfMovement.Stand.ordinal()).setFrameDuration(0.03f);
        animations.get(Constants.typeOfDirection.Right.ordinal()).get(Constants.typeOfMovement.Stand.ordinal()).setFrameDuration(0.03f);
        timeMove = (int )(Math.random() * 1000) + 10;
    } */
}
