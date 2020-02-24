/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.Inputs;
import com.mygdx.game.IntVector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Villager;
import com.mygdx.game.world.AllBlocks;
import com.mygdx.game.world.Background;
import com.mygdx.game.world.Block;
import com.mygdx.game.world.Map;

/**
 *
 * @author Fery
 */
public class GameScreen implements Screen{
    
    ShapeRenderer sr;
    
    public static OrthographicCamera camera;
    ExtendViewport viewport;
    
    private final MyGdxGame parent;
    private final Stage stage;
    
    private final Background bck;
    private final Map map;
    public static AllBlocks allBlocks = new AllBlocks();
    private final Player player;
    private final Villager villager;
    
    private final DebugHUD debugHUD;
    
    private final int mapWidth = 200;
    private final int mapHeight = 50;
    
    private final IntVector2 cam =  new IntVector2(MyGdxGame.width/2, MyGdxGame.height/2);
    private IntVector2 blockIdx = new IntVector2();
    
    private boolean allowRotation = true;
    
    public GameScreen(MyGdxGame myGdxGame){
        parent = myGdxGame;
        camera = new OrthographicCamera(MyGdxGame.width,MyGdxGame.height);
        // create stage and set it as input processor
        stage = new Stage(new FillViewport(MyGdxGame.width,MyGdxGame.height,camera));
        
        map = new Map(mapWidth, mapHeight);
        bck = new Background();
        player = new Player(stage);
        
        villager = new Villager(stage);
        
        debugHUD = new DebugHUD();
        
        sr = new ShapeRenderer();
  
    }
    
    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(Inputs.instance);  
    }
    
    @Override
    public void render(float f) {
        
        if (Inputs.instance.pause){
            parent.changeScreen(MyGdxGame.PAUSE);
            return;
        }
        
        
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        if (player.getX()-cam.X + MyGdxGame.width/2 > MyGdxGame.width-400) {
            cam.X+=player.getSpeed();
        }
        if (player.getX()-cam.X + MyGdxGame.width/2 < 400) {
            cam.X-=player.getSpeed();
        }
        if (player.getY()-cam.Y + MyGdxGame.height/2 < 200) {
            cam.Y-=player.getSpeed()*2;
        }
        if (player.getY()-cam.Y + MyGdxGame.height/2 > 300) {
            cam.Y+=player.getSpeed()*2;
        }

        camera.position.set(cam.X ,cam.Y, camera.position.z);
        camera.update();

        
       // Vector3 tp = new Vector3();
        //System.err.println(camera.unproject(tp.set(Inputs.instance.mouseX, Inputs.instance.mouseY, 0)));

        //System.out.println(stage.hit(Inputs.instance.mouseX, Inputs.instance.mouseY, true));
        
        stage.getBatch().setProjectionMatrix(camera.combined);
        stage.getBatch().begin();

        bck.drawBackground(stage.getBatch());

        map.draw(stage);

        villager.updatePosition(map, cam);
        villager.draw(stage);
        
        player.updatePosition(map,cam);
        player.draw(stage);
        
        //show debug informations
        if (Inputs.instance.debugMode)
            debugHUD.draw(player, stage.getBatch(),cam);

        if (!Inputs.instance.showInventory)
        {
            blockIdx = map.getBlockIdxFromMouseXY(Inputs.instance.mouseX, Inputs.instance.mouseY, cam);

            if (Inputs.instance.mouseLeft && !map.isBlockEmpty(blockIdx)){
                //TODO break animation
                player.getInventory().addItemToInvenotry(map.getBlockByIdx(blockIdx));
                map.removeBlock(blockIdx);
            }
            else if (Inputs.instance.mouseMiddle && !map.isBlockEmpty(blockIdx) && allowRotation){
                allowRotation = false;
                map.getBlockByIdx(blockIdx).textureRotation -= 90;
            }
            else if (Inputs.instance.mouseRight && map.isBlockEmpty(blockIdx)){
                if (player.getInventory().getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].isEmpty() == false){
                    player.getInventory().getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].numOfItem--;
                    map.getBlockArray()[blockIdx.X][blockIdx.Y] = new Block(player.getInventory().getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].getItem());
                }
            }
            else if (!Inputs.instance.mouseMiddle){
                allowRotation = true;
            }
            
            if ( Inputs.instance.srdco )
                stage.getBatch().draw(AllBlocks.heard, 100, 100);
        }
        
        stage.getBatch().end();

        
        if (!map.isBlockEmpty(blockIdx) && !Inputs.instance.showInventory)
        {
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeType.Line);
            sr.setColor(new Color(0,0,0,0));
            sr.rect(blockIdx.X*Block.size, blockIdx.Y*Block.size, Block.size, Block.size);
            sr.end();
        }
        

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when the screen size is changed
        stage.getViewport().update(width, height, true);
    }
    
    
    @Override
    public void pause() {
        
    }
    

    @Override
    public void resume() {
    
    }

    @Override
    public void hide() {
        
    }
    
    @Override
    public void dispose() {
        player.dispose();

        // dispose of assets when not needed anymore
        stage.dispose();
        
    }
}
