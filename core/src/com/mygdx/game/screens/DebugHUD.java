/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Inputs;
import com.mygdx.game.IntVector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.Player;
import java.text.DecimalFormat;

/**
 *
 * @author Fery
 */
public class DebugHUD implements Disposable{

    private BitmapFont font;
    private String dbgStr = "";
    
    private Stage stageHUD;
    
    private OrthographicCamera camera;
    
    private SpriteBatch batch = new SpriteBatch();
    
    public DebugHUD(SpriteBatch spriteBatch) {
        font = new BitmapFont();
        //this.camera = camera;
        /*FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("open-sans/OpenSans-Regular.ttf"));
FreeTypeFontParameter parameter = new FreeTypeFontParameter();
parameter.size = 12;*/
//parameter.genMipMaps = true;
//font = generator.generateFont(parameter); // font size 12 pixels
//font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//generator.dispose(); // don't forget to dispose to avoid memory leaks!
       
camera = new OrthographicCamera();
stageHUD = new Stage(new FitViewport(MyGdxGame.width,MyGdxGame.height,camera),batch);

    }
    
    public void draw(Player player, Vector2 cam, double currentTime){
        //stageHUD.draw();
        createDebugInfo(player, cam, currentTime);
        //font.getData().setScale(0.1f);
        //stageHUD.getBatch().setProjectionMatrix(camera.combined);
        stageHUD.getBatch().begin();
        font.draw(batch, dbgStr, 0,MyGdxGame.height);
        dbgStr = "";
        stageHUD.getBatch().end();
    
    }
    
    private void createDebugInfo(Player player, Vector2 cam, double currentTime){
        addPlayerInfo(player, cam);
        addCamInfo(cam);
        addMouseInfo();
        addTimeInfo(currentTime);

    }
    
    private void addPlayerInfo(Player player, Vector2 cam){
        dbgStr += "Player position X: " + player.getX() + ", Y: " + player.getY() + "\n";
        dbgStr += "RealCoordinate X: " + ((player.getX()+70)-(cam.x-MyGdxGame.width/2)) + "\n";
    }

    private void addCamInfo(Vector2 cam) {
        dbgStr += "CamX: " + cam.x + ", CamY: " + cam.y + "\n";
    }

    private void addMouseInfo() {
        dbgStr += "MouseX: " + Inputs.instance.mouseX + ", MouseY: " + Inputs.instance.mouseY + "\n";
    }
    
    private void addTimeInfo(double currentTime) {
        
        dbgStr += "Time: " + String.format("%2d", (int)(currentTime*10)/10) + ":" + String.format("%2d", (int)(60*(currentTime-(int)currentTime))) + " hours\n";
    }
    
    @Override
    public void dispose() {
        stageHUD.dispose();}

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Stage getStageHUD() {
        return stageHUD;
    }


    
    
}
