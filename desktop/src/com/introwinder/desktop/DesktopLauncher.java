package com.introwinder.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.introwinder.MyIntroWinder;
import com.introwinder.tools.Info;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.x = 64;
		config.y = 64;
		config.width = (int) ((float) Info.V_WIDTH * Info.DESKTOP_SCALE);
		config.height = (int) ((float) Info.V_HEIGHT * Info.DESKTOP_SCALE);
		new LwjglApplication(new MyIntroWinder(), config);
	}
}
