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
public class Golem extends Entity{

    public Golem(int id, float x, float y) {
        super(id, x, y, Constants.typeOfEntity.golem);
        changeScale(4f);
        this.demage = 15;
    }  
}
