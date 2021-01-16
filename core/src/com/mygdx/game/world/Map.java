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
import com.mygdx.game.Constants;
import com.mygdx.game.Inputs;
import com.mygdx.game.IntVector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.world.water.Water;
import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author Fery
 */
public class Map extends WorldObject{
    
    private Block[][] mapArray;
    
    
    private int width = Constants.WIDTH_OF_MAP;
    private int height = Constants.HEIGHT_OF_MAP;
    
    // Ground 
    private Block[][] groundBckArr;
    private PerlinNoise2D perlinNoise2D;
    private int[] noiseArr;
    
    private int groundIndexX = 0;
    private int groundIndexY = 0;
    
    private ArrayList<Water> waterList = new ArrayList<>();
    
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
    private float stateTime = 0;
    private float stateTimeTorch = 0;
    private boolean isMining = false;
    private Block miningBlock = null;
    private Block minedBlock = null;
    IntVector2 rectVector = new IntVector2();
    
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Map() {
        mapArray = new Block[width][height];        
        
        generateMap();

        textureAtlas = new TextureAtlas("block/cracking1.txt");
        breakBlockAnimation = new Animation<>(0.5f, textureAtlas.getRegions());
        textureTorchAtlas = new TextureAtlas("block/walltorch.txt");
        torchAnimation = new Animation<>(0.1f, textureTorchAtlas.getRegions());
    }
    
