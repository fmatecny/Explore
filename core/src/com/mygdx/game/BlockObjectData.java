/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game;

import com.mygdx.game.world.AllBlocks;

/**
 *
 * @author Fery
 */
public class BlockObjectData {
    
    private IntVector2 pos;
    private int id;

    public BlockObjectData(IntVector2 pos, int id) {
        this.pos = pos;
        this.id = id;
    }
    
    public BlockObjectData(int x, int y, int id) {
        this.pos = new IntVector2(x, y);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public IntVector2 getPos() {
        return pos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPos(IntVector2 pos) {
        this.pos = pos;
    }
    
    
    
}
