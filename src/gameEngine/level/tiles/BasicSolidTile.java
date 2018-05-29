package gameEngine.level.tiles;

/**
 * The Class BasicSolidTile.
 */
public class BasicSolidTile extends BasicTile {

	/**
	 * Instantiates a new basic solid tile.
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
	public BasicSolidTile(int id, int x, int y, int tileColour, int levelColour) {
		super(id, x, y, tileColour, levelColour);
		this.solid = true;
	}

}
