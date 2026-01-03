/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.BlockObjectData;
import com.mygdx.game.Constants;
import com.mygdx.game.Inputs;
import com.mygdx.game.IntVector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.inventory.AllItems;
import com.mygdx.game.inventory.InventoryFurnace;
import com.mygdx.game.inventory.InventoryPackage;
import com.mygdx.game.inventory.Item;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.water.Lake;
import com.mygdx.game.world.water.Water;
import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class Map extends WorldObject{
    private int width = Constants.WIDTH_OF_MAP;
    private int height = Constants.HEIGHT_OF_MAP;
    private float w_in_m;
    private float h_in_m;
    private float ppm_viewport_ratio_x;
    private float ppm_viewport_ratio_y;
    
    // Ground 
    private Block[][] mapArray;
    private Block[][] mapBackgroundArray;
    private PerlinNoise2D perlinNoise2D;
    private int[] groundIndexArray;
    
    private int groundIndexX = 0;
    private int groundIndexY = 0;
    
    private ArrayList<Lake> lakeList = new ArrayList<>();
    private ArrayList<Chest> chestList = new ArrayList<>();
    private ArrayList<Furnace> furnaceList = new ArrayList<>();
    
    private int left, right, down, up, left_cam_edge, down_cam_edge;
    private int previousLeft = 0; //40; //should be position of camera(cam.x/Block.size) + min. 24
    private int previousRight = 0;//width;
    private int previousDown = 0;
    private int previousUp = height;
    private boolean isFirstCycle = true;
    
    private TextureAtlas textureAtlas;
    private Animation<AtlasRegion> breakBlockAnimation;
    
    private TextureAtlas textureTorchAtlas;
    private Animation<AtlasRegion> torchAnimation;
    private ArrayList<IntVector2> torchsPos = new ArrayList<>();
    private ArrayList<IntVector2> doorsUpPos = new ArrayList<>();
    private ArrayList<IntVector2> knightPos = new ArrayList<>();
    private ArrayList<IntVector2> treePos = new ArrayList<>();
    private IntVector2 kingPos = new IntVector2();
    private float stateTime = 0;
    private float stateTimeTorch = 0;
    
    private boolean isMining = false;
    private Block miningBlock = null;
    private Block minedBlock = null;
    IntVector2 rectVector = new IntVector2();
    
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Map() {
        w_in_m = MyGdxGame.width/Constants.PPM;
        h_in_m = MyGdxGame.height/Constants.PPM;
        ppm_viewport_ratio_x = (Gdx.graphics.getWidth()*Constants.PPM)/MyGdxGame.width;
        ppm_viewport_ratio_y = (Gdx.graphics.getHeight()*Constants.PPM)/MyGdxGame.height;
        
        mapArray = new Block[width][height];        
        mapBackgroundArray = new Block[width][height];
        perlinNoise2D = new PerlinNoise2D();

        textureAtlas = new TextureAtlas("block/cracking1.txt");
        breakBlockAnimation = new Animation<>(0.5f, textureAtlas.getRegions());
        textureTorchAtlas = new TextureAtlas("block/walltorch.txt");
        torchAnimation = new Animation<>(0.1f, textureTorchAtlas.getRegions());
    }
    
    
    private void reset()
    {
        for (int x = 0; x < mapArray.length; x++) {
            for (int y = 0; y < mapArray[x].length; y++) {
                mapArray[x][y] = null;
                mapBackgroundArray[x][y] = null;
            }
        }
        groundIndexArray = null;

        groundIndexX = 0;
        groundIndexY = 0;

        lakeList.clear();
        chestList.clear();
        furnaceList.clear();
        doorsUpPos.clear();
        knightPos.clear();
        treePos.clear();
    }
    
    public void generateMap() {

        
        boolean isCaslteGenerated = false;
        
        generateGround(width, height-Constants.HEIGHT_OF_SKY);
        
        // initial value - [10, 20)
        groundIndexX = (int )(Math.random() * 10 + 10);
        
        
        boolean isVillageGenerating = false;
        int numberOfHouseInVillage = 0;
        int villageAvalaibleFromIndexX = 0;
        int percent = 0;
        
        // generate objects 
        while (groundIndexX < width-20) 
        {    
            // find top of the ground  
            groundIndexY = groundIndexArray[groundIndexX];
            
            // based on percent generates object
            if (isVillageGenerating == false)
                percent = (int )(Math.random() * 100);
            
            if (percent > 50 && groundIndexY < 100 && isVillageGenerating == false && villageAvalaibleFromIndexX <= groundIndexX)
            {
                numberOfHouseInVillage = (int )(Math.random() * 4) + 3; //3 - 7 houses
                System.out.println("Number of Houses = " + numberOfHouseInVillage + ", groundIndexX = " + groundIndexX + ", groundIndexY = " + groundIndexY);
            
                isVillageGenerating = true;
                int lastIndex = groundIndexY;
                //check if is it possible to generate village - ground level needs to be <=3 on 10blocks
                for (int i = 1; i <= numberOfHouseInVillage && groundIndexX+i*10 < width; i++) {
                    for (int j = 0; j < height-Constants.HEIGHT_OF_SKY; j++) 
                    {
                        if (mapArray[groundIndexX+i*10-1][j] == null)//TODO out of range
                        {
                            System.out.println("j = " + j + ", lastIndex = " + lastIndex);
                            if (abs(j - lastIndex) > 3){
                                i = numberOfHouseInVillage+1;
                                isVillageGenerating = false;
                            }
                            break;
                        }
                    } 
                }
            }
            
            if (isVillageGenerating) {
                generateHouse();
                if (--numberOfHouseInVillage <= 0)
                {
                    isVillageGenerating = false;
                    villageAvalaibleFromIndexX = groundIndexX+300;
                }
            }
            else if (percent > 5){
                createTrees();
            }
            else{
                if (isCaslteGenerated == false)
                    isCaslteGenerated = generateCastle();
                else
                    createWater();
            } 
        }
        
        if (isCaslteGenerated == false)
        {
            reset();
            System.err.println("Generation of map failed. Retry");
            this.generateMap();
            return;
        }
            
        
        //generateCaves();
    }
    
    
    private void generateGround(int w, int h){
        int randomIdx = 0;
        int[] noiseArr = perlinNoise2D.getNoiseArr(w/2, h);
        
        groundIndexArray = new int[w];
        
        for (int i = 0; i < w; i++) 
        {
            for (int j = 0; j < h; j++) 
            {
                if (j == 0)
                    mapArray[i][j] = AllBlocks.unbreakable;
                else if (j == noiseArr[i/2]){
                    mapArray[i][j] = new Block(AllBlocks.ground);
                    mapBackgroundArray[i][j] = AllBlocks.groundBck;
                }
                else if (j-1 == noiseArr[i/2]){
                    mapArray[i][j] = new Block(AllBlocks.grassy_ground);
                    groundIndexArray[i] = j;
                }
                else if (j < noiseArr[i/2]){
                     randomIdx = (int) (Math.random() * 100);
                    //diamond area
                    if (j <= 10)
                    {
                        if (randomIdx < 5)
                            mapArray[i][j] = new Block(AllBlocks.diamond);
                        else if (randomIdx < 8 && j > 5)
                            mapArray[i][j] = new Block(AllBlocks.gold);
                        else if (randomIdx < 10 && j > 3)
                            mapArray[i][j] = new Block(AllBlocks.coal);
                        else
                            mapArray[i][j] = new Block(AllBlocks.rock);
                    
                    }//gold area
                    else if(j <= 13)
                    {
                        if (randomIdx < 7)
                            mapArray[i][j] = new Block(AllBlocks.gold);
                        else if (randomIdx < 13)
                            mapArray[i][j] = new Block(AllBlocks.coal);
                        else if (randomIdx < 17)
                            mapArray[i][j] = new Block(AllBlocks.iron);
                        else
                            mapArray[i][j] = new Block(AllBlocks.rock);
                    }//iron area
                    else if(j <= 25)
                    {
                        if (randomIdx < 10)
                            mapArray[i][j] = new Block(AllBlocks.iron);
                        else if (randomIdx < 20)
                            mapArray[i][j] = new Block(AllBlocks.coal);
                        else
                            mapArray[i][j] = new Block(AllBlocks.rock);
                    }//coal area
                    else if(j <= 35)
                    {
                        if (randomIdx < 15)
                            mapArray[i][j] = new Block(AllBlocks.coal);
                        else if (randomIdx < 30 && j > 30)
                            mapArray[i][j] = new Block(AllBlocks.ground);
                        else
                            mapArray[i][j] = new Block(AllBlocks.rock);
                    }
                    else
                    {
                        if (randomIdx < 10)
                            mapArray[i][j] = new Block(AllBlocks.coal);
                        else if (randomIdx < 80)
                            mapArray[i][j] = new Block(AllBlocks.ground);
                        else
                            mapArray[i][j] = new Block(AllBlocks.rock);
                    }

                    mapBackgroundArray[i][j] = AllBlocks.groundBck;
                } 
            }
        }
    }
    
    private void generateHouse() {
        int houseWidth = (int )(Math.random() * 2 + 9);
        int houseHeight;
        int verticalMetre = 10;

        for (int j = 0; j < height-Constants.HEIGHT_OF_SKY; j++) 
        {
            if (mapArray[groundIndexX+houseWidth-1][j] == null)
            {
                verticalMetre = abs(j-groundIndexY);
                // avarage of left and right ground indexes
                groundIndexY = (j+groundIndexY)/2;
                break;
            }
        }
        
        // maximal vertical metre must be lower than 3 for generating of house
        if (verticalMetre < 3)
        {
            houseHeight = (int )(Math.random() * 2 + 6);
            House house = new House(houseWidth,houseHeight,groundIndexX,groundIndexY);
            Block[][] houseArr = house.getHouse();
            doorsUpPos.add(house.getDoorUpPos());

            for (int x = 0; x < houseWidth; x++) 
            {
                for (int y = 0; y < houseHeight; y++) 
                {
                    if (houseArr[x][y] != null)
                        mapArray[x+groundIndexX-1][y+groundIndexY] = houseArr[x][y];
                }
            }
            groundIndexX += (int )(Math.random() * 15 + houseWidth);
        }
    }
    
    /*private void generateSmithHouse(){ //TODO
        int houseWidth = (int )(Math.random() * 2 + 9);
        int houseHeight;
        int verticalMetre = 0;

        for (int j = 0; j < height-Constants.HEIGHT_OF_SKY; j++) 
        {
            if (mapArray[groundIndexX+houseWidth-1][j] == null)
            {
                verticalMetre = abs(j-groundIndexY);
                // avarage of left and right ground indexes
                groundIndexY = (j+groundIndexY)/2;
                break;
            }
        }
        
        // maximal vertical metre must be lower than 3 for generating of house
        if (verticalMetre < 3)
        {
            houseHeight = (int )(Math.random() * 2 + 6);
            House house = new House(houseWidth,houseHeight,groundIndexX,groundIndexY);
            Block[][] houseArr = house.getHouse();
            doorsUpPos.add(house.getDoorUpPos());

            for (int x = 0; x < houseWidth; x++) 
            {
                for (int y = 0; y < houseHeight; y++) 
                {
                    if (houseArr[x][y] != null)
                        mapArray[x+groundIndexX-1][y+groundIndexY] = houseArr[x][y];
                }
            }
            groundIndexX += (int )(Math.random() * 15 + houseWidth);
        }
    }*/
    
    private boolean generateCastle(){
        int castleWidth = (int )(Math.random() * 10 + 100);
        int castleHeight;
        int verticalMetre = 10;

        if (groundIndexX+castleWidth-1 >= mapArray.length)
            return false;
        
        int minIdxY = height;
        int maxIdxY = 0;
        for (int x = 0; x < castleWidth; x++) 
        {
            if (minIdxY > groundIndexArray[groundIndexX+x])
            {
                minIdxY = groundIndexArray[groundIndexX+x];
            }
            
            if (maxIdxY < groundIndexArray[groundIndexX+x])
            {
                maxIdxY = groundIndexArray[groundIndexX+x];
            }
        }
        
        verticalMetre = maxIdxY-minIdxY;
        // avarage of left and right ground indexes
        groundIndexY = (maxIdxY+minIdxY)/2;
        
        // maximal vertical metre must be lower than 5 for generating of castle
        if (verticalMetre < 5)
        {
            castleHeight = (int )(Math.random() * 5 + 24);
            Castle castle = new Castle(castleWidth,castleHeight,groundIndexX,groundIndexY);
            Block[][] castleArr = castle.getCastle();
            Block[][] castleBckArr = castle.getCastleBackground();

            for (int x = 0; x < castleWidth; x++) 
            {
                for (int y = 0; y < castleHeight; y++) 
                {
                    if (castleBckArr[x][y] != null)
                        mapBackgroundArray[x+groundIndexX-1][y+groundIndexY] = castleBckArr[x][y];
                    else
                        mapBackgroundArray[x+groundIndexX-1][y+groundIndexY] = null;
                    
                    
                    if (castleArr[x][y] == null)
                    {
                        removeBody(x+groundIndexX-1, y+groundIndexY);
                        mapArray[x+groundIndexX-1][y+groundIndexY] = null;
                    }
                    else
                    {
                        if (castleArr[x][y].id == AllBlocks.empty.id)
                        {
                            //mapBackgroundArray[x+groundIndexX-1][y+groundIndexY] = AllBlocks.gravel;
                            if (mapArray[x+groundIndexX-1][y+groundIndexY] != null)//there should be empty block, only background
                            {
                                removeBody(x+groundIndexX-1, y+groundIndexY);
                                mapArray[x+groundIndexX-1][y+groundIndexY] = null;
                            }
                        }
                        else
                        {
                            mapArray[x+groundIndexX-1][y+groundIndexY] = castleArr[x][y];
                        
                            if (mapArray[x+groundIndexX-1][y+groundIndexY].id == AllBlocks.torch.id)
                                torchsPos.add(new IntVector2(x+groundIndexX-1, y+groundIndexY));
                            else if (mapArray[x+groundIndexX-1][y+groundIndexY].id == AllBlocks.chest.id)
                            {
                                //TODO generate random items in chests
                                Chest chest = new Chest(x+groundIndexX-1, y+groundIndexY);
                                chest.chestPackage.addObject(new Item(AllItems.diamondArmor));
                                chestList.add(chest);
                            }
                        }
                    }
                }
            }
            
            // set new ground index to avoid caves go trough 
            for (int x = 0; x < castleWidth; x++) 
            {
                groundIndexArray[groundIndexX+x] = groundIndexY-2;
            }
            
            
            System.out.println("Castle groundIndexX = " + groundIndexX + ", groundIndexY = " + groundIndexY);
            groundIndexX += (int )(Math.random() * 15 + castleWidth);
            
            knightPos = castle.getKnightPositions();
            kingPos = castle.getKingPosition();
            
            return true;
        }
        
        return false;
    }
    
    private boolean generateCaves(){
        int cavesArrayWidth = Constants.WIDTH_OF_MAP;
        int cavesArrayHeight = Constants.HEIGHT_OF_MAP-Constants.HEIGHT_OF_SKY;
        Caves caves = new Caves(cavesArrayWidth, cavesArrayHeight, perlinNoise2D.getNoiseArr2d(cavesArrayWidth, cavesArrayHeight, true));
        Block[][] cavesArray = caves.getCavesArray();

        for (int x = 1; x < cavesArray.length; x++) 
        {
            for (int y = 1; y < cavesArray[x].length; y++) 
            {
                // remove only when there is cave && block exists in map && is not above ground
                if (cavesArray[x][y] != null && mapArray[x][y] != null && y <= groundIndexArray[x])
                {
                    removeBody(x, y);
                    mapArray[x][y] = null;
                }
            }
        }

        return true;
    }
    
    private void createTrees(){
        boolean treePosSet = false;
        int treeWidth = (int )(Math.random() * 5 + 4);
        int treeHeight = (int )(Math.random() * 5 + 5);

        if (treeWidth%2 == 0)
            treeWidth++;

        Tree tree = new Tree(treeWidth,treeHeight, groundIndexX, groundIndexY);
        Block[][] treeArr = tree.getTree();
        boolean mushroomSet = !tree.hasPlant();

        for (int i = 0; i < treeWidth; i++) {
            for (int j = 0; j < treeHeight; j++) {
                
                if (mapArray[i+groundIndexX-treeWidth/2][j+groundIndexY] == null)
                {
                    if (treeArr[i][j] != null )
                    {
                        mapArray[i+groundIndexX-treeWidth/2][j+groundIndexY] = treeArr[i][j];

                        if (treePosSet == false)
                        {
                            treePosSet = true;
                            treePos.add(new IntVector2(groundIndexX, j+groundIndexY));
                        }
                    }
                    else if (i == tree.getPlantXPos() && mushroomSet == false && 
                            mapArray[i+groundIndexX-treeWidth/2][j+groundIndexY-1] != null)
                    {
                        if  (mapArray[i+groundIndexX-treeWidth/2][j+groundIndexY-1].id != AllBlocks.leaf.id)
                        {
                            mushroomSet = true;
                            mapArray[i+groundIndexX-treeWidth/2][j+groundIndexY] = tree.getPlant();
                        }
                    }
                }
            }
        }
        
        groundIndexX += (int )(Math.random() * 15 + treeWidth);
    
    }
    
    private void createWater(){

        int widthOfLake = (int)(Math.random() * 10 ) + 5;
        int heightOfLake = (int)(Math.random() * widthOfLake/4) + widthOfLake/3;

        Lake lake = new Lake(groundIndexX, groundIndexY, widthOfLake, heightOfLake);
        lakeList.add(lake);
        
        ArrayList<Water> waterList = lake.getWaterList();

        for (Water water : waterList) {
            
            if (water.hasWaves())
            {
                if (mapArray[water.x-1][water.y] == null)
                {
                    //addBodyToIdx(water.x-1, water.y, AllBlocks.grassy_ground);
                    mapArray[water.x-1][water.y] = new Block(AllBlocks.sand);
                    mapBackgroundArray[water.x-1][water.y] = AllBlocks.groundBck;
                    if (mapArray[water.x-1][water.y-1] != null){
                        removeBlock(water.x-1, water.y-1);
                        mapArray[water.x-1][water.y-1] = new Block(AllBlocks.sand);
                        mapBackgroundArray[water.x-1][water.y-1] = AllBlocks.groundBck;}
                }
                if (mapArray[water.x+(int)(water.width/0.4f)][water.y] == null)
                {
                    //addBodyToIdx(water.x+(int)(water.width/0.4f), water.y, AllBlocks.grassy_ground);
                    mapArray[water.x+(int)(water.width/0.4f)][water.y] = new Block(AllBlocks.sand);
                    mapBackgroundArray[water.x+(int)(water.width/0.4f)][water.y] = AllBlocks.groundBck;
                    if (mapArray[water.x+(int)(water.width/0.4f)][water.y-1] != null){
                        removeBlock(water.x+(int)(water.width/0.4f), water.y-1);
                        mapArray[water.x+(int)(water.width/0.4f)][water.y-1] = new Block(AllBlocks.sand);
                        mapBackgroundArray[water.x+(int)(water.width/0.4f)][water.y-1] = AllBlocks.groundBck;}

                }
            }

            
            
            
            for (int y = water.y; y < water.y + water.height/0.4f; y++) {
                for (int x = water.x; x < water.x + water.width/0.4f; x++) {
                    if (water.hasWaves()){
                        if (mapArray[x][y+1] != null)
                            removeBlock(x, y+1);
                    }
                    
                    
                    if (mapArray[x][y] != null)
                        removeBlock(x, y);
                    mapBackgroundArray[x][y] = AllBlocks.groundBck;
                    
                    if (groundIndexY-heightOfLake == water.y)
                    {
                        int numOfSands = (int )(Math.random() * 2 + 2);
                        //left bottom and right
                        for (int i = 1; i <= numOfSands; i++) {
                            if (mapArray[x-i][water.y-1] != null){
                                    removeBlock(x-i, water.y-1);
                                }
                            mapArray[x-i][water.y-1] = new Block(AllBlocks.sand);  
                            
                            if (mapArray[water.x+(int)(water.width/0.4f) + i-1][water.y-1] != null){
                                    removeBlock(water.x+(int)(water.width/0.4f) + i-1,water.y-1);
                                }
                            mapArray[water.x+(int)(water.width/0.4f) + i-1][water.y-1] = new Block(AllBlocks.sand);  
                        }

                        
                        
                        numOfSands = (int )(Math.random() * 2 + 1);
                        for (int i = 1; i <= numOfSands; i++) {
                            //add sand to bottom

                                if (mapArray[x][water.y-i] != null){
                                    removeBlock(x, water.y-i);
                                }
                                mapArray[x][water.y-i] = new Block(AllBlocks.sand);
                            }     
                    }
                    
                    
 
                }
                
                int numOfSands = (int )(Math.random() * 3 + 2);
                
                for (int i = 1; i <= numOfSands; i++) 
                {
                    //add sand to left side
                    if (mapArray[water.x-i][water.y] != null){
                        removeBlock(water.x-i, water.y);
                    }
                    mapArray[water.x-i][water.y] = new Block(AllBlocks.sand);
                    mapBackgroundArray[water.x-i][water.y] = AllBlocks.groundBck;   
                }
                
                numOfSands = (int )(Math.random() * 4 + 1);
                
                for (int i = 0; i <= numOfSands; i++) 
                {
                    //add sand to right side
                    if (mapArray[water.x+(int)(water.width/0.4f) + i][water.y] != null){
                        removeBlock(water.x+(int)(water.width/0.4f) + i, water.y);
                    }
                    mapArray[water.x+(int)(water.width/0.4f) + i][water.y] = new Block(AllBlocks.sand);
                    mapBackgroundArray[water.x+(int)(water.width/0.4f) + i][water.y] = AllBlocks.groundBck;
                }
                
                

            }

            
        }

        groundIndexX += (int )(Math.random() * 15 + widthOfLake+5);
    }
    
    public void rotateBlock(Body b){
        getBlockByIdx((int)(b.getPosition().x/Block.size_in_meters),(int)(b.getPosition().y/Block.size_in_meters)).textureRotation -= 90;
    }
    
    
    private Block getBlock(int x, int y){
        if (y < 0)
            y = 0;
        
        if (x < 0)
            x = 0;
        
        if (x >= mapArray.length)
            return null;
        
        if (y >= mapArray[x].length)
            return null;
        
        return mapArray[x][y];
    
    }
    
    public Block getBlockByIdx(IntVector2 v){
        if (v == null)
            return null;
        
        return getBlock(v.X,v.Y);
    
    }
    
    public Block getBlockByIdx(int x, int y){
        return getBlock(x, y);
    
    }

    public int getBlockId(IntVector2 v){
        return getBlockId(v.X, v.Y); 
    }
    
    public int getBlockId(int x, int y){
        Block b = getBlockByIdx(x, y);
        return b == null ? -1 : b.id; 
    }
    
    public Block[][] getBlockArray() {
        return mapArray;
    }    
    
    public Block[][] getBckArray(){
        return mapBackgroundArray;
    }
        
    public void removeBlock(int x, int y){
            if (mapArray[x][y].id == AllBlocks.door_down.id)
                removeBody(x, y+1);
            if (mapArray[x][y].id == AllBlocks.door_up.id)
                removeBody(x, y-1);
            removeBody(x, y);
            
            if (mapArray[x][y].id == AllBlocks.door_down.id)
                mapArray[x][y+1] = null;
            if (mapArray[x][y].id == AllBlocks.door_up.id)
                mapArray[x][y-1] = null;
            
            if (minedBlock == mapArray[x][y]){
                miningBlock = null;
                minedBlock = null;}
            
            if (mapArray[x][y].id == AllBlocks.torch.id)
            {
                for (IntVector2 torchsPo : torchsPos) {
                    if (torchsPo.X == x && torchsPo.Y == y){
                        torchsPos.remove(torchsPo);
                        break;
                    }
                }
            }
            
            if (mapArray[x][y].id == AllBlocks.chest.id)
            {
                for (Chest chest : chestList) {
                    if (chest.positon.X == x && chest.positon.Y == y){
                        chestList.remove(chest);
                        break;
                    }
                }
            }
            
            if (mapArray[x][y].id == AllBlocks.furnace.id)
            {
                for (Furnace furnace : furnaceList) {
                    if (furnace.positon.X == x && furnace.positon.Y == y){
                        furnaceList.remove(furnace);
                        break;
                    }
                }
            }
                
            
            mapArray[x][y] = null;
           
    }
    
    public boolean addBodyToIdx(int x, int y, Block b){
        //if block is on background or front, torch is always on background
        b.blocked = !Inputs.instance.control_left && b.id != AllBlocks.torch.id && b.id != AllBlocks.ladder.id;
        
        //create new body beacuse in Inventar is only one body 
        if (b.id == AllBlocks.door.id || b.id == AllBlocks.door_up.id || b.id == AllBlocks.door_down.id)
        {
            if (mapArray[x][y+1] != null)
                return false;
            
            mapArray[x][y] = new Block(AllBlocks.door_down);
            mapArray[x][y+1] = new Block(AllBlocks.door_up);
            mapArray[x][y+1].setBody(createBodie(GameScreen.world, x, y+1, b.blocked, mapArray[x][y+1].id));
        }
        else{
            mapArray[x][y] = new Block(b);
        }
        
        if (b.id == AllBlocks.torch.id)
            torchsPos.add(new IntVector2(x, y));
        else if (b.id == AllBlocks.chest.id)
            chestList.add(new Chest(x, y));
        else if (b.id == AllBlocks.furnace.id)
            furnaceList.add(new Furnace(x, y));
        
        mapArray[x][y].setBody(createBodie(GameScreen.world, x, y, b.blocked, mapArray[x][y].id));
        
        return true;
    }
    
    public void removeBody(int x, int y){
        if (mapArray[x][y] != null)
        {
            if (mapArray[x][y].getBody() != null)
            {
                GameScreen.world.destroyBody(mapArray[x][y].getBody());
                mapArray[x][y].setBody(null);
            }
        }
    }
    
    public void removeBody(IntVector2 v){
        removeBody(v.X, v.Y);
    }
    
    public IntVector2 getBlockIdxFromMouseXY(int x, int y, Vector2 cam){
        
        IntVector2 v = new IntVector2();
        
        v.X = (int)((x+(cam.x-MyGdxGame.width/2))/Block.size_in_meters);
        v.Y = (int)((MyGdxGame.height-(y-(cam.y-MyGdxGame.height/2)))/Block.size_in_meters);
        
        return v;
    
    
    }
    
    public boolean isBlockEmpty(IntVector2 idx){
        Block b = getBlockByIdx(idx);
        
        return (b == null || b == AllBlocks.empty);
    
    }
    
    public void draw(SpriteBatch spriteBatch, Vector2 cam){
        
        left_cam_edge = (int) (cam.x/Block.size_in_meters - MyGdxGame.width/2/Block.size_in_pixels);
        down_cam_edge = (int) (cam.y/Block.size_in_meters - MyGdxGame.height/2/Block.size_in_pixels);
        
        left = left_cam_edge - Constants.SIZE_OF_CHUNK;
        right = left_cam_edge + (MyGdxGame.width/Block.size_in_pixels) + Constants.SIZE_OF_CHUNK;
        down = down_cam_edge;// + Constants.SIZE_OF_CHUNK;
        up = height; //down_cam_edge + MyGdxGame.height/Block.size_in_pixels + Constants.SIZE_OF_CHUNK;
        
        /*
        int left = (int) (((cam.x*100.0f)-640)/40)+Constants.SIZE_OF_CHUNK;
        right = (int) (((cam.x*100.0f)+640)/40);
        down = (int) (((cam.y*100.0f)-360)/40)+Constants.SIZE_OF_CHUNK;
        jump = (int) (((cam.y*100.0f)+360)/40);*/
        
        if (left < 0)
            left = 0;
        left = (((int)(left/Constants.SIZE_OF_CHUNK))*Constants.SIZE_OF_CHUNK);
        

        if (right%Constants.SIZE_OF_CHUNK != 0)
            right = (((int)(right/Constants.SIZE_OF_CHUNK))*Constants.SIZE_OF_CHUNK)+1;
        if (right > width)
            right = width;
        
        if (down < 0)
            down = 0;
        if (down > height)
            down = height;
        down = (((int)(down/Constants.SIZE_OF_CHUNK))*Constants.SIZE_OF_CHUNK);
        /*
        if (up > height)
            up = height;
        if (up < 0)
            up = 0;
        up = (((int)(up/Constants.SIZE_OF_CHUNK))*Constants.SIZE_OF_CHUNK);*/
        
        if(isFirstCycle)
        {
            previousLeft = right;
            previousRight = right;
            isFirstCycle = false; 
        }

        // create bodies -> one chunck on left side - when player is going to the left
        if (previousLeft > left)
            createBodyToMap(left, previousLeft, down, up);
        
        // remove bodies -> one chunck on left side - when player is going to the right
        if (previousLeft < left)
            removeBodyFromMap(previousLeft, left, down, up);
        
        
        if (previousRight < right)
            createBodyToMap(previousRight, right, down, up);
        
        if (previousRight > right)
            removeBodyFromMap(right, previousRight, down, up);
        
        
        if (previousDown > down)
            createBodyToMap(left, right, down, previousDown);
        
        if (previousDown < down)
            removeBodyFromMap(left, right, previousDown, down);
        /*
        if (previousUp > up )
            removeBodyFromMap(left, right, up, previousUp);
        
        if (previousUp < up)
            createBodyToMap(left, right, previousUp, up);*/
        
        previousLeft = left;
        previousRight = right;
        previousDown = down;
        previousUp = up;
                
        for (int i = left; i < right; i++)
        {
            for (int j = down; j < up; j++) 
            {
                if (mapArray[i][j] != null)
                {
                        
                    if (mapArray[i][j].isWholeBlock == false)
                    {
                        if (mapBackgroundArray[i][j] != AllBlocks.empty && mapBackgroundArray[i][j] != null)
                            spriteBatch.draw(mapBackgroundArray[i][j].texture, i*Block.size_in_meters, j*Block.size_in_meters, Block.size_in_meters, Block.size_in_meters);
                    }
                    
                    if (mapArray[i][j].id != AllBlocks.torch.id)// dont need to show torch, it will be animate
                        drawWithRotation(spriteBatch, i, j);
                }
                else if (mapBackgroundArray[i][j] != AllBlocks.empty && mapBackgroundArray[i][j] != null)
                {
                    spriteBatch.draw(mapBackgroundArray[i][j].texture, i*Block.size_in_meters, j*Block.size_in_meters, Block.size_in_meters, Block.size_in_meters);
                }
            }
        }

        
        if (isMining){
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = breakBlockAnimation.getKeyFrame(stateTime);
            IntVector2 v = ((BlockObjectData)miningBlock.getBody().getUserData()).getPos();
            spriteBatch.draw(currentFrame, v.X*Block.size_in_meters, v.Y*Block.size_in_meters, Block.size_in_meters, Block.size_in_meters);
            if (breakBlockAnimation.isAnimationFinished(stateTime)){
                isMining = false; 
                minedBlock = miningBlock;
            }
        }
        
        if (torchsPos.isEmpty() && stateTimeTorch != 0){
            stateTimeTorch = 0;}
        else{
            stateTimeTorch += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = torchAnimation.getKeyFrame(stateTimeTorch, true);
            for (IntVector2 torchPos : torchsPos) {
                spriteBatch.draw(currentFrame, torchPos.X*Block.size_in_meters, torchPos.Y*Block.size_in_meters, Block.size_in_meters, Block.size_in_meters);   
            }
        }
    }

    private void drawWithRotation(SpriteBatch spriteBatch, int i, int j) {
       Block b = mapArray[i][j];
       spriteBatch.draw(b.texture, 
                            i*Block.size_in_meters, j*Block.size_in_meters, 
                            Block.size_in_meters/2, Block.size_in_meters/2, 
                            Block.size_in_meters, Block.size_in_meters, 
                            1.0f, 1.0f, 
                            b.textureRotation, 
                            0, 0, 
                            b.texture.getWidth(), b.texture.getHeight(),  
                            false, false); 
    }
    
    public void drawLake(){
        for (Lake lake : lakeList)
        {
            lake.draw();
        }
    
    }

    public void drawRectOnBlock(Body b, Vector2 cam, Vector2 player){
        if (b.getUserData() instanceof BlockObjectData)
        {
            IntVector2 v = ((BlockObjectData)b.getUserData()).getPos();
            if (getBlockByIdx(v).id == AllBlocks.unbreakable.id)
                return; 
                        
            shapeRenderer.begin(ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            //System.out.println(v.X + "|" + v.Y);
            if (getBlockByIdx(v) != null)
            {
                shapeRenderer.rect( v.X * Block.size_in_pixels * Gdx.graphics.getWidth()/MyGdxGame.width - cam.x*ppm_viewport_ratio_x + Gdx.graphics.getWidth()/2.0f,
                                    v.Y * Block.size_in_pixels * Gdx.graphics.getHeight()/MyGdxGame.height - cam.y*ppm_viewport_ratio_y + Gdx.graphics.getHeight()/2.0f, 
                                    Block.size_in_pixels*Gdx.graphics.getWidth()/MyGdxGame.width, 
                                    Block.size_in_pixels*Gdx.graphics.getHeight()/MyGdxGame.height);
                rectVector.set(v);
                
            }
            if (Inputs.instance.debugMode)
            {
                shapeRenderer.line(
                        (b.getPosition().x  - cam.x + w_in_m/2)*ppm_viewport_ratio_x,
                        (b.getPosition().y  - cam.y + h_in_m/2)*ppm_viewport_ratio_y, 
                        (player.x           - cam.x + w_in_m/2)*ppm_viewport_ratio_x,
                        (player.y           - cam.y + h_in_m/2)*ppm_viewport_ratio_y);
            }
            shapeRenderer.end();
        }
   
    }
    
    public void mining(IntVector2 v, int hitForce){
        if (v.equal(rectVector) == false)
            return;
        
        if ((miningBlock == null && minedBlock == null) ||
            (getBlockByIdx(v) != miningBlock) ) 
        {
            int damage = hitForce;
            
            if (Inputs.instance.debugMode)
                damage = 100;
 
            miningBlock = getBlockByIdx(v);
            stateTime = 0;
            isMining = true;
            breakBlockAnimation.setFrameDuration(miningBlock.hardness/(10.0f*damage));
        }
    }
    
    public boolean isMiningDone(){
        return !isMining && minedBlock!=null; 
    }
    
    public void stopMining(){
        isMining = false;
        stateTime = 0;
        miningBlock = null;
        minedBlock = null;
    }
    
    private void createBodyToMap(int fromX, int toX, int fromY, int toY) {
        for (int x = fromX; x < toX; x++) 
        {
            for (int y = fromY; y < toY; y++) 
            {
                if (mapArray[x][y] != null)
                    mapArray[x][y].setBody(createBodie(GameScreen.world, x, y, mapArray[x][y].blocked, mapArray[x][y].id));
            }
        }
    }

    private void removeBodyFromMap(int fromX, int toX, int fromY, int toY) {
        for (int x = fromX; x < toX; x++) 
        {
            for (int y = fromY; y < toY; y++) 
            {
                if (mapArray[x][y] != null)
                    removeBody(x, y);
            }
        }
    }
    
    public void addTorchToPos(IntVector2 pos){
        torchsPos.add(pos);
    }
    
    public InventoryPackage getChestPackage(IntVector2 v){
        for (Chest chest : chestList) {
            if (chest.positon.equal(v))
                return chest.chestPackage;
        }
        return null;
    }
    
    public InventoryFurnace getInvenotryFurnace(IntVector2 v){
        for (Furnace furnace : furnaceList) {
            if (furnace.positon.equal(v))
                return furnace.inventoryFurnace;
        }
        return null;
    }

    public ArrayList<Chest> getChestList() {
        return chestList;
    }

    public ArrayList<Furnace> getFurnaceList() {
        return furnaceList;
    }

    public ArrayList<IntVector2> getDoorsUpPos() {
        return doorsUpPos;
    }
    
    public ArrayList<IntVector2> getKnightPos(){
        return knightPos;
    }

    public ArrayList<IntVector2> getTreePos() {
        return treePos;
    }

    
    public IntVector2 getKingPos(){
        return kingPos;
    }
    
    public Vector2 getFreePosition(int x) {
        
        for (int i = 0; i < mapArray[x].length; i++)
        {
            if (mapArray[x][i] == null)
                return new Vector2(x*Block.size_in_meters, i*Block.size_in_meters);
        }
        
        return null;
    }
    
    public Vector2 getRandomGroundPosForEntity(){
        int positionX = (int) (Math.random() * width);
        
        for (int y = mapArray[positionX].length-1; y > 1; y--) 
        {
            if (mapArray[positionX][y-1] != null)
            {
                if (mapArray[positionX][y-1].blocked)
                    return new Vector2(positionX*Block.size_in_meters, y*Block.size_in_meters);
            }
        }
        return new Vector2(0, 0);
    }
    
}
