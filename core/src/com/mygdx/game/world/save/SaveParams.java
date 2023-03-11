/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world.save;

import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class SaveParams {

    public SaveMapParams[][] saveMapParams;
    public SavePlayerParams savePlayerParams;
    public ArrayList<SaveChestParams> saveChestsParams;
    public double hours;
    
    public SaveParams() {
    }
    
}
