package gameEngine.entities;

import gameEngine.graphics.Screen;
import gameEngine.level.Level;

/**
 * The Class Entity.
 */
public abstract class Entity {

	/** The y. */
	public int x, y;

	/** The level. */
	protected Level level;

	/**
	 * Instantiates a new entity.
	 *
	 * @param level
	 *            the level
	 */
	public Entity(Level level) {
		init(level);
	}

	/**
	 * Inits the level.
	 *
	 * @param level
	 *            the level
	 */
	public final void init(Level level) {
		this.level = level;
	}

	/**
	 * Tick.
	 */
	public abstract void tick();

	/**
	 * Render.
	 *
	 * @param screen
	 *            the screen
	 */
	public abstract void render(Screen screen);

}
