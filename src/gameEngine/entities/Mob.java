package gameEngine.entities;

import gameEngine.level.Level;
import gameEngine.level.tiles.Tile;

/**
 * The Class Mob.
 */
public abstract class Mob extends Entity {

	/** The name. */
	protected String name;

	/** The speed. */
	protected int speed;

	/** The num steps. */
	protected int numSteps = 0;

	/** The is moving. */
	protected boolean isMoving;

	/** The moving dir. */
	protected int movingDir = 1;

	/** The scale. */
	protected int scale = 1;

	/**
	 * Instantiates a new mob.
	 *
	 * @param level
	 *            the level
	 * @param name
	 *            the name
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param speed
	 *            the speed
	 */
	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		this.name = name;
		this.speed = speed;
		this.x = x;
		this.y = y;
	}

	/**
	 * Move.
	 *
	 * @param xa
	 *            the xa
	 * @param ya
	 *            the ya
	 */
	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;
		if (!hasCollided(xa, ya)) {
			if (ya < 0)
				movingDir = 0;
			if (ya > 0)
				movingDir = 1;
			if (xa < 0)
				movingDir = 2;
			if (xa > 0)
				movingDir = 3;
			x += xa * speed;
			y += ya * speed;
		}
	}

	/**
	 * Checks for collided.
	 *
	 * @param xa
	 *            the xa
	 * @param ya
	 *            the ya
	 * @return true, if successful
	 */
	public abstract boolean hasCollided(int xa, int ya);

	/**
	 * Checks if is solid tile.
	 *
	 * @param xa
	 *            the xa
	 * @param ya
	 *            the ya
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if is solid tile
	 */
	protected boolean isSolidTile(int xa, int ya, int x, int y) {
		if (level == null)
			return false;
		Tile lastTile = level.getTile((this.x + x) >> 3, (this.y + y) >> 3);
		Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);

		if (!lastTile.equals(newTile) && newTile.isSolid()) {
			return true;
		}
		return false;
	}

	// GETTERS AND SETTERS
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the num steps.
	 *
	 * @return the num steps
	 */
	public int getNumSteps() {
		return numSteps;
	}

	/**
	 * Checks if is moving.
	 *
	 * @return true, if is moving
	 */
	public boolean isMoving() {
		return isMoving;
	}

	/**
	 * Gets the moving dir.
	 *
	 * @return the moving dir
	 */
	public int getMovingDir() {
		return movingDir;
	}

	/**
	 * Sets the num steps.
	 *
	 * @param numSteps
	 *            the new num steps
	 */
	public void setNumSteps(int numSteps) {
		this.numSteps = numSteps;
	}

	/**
	 * Sets the moving.
	 *
	 * @param isMoving
	 *            the new moving
	 */
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	/**
	 * Sets the moving dir.
	 *
	 * @param movingDir
	 *            the new moving dir
	 */
	public void setMovingDir(int movingDir) {
		this.movingDir = movingDir;
	}

}
