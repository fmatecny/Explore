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
public class Girl extends EntityBipedal{

    /*public Girl(int id, float x, float y) {
        super(id, x, y, Constants.typeOfEntity.girl); 
        System.out.println("Girl " + id + " has position " + x + "|" + y);
    }*/
    public Girl(Builder b) {
        super(b); 
    }
    
    public static class Builder extends Entity.Builder<Builder>{

        @Override
        public Girl build(int id) {
            this.setTypeOfEntity(Constants.typeOfEntity.girl);
            this.setId(id);
            return new Girl(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    
    }
}
