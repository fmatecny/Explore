/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.IntVector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.world.water.Water;
import static java.lang.Math.abs;

/**
 *
 * @author Fery
 */
public class Map {
    
    private Body[][] bodiesArray;
    private Block[][] groundBckArr;
    
    private int width;
    private int height;
    
    private int groundIndexX = 0;
    private int groundIndexY = 0;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        bodiesArray = new Body[width][height];        
        
        Ground ground = new Ground(width, height-10);
        Body[][] groundBodiesArr = ground.getBlockArray();
        groundBckArr = new Block[width][height];
       
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height-10; j++) {
                bodiesArray[i][j] = groundBodiesArr[i][j];
                groundBckArr[i][j] = ground.getBlockBckArray()[i][j];
            }
        } 
        
        groundIndexX = (int )(Math.random() * 10 + 10);
        
        while (groundIndexX < width-20) 
        {            
            for (int j = 0; j < height-10; j++) {
                if (groundBodiesArr[groundIndexX][j] == null)
                {
                    groundIndexY = j;
                    break;
                }
            }           
            
            int typeOfCreation = (int )(Math.random() * 100);
            if (typeOfCreation > 70) {
                createHouses(groundBodiesArr);
            }
            else{
                createTrees();
            }
            //TODO createWater();
        } 
    }
    
    private void createHouses(Body[][] groundBodiesArr) {
        int houseWidth = 0;
        int houseHeight = 0;
        int diff = 0;
        
        houseWidth = (int )(Math.random() * 2 + 9);

        
        for (int j = 0; j < height-10; j++) {
            if (groundBodiesArr[groundIndexX+houseWidth-1][j] == null)
            {
                diff = abs(j-groundIndexY);
                groundIndexY = (j+groundIndexY)/2;
                break;
            }
        }
        
        if (diff < 2)
        {
            houseHeight = (int )(Math.random() * 2 + 6);
            House house = new House(houseWidth,houseHeight,groundIndexX,groundIndexY);
            Body[][] houseArr = house.getHouse();

            for (int i = 0; i < houseWidth; i++) {
                for (int j = 0; j < houseHeight; j++) {
                    if (houseArr[i][j] != null){
                        bodiesArray[i+groundIndexX-1][j+groundIndexY] = houseArr[i][j];}
                }
            }
            groundIndexX += (int )(Math.random() * 15 + houseWidth);
        }
        

    }
    
    private void createTrees(){
        int treeWidth = 0;
        int treeHeight = 0;
        
        treeWidth = (int )(Math.random() * 5 + 4);
        if (treeWidth%2 == 0)
            treeWidth++;

        treeHeight = (int )(Math.random() * 5 + 5);
        Tree tree = new Tree(treeWidth,treeHeight, groundIndexX, groundIndexY);
        Body[][] treeArr = tree.getTree();

        for (int i = 0; i < treeWidth; i++) {
            for (int j = 0; j < treeHeight; j++) {
                if (treeArr[i][j] != null && (bodiesArray[i+groundIndexX-treeWidth/2][j+groundIndexY] == null)){
                    bodiesArray[i+groundIndexX-treeWidth/2][j+groundIndexY] = treeArr[i][j];}
            }
        }
        groundIndexX += (int )(Math.random() * 15 + treeWidth);
    
    }
    
    private void createWater(){
        Water water = new Water();
        //water.createBody(world, 0, 2, 10, 10); //world, x, y, width, height
        //water.setDebugMode(true);
    }
    
    
    public Block getBlock(float x, float y){
        if ((int)(y/Block.size) <= 0)
            y = Block.size;
        
        return (Block) bodiesArray[(int)(x/Block.size)][(int)(y/Block.size)].getUserData();
    
    }
    
    public Block getBlockByIdx(IntVector2 v){
        return (Block) bodiesArray[v.X][v.Y].getUserData();
    
    }
    
    public Block getBlockByIdx(int x, int y){
        return (Block) bodiesArray[x][y].getUserData();
    
    }

    public Body[][] getBodiesArray() {
        return bodiesArray;
    }
    
    /*public void removeBlock(int x, int y){
        bodiesArray[x][y] = AllBlocks.empty;
    }
    
    public void removeBlock(IntVector2 v){
        bodiesArray[v.X][v.Y] = AllBlocks.empty;
    }*/
    
    public IntVector2 getBlockIdxFromMouseXY(int x, int y, Vector2 cam){
        
        IntVector2 v = new IntVector2();
        
        v.X = (int)((x+(cam.x-MyGdxGame.width/2))/Block.size);
        v.Y = (int)((MyGdxGame.height-(y-(cam.y-MyGdxGame.height/2)))/Block.size);
        
        return v;
    
    
    }
    
    public boolean isBlockEmpty(IntVector2 idx){
        Block b = getBlockByIdx(idx);
        
        return (b == null || b == AllBlocks.empty);
    
    }
    
    public void draw(SpriteBatch spriteBatch){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bodiesArray[i][j] != null)
                    //stage.getBatch().draw( bodiesArray[i][j].texture, i*Block.size, j*Block.size, Block.size, Block.size);
                    drawWithRotation(spriteBatch, i, j);
                else if (groundBckArr[i][j] != AllBlocks.empty && groundBckArr[i][j] != null)
                    spriteBatch.draw( groundBckArr[i][j].texture, i*Block.size, j*Block.size, Block.size, Block.size);
            }

        }
    }

    private void drawWithRotation(SpriteBatch spriteBatch, int i, int j) {
       Block b = (Block)bodiesArray[i][j].getUserData();
       spriteBatch.draw(b.texture, 
                            i*Block.size, j*Block.size, 
                            Block.size/2, Block.size/2, 
                            Block.size, Block.size, 
                            1.0f, 1.0f, 
                            b.textureRotation, 
                            0, 0, 
                            b.texture.getWidth(), b.texture.getHeight(),  
                            false, false); 
    }
        
}
