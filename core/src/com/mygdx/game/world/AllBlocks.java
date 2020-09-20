/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Fery
 */
public class AllBlocks {
    
    private enum t
    {
       empty, groundBck, ground, grassy_ground, wood,
       
       leaf,
       
       gravel, sand, stone, coal, iron, gold, diamond,
       
       wood_stairs, plank, half_plank,
       
       door, door_down, door_up, window,
       
       torch, ladder,
       
       chest, furnace

    }

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
    public static Block iron;
    public static Block gold;
    public static Block diamond;
//    public static Block bones;
    
    public static Block leaf;
    public static Block door_down;
    public static Block door_up;
    public static Block door;
    public static Block window;
    
    public static Block plank;
    public static Block half_plank;
    public static Block wood_stairs;
    
    public static Block torch;
    public static Block ladder;
    
    public static Block chest;
    public static Block furnace;
    
    
    public static Texture heard;
 
    public AllBlocks() {

        heard = new Texture(Gdx.files.internal("heart.png"));

        coal = new Block();
        coal.id = t.coal.ordinal();
        coal.blocked = true;
        coal.hardness = 5;
        coal.textureRotation = 0;
        coal.isRotationAllowed = false;
        coal.stackable = true;
        coal.texture = new Texture(Gdx.files.internal("block/coal.png"));
        
        diamond = new Block();
        diamond.id = t.diamond.ordinal();
        diamond.blocked = true;
        diamond.hardness = 5;
        diamond.textureRotation = 0;
        diamond.stackable = true;
        diamond.texture = new Texture(Gdx.files.internal("block/diamond.png"));        
        
        door = new Block();
        door.id = t.door.ordinal();
        door.blocked = false;
        door.hardness = 5;
        door.textureRotation = 0;
        door.isRotationAllowed = false;
        door.stackable = false;
        door.texture = new Texture(Gdx.files.internal("block/door.png"));
        
        door_down = new Block();
        door_down.id = t.door_down.ordinal();
        door_down.blocked = false;
        door_down.hardness = 5;
        door_down.textureRotation = 0;
        door_down.isRotationAllowed = false;
        door_down.stackable = false;
        door_down.texture = new Texture(Gdx.files.internal("block/door_down.jpg"));
        
        door_up = new Block();
        door_up.id = t.door_up.ordinal();
        door_up.blocked = false;
        door_up.hardness = 5;
        door_up.textureRotation = 0;
        door_up.isRotationAllowed = false;
        door_up.stackable = false;
        door_up.texture = new Texture(Gdx.files.internal("block/door_up.jpg"));

        empty = new Block();
        empty.id = t.empty.ordinal();
        empty.blocked = false;
        empty.hardness = 0;
        empty.textureRotation = 0;
        empty.stackable = false;
        empty.texture = null;
        
        furnace = new Block();
        furnace.id = t.furnace.ordinal();
        furnace.blocked = false;
        furnace.hardness = 5;
        furnace.textureRotation = 0;
        furnace.stackable = false;
        furnace.texture = new Texture(Gdx.files.internal("block/furnace.jpg"));
        
        gold = new Block();
        gold.id = t.gold.ordinal();
        gold.blocked = true;
        gold.hardness = 5;
        gold.textureRotation = 0;
        gold.stackable = true;
        gold.texture = new Texture(Gdx.files.internal("block/gold.png"));
        
        grassy_ground = new Block();
        grassy_ground.id = t.grassy_ground.ordinal();
        grassy_ground.blocked = true;
        grassy_ground.hardness = 5;
        grassy_ground.textureRotation = 0;
        grassy_ground.stackable = true;
        grassy_ground.texture = new Texture(Gdx.files.internal("block/leafy_ground.png"));

        gravel = new Block();
        gravel.id = t.gravel.ordinal();
        gravel.blocked = true;
        gravel.hardness = 5;
        gravel.textureRotation = 0;
        gravel.stackable = true;
        gravel.texture = new Texture(Gdx.files.internal("block/gravel.jpg"));
        
        ground = new Block();
        ground.id = t.ground.ordinal();
        ground.blocked = true;
        ground.hardness = 5;
        ground.textureRotation = 0;
        ground.stackable = true;
        ground.texture = new Texture(Gdx.files.internal("block/ground.png"));
        
        groundBck = new Block();
        groundBck.id = t.groundBck.ordinal();
        groundBck.blocked = false;
        groundBck.hardness = 5;
        groundBck.textureRotation = 0;
        groundBck.stackable = true;
        groundBck.texture = new Texture(Gdx.files.internal("block/groundBck.png"));
        
        /*houseStone = new Block();
        houseStone.blocked = false;
        houseStone.blocked = false;
        houseStone.hardness = 5;
        houseStone.textureRotation = 0;
        houseStone.texture = new Texture(Gdx.files.internal("block/rocky.png"));       */ 

        half_plank = new Block();
        half_plank.id = t.half_plank.ordinal();
        half_plank.blocked = false;
        half_plank.hardness = 5;
        half_plank.textureRotation = 0;
        half_plank.stackable = true;
        half_plank.texture = new Texture(Gdx.files.internal("block/plank_halfblock.png"));
        
        chest = new Block();
        chest.id = t.chest.ordinal();
        chest.blocked = false;
        chest.hardness = 5;
        chest.textureRotation = 0;
        chest.stackable = false;
        chest.texture = new Texture(Gdx.files.internal("block/chest.jpg"));
        
        iron = new Block();
        iron.id = t.iron.ordinal();
        iron.blocked = true;
        iron.hardness = 5;
        iron.textureRotation = 0;
        iron.stackable = true;
        iron.texture = new Texture(Gdx.files.internal("block/iron.png"));

        ladder = new Block();
        ladder.id = t.ladder.ordinal();
        ladder.blocked = false;
        ladder.hardness = 5;
        ladder.textureRotation = 0;
        ladder.stackable = true;
        ladder.texture = new Texture(Gdx.files.internal("block/ladder.png"));
        
        leaf = new Block();
        leaf.id = t.leaf.ordinal();
        leaf.blocked = true;
        leaf.hardness = 5;
        leaf.textureRotation = 0;
        leaf.stackable = true;
        leaf.texture = new Texture(Gdx.files.internal("block/leaf.png"));

        plank = new Block();
        plank.id = t.plank.ordinal();
        plank.blocked = false;
        plank.hardness = 5;
        plank.textureRotation = 0;
        plank.stackable = true;
        plank.texture = new Texture(Gdx.files.internal("block/plank.jpg"));
        
        sand = new Block();
        sand.id = t.sand.ordinal();
        sand.blocked = true;
        sand.hardness = 5;
        sand.textureRotation = 0;
        sand.stackable = true;
        sand.texture = new Texture(Gdx.files.internal("block/sand.jpg"));
        
        stone = new Block();
        stone.id = t.stone.ordinal();
        stone.blocked = true;
        stone.hardness = 5;
        stone.textureRotation = 0;
        stone.stackable = true;
        stone.texture = new Texture(Gdx.files.internal("block/rocky.png"));        

        torch = new Block();
        torch.id = t.torch.ordinal();
        torch.blocked = false;
        torch.hardness = 5;
        torch.textureRotation = 0;
        torch.stackable = true;
        torch.texture = new Texture(Gdx.files.internal("block/walltorch1.gif"));//torch.png"));
        
        window = new Block();
        window.id = t.window.ordinal();
        window.blocked = false;
        window.hardness = 5;
        window.textureRotation = 0;
        window.stackable = true;
        window.texture = new Texture(Gdx.files.internal("block/window.jpg"));
        
        wood = new Block();
        wood.id = t.wood.ordinal();
        wood.blocked = true;
        wood.hardness = 5;
        wood.textureRotation = 0;
        wood.stackable = true;
        wood.texture = new Texture(Gdx.files.internal("block/wood.jpg"));

        wood_stairs = new Block();
        wood_stairs.id = t.wood_stairs.ordinal();
        wood_stairs.blocked = false;
        wood_stairs.hardness = 5;
        wood_stairs.textureRotation = 0;
        wood_stairs.isRotationAllowed = true;
        wood_stairs.stackable = true;
        wood_stairs.texture = new Texture(Gdx.files.internal("block/wood_stairs.png"));

    }

}
