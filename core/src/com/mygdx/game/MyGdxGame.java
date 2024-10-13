package com.mygdx.game;

import com.mygdx.game.screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.screens.PauseScreen;

public class MyGdxGame extends Game {
       
    public static int width = 1280;
    public static int height = 720;
    
    public static String worldName;
    public static String playerName;
    
    private MenuScreen menuScreen;
    private NewGameScreen newGameScreen;
    private LoadGameScreen loadGameScreen;
    private LoadingScreen loadingScreen;
    private PreferencesScreen settingScreen;
    private ExitScreen exitScreen;
    private AppPreferences preferences;
    private GameScreen gameScreen;
    private PauseScreen pauseScreen;
    
    public final static int MENU        = 0;
    public final static int NEWGAME     = 1;
    public final static int LOADGAME    = 2;
    public final static int SETTINGS    = 3;
    public final static int EXIT        = 4; 
    public final static int GAME        = 5;
    public final static int PAUSE       = 6;
    public final static int LOADING     = 7;
    
    public boolean resolutionChanged = false;
    
    public void changeScreen(int screen)
    {
            switch(screen){
                    case MENU:
                            if(menuScreen == null) menuScreen = new MenuScreen(this);
                            /*if (((Gdx.graphics.getWidth() != width) && Gdx.graphics.isFullscreen() == false) || 
                               (Gdx.graphics.isFullscreen()&& preferences.isFullscreenEnabled() == false))
                            {
                                Gdx.graphics.setWindowedMode(width, height); 
                            }
                            if (Gdx.graphics.isFullscreen() == false && preferences.isFullscreenEnabled())
                                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());*/
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
                            //when settingScreen is called from pauseScreen we want to go back to pauseScreen
                            if (this.getScreen() == pauseScreen)
                                settingScreen.setPause();
                            
                            this.setScreen(settingScreen);
                            break;
                    case EXIT:
                            if(exitScreen == null) exitScreen = new ExitScreen(this);
                            this.setScreen(exitScreen);
                            break;
                    case GAME:
                            /*if(gameScreen != null && resolutionChanged) 
                            {
                                gameScreen.dispose();
                                resolutionChanged = false;
                                gameScreen = new GameScreen(this);
                            }*/
                            if (gameScreen == null) gameScreen = new GameScreen(this);
                            
                            this.setScreen(gameScreen);
                            break;
                    case PAUSE:
                            if(pauseScreen == null) pauseScreen = new PauseScreen(this);
                            this.setScreen(pauseScreen);
                            break;
                    case LOADING:
                            if(loadingScreen == null) loadingScreen = new LoadingScreen(this);
                            this.setScreen(loadingScreen);
                            break;
                            
                    
            }
    }

    public AppPreferences getPreferences(){
        return this.preferences;
    }

    @Override
    public void create() 
    {
        Inputs.instance = new Inputs();
        
        Skins.instance = new Skins();

        preferences = new AppPreferences();
        this.setWindowMode();
        
        //MyAssetManager.instance = new MyAssetManager();
        
        MyMusic.instance = new MyMusic(preferences);

        if (preferences.isMusicEnabled()){
            MyMusic.instance.playMenuMusic();  
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

    public void createGameScreen() {
        if (gameScreen != null) gameScreen.dispose();
        gameScreen = new GameScreen(this);
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }
    
    public void setWindowMode(){
        switch (preferences.getResolution()) 
        {
            case 0: MyGdxGame.width = 640;
                    MyGdxGame.height = 480;
                    break;
            case 1: MyGdxGame.width = 960;
                    MyGdxGame.height = 540;
                    break;
            case 2: MyGdxGame.width = 1280;
                    MyGdxGame.height = 720;
                    break;
            case 3: MyGdxGame.width = 1920;
                    MyGdxGame.height = 1080;
                    break;
            default:
                throw new AssertionError();
        }
        if (((Gdx.graphics.getWidth() != width) && Gdx.graphics.isFullscreen() == false) || 
           (Gdx.graphics.isFullscreen()&& preferences.isFullscreenEnabled() == false))
            Gdx.graphics.setWindowedMode(width, height);
        if (Gdx.graphics.isFullscreen() == false && preferences.isFullscreenEnabled())
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }    
}