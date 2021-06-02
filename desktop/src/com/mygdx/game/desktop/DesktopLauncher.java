package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Pashmak;
import com.mygdx.game.controller.Finisher;
import com.mygdx.game.controller.Initializer;

import java.io.IOException;

public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 800;
		Initializer.addUsers();
		new LwjglApplication(new Pashmak(), config);
		Finisher.finish();
		System.out.println("111111111");
	}
}
