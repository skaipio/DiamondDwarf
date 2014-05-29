package org.diamonddwarf;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "DiamondDwarf";
		cfg.width = 1024;
		cfg.height = 12 * 64;

		Settings settings = new Settings();
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;
		settings.useIndexes = true;
		settings.paddingX = 0;
		settings.paddingY = 0;

		TexturePacker2.process(settings, "../unpackedTextures",
				"../DiamondDwarf-android/assets", "packedTextures");

		new LwjglApplication(new Game(), cfg);
	}
}
