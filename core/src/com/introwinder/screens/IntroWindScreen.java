package com.introwinder.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.introwinder.MyIntroWinder;
import com.introwinder.tools.Info;
import com.introwinder.tools.IntroWinder;
import com.introwinder.tools.QQ;

public class IntroWindScreen implements Screen {
	private MyIntroWinder game;
	private IntroWinder fs;
	private OrthographicCamera gamecam;
	private Viewport gameport;

	private float zoomTimer;
	private static final float ZOOM_TIME = 1.5f;

	public IntroWindScreen(MyIntroWinder game) {
		this.game = game;
		fs = new IntroWinder(game.manager);
		zoomTimer = 0f;
		gamecam = new OrthographicCamera();
		gameport = new FitViewport(Info.V_WIDTH, Info.V_HEIGHT, gamecam);
		gamecam.position.set(0f, 0f, 0f);
		gamecam.update();
QQ.pr("gamecam.zoom="+gamecam.zoom);
	}

	@Override
	public void render(float delta) {
		// do update (sprite textures, positions, etc.)
		fs.update(delta);
		// clear the screen
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updateCamera(delta);

		// draw stuff
		fs.draw(game.batch);
	}

	private void updateCamera(float delta) {
		if(fs.isDoneStepping()) {
QQ.pr("done stepping, zooming now, timer="+zoomTimer);
			if(zoomTimer < ZOOM_TIME) {
				gamecam.zoom = 1.001f - zoomTimer / ZOOM_TIME;
			}
			else {
				gamecam.zoom = 0.001f;
			}
			gamecam.update();
			game.batch.setProjectionMatrix(gamecam.combined);
			zoomTimer += delta;
		}
		else
			game.batch.setProjectionMatrix(gamecam.combined);
	}

	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
		gameport.update(width, height);
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
		fs.dispose();
	}
}
