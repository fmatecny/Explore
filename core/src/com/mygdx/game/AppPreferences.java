/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 *
 * @author Fery
 */
public class AppPreferences {
    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    
    private static final String PREF_RESOLUTION_TYPE = "resolution.type";
    private static final String PREF_FULSCREEN_ENABLED = "fullscreen.enabled";
    
    private static final String PREFS_NAME = "explore.pref";

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }
    
    public void setResolution(int type){
        getPrefs().putInteger(PREF_RESOLUTION_TYPE, type);
        getPrefs().flush();
    }
    
    public int getResolution(){
        return getPrefs().getInteger(PREF_RESOLUTION_TYPE);
    }

    public boolean isFullscreenEnabled() {
        return getPrefs().getBoolean(PREF_FULSCREEN_ENABLED, true);
    }

    public void setFullscreenEnabled(boolean fullscreenEnabled) {
        getPrefs().putBoolean(PREF_FULSCREEN_ENABLED, fullscreenEnabled);
        getPrefs().flush();
    }
    
    
}
