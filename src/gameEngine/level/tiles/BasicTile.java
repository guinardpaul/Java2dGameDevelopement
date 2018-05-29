package gameEngine.level.tiles;

import gameEngine.graphics.Screen;
import gameEngine.level.Level;

/**
 * The Class BasicTile.
 */
public class BasicTile extends Tile {

	/** The Constant SHEET_WIDTH. */
	private final static int SHEET_WIDTH = 32;

	/** The tile id. */
	protected int tileId;

	/** The tile colour. */
	protected int tileColour;

	/**
	 * Instantiates a new basic tile.
	 *
	 * @param id
	 *            the id
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param tileColour
	 *            the tile colour
	 * @param levelColour
	 *            the level colour
	 */
	public BasicTile(int id, int x, int y, int tileColour, int levelColour) {
		super(id, false, false, levelColour);
		this.tileId = x + y * SHEET_WIDTH;
		this.tileColour = tileColour;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gameEngine.level.tiles.Tile#tick()
	 */
	@Override
	public void tick() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gameEngine.level.tiles.Tile#render(gameEngine.graphics.Screen,
	 * gameEngine.level.Level, int, int)
	 */
	@Override
	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColour, 0x00, 1);
	}

}
