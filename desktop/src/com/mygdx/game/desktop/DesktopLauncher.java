package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                //config.useGL30 = true;
                config.height = 720;
                config.width = 1280;
                config.title = "Explore";
                config.addIcon("icon-256.jpg", Files.FileType.Internal);
                config.addIcon("icon-64.jpg", Files.FileType.Internal);
                config.addIcon("icon-32.jpg", Files.FileType.Internal);
		new LwjglApplication(new MyGdxGame(), config);
	}
}
