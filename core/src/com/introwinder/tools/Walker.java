package com.introwinder.tools;

import com.introwinder.tools.Info.Dir4;

public class Walker {
	private Vec2i curPos;
	private Dir4 currentDir;
	private int rightConstraint, topConstraint, leftConstraint, bottomConstraint;
	private int numSteps;

	public Walker(int numTilesWide, int numTilesHigh) {
		// start in top left
		curPos = new Vec2i(0, numTilesHigh-1);
		currentDir = Dir4.RIGHT;

		rightConstraint = numTilesWide-1;
		topConstraint = numTilesHigh-1;
		leftConstraint = 0;
		bottomConstraint = 0;

		numSteps = 0;
	}

	private Vec2i getStepFromDir(Dir4 d) {
		switch(d) {
			case RIGHT:
				return new Vec2i(1, 0);
			case UP:
				return new Vec2i(0, 1);
			case LEFT:
				return new Vec2i(-1, 0);
			case DOWN:
			default:
				return new Vec2i(0, -1);
		}
	}

	/*
	 * Returns true if current position moved.
	 * Returns false if current position could not move in any direction due to constraints.
	 */
	public boolean step() {
		// zero space between constraints?
		if(leftConstraint >= rightConstraint && bottomConstraint >= topConstraint)
			return false;
Dir4 oldDir = currentDir;
		// If moving one step in the current direction would move beyond a constraint, then change current direction
		// (turn clockwise)
		Vec2i next = curPos.cpy().add(getStepFromDir(currentDir));
		switch(currentDir) {
			case RIGHT:
				if(next.x > rightConstraint) {
					if(topConstraint > bottomConstraint)
						topConstraint--;
					currentDir = Dir4.DOWN;
				}
				break;
			case UP:
				if(next.y > topConstraint) {
					if(leftConstraint < rightConstraint)
						leftConstraint++;
					currentDir = Dir4.RIGHT;
				}
				break;
			case LEFT:
				if(next.x < leftConstraint) {
					if(bottomConstraint < topConstraint)
						bottomConstraint++;
					currentDir = Dir4.UP;
				}
				break;
			default:	// DOWN
				if(next.y < bottomConstraint) {
					if(rightConstraint > leftConstraint)
						rightConstraint--;
					currentDir = Dir4.LEFT;
				}
				break;
		}

		Vec2i step = getStepFromDir(currentDir);
		curPos.add(step);
		numSteps++;
if(oldDir != currentDir)
	QQ.pr("changed dir from " + oldDir + " to " + currentDir + ", at step #" + numSteps + ", curPos=" + curPos);
		return true;
	}

	public Vec2i getCurPos() {
		return curPos;
	}

	public Dir4 getCurDir() {
		return currentDir;
	}

	public int getNumSteps() {
		return numSteps;
	}
}