    private void generateMap() {
        
        generateGround(width, height-Constants.HEIGHT_OF_SKY);
        
        /*Ground ground = new Ground(width, height-Constants.HEIGHT_OF_SKY);
        Block[][] groundBlocksArr = ground.getGroundBlocksArray();
        groundBckArr = new Block[width][height];
       
        for (int x = 0; x < width; x++) 
        {
            for (int y = 0; y < height-Constants.HEIGHT_OF_SKY; y++) 
            {
                mapArray[x][y] = groundBlocksArr[x][y];
                groundBckArr[x][y] = ground.getBackgroundBlocksArray()[x][y];
            }
        } */
        
        // initial value - [10, 20)
        groundIndexX = (int )(Math.random() * 10 + 10);
        
        // generate objects 
        while (groundIndexX < width-20) 
        {    
            // find top of the ground
            for (int j = 0; j < height-Constants.HEIGHT_OF_SKY; j++) 
            {
                if (mapArray[groundIndexX][j] == null)
                {
                    groundIndexY = j;
                    break;
                }
            }           
            
            // based on percent generates object
            int percent = (int )(Math.random() * 100);
            if (percent > 70) {
                generateHouse();
            }
            else if (percent > 5){
                createTrees();
            }
            else{
                createWater();
            } 
        }
    }
    
    
    private void generateGround(int w, int h){
        int randomIdx = 0;
        perlinNoise2D = new PerlinNoise2D();
        noiseArr = perlinNoise2D.getNoiseArr(h);
        
        groundBckArr = new Block[w][h];

        for (int i = 0; i < w; i++) 
        {
            for (int j = 0; j < h; j++) 
            {
                if (j == noiseArr[i/2]){
                    mapArray[i][j] = new Block(AllBlocks.ground);
                    groundBckArr[i][j] = AllBlocks.groundBck;
                }
                else if (j-1 == noiseArr[i/2]){
                    mapArray[i][j] = new Block(AllBlocks.grassy_ground);
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
                            mapArray[i][j] = new Block(AllBlocks.stone);
                    
                    }//gold area
                    else if(j <= 20)
                    {
                        if (randomIdx < 7)
                            mapArray[i][j] = new Block(AllBlocks.gold);
                        else if (randomIdx < 13)
                            mapArray[i][j] = new Block(AllBlocks.coal);
                        else if (randomIdx < 17)
                            mapArray[i][j] = new Block(AllBlocks.iron);
                        else
                            mapArray[i][j] = new Block(AllBlocks.stone);
                    }//iron area
                    else if(j <= 35)
                    {
                        if (randomIdx < 10)
                            mapArray[i][j] = new Block(AllBlocks.iron);
                        else if (randomIdx < 20)
                            mapArray[i][j] = new Block(AllBlocks.coal);
                        else
                            mapArray[i][j] = new Block(AllBlocks.stone);
                    }//coal area
                    else
                    {
                        if (randomIdx < 15)
                            mapArray[i][j] = new Block(AllBlocks.coal);
                        else
                            mapArray[i][j] = new Block(AllBlocks.stone);
                    }

                    groundBckArr[i][j] = AllBlocks.groundBck;
                }      
            }
        }
    }
    
    private void generateHouse() {
        int houseWidth = (int )(Math.random() * 2 + 9);
        int houseHeight = 0;
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
        
        // maximal vertical metre must be lower than 2 for generating of house
        if (verticalMetre < 2)
        {
            houseHeight = (int )(Math.random() * 2 + 6);
            House house = new House(houseWidth,houseHeight,groundIndexX,groundIndexY);
            Block[][] houseArr = house.getHouse();

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
    
    private void createTrees(){
        int treeWidth = (int )(Math.random() * 5 + 4);
        int treeHeight = (int )(Math.random() * 5 + 5);

        if (treeWidth%2 == 0)
            treeWidth++;

        Tree tree = new Tree(treeWidth,treeHeight, groundIndexX, groundIndexY);
        Block[][] treeArr = tree.getTree();

        for (int i = 0; i < treeWidth; i++) {
            for (int j = 0; j < treeHeight; j++) {
                if (treeArr[i][j] != null && (mapArray[i+groundIndexX-treeWidth/2][j+groundIndexY] == null)){
                    mapArray[i+groundIndexX-treeWidth/2][j+groundIndexY] = treeArr[i][j];}
            }
        }
        groundIndexX += (int )(Math.random() * 15 + treeWidth);
    
    }
    
    private void createWater(){
        int idxY = groundIndexY;
        
        for (int i = groundIndexY; i > 0; i--) {
           if (groundBckArr[groundIndexX][i] != null){
              idxY = i-1; 
              break;
           }
        }
        
        Water water = new Water();
        water.createBody(GameScreen.world, 0.4f*5f + groundIndexX*Block.size, 0.4f*1+idxY*Block.size, 10*0.4f, 2*0.4f); //world, x, y, width, height
        water.setDebugMode(false);
        waterList.add(water);
        
        for (int i = groundIndexX; i < groundIndexX+10; i++) 
        {
            for (int j = idxY; j < idxY+2; j++) 
            {
                if (mapArray[i][j] != null)
                    removeBlock(i, j);
            }  
        }

        
        groundIndexX += (int )(Math.random() * 15 + 10);
    }
    
    public void rotateBlock(Body b){
        getBlockByIdx((int)(b.getPosition().x/Block.size),(int)(b.getPosition().y/Block.size)).textureRotation -= 90;
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
            
            if (mapArray[x][y].id == AllBlocks.torch.id){
                for (IntVector2 torchsPo : torchsPos) {
                    if (torchsPo.X == x && torchsPo.Y == y){
                        torchsPos.remove(torchsPo);
                        break;
                    }
                }
            }
                
            
            mapArray[x][y] = null;
           
    }
    
    public boolean addBodyToIdx(int x, int y, Block b){
        //create new body beacuse in Inventar is only one body 
        if (b.id == AllBlocks.door.id || b.id == AllBlocks.door_up.id || b.id == AllBlocks.door_down.id)
        {
            if (mapArray[x][y+1] != null)
                return false;
            
            mapArray[x][y] = new Block(AllBlocks.door_down);
            mapArray[x][y+1] = new Block(AllBlocks.door_up);
            mapArray[x][y+1].setBody(createBodie(GameScreen.world, x, y+1, b.blocked));
        }
        else{
            mapArray[x][y] = new Block(b);
        }
        
        if (b.id == AllBlocks.torch.id)
            torchsPos.add(new IntVector2(x, y));
        
        mapArray[x][y].setBody(createBodie(GameScreen.world, x, y, b.blocked));
        
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
        
        v.X = (int)((x+(cam.x-MyGdxGame.width/2))/Block.size);
        v.Y = (int)((MyGdxGame.height-(y-(cam.y-MyGdxGame.height/2)))/Block.size);
        
        return v;
    
    
    }
    
    public boolean isBlockEmpty(IntVector2 idx){
        Block b = getBlockByIdx(idx);
        
        return (b == null || b == AllBlocks.empty);
    
    }
    
    public void draw(SpriteBatch spriteBatch, Vector2 cam){
        
        left_cam_edge = (int) (cam.x/Block.size - MyGdxGame.width/2/Block.size_in_pixels);
        down_cam_edge = (int) (cam.y/Block.size - MyGdxGame.height/2/Block.size_in_pixels);
        
        left = left_cam_edge - Constants.SIZE_OF_CHUNK;
        right = left_cam_edge + (MyGdxGame.width/Block.size_in_pixels) + Constants.SIZE_OF_CHUNK;
        down = down_cam_edge;// + Constants.SIZE_OF_CHUNK;
        up = down_cam_edge + MyGdxGame.height/Block.size_in_pixels + Constants.SIZE_OF_CHUNK;
        
        /*
        int left = (int) (((cam.x*100.0f)-640)/40)+Constants.SIZE_OF_CHUNK;
        right = (int) (((cam.x*100.0f)+640)/40);
        down = (int) (((cam.y*100.0f)-360)/40)+Constants.SIZE_OF_CHUNK;
        up = (int) (((cam.y*100.0f)+360)/40);*/
        
        if (left < 0)
            left = 0;
        left = (((int)(left/Constants.SIZE_OF_CHUNK))*Constants.SIZE_OF_CHUNK);
        
        if (right > width)
            right = width;
        right = (((int)(right/Constants.SIZE_OF_CHUNK))*Constants.SIZE_OF_CHUNK);
        
        
        if (down < 0)
            down = 0;
        if (down > height)
            down = height;
        down = (((int)(down/Constants.SIZE_OF_CHUNK))*Constants.SIZE_OF_CHUNK);
        
        if (up > height)
            up = height;
        if (up < 0)
            up = 0;
        up = (((int)(up/Constants.SIZE_OF_CHUNK))*Constants.SIZE_OF_CHUNK);
        
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
        
        if (previousUp > up )
            removeBodyFromMap(left, right, up, previousUp);
        
        if (previousUp < up)
            createBodyToMap(left, right, previousUp, up);
        
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
                    
                    if (mapArray[i][j].id == AllBlocks.ladder.id && j < height-Constants.HEIGHT_OF_SKY)
                    {
                        if (groundBckArr[i][j] != AllBlocks.empty && groundBckArr[i][j] != null)
                            spriteBatch.draw( groundBckArr[i][j].texture, i*Block.size, j*Block.size, Block.size, Block.size);
                    }
                    
                    
                    //stage.getBatch().draw( mapArray[x][y].texture, x*Block.size, y*Block.size, Block.size, Block.size);
                    if (mapArray[i][j].id == AllBlocks.torch.id && j < height-Constants.HEIGHT_OF_SKY)
                    {
                        if (groundBckArr[i][j] != AllBlocks.empty && groundBckArr[i][j] != null)
                            spriteBatch.draw( groundBckArr[i][j].texture, i*Block.size, j*Block.size, Block.size, Block.size);
                    }else
                        drawWithRotation(spriteBatch, i, j);
                }
                else if(j < height-Constants.HEIGHT_OF_SKY) {
                    if (groundBckArr[i][j] != AllBlocks.empty && groundBckArr[i][j] != null)
                        spriteBatch.draw( groundBckArr[i][j].texture, i*Block.size, j*Block.size, Block.size, Block.size);
                }
            }
        }
        
        
        if (isMining){
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = breakBlockAnimation.getKeyFrame(stateTime);
            IntVector2 v = (IntVector2)miningBlock.getBody().getUserData();
            spriteBatch.draw(currentFrame, v.X*Block.size, v.Y*Block.size, Block.size, Block.size);
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
                spriteBatch.draw(currentFrame, torchPos.X*Block.size, torchPos.Y*Block.size, Block.size, Block.size);   
            }
        }
    }

    private void drawWithRotation(SpriteBatch spriteBatch, int i, int j) {
       Block b = mapArray[i][j];
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
    
    public void drawWater(){
        for (Water water : waterList)
        {
            water.update();
            water.draw(GameScreen.camera);
        }
    
    }

    public void drawRectOnBlock(Body b, Vector2 cam, Vector2 player){
        if (b.getUserData() instanceof IntVector2)
        {
            IntVector2 v = (IntVector2)b.getUserData();
            shapeRenderer.begin(ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            if (getBlockByIdx(v) != null && player.dst(b.getPosition()) < 1.5f)
            {
                shapeRenderer.rect( v.X * Block.size_in_pixels - cam.x*GameScreen.PPM + MyGdxGame.width/2.0f,
                                    v.Y * Block.size_in_pixels - cam.y*GameScreen.PPM + MyGdxGame.height/2.0f, 
                                    Block.size_in_pixels, 
                                    Block.size_in_pixels);
                rectVector.set(v);
                
            }
            if (Inputs.instance.debugMode)
            {
                shapeRenderer.line(
                        b.getPosition().x*GameScreen.PPM - cam.x*GameScreen.PPM + MyGdxGame.width/2.0f,
                        b.getPosition().y*GameScreen.PPM - cam.y*GameScreen.PPM + MyGdxGame.height/2.0f, 
                        player.x*GameScreen.PPM- cam.x*GameScreen.PPM + MyGdxGame.width/2.0f,
                        player.y*GameScreen.PPM- cam.y*GameScreen.PPM + MyGdxGame.height/2.0f);
            }
                shapeRenderer.end();
        }
   
    }
    
    public void mining(IntVector2 v){
        if (v.equal(rectVector) == false)
            return;
        
        if ((miningBlock == null && minedBlock == null) ||
            (getBlockByIdx(v) != miningBlock) ) 
        {
            miningBlock = getBlockByIdx(v);
            stateTime = 0;
            isMining = true;
            breakBlockAnimation.setFrameDuration(miningBlock.hardness/100.0f);
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
                    mapArray[x][y].setBody(createBodie(GameScreen.world, x, y, mapArray[x][y].blocked));
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
      
}
