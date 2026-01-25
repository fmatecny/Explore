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
public class King extends EntityBipedal{

    public King(Builder b) {
        super(b);
    }
    
    /*public King(int id, float x, float y) {
        super(id, x, y, Constants.typeOfEntity.king); 
        System.out.println("King " + id + " has position " + x + "|" + y);
    }
    */
    public static class Builder extends Entity.Builder<Builder>{
        @Override
        public King build(int id) {
            this.setTypeOfEntity(Constants.typeOfEntity.king);
            this.setId(id);
            return new King(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
