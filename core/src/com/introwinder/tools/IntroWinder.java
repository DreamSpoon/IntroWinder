package com.introwinder.tools;

import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.introwinder.tools.Info.Dir4;

public class IntroWinder implements Disposable {
	private static final float TILE_TIME_MULT = 0.45f;	// DEBUG

	private AssetManager manager;
	private Texture img;
	private LinkedList<TileSprite> tsList;
	private float createTimer;
	private Walker walker;
	private boolean isDone;
	private Dir4 lastDir;
	private Music[] dirMusic;
	private Music currentMusic;

	private int[] stepDelayCounts = {
			// draw first 2 rows and first 2 columns
			4,
			8,
			22,

			26,
			30,
			44,

			48,
			52,
			66,

			70,
			74,
			87,

			// draw remaining rows and columns
			// A
			108,
			128,
			148,
			167,

			// B
			186,
			204,
			222,
			239,

			// C
			256,
//			272,
//			288,
//			303,

			// D
			318,
//			332,
//			346,
//			359,

			// E
			372,
//			384,
//			396,
//			407,

			// F
			418,
//			428,
//			438,
//			447,

			// G
			456,
//			464,
//			472,
//			479,

			// H
			486,
//			492,
//			498,
//			503,

			// I
			508,
			512,
			516,
			519,

			// J
			522,
			524,
			526,
			528,

			// K
			529
		};
	private float[] stepDelayTimes = {
			// draw first 2 rows and first 2 columns
			1/4f,
			1/8f,
			1/16f,

			1/4f,
			1/8f,
			1/16f,

			1/4f,
			1/8f,
			1/16f,

			1/4f,
			1/8f,
			1/16f,

			// draw remaining rows and columns
			// A
			1/12f,
			1/16f,
			1/24f,
			1/32f,

			// B
			1/48f,
			1/64f,
			1/96f,
			1/128f,

			// C
			1/196f,

			// D
			1/196f,

			// E
			1/256f,

			// F
			1/256f,

			// G
			1/200f,

			// H
			1/140f,

			// I
			1/110f,
			1/60f,
			1/24f,
			1/12f,

			// J
			1/6f,
			1/4f,
			1/2f,
			1/1f,

			// K
			1/1f
		};

	public IntroWinder(AssetManager manager) {
		this.manager = manager;

		img = new Texture(Info.TEXTURE_FILENAME);
		tsList = new LinkedList<TileSprite>();
		createTimer = 0f;
		walker = new Walker(Info.TILECOUNT_X, Info.TILECOUNT_Y);
		isDone = false;
		lastDir = null;
		dirMusic = new Music[4];
		dirMusic[Dir4.RIGHT.ordinal()] = manager.get(Info.Music.SMB, Music.class);
		dirMusic[Dir4.DOWN.ordinal()] = manager.get(Info.Music.METROID, Music.class);
		dirMusic[Dir4.LEFT.ordinal()] = manager.get(Info.Music.KIDICARUS, Music.class);
		dirMusic[Dir4.UP.ordinal()] = manager.get(Info.Music.ZELDA2, Music.class);
		for(int i=0; i<Dir4.values().length; i++) {
			dirMusic[i].setLooping(false);
			dirMusic[i].setVolume(Info.Music.VOLUME);
		}
		currentMusic = null;
	}

	public void draw(SpriteBatch batch) {
		batch.begin();
		for(TileSprite ts : tsList) 
			ts.draw(batch);
		batch.end();
	}

	public void update(float delta) {
		if(isDone) {
			currentMusic.stop();
			return;
		}

		// if walker changed direction then change the music
		if(lastDir != walker.getCurDir() && walker.getNumSteps() > 0) {
			lastDir = walker.getCurDir();
			switchMusic(lastDir);
		}

		float delay = calcDelayForStepManual(walker.getNumSteps(), Info.TOTAL_STEPS);
		while(createTimer > delay) {
//QQ.pr("step="+walker.getNumSteps()+", delay="+delay);
			createTimer -= delay;

			tsList.add(new TileSprite(img, walkerPosToTilePos(walker.getCurPos()),
					walkerPosToScreenPos(walker.getCurPos())));
			if(!walker.step()) {
				isDone = true;
				break;
			}

			// recalculate delay for next iteration of while loop
			delay = calcDelayForStepManual(walker.getNumSteps(), Info.TOTAL_STEPS);
		}

		createTimer += delta;
	}

	private float calcDelayForStepManual(int step, int totalSteps) {
		for(int i=0; i<stepDelayCounts.length; i++) {
			if(step < stepDelayCounts[i])
				return stepDelayTimes[i] * TILE_TIME_MULT;
		}
		return stepDelayTimes[stepDelayTimes.length-1] * TILE_TIME_MULT;
	}

	private void switchMusic(Dir4 direction) {
		if(currentMusic != null)
			currentMusic.pause();

		dirMusic[direction.ordinal()].play();
		currentMusic = dirMusic[direction.ordinal()];
	}

	private Vec2i walkerPosToScreenPos(Vec2i walkerPos) {
		return new Vec2i((int) (walkerPos.x*Info.TILE_WIDTH - Info.TILECOUNT_X/2f*Info.TILE_WIDTH),
				(int) (walkerPos.y*Info.TILE_HEIGHT - Info.TILECOUNT_X/2f*Info.TILE_WIDTH));
	}

	private Vec2i walkerPosToTilePos(Vec2i walkerPos) {
		return new Vec2i(walkerPos.x, Info.TILECOUNT_Y-1-walkerPos.y);
	}

	@Override
	public void dispose() {
		img.dispose();
	}

	public boolean isDoneStepping() {
		return isDone;
	}
}
