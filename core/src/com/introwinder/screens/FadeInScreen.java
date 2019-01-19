package com.introwinder.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.introwinder.MyIntroWinder;

public class FadeInScreen implements Screen {
	private static final float FADE_TIME = 0.4f;

	private MyIntroWinder game;
	private float fadeTimer;

	public FadeInScreen(MyIntroWinder game) {
		this.game = game;
		fadeTimer = 0f;
	}
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		update(delta);

		float g = fadeTimer/FADE_TIME;
		Gdx.gl.glClearColor(g, g, g, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	private void update(float delta) {
		if(fadeTimer > FADE_TIME) {
			game.setScreen(new IntroWindScreen(game));
			dispose();
		}
		fadeTimer += delta;
	}
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}
}
