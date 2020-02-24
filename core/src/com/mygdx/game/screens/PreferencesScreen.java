/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGdxGame;


/**
 *
 * @author Fery
 */
public class PreferencesScreen extends ExploreMenuScreen{
    
    public PreferencesScreen(MyGdxGame myGdxGame){
            super(myGdxGame);

    }

    @Override
    public void show() {
        getStage().clear();
        Gdx.input.setInputProcessor(getStage());

        // Create a table that fills the screen. Everything else will go inside
        // this table.
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.left();
        table.padTop(50);
        table.padLeft(100);
        //table.setDebug(true);
        getStage().addActor(table);

        // temporary until we have asset manager in
        Skin skin = getSkin();

        // Sound on/off
        final TextButton soundEffectsButton = new TextButton("Sound: ON", skin);
        soundEffectsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (!soundEffectsButton.isChecked()) {
                    soundEffectsButton.setText("Sound: OFF");
                } else {
                    soundEffectsButton.setText("Sound: ON");
                }
            }
        });
        soundEffectsButton.setChecked(true);
        
        // sound volume        
        Stack volumeSoundStack = new Stack();
        volumeSoundStack.add(new Slider(0, 100, 1, false, skin));
        Label label = new Label("Volume", skin);
        label.setAlignment(Align.center);
        label.setTouchable(Touchable.disabled);
        volumeSoundStack.add(label);
        
        
        // Music on/off
        final TextButton musicButton = new TextButton("Music: ON", skin);
        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (!musicButton.isChecked()) {
                    musicButton.setText("Music: OFF");
                } else {
                    musicButton.setText("Music: ON");
                }
            }
        });
        musicButton.setChecked(true);        
        
        // Music volume
        Stack volumeMusicStack = new Stack();
        volumeMusicStack.add(new Slider(0, 100, 1, false, skin));
        Label MusicLabel = new Label("Volume", skin);
        MusicLabel.setAlignment(Align.center);
        MusicLabel.setTouchable(Touchable.disabled);
        volumeMusicStack.add(MusicLabel);

        
        // Resolution
        final TextButton resolutionButton = new TextButton("Resolution: 1280x720", skin);
        resolutionButton.addListener(new ChangeListener() {
            private int state = 2;
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                getParent().resolutionChanged = true;
                state = (state+1)%4;
                switch (state) {
                    case 0: resolutionButton.setText("Resolution: 640x480");
                            MyGdxGame.width = 640;
                            MyGdxGame.height = 480;
                            break;
                    case 1: resolutionButton.setText("Resolution: 960x540");
                            MyGdxGame.width = 960;
                            MyGdxGame.height = 540;
                            break;
                    case 2: resolutionButton.setText("Resolution: 1280x720");
                            MyGdxGame.width = 1280;
                            MyGdxGame.height = 720;
                            break;
                    case 3: resolutionButton.setText("Resolution: 1920x1080");
                            MyGdxGame.width = 1920;
                            MyGdxGame.height = 1080;
                            break;
                    default:
                        throw new AssertionError();
                }
            }
        });
        //resolutionButton.setChecked(true); 
        
        

        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                        getParent().changeScreen(MyGdxGame.MENU);

                }
        });

        Label preferencesLabel = new Label( "Preferences", skin, "bold" );
        Label titleLabel = new Label( "Explore", skin, "title" );

        table.add(titleLabel).padBottom(30).colspan(2);
        table.row().pad(10, 0, 10, 0); 
        table.add(preferencesLabel).colspan(2).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(soundEffectsButton).width(titleLabel.getWidth()/2);
        table.add(volumeSoundStack).width(titleLabel.getWidth()/2);
        table.row().pad(10,0,10,0);
        table.add(musicButton).width(titleLabel.getWidth()/2);
        table.add(volumeMusicStack).width(titleLabel.getWidth()/2);
        table.row().pad(10,0,10,0);
        table.add(resolutionButton).colspan(2).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(backButton).colspan(2).fillX().uniformX();

    }
}
