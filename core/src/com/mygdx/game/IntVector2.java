/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

/**
 *
 * @author Fery
 */
public class IntVector2 {
    
    public int X = 0;
    public int Y = 0;

    public IntVector2() {
    }
    
    public IntVector2(int x, int y) {
        this.X = x;
        this.Y = y;
    }
    
    public void setXY(int x, int y){
        this.X = x;
        this.Y = y;
    }
    
    public void set(IntVector2 v){
        setXY(v.X, v.Y);
    }
    
    public boolean equal(IntVector2 v){
        if (v == null)
            return false;
        
        return v.X == this.X && v.Y == this.Y;
    
    
    }
    
}
