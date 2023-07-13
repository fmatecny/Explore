package com.mygdx.game;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
                //config.useGL30 = true;
                config.setWindowedMode(1280, 720);
                //config.setFullscreenMode(config.getDisplayMode());
                //config.setWindowedMode(640, 480);
                config.setTitle("Explore");
                config.setWindowIcon(Files.FileType.Internal, "icon-256.jpg");
                config.setWindowIcon(Files.FileType.Internal, "icon-64.jpg");
                config.setWindowIcon(Files.FileType.Internal, "icon-32.jpg");
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
