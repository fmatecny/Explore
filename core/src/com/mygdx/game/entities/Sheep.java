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
public class Sheep extends EntityQuadruped {

    public Sheep(Builder b) {
        super(b);
    }
    
    /*public Sheep(int id, float x, float y, float scale) {
        super(id, x, y, Constants.typeOfEntity.sheep, scale, true); 
        System.out.println("Sheep " + id + " has position " + x + "|" + y);
    }   */
    public static class Builder extends Entity.Builder<Builder>{

        @Override
        public Sheep build(int id) {
            this.setTypeOfEntity(Constants.typeOfEntity.sheep);
            this.setId(id);
            return new Sheep(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    
    }
}
