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
public class Knight extends EntityBipedal{

    public Knight(Builder b) {
        super(b);
    }
    
    /*public Knight(int id, float x, float y) {
        super(id, x, y, Constants.typeOfEntity.knight); 
        System.out.println("Knight " + id + " has position " + x + "|" + y);
    }*/
    
    public static class Builder extends Entity.Builder<Builder>{
        @Override
        public Knight build(int id) {
            this.setTypeOfEntity(Constants.typeOfEntity.knight);
            this.setId(id);
            return new Knight(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
        
    }
    
}
