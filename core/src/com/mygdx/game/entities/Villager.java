/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.entities;

import com.mygdx.game.Constants;

/**
 *
 * @author Fery
 */
public class Villager extends EntityBipedal{

    public Villager(Builder b) {
        super(b);
    }
        
    /*public Villager(int id, float x, float y) {
        super(id, x, y, Constants.typeOfEntity.villager); 
        System.out.println("Villager " + id + " has position " + x + "|" + y);
        createInventoryShop();
    }*/
    
    public static class Builder extends Entity.Builder<Builder>{

        @Override
        public Villager build(int id) {
            this.setEntityTextureSizeRatio(197f/512f);
            this.setTypeOfEntity(Constants.typeOfEntity.villager);
            this.setId(id);
            //changeScale(4f);
            return new Villager(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
    
}
