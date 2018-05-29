package gameEngine.graphics;

// TODO: Auto-generated Javadoc
/**
 * The Class Screen.
 */
public class Screen {

	/** The Constant MAP_WIDTH. */
	public static final int MAP_WIDTH = 64;
	
	/** The Constant MAP_WIDTH_MASK. */
	public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

	/** The Constant BIT_MIRROR_X. */
	public static final byte BIT_MIRROR_X = 0x01;
	
	/** The Constant BIT_MIRROR_Y. */
	public static final byte BIT_MIRROR_Y = 0x02;

	/** The pixels. */
	public int[] pixels;

	/** The x offset. */
	public int xOffset = 0;
	
	/** The y offset. */
	public int yOffset = 0;

	/** The width. */
	public int width;
	
	/** The height. */
	public int height;

	/** The sheet. */
	public SpriteSheet sheet;

	/**
	 * Instantiates a new screen.
	 *
	 * @param width the width
	 * @param height the height
	 * @param sheet the sheet
	 */
	public Screen(int width, int height, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		this.sheet = sheet;

		pixels = new int[width * height];
	}

	/**
	 * Render.
	 *
	 * @param xPos the x pos
	 * @param yPos the y pos
	 * @param tile the tile
	 * @param colour the colour
	 * @param mirrorDir the mirror dir
	 * @param scale the scale
	 */
	public void render(int xPos, int yPos, int tile, int colour, int mirrorDir, int scale) {
		xPos -= xOffset;
		yPos -= yOffset;

		int scaleMap = scale - 1;
		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;
		// x y position of the tile we want to draw
		int xTile = tile % 32;
		int yTile = tile / 32;
		int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width;
		for (int y = 0; y < 8; y++) {
			int ySheet = y;
			if (mirrorY) {
				ySheet = 7 - y;
			}
			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3) / 2);
			for (int x = 0; x < 8; x++) {
				int xSheet = x;
				if (mirrorX) {
					xSheet = 7 - x;
				}
				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3) / 2);
				int col = (colour >> (sheet.pixels[xSheet + ySheet * sheet.width + tileOffset] * 8)) & 255;
				if (col < 255) {
					for (int yScale = 0; yScale < scale; yScale++) {
						if ((yPixel + yScale) < 0 || (yPixel + yScale) >= height)
							continue;
						for (int xScale = 0; xScale < scale; xScale++) {
							if ((xPixel + xScale) < 0 || (xPixel + xScale) >= width)
								continue;
							pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
						}
					}
				}
			}
		}
	}

	/**
	 * Sets the offset.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void setOffset(int x, int y) {
		xOffset = x;
		yOffset = y;
	}

}
