package com.introwinder.tools;

public class Vec2i {
	public int x;
	public int y;

	public Vec2i() {
		this(0, 0);
	}

	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vec2i cpy() {
		return new Vec2i(this.x, this.y);
	}

	public Vec2i add(Vec2i step) {
		this.x += step.x;
		this.y += step.y;
		return this;
	}

	public String toString() {
		return "[ " + x + ", " + y + " ]";
	}
}
