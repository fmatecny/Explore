/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Skins;

/**
 *
 * @author Fery
 */
public abstract class ExploreMenuScreen implements Screen{
    
    private MyGdxGame parent;
    private Stage stage;
    Texture bground = new Texture(Gdx.files.internal("backgroundMenu3.jpg"));

    public ExploreMenuScreen(MyGdxGame myGdxGame) {
        parent = myGdxGame;
        // create stage and set it as input processor
        stage = new Stage(new ScreenViewport());

    }

    public MyGdxGame getParent() {
        return parent;
    }

    public Stage getStage() {
        return stage;
    }
    
    public Skin getSkin(){
        return Skins.skin;
    }
       
    @Override
    public void render(float f) {
                   // clear the screen ready for next set of images to be drawn
            Gdx.gl.glClearColor(0f, 0f, 0f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // tell our stage to do actions and draw itself
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            

            stage.getBatch().begin();
            stage.getBatch().draw(bground, 520, 0);
            stage.getBatch().end();
            
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
        // dispose of assets when not needed anymore
        stage.dispose();
    }
    
}
