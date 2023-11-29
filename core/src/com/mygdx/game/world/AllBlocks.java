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
    
    private enum t
    {
       empty, groundBck, ground, grassy_ground, wood,//10,15,20
       
       leaf,//5
       
       gravel, sand, stone, coal, iron, gold, diamond, unbreakable,//8,7,30,40,60,70,100
       
       wood_stairs, plank, half_plank,//25
       
       stone_stairs, half_stone,//35
       
       door, door_down, door_up, window,//25,10
       
       torch, ladder,//10,20
       
       chest, furnace//25

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
    public static Block unbreakable;
    
    public static Block leaf;
    public static Block door_down;
    public static Block door_up;
    public static Block door;
    public static Block window;
    
    public static Block plank;
    public static Block half_plank;
    public static Block wood_stairs;
    
    public static Block stone_stairs;
    public static Block half_stone;
    
    public static Block torch;
    public static Block ladder;
    
    public static Block chest;
    public static Block furnace;
    
    
    public static ArrayList<Block> blockList;
    
    public static Texture heard;
 
    public AllBlocks() {

        blockList = new ArrayList<Block>();
        
        heard = new Texture(Gdx.files.internal("heart.png"));

        coal = new Block();
        coal.id = t.coal.ordinal();
        coal.info = "Coal";
        coal.blocked = true;
        coal.hardness = 40;
        coal.textureRotation = 0;
        coal.isRotationAllowed = false;
        coal.stackable = true;
        coal.texture = new Texture(Gdx.files.internal("block/coal.png"));
        blockList.add(coal);
        
        diamond = new Block();
        diamond.id = t.diamond.ordinal();
        diamond.info = "Diamond";
        diamond.blocked = true;
        diamond.hardness = 100;
        diamond.textureRotation = 0;
        diamond.stackable = true;
        diamond.texture = new Texture(Gdx.files.internal("block/diamond.png"));
        blockList.add(diamond);        
        
        door = new Block();
        door.id = t.door.ordinal();
        door.info = "Door";
        door.blocked = false;
        door.hardness = 25;
        door.textureRotation = 0;
        door.isRotationAllowed = false;
        door.stackable = false;
        door.texture = new Texture(Gdx.files.internal("block/door.png"));
        blockList.add(door);
        
        door_down = new Block();
        door_down.id = t.door_down.ordinal();
        door_down.info = "Door down";
        door_down.blocked = false;
        door_down.hardness = 25;
        door_down.textureRotation = 0;
        door_down.isRotationAllowed = false;
        door_down.stackable = false;
        door_down.texture = new Texture(Gdx.files.internal("block/door_down.jpg"));
        blockList.add(door_down);
        
        door_up = new Block();
        door_up.id = t.door_up.ordinal();
        door_up.info = "Door up";
        door_up.blocked = false;
        door_up.hardness = 25;
        door_up.textureRotation = 0;
        door_up.isRotationAllowed = false;
        door_up.stackable = false;
        door_up.texture = new Texture(Gdx.files.internal("block/door_up.jpg"));
        blockList.add(door_up);
        
        empty = new Block();
        empty.id = t.empty.ordinal();
        empty.info = "Empty";
        empty.blocked = false;
        empty.hardness = 0;
        empty.textureRotation = 0;
        empty.stackable = false;
        empty.texture = null;
        blockList.add(empty);
        
        furnace = new Block();
        furnace.id = t.furnace.ordinal();
        furnace.info = "Furnace";
        furnace.blocked = false;
        furnace.hardness = 25;
        furnace.textureRotation = 0;
        furnace.stackable = false;
        furnace.texture = new Texture(Gdx.files.internal("block/furnace.jpg"));
        blockList.add(furnace);
        
        gold = new Block();
        gold.id = t.gold.ordinal();
        gold.info = "Gold";
        gold.blocked = true;
        gold.hardness = 70;
        gold.textureRotation = 0;
        gold.stackable = true;
        gold.texture = new Texture(Gdx.files.internal("block/gold.png"));
        blockList.add(gold);
        
        grassy_ground = new Block();
        grassy_ground.id = t.grassy_ground.ordinal();
        grassy_ground.info = "Grass";
        grassy_ground.blocked = true;
        grassy_ground.hardness = 15;
        grassy_ground.textureRotation = 0;
        grassy_ground.stackable = true;
        grassy_ground.texture = new Texture(Gdx.files.internal("block/leafy_ground.png"));
        blockList.add(grassy_ground);

        gravel = new Block();
        gravel.id = t.gravel.ordinal();
        gravel.info = "Gravel";
        gravel.blocked = true;
        gravel.hardness = 8;
        gravel.textureRotation = 0;
        gravel.stackable = true;
        gravel.texture = new Texture(Gdx.files.internal("block/gravel.jpg"));
        blockList.add(gravel);
        
        ground = new Block();
        ground.id = t.ground.ordinal();
        ground.info = "Ground";
        ground.blocked = true;
        ground.hardness = 10;
        ground.textureRotation = 0;
        ground.stackable = true;
        ground.texture = new Texture(Gdx.files.internal("block/ground.png"));
        blockList.add(ground);
        
        groundBck = new Block();
        groundBck.id = t.groundBck.ordinal();
        groundBck.info = "Ground";
        groundBck.blocked = false;
        groundBck.hardness = 10;
        groundBck.textureRotation = 0;
        groundBck.stackable = true;
        groundBck.texture = new Texture(Gdx.files.internal("block/groundBck.png"));
        blockList.add(groundBck);
        
        /*houseStone = new Block();
        houseStone.blocked = false;
        houseStone.blocked = false;
        houseStone.hardness = 5;
        houseStone.textureRotation = 0;
        houseStone.texture = new Texture(Gdx.files.internal("block/rocky.png"));       */ 

        half_plank = new Block();
        half_plank.id = t.half_plank.ordinal();
        half_plank.info = "Half plank";
        half_plank.blocked = false;
        half_plank.hardness = 25;
        half_plank.textureRotation = 0;
        half_plank.stackable = true;
        half_plank.texture = new Texture(Gdx.files.internal("block/plank_halfblock.png"));
        blockList.add(half_plank);
        
        half_stone = new Block();
        half_stone.id = t.half_stone.ordinal();
        half_stone.info = "Half stone";
        half_stone.blocked = false;
        half_stone.hardness = 35;
        half_stone.textureRotation = 0;
        half_stone.stackable = true;
        half_stone.texture = new Texture(Gdx.files.internal("block/stone_halfblock.png"));
        blockList.add(half_stone);
        
        chest = new Block();
        chest.id = t.chest.ordinal();
        chest.info = "Chest";
        chest.blocked = false;
        chest.hardness = 25;
        chest.textureRotation = 0;
        chest.stackable = false;
        chest.texture = new Texture(Gdx.files.internal("block/chest.jpg"));
        blockList.add(chest);
        
        iron = new Block();
        iron.id = t.iron.ordinal();
        iron.info = "Iron";
        iron.blocked = true;
        iron.hardness = 60;
        iron.textureRotation = 0;
        iron.stackable = true;
        iron.texture = new Texture(Gdx.files.internal("block/iron.png"));
        blockList.add(iron);

        ladder = new Block();
        ladder.id = t.ladder.ordinal();
        ladder.info = "Ladder";
        ladder.blocked = false;
        ladder.hardness = 20;
        ladder.textureRotation = 0;
        ladder.stackable = true;
        ladder.texture = new Texture(Gdx.files.internal("block/ladder.png"));
        blockList.add(ladder);
        
        leaf = new Block();
        leaf.id = t.leaf.ordinal();
        leaf.info = "Leaf";
        leaf.blocked = true;
        leaf.hardness = 5;
        leaf.textureRotation = 0;
        leaf.stackable = true;
        leaf.texture = new Texture(Gdx.files.internal("block/leaf.png"));
        blockList.add(leaf);

        plank = new Block();
        plank.id = t.plank.ordinal();
        plank.info = "Plank";
        plank.blocked = false;
        plank.hardness = 25;
        plank.textureRotation = 0;
        plank.stackable = true;
        plank.texture = new Texture(Gdx.files.internal("block/plank.jpg"));
        blockList.add(plank);
        
        sand = new Block();
        sand.id = t.sand.ordinal();
        sand.info = "Sand";
        sand.blocked = true;
        sand.hardness = 7;
        sand.textureRotation = 0;
        sand.stackable = true;
        sand.texture = new Texture(Gdx.files.internal("block/sand.jpg"));
        blockList.add(sand);
        
        stone = new Block();
        stone.id = t.stone.ordinal();
        stone.info = "Stone";
        stone.blocked = true;
        stone.hardness = 30;
        stone.textureRotation = 0;
        stone.stackable = true;
        stone.texture = new Texture(Gdx.files.internal("block/rocky.png"));
        //stone.texture = MyAssetManager.manager.get("block/rocky.png",Texture.class); 
        blockList.add(stone);
        
        stone_stairs = new Block();
        stone_stairs.id = t.stone_stairs.ordinal();
        stone_stairs.info = "Stone stairs";
        stone_stairs.blocked = false;
        stone_stairs.hardness = 35;
        stone_stairs.textureRotation = 0;
        stone_stairs.isRotationAllowed = true;
        stone_stairs.stackable = true;
        stone_stairs.texture = new Texture(Gdx.files.internal("block/stone_stairs.png"));
        blockList.add(stone_stairs);
        
        torch = new Block();
        torch.id = t.torch.ordinal();
        torch.info = "Torch";
        torch.blocked = false;
        torch.hardness = 10;
        torch.textureRotation = 0;
        torch.stackable = true;
        torch.texture = new Texture(Gdx.files.internal("block/walltorch1.gif"));//torch.png"));
        blockList.add(torch);
        
        unbreakable = new Block();
        unbreakable.id = t.unbreakable.ordinal();
        unbreakable.info = "Unbreakable";
        unbreakable.blocked = true;
        unbreakable.hardness = 99;
        unbreakable.textureRotation = 0;
        unbreakable.stackable = false;
        unbreakable.texture = new Texture(Gdx.files.internal("block/unbreakable.png"));
        blockList.add(unbreakable);
        
        window = new Block();
        window.id = t.window.ordinal();
        window.info = "Window";
        window.blocked = false;
        window.hardness = 10;
        window.textureRotation = 0;
        window.stackable = true;
        window.texture = new Texture(Gdx.files.internal("block/window.jpg"));
        blockList.add(window);
        
        wood = new Block();
        wood.id = t.wood.ordinal();
        wood.info = "Wood";
        wood.blocked = true;
        wood.hardness = 20;
        wood.textureRotation = 0;
        wood.stackable = true;
        wood.texture = new Texture(Gdx.files.internal("block/wood.jpg"));
        blockList.add(wood);

        wood_stairs = new Block();
        wood_stairs.id = t.wood_stairs.ordinal();
        wood_stairs.info = "Wood stairs";
        wood_stairs.blocked = false;
        wood_stairs.hardness = 25;
        wood_stairs.textureRotation = 0;
        wood_stairs.isRotationAllowed = true;
        wood_stairs.stackable = true;
        wood_stairs.texture = new Texture(Gdx.files.internal("block/wood_stairs.png"));
        blockList.add(wood_stairs);

    }

    public static Block getBlockById(int id){
        if (id < 0)
            return null;
        
        for (Block block : blockList) 
        {
            if (block.id == id)
                return block; 
        }
        
        return null;
    }
    
}
