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
public class Skeleton extends EntityBipedal{

    public Skeleton(Builder b) {
        super(b);
    }
    /*public Skeleton(int id, float x, float y) {
        super(id, x, y, Constants.typeOfEntity.skeleton); 
        System.out.println("Skeleton " + id + " has position " + x + "|" + y);
    }*/
    
    public static class Builder extends Entity.Builder<Builder>{
        @Override
        public Skeleton build(int id) {
            this.setTypeOfEntity(Constants.typeOfEntity.skeleton);
            this.setId(id);
            return new Skeleton(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
