/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 *
 * @author Fery
 */
public class MyMusic {

    
    public static MyMusic instance;
    
    private Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/LonelyMountain.mp3"));
    
    private AppPreferences preferences;
    
    public MyMusic(AppPreferences p){
        preferences = p;
    }
    
    public void playMenuMusic(){
        setMenuMusicVolume(preferences.getMusicVolume());
        menuMusic.setLooping(true);
        menuMusic.play();
    }
    
    public void stopMenuMusic(){
        menuMusic.stop();
    }
    
    public void setMenuMusicVolume(float volume){
        menuMusic.setVolume(volume);
    }
    
    public void setSoundVolume(float volume){
        //menuMusic.setVolume(volume);
    }
    
}
