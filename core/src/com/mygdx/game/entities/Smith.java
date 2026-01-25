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
public class Smith extends EntityBipedal{

    public Smith(Builder b) {
        super(b);
    }

    /*public Smith(int id, float x, float y) {
        super(id, x, y, Constants.typeOfEntity.smith);
        this.demage = 20;
        createInventoryShop();
    } */
    
    public static class Builder extends Entity.Builder<Builder>{
        @Override
        public Smith build(int id) {
            this.setTypeOfEntity(Constants.typeOfEntity.smith);
            this.setId(id);
            return new Smith(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
    
}
