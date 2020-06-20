package com.mygdx.game;

import com.mygdx.game.screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.screens.PauseScreen;

public class MyGdxGame extends Game {
       
    public static int width = 1280;
    public static int height = 720;
    
    private MenuScreen menuScreen;
    private NewGameScreen newGameScreen;
    private LoadGameScreen loadGameScreen;
    private PreferencesScreen settingScreen;
    private ExitScreen exitScreen;
    private AppPreferences preferences;
    private GameScreen gameScreen;
    private PauseScreen pauseScreen;
    
    public final static int MENU = 0;
    public final static int NEWGAME = 1;
    public final static int LOADGAME = 2;
    public final static int SETTINGS = 3;
    public final static int EXIT = 4;
    
    public final static int GAME = 5;
    public final static int PAUSE = 6;
    
    public boolean resolutionChanged = false;
    
    private Music menuMusic;
    
    public void changeScreen(int screen)
    {
            switch(screen){
                    case MENU:
                            if(menuScreen == null) menuScreen = new MenuScreen(this);
                            this.setScreen(menuScreen);
                            break;
                    case NEWGAME:
                            if(newGameScreen == null) newGameScreen = new NewGameScreen(this);
                            this.setScreen(newGameScreen);
                            break;
                    case LOADGAME:
                            if(loadGameScreen == null) loadGameScreen = new LoadGameScreen(this);
                            this.setScreen(loadGameScreen);
                            break;
                    case SETTINGS:
                            if(settingScreen == null) settingScreen = new PreferencesScreen(this);
                            this.setScreen(settingScreen);
                            break;
                    case EXIT:
                            if(exitScreen == null) exitScreen = new ExitScreen(this);
                            this.setScreen(exitScreen);
                            break;
                    case GAME:
                            if(gameScreen != null && resolutionChanged) 
                            {
                                gameScreen.dispose();
                                resolutionChanged = false;
                                gameScreen = new GameScreen(this);
                            }
                            if (gameScreen == null) gameScreen = new GameScreen(this);
                            
                            this.setScreen(gameScreen);
                            break;
                    case PAUSE:
                            if(pauseScreen == null) pauseScreen = new PauseScreen(this);
                            this.setScreen(pauseScreen);
                            break;
            }
    }

    public AppPreferences getPreferences(){
        return this.preferences;
    }

    public void playMenuMusic(){
        menuMusic.setLooping(true);
        menuMusic.play();
    }
    
    public void stopMenuMusic(){
        menuMusic.stop();
    }
    
    public void setMenuMusicVolume(float volume){
        menuMusic.setVolume(volume);
    }
    
    
    @Override
    public void create() 
    {
        Inputs.instance = new Inputs();
        
        Skins.instance = new Skins();
        preferences = new AppPreferences();
        
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/LonelyMountain.mp3"));
        
        if (preferences.isMusicEnabled()){
            setMenuMusicVolume(preferences.getMusicVolume());
            playMenuMusic();  
        }
        
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
        
    }

   /* @Override
    public void dispose() {
        //textureAtlas.dispose();
    }

    @Override
    public void render() {
        /*Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        banana.draw(batch);
        batch.end();*/
        
        //changeScreen(MENU);
        
    //}
   
}