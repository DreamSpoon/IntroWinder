package com.introwinder.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TileSprite extends Sprite {
	public TileSprite(Texture img, Vec2i imgTilePos, Vec2i screenPos) {
		setRegion(new TextureRegion(img, imgTilePos.x*Info.TILE_WIDTH, imgTilePos.y*Info.TILE_HEIGHT,
				Info.TILE_WIDTH, Info.TILE_HEIGHT));
		setBounds(screenPos.x, screenPos.y, Info.TILE_WIDTH, Info.TILE_HEIGHT);
	}
}
