package gameEngine.level.tiles;

/**
 * The Class AnimatedTile.
 */
public class AnimatedTile extends BasicTile {

	/** The animation tile coords. */
	private int[][] animationTileCoords;

	/** The current animation index. */
	private int currentAnimationIndex;

	/** The last iteration time. */
	private long lastIterationTime;

	/** The animation switch delay. */
	private int animationSwitchDelay;

	/**
	 * Instantiates a new animated tile.
	 *
	 * @param id
	 *            the id
	 * @param animationCoords
	 *            the animation coords
	 * @param tileColour
	 *            the tile colour
	 * @param levelColour
	 *            the level colour
	 * @param animationSwitchDelay
	 *            the animation switch delay
	 */
	public AnimatedTile(int id, int[][] animationCoords, int tileColour, int levelColour, int animationSwitchDelay) {
		super(id, animationCoords[0][0], animationCoords[0][1], tileColour, levelColour);
		this.animationTileCoords = animationCoords;
		this.currentAnimationIndex = 0;
		this.lastIterationTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gameEngine.level.tiles.BasicTile#tick()
	 */
	@Override
	public void tick() {
		if ((System.currentTimeMillis() - lastIterationTime) >= animationSwitchDelay) {
			lastIterationTime = System.currentTimeMillis();
			currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
			this.tileId = (animationTileCoords[currentAnimationIndex][0]
					+ (animationTileCoords[currentAnimationIndex][1] * 32));
		}
	}

}
