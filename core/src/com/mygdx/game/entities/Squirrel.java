/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Constants;
import com.mygdx.game.MyContactListener;
import com.mygdx.game.world.Block;
import java.util.AbstractMap;

/**
 *
 * @author Fery
 */
public class Squirrel extends Entity{
    
    public Squirrel(int id, float x, float y) {
        super(id, x, y, Constants.typeOfEntity.squirrel, 2f);
        this.setSpeed(1.5f);
        this.setHeightOffset(0.0f);
        System.out.println("Squirrel " + id + " has position " + x + "|" + y);
    }
}
