package org.diamonddwarf;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "DiamondDwarf";
		cfg.width = 480;
		cfg.height = 260;
		
		new LwjglApplication(new Game(), cfg);
	}
}
