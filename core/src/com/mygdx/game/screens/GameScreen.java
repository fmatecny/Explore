/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Inputs;
import com.mygdx.game.IntVector2;
import com.mygdx.game.MyContactListener;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Villager;
import com.mygdx.game.world.AllBlocks;
import com.mygdx.game.world.Background;
import com.mygdx.game.world.Block;
import com.mygdx.game.world.Map;
import com.mygdx.game.world.water.Water;

/**
 *
 * @author Fery
 */
public class GameScreen implements Screen{
    
    private final MyGdxGame parent;
    
    public static float PPM = 100;

    public static OrthographicCamera camera;
    private Vector2 cam =  new Vector2(MyGdxGame.width/PPM/2.0f, MyGdxGame.height/PPM/2.0f);
    private Vector3 v3 = new Vector3();
        
    private ExtendViewport viewport;
    private final Stage stage;
    private SpriteBatch spriteBatch;
    
    private final Background bck;
    private final Map map;
    public static AllBlocks allBlocks = new AllBlocks();
    private final Player player;
    //private final Villager villager;
    private final DebugHUD debugHUD;
    
    private final int mapWidth = 100;
    private final int mapHeight = 50;
    
    private boolean allowRotation = true;
    
    private Body hitBody;
    
    public static World world;
    
    private Box2DDebugRenderer debugRenderer;


    QueryCallback callback = new QueryCallback() 
    {
        @Override
        public boolean reportFixture(Fixture fxtr) {
            if (fxtr.testPoint(v3.x, v3.y)){
                hitBody = fxtr.getBody();
                return false;
            }
            else {
                return true;
            }
        }
    };
    
    public GameScreen(MyGdxGame myGdxGame){
        parent = myGdxGame;
        camera = new OrthographicCamera();//MyGdxGame.width,MyGdxGame.height);
        // create stage and set it as input processor
        stage = new Stage(new FitViewport(MyGdxGame.width/PPM,MyGdxGame.height/PPM,camera));

        spriteBatch = new SpriteBatch();
        
        // Create box2d world
        world = new World(new Vector2(0, -10), true);
        //world.setContactListener(new MyContactListener());
        debugRenderer = new Box2DDebugRenderer();   
        
        // Create map - blocks, houses, trees...
        map = new Map(mapWidth, mapHeight);
        
        // Create background
        bck = new Background();
        
        // Create player
        player = new Player(stage, spriteBatch);
        
        //villager = new Villager(stage);
        
        // Create debug HUD
        debugHUD = new DebugHUD(spriteBatch);
    }
    
    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(Inputs.instance);
        world.setContactListener(new MyContactListener());
    }
    
    @Override
    public void render(float f) {
        
        world.step(1 / 60f, 6, 2);
        
        if (Inputs.instance.pause){
            parent.changeScreen(MyGdxGame.PAUSE);
            return;
        }
        
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        //stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        updateCamera();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        bck.drawBackground(spriteBatch);

        map.draw(spriteBatch);

        //villager.updatePosition();
        //villager.draw(spriteBatch);

        player.updatePosition(camera);
        player.draw(spriteBatch);

       
        if (!Inputs.instance.showInventory)
        {
            camera.unproject(v3.set(Inputs.instance.mouseX, Inputs.instance.mouseY, 0f));
            hitBody = null;
            world.QueryAABB(callback, v3.x, v3.y, v3.x, v3.y);
            
            // take block to inventory
            if (Inputs.instance.mouseLeft && hitBody != null)
            {
                if (hitBody.getUserData() instanceof Water == false)
                {
                    Block b = (Block)hitBody.getUserData();
                    //System.err.println((int)(v3.x*100/40) + "|" + (int)(v3.y*100/40) + "|" + v3.x + "|" + hitBody.getPosition());
                    if (b != null){
                        map.removeBodyFromWorld(hitBody);
                        b.textureRotation = 0;
                        player.getInventory().addItemToInvenotry(b);
                    }
                }
            }
            else if (Inputs.instance.mouseMiddle &&  hitBody != null && allowRotation)
            {
                allowRotation = false;
                map.rotateBlock(hitBody);
                
            }
            else if (Inputs.instance.mouseRight && hitBody == null)
            {
                if (player.getInventory().getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].isEmpty() == false){
                    player.getInventory().getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].numOfItem--;
                    map.addBodyToIdx((int)(v3.x*100/40), (int)(v3.y*100/40), player.getInventory().getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].getItem());      
                }
            }
            else if (!Inputs.instance.mouseMiddle){
                allowRotation = true;
            }
            
            if ( Inputs.instance.srdco )
                spriteBatch.draw(AllBlocks.heard, 1, 1);
        }       
        
        //spriteBatch.setProjectionMatrix(player.getInventory().getStageInventory().getCamera().combined);   
        spriteBatch.end();
        
        player.getInventory().draw();
        
        //show debug informations
        if (Inputs.instance.debugMode)
        {
            spriteBatch.setProjectionMatrix(debugHUD.getStageHUD().getCamera().combined);
            debugHUD.draw(player, spriteBatch,cam);
        }


        debugRenderer.render(world, camera.combined);

        stage.draw();
    }

    private void updateCamera(){
        
        if (player.getX()-cam.x + MyGdxGame.width/2/PPM > MyGdxGame.width/PPM-4) {
            //cam.x+=0.01;
            cam.x = player.getX()-(MyGdxGame.width/PPM-4 - MyGdxGame.width/2/PPM);
        }
        if (player.getX()-cam.x + MyGdxGame.width/2/PPM < 4) {
            //cam.x-=0.01;//player.getSpeed();
            cam.x= MyGdxGame.width/2/PPM +player.getX() - 4;
        }
        
        if (player.getY()-cam.y + MyGdxGame.height/2/PPM < 2) {
            //cam.y-=0.01;//player.getSpeed()*2;
            cam.y = player.getY() + MyGdxGame.height/2/PPM - 2;
        }
        
        if (player.getY()-cam.y + MyGdxGame.height/2/PPM > 3) {
            //cam.y+=0.01;//player.getSpeed()*2;
            cam.y = player.getY() + MyGdxGame.height/2/PPM - 3;
        }
        
        camera.position.set(cam.x ,cam.y, camera.position.z);
        camera.update();
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

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }  
}
