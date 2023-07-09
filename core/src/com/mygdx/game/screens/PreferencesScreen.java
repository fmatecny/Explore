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
import com.mygdx.game.MyMusic;

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
        soundEffectsButton.setChecked(getParent().getPreferences().isSoundEffectsEnabled());
        if (!getParent().getPreferences().isSoundEffectsEnabled())
            soundEffectsButton.setText("Sound: OFF");

        soundEffectsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (!soundEffectsButton.isChecked()) {
                    getParent().getPreferences().setSoundEffectsEnabled(false);
                    soundEffectsButton.setText("Sound: OFF");
                } else {
                    getParent().getPreferences().setSoundEffectsEnabled(true);
                    soundEffectsButton.setText("Sound: ON");
                }
            }
        });
        //soundEffectsButton.setChecked(true);
        
        // sound volume        
        Stack volumeSoundStack = new Stack();
        
        final Label soundLabel = new Label(Integer.toString((int)(getParent().getPreferences().getSoundVolume()*100))+"%", skin);
        soundLabel.setAlignment(Align.center);
        soundLabel.setTouchable(Touchable.disabled);
        
        final Slider volumeSoundSlider = new Slider(0, 1, 0.1f, false, skin);
        volumeSoundSlider.setValue(getParent().getPreferences().getSoundVolume());
        volumeSoundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                getParent().getPreferences().setSoundVolume(volumeSoundSlider.getValue());
                MyMusic.instance.setSoundVolume(volumeSoundSlider.getValue());
                soundLabel.setText(Integer.toString((int)(volumeSoundSlider.getValue()*100))+"%");
            }
        });
        volumeSoundStack.add(volumeSoundSlider);
        volumeSoundStack.add(soundLabel);  
        
        // Music on/off
        final TextButton musicButton = new TextButton("Music: ON", skin);
        if (!getParent().getPreferences().isMusicEnabled())
            musicButton.setText("Music: OFF");
        
        musicButton.setChecked(getParent().getPreferences().isMusicEnabled());
        musicButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (!musicButton.isChecked()) {
                    getParent().getPreferences().setMusicEnabled(false);
                    MyMusic.instance.stopMenuMusic();
                    musicButton.setText("Music: OFF");
                } else {
                    getParent().getPreferences().setMusicEnabled(true);
                    MyMusic.instance.playMenuMusic();
                    musicButton.setText("Music: ON");
                }
            }
        });
                
        
        // Music volume
        Stack volumeMusicStack = new Stack();
        
        final Label musicLabel = new Label(Integer.toString((int)(getParent().getPreferences().getMusicVolume()*100))+"%", skin);
        musicLabel.setAlignment(Align.center);
        musicLabel.setTouchable(Touchable.disabled);
        
        
        final Slider volumeMusicSlider = new Slider(0, 1, 0.1f, false, skin);
        volumeMusicSlider.setValue(getParent().getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                getParent().getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                MyMusic.instance.setMenuMusicVolume(volumeMusicSlider.getValue());
                musicLabel.setText(Integer.toString((int)(volumeMusicSlider.getValue()*100))+"%");
            }
        });
        volumeMusicStack.add(volumeMusicSlider);
        volumeMusicStack.add(musicLabel);


        
        // Resolution
        final TextButton resolutionButton = new TextButton("Resolution: 1280x720", skin);
        
        switch (getParent().getPreferences().getResolution()) {
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
        
        resolutionButton.addListener(new ChangeListener() {
            private int state = getParent().getPreferences().getResolution();
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
                getParent().getPreferences().setResolution(state);
            }
        });
        //resolutionButton.setChecked(true); 
        
        
        //fullscreen
        final TextButton fullscreenButton = new TextButton("Fullscreen: ON", skin);
        fullscreenButton.setChecked(getParent().getPreferences().isFullscreenEnabled());
        if (!getParent().getPreferences().isFullscreenEnabled())
            fullscreenButton.setText("Fullscreen: OFF");

        fullscreenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                if (!fullscreenButton.isChecked()) {
                    getParent().getPreferences().setFullscreenEnabled(false);
                    fullscreenButton.setText("Fullscreen: OFF");
                } else {
                    getParent().getPreferences().setFullscreenEnabled(true);
                    fullscreenButton.setText("Fullscreen: ON");
                }
            }
        });
        
        

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
        table.add(fullscreenButton).colspan(2).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(backButton).colspan(2).fillX().uniformX();
    }
}
