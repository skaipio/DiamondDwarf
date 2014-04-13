package org.diamonddwarf;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "DiamondDwarf";
		cfg.width = 640+200;
		cfg.height = 640;
		
		Settings settings = new Settings();
        settings.maxWidth = 512;
        settings.maxHeight = 512;
        settings.useIndexes = true;
		
		TexturePacker2.process(settings, "../unpackedTextures", "../DiamondDwarf-android/assets", "packedTextures");
		
		new LwjglApplication(new Game(), cfg);
	}
}
