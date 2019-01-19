package com.introwinder;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.introwinder.screens.FadeInScreen;
import com.introwinder.tools.Info;

public class MyIntroWinder extends Game {
	public AssetManager manager;
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();

		manager = new AssetManager();
		manager.load(Info.Music.SMB, Music.class);
		manager.load(Info.Music.METROID, Music.class);
		manager.load(Info.Music.KIDICARUS, Music.class);
		manager.load(Info.Music.ZELDA2, Music.class);
		manager.finishLoading();

		setScreen(new FadeInScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		manager.dispose();
	}
}
