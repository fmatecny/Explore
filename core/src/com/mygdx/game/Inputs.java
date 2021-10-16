/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Fery
 */
public class Inputs implements InputProcessor{

    public static Inputs instance;
    
    public boolean pause = false;
    public boolean right = false;
    public boolean left = false;
    public boolean jump = false;
    public boolean up = false;
    public boolean down = false;
    public boolean run = false;
    //public boolean inventoryBtn = false;
    public boolean control_left = false;
    public boolean showInventory = false;
    public boolean debugMode = false;
    public int mouseX = 0;
    public int mouseY = 0;
    public boolean mouseLeft = false;
    public boolean mouseRight = false;
    public boolean mouseMiddle = false;
    public int scrollIdx = 0;
    public final int maxScrolIdx = 8;
    
    public boolean srdco = false;
    
    
    @Override
    public boolean keyDown(int keycode) {
        //System.out.println(keycode);
        switch (keycode) {
            case Keys.RIGHT:
            case Keys.D:
                            right = true; 
                            break;
            case Keys.LEFT:
            case Keys.A:
                            left = true;
                            break;
                            
            case Keys.UP:
            case Keys.W:
                            up = true;
                            break;
                            
            case Keys.SPACE:
                            jump = true;
                            break;
                            
            case Keys.DOWN:
            case Keys.S:
                            down = true;
                            break;
                            
            /*case Keys.E:
                            //inventoryBtn = true;
                            break;*/
                            
            case Keys.SHIFT_LEFT:
                            run = true;
                            break;
                            
            case Keys.CONTROL_LEFT:
                            control_left = true;
                            break;
                            
            case Keys.ESCAPE:
                            pause = true;
                            break;
                    
            case Keys.H:
                            srdco = true;
                            break;
                            
                            
            default:
                return false;
        }        
        return false;
    }
    
    @Override
    public boolean keyUp(int keycode) {

        switch (keycode) {
            case Keys.F1:
                            debugMode = !debugMode;
                            break;
                            
            case Keys.RIGHT:
            case Keys.D:
                            right = false; 
                            break;
            case Keys.LEFT:
            case Keys.A:
                            left = false;
                            break;
                            
            case Keys.UP:
            case Keys.W:
                            up = false;
                            break; 
                            
            case Keys.SPACE:
                            jump = false;
                            break;
                            
            case Keys.DOWN:
            case Keys.S:
                            down = false;
                            break;
                            
            case Keys.E:
                            //inventoryBtn = false;
                            showInventory = !showInventory;
                            break;

            case Keys.CONTROL_LEFT:
                            control_left = false;
                            break;
                            
            case Keys.SHIFT_LEFT:
                            run = false;
                            break;
                            
            case Keys.ESCAPE:
                            pause = false;
                            break;
                            
            case Keys.H:
                            srdco = false;
                            break;
                            
            default:
                return false;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        //System.err.println(x + ", " + ", " + y + ", " + pointer + ", " + button);
        switch (button) {
            case Buttons.LEFT:
                            mouseLeft = true;
                            break;
                            
            case Buttons.RIGHT:
                            mouseRight = true;
                            break;
                            
            case Buttons.MIDDLE:
                            mouseMiddle = true;
                            break;
                           
            default:
                return false;
        }
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        switch (button) {
            case Buttons.LEFT:
                            mouseLeft = false;
                            break;
                            
            case Buttons.RIGHT:
                            mouseRight = false;
                            break;
                            
            case Buttons.MIDDLE:
                            mouseMiddle = false;
                            break;
                            
            default:
                return false;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int i2) {
        //System.err.println(x + ", " + y + ", " + i2);
        mouseX =  x;
        mouseY = y;
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        //System.out.println(x + "," + y);
        mouseX =  x;
        mouseY = y;
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        
        scrollIdx += i;
        
        if (scrollIdx < 0)
            scrollIdx = maxScrolIdx;
        
        if (scrollIdx > maxScrolIdx)
            scrollIdx = 0;
        
        //System.out.println(scrollIdx);
        return false;
    }
}
