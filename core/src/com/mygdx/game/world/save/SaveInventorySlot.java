/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world.save;

/**
 *
 * @author Fery
 */
public class SaveInventorySlot {

        public enum t
        {
           block, item, tool
        }
    
        public int id;
        public int amount;
        public t type;

        public SaveInventorySlot() {
        }

    }
