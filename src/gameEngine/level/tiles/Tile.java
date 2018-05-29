package gameEngine.level.tiles;

import gameEngine.graphics.Colours;
import gameEngine.graphics.Screen;
import gameEngine.level.Level;

/**
 * The Class Tile.
 */
public abstract class Tile {

	/** The Constant tiles. */
	public static final Tile[] tiles = new Tile[256];

	/** The VOID Tile. */
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colours.get(000, -1, -1, -1), 0xFF000000);

	/** The STONE Tile. */
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colours.get(-1, 333, -1, -1), 0xFF555555);

	/** The GRASS Tile. */
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colours.get(-1, 131, 141, -1), 0xFF00FF00);

	/** The WATER Tile. */
	public static final Tile WATER = new AnimatedTile(3, new int[][] { { 0, 5 }, { 1, 5 }, { 2, 5 }, { 1, 5 } },
			Colours.get(-1, 004, 115, -1), 0xFF0000FF, 500);

	/** The id. */
	protected byte id;

	/** The solid. */
	protected boolean solid;

	/** The emitter. */
	protected boolean emitter;

	/** The level colour. */
	private int levelColour;

	/**
	 * Instantiates a new tile.
	 *
	 * @param id
	 *            the id
	 * @param isSolid
	 *            the is solid
	 * @param isEmitter
	 *            the is emitter
	 * @param levelColour
	 *            the level colour
	 */
	public Tile(int id, boolean isSolid, boolean isEmitter, int levelColour) {
		this.id = (byte) id;
		if (tiles[id] != null)
			throw new RuntimeException("Duplicate tile id on " + id);
		this.solid = isSolid;
		this.emitter = isEmitter;
		this.levelColour = levelColour;
		tiles[id] = this;
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
	 * @param level
	 *            the level
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public abstract void render(Screen screen, Level level, int x, int y);

	// GETTERS AND SETTERS
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public byte getId() {
		return id;
	}

	/**
	 * Checks if is solid.
	 *
	 * @return true, if is solid
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * Checks if is emitter.
	 *
	 * @return true, if is emitter
	 */
	public boolean isEmitter() {
		return emitter;
	}

	/**
	 * Gets the level colour.
	 *
	 * @return the level colour
	 */
	public int getLevelColour() {
		return levelColour;
	}

}
