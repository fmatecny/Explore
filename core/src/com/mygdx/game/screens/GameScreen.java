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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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
import com.mygdx.game.world.AllBlocks;
import com.mygdx.game.world.Background;
import com.mygdx.game.world.Block;
import com.mygdx.game.world.Map;

/**
 *
 * @author Fery
 */
public class GameScreen implements Screen{
    
    public static float PPM = 100;
    
    ShapeRenderer sr;
    
    boolean foo = false;
    
    public static OrthographicCamera camera;
    ExtendViewport viewport;
    
    private final MyGdxGame parent;
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
    private Vector2 cam =  new Vector2(MyGdxGame.width/PPM/2.0f, MyGdxGame.height/PPM/2.0f);
    private IntVector2 blockIdx = new IntVector2();
    
    private boolean allowRotation = true;
    
    private Body hitBody;
    
    public static World world;
    Box2DDebugRenderer debugRenderer;
            BodyDef b;
            
    Vector3 v3 = new Vector3();
    QueryCallback callback = new QueryCallback() {
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
        world.setContactListener(new MyContactListener());
        debugRenderer = new Box2DDebugRenderer();   
        
        // Create map - blocks, houses, trees...
        map = new Map(mapWidth, mapHeight);
        
        // Create background
        bck = new Background();
        
        // Create player
        player = new Player(stage);
        
        //villager = new Villager(stage);
        
        // Create debug HUD
        debugHUD = new DebugHUD(spriteBatch);
        
        sr = new ShapeRenderer();
 
    }
    
    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(Inputs.instance);  
    }
    
    @Override
    public void render(float f) {
        
        //world.step(1 / 60f, 6, 2);
        
        if (Inputs.instance.pause){
            parent.changeScreen(MyGdxGame.PAUSE);
            return;
        }
        
        
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        //stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        if (player.getX()-cam.x + MyGdxGame.width/2/PPM > MyGdxGame.width/PPM-4) {
            cam.x+=0.01;//player.getSpeed();
        }
        if (player.getX()-cam.x + MyGdxGame.width/2/PPM < 4) {
            cam.x-=0.01;//player.getSpeed();
        }
        
        if (player.getY()-cam.y + MyGdxGame.height/2/PPM < 2) {
            cam.y-=0.01;//player.getSpeed()*2;
        }
        
        if (player.getY()-cam.y + MyGdxGame.height/2/PPM > 3) {
            cam.y+=0.01;//player.getSpeed()*2;
        }

       /*if (Inputs.instance.up)
           cam.y+=0.01;*/
       if (Inputs.instance.down)
           cam.y-=0.01;
       /*if (Inputs.instance.right)
           cam.x+=2.0/PPM;
       if (Inputs.instance.left)
           cam.x--;*/
       
        camera.position.set(cam.x ,cam.y, camera.position.z);
        camera.update();

        
       // Vector3 tp = new Vector3();
        //System.err.println(camera.unproject(tp.set(Inputs.instance.mouseX, Inputs.instance.mouseY, 0)));

        //System.out.println(stage.hit(Inputs.instance.mouseX, Inputs.instance.mouseY, true));
        
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        bck.drawBackground(spriteBatch);

        map.draw(spriteBatch);

        //villager.updatePosition(map, cam);
        //villager.draw(stage);

        player.updatePosition(map,cam);
        player.draw(spriteBatch);
       
        if (!Inputs.instance.showInventory)
        {
            camera.unproject(v3.set(Inputs.instance.mouseX, Inputs.instance.mouseY, 0f));
            hitBody = null;
            world.QueryAABB(callback, v3.x, v3.y, v3.x, v3.y);
            
            if (Inputs.instance.mouseLeft && hitBody != null){
                //TODO break animation
                //player.getInventory().addItemToInvenotry(map.getBlockByIdx(blockIdx));
                
                //map.removeBlock(blockIdx);
                Block b = (Block)hitBody.getUserData();
                System.err.println((int)(v3.x*100/40) + "|" + (int)(v3.y*100/40) + "|" + v3.x + "|" + hitBody.getPosition());
                map.getBodiesArray()[(int)(hitBody.getPosition().x*100/40)][(int)(hitBody.getPosition().y*100/40)] = null;
                world.destroyBody(hitBody);
            }
            else if (Inputs.instance.mouseMiddle &&  hitBody != null && allowRotation){
                allowRotation = false;
                map.getBlockByIdx((int)(v3.x*100/40),(int)(v3.y*100/40)).textureRotation -= 90;
            }
            /*else if (Inputs.instance.mouseRight && map.isBlockEmpty(blockIdx)){
                if (player.getInventory().getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].isEmpty() == false){
                    player.getInventory().getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].numOfItem--;
                    map.getBodiesArray()[blockIdx.X][blockIdx.Y] = new Block(player.getInventory().getInventoryBarHUD().inventoryBar[Inputs.instance.scrollIdx].getItem());
                }
            }*/
            else if (!Inputs.instance.mouseMiddle){
                allowRotation = true;
            }
            
            if ( Inputs.instance.srdco )
                stage.getBatch().draw(AllBlocks.heard, 100, 100);
        }       
        
        spriteBatch.end();
        //show debug informations
        if (Inputs.instance.debugMode){
            spriteBatch.setProjectionMatrix(debugHUD.getStageHUD().getCamera().combined);
            debugHUD.draw(player, spriteBatch,cam);}
                ///------
        
        // creates a square at the clicked point
        if(Inputs.instance.run && foo == false) {
                b = createBody();
                foo = true;
        }
        
        if (Inputs.instance.run == false)
            foo = false;
        
        world.step(1/60f, 6, 2);
		
        //water.update();
        //water.draw(camera);
        
        //if (b != null)
            //stage.getBatch().draw(AllBlocks.coal.texture, b.position.x, b.position.y, 100,100);

        debugRenderer.render(world, camera.combined);
        //-------------
        /*if (!map.isBlockEmpty(blockIdx) && !Inputs.instance.showInventory)
        {
            sr.setProjectionMatrix(camera.combined);
            sr.begin(ShapeType.Line);
            sr.setColor(new Color(0,0,0,0));
            sr.rect(blockIdx.X*Block.size, blockIdx.Y*Block.size, Block.size, Block.size);
            sr.end();
        }*/
        

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
    
    
    private BodyDef createBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		
		// Set our body's starting position in the world
		bodyDef.position.set((float)((float)Inputs.instance.mouseX/(float)PPM)-(float)cam.x/(float)PPM, camera.viewportHeight - Inputs.instance.mouseY/PPM );
  
		
		// Create our body in the world using our body definition
		Body body = world.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		PolygonShape square = new PolygonShape();
		square.setAsBox(0.2f, 0.2f);
                

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = square;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.5f;

		// Create our fixture and attach it to the body
		body.createFixture(fixtureDef);

		square.dispose();
                
                return bodyDef;
	}


    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
    
    
    
}
