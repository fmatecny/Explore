/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class AllBlocks {
    
    /*public enum t
	{
            clear, water, water1, wood, grassy_ground, ground, gravel, stone, smoothstone, sand, coal, iron, gold, diamond, bones, plank,
            leaf,
            //mine
            ladder, prop, bridge, torch, cart, rail, headlamp, 
            
            //village
            door_down, door_up, window,
            
            //castle
            chest, woodStairs, stoneStairs, woodHalfblock, stoneHalfblock,
	}*/

    public static Block empty;
//    public static Block dirt;
    public static Block wood;
    public static Block grassy_ground;
    public static Block ground;
    public static Block groundBck;
    public static Block gravel;
    public static Block stone;
//    public static Block houseStone;
//    public static Block smoothstone;
    public static Block sand;
//    public static Block water;
    public static Block coal;
//    public static Block iron;
//    public static Block gold;
//    public static Block diamond;
//    public static Block bones;
    
    public static Block leaf;
    public static Block door_down;
    public static Block door_up;
    public static Block window;
    
    public static Block plank;
    
    
    public static Texture heard;
    
    private ArrayList<Block> blockList = new ArrayList<>();
    
    public AllBlocks() {
        
/*        dirt = new Block();
        dirt.blocked = true;
        dirt.hardness = 5;
        dirt.textureRotation = 0;
        dirt.texture = new Texture(Gdx.files.internal("block/dirt.png"));*/


        heard = new Texture(Gdx.files.internal("heart.png"));

        coal = new Block();
        coal.id = 13;
        coal.blocked = true;
        coal.hardness = 5;
        coal.textureRotation = 0;
        coal.isRotationAllowed = false;
        coal.texture = new Texture(Gdx.files.internal("block/coal.png"));
        blockList.add(coal);
        
        door_down = new Block();
        door_down.id = 1;
        door_down.blocked = false;
        door_down.hardness = 5;
        door_down.textureRotation = 0;
        door_down.isRotationAllowed = false;
        door_down.texture = new Texture(Gdx.files.internal("block/door_down.jpg"));
        blockList.add(door_down);
        
        door_up = new Block();
        door_up.id = 2;
        door_up.blocked = false;
        door_up.hardness = 5;
        door_up.textureRotation = 0;
        door_up.isRotationAllowed = false;
        door_up.texture = new Texture(Gdx.files.internal("block/door_up.jpg"));
        blockList.add(door_up);

        empty = new Block();
        empty.id = 0;
        empty.blocked = false;
        empty.hardness = 0;
        empty.textureRotation = 0;
        empty.texture = null;
        blockList.add(empty);
        
        grassy_ground = new Block();
        grassy_ground.id = 3;
        grassy_ground.blocked = true;
        grassy_ground.hardness = 5;
        grassy_ground.textureRotation = 0;
        grassy_ground.texture = new Texture(Gdx.files.internal("block/leafy_ground.png"));
        blockList.add(grassy_ground);

        gravel = new Block();
        gravel.id = 4;
        gravel.blocked = true;
        gravel.hardness = 5;
        gravel.textureRotation = 0;
        gravel.texture = new Texture(Gdx.files.internal("block/gravel.jpg"));
        blockList.add(gravel);
        
        ground = new Block();
        ground.id = 5;
        ground.blocked = true;
        ground.hardness = 5;
        ground.textureRotation = 0;
        ground.texture = new Texture(Gdx.files.internal("block/ground.png"));
        blockList.add(ground);
        
        groundBck = new Block();
        groundBck.id = 6;
        groundBck.blocked = false;
        groundBck.hardness = 5;
        groundBck.textureRotation = 0;
        groundBck.texture = new Texture(Gdx.files.internal("block/groundBck.png"));
        blockList.add(groundBck);
        
        /*houseStone = new Block();
        houseStone.blocked = false;
        houseStone.blocked = false;
        houseStone.hardness = 5;
        houseStone.textureRotation = 0;
        houseStone.texture = new Texture(Gdx.files.internal("block/rocky.png"));       */ 

        leaf = new Block();
        leaf.id = 7;
        leaf.blocked = true;
        leaf.hardness = 5;
        leaf.textureRotation = 0;
        leaf.texture = new Texture(Gdx.files.internal("block/leaf.png"));
        blockList.add(leaf);

        plank = new Block();
        plank.id = 8;
        plank.blocked = false;
        plank.hardness = 5;
        plank.textureRotation = 0;
        plank.texture = new Texture(Gdx.files.internal("block/plank.jpg"));
        
        sand = new Block();
        sand.id = 9;
        sand.blocked = true;
        sand.hardness = 5;
        sand.textureRotation = 0;
        sand.texture = new Texture(Gdx.files.internal("block/sand.jpg"));
        
        stone = new Block();
        stone.id = 10;
        stone.blocked = true;
        stone.hardness = 5;
        stone.textureRotation = 0;
        stone.texture = new Texture(Gdx.files.internal("block/rocky.png"));        

        window = new Block();
        window.id = 11;
        window.blocked = false;
        window.hardness = 5;
        window.textureRotation = 0;
        window.texture = new Texture(Gdx.files.internal("block/window.jpg"));
        
        wood = new Block();
        wood.id = 12;
        wood.blocked = true;
        wood.hardness = 5;
        wood.textureRotation = 0;
        wood.texture = new Texture(Gdx.files.internal("block/wood.jpg"));

        
        
/*        smoothstone = new Block();
        smoothstone.blocked = true;
        smoothstone.hardness = 5;
        smoothstone.textureRotation = 0;
*/
    }
    
    public Block getBlockById(int id){
        for (int i = 0; i < blockList.size(); i++) {
            if (blockList.get(i).id == id)
                return blockList.get(i);
        }
       
       return empty;
    }
    
}
