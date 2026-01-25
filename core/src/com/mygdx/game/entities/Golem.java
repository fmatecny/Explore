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
public class Golem extends EntityBipedal{

    public Golem(Builder b) {
        super(b);
        this.demage = 15;
    }

    /*public Golem(int id, float x, float y) {
        super(id, x, y, Constants.typeOfEntity.golem);
        changeScale(4f);
        this.demage = 15;
    }  */
    
    public static class Builder extends Entity.Builder{

        @Override
        public Golem build(int id) {
            this.setTypeOfEntity(Constants.typeOfEntity.golem);
            this.setId(id);
            //changeScale(4f);
            return new Golem(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
