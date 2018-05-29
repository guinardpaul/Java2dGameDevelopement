package gameEngine.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import gameEngine.entities.Entity;
import gameEngine.entities.PlayerMP;
import gameEngine.graphics.Screen;
import gameEngine.level.tiles.Tile;

/**
 * The Class Level.
 */
public class Level {

	/** The tiles. */
	private byte[] tiles;

	/** The width. */
	public int width;

	/** The height. */
	public int height;

	/** The entities. */
	private List<Entity> entities = new ArrayList<>();

	/** The image path. */
	private String imagePath;

	/** The image. */
	private BufferedImage image;

	/**
	 * Instantiates a new level.
	 *
	 * @param imagePath
	 *            the image path
	 */
	public Level(String imagePath) {
		if (imagePath != null) {
			this.imagePath = imagePath;
			this.loadLevelFromFile();
		} else {
			this.width = 64;
			this.height = 64;
			tiles = new byte[width * height];
			this.generateLevel();
		}
	}

	/**
	 * Load level from file.
	 */
	private void loadLevelFromFile() {
		try {
			this.image = ImageIO.read(Level.class.getResource(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width * height];
			this.loadTiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load tiles from file.
	 */
	private void loadTiles() {
		int[] tileColours = this.image.getRGB(0, 0, width, height, null, 0, width);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tileCheck: for (Tile t : Tile.tiles) {
					if (t != null && t.getLevelColour() == tileColours[x + y * width]) {
						this.tiles[x + y * width] = t.getId();
						break tileCheck;
					}
				}
			}
		}
	}

	/**
	 * Generate level.
	 */
	private void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x * y % 10 < 7) {
					tiles[x + y * width] = Tile.GRASS.getId();
				} else {
					tiles[x + y * width] = Tile.STONE.getId();
				}
			}
		}
	}

	/**
	 * Tick.
	 */
	public void tick() {
		for (Entity e : getEntities()) {
			e.tick();
		}

		for (Tile t : Tile.tiles) {
			if (t == null)
				break;
			t.tick();
		}
	}

	/**
	 * Render entities.
	 *
	 * @param screen
	 *            the screen
	 */
	public void renderEntities(Screen screen) {
		for (Entity e : getEntities()) {
			e.render(screen);
		}
	}

	/**
	 * Render tile.
	 *
	 * @param screen
	 *            the screen
	 * @param xOffset
	 *            the x offset
	 * @param yOffset
	 *            the y offset
	 */
	public void renderTile(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0)
			xOffset = 0;
		if (xOffset > (width << 3) - screen.width)
			xOffset = (width << 3) - screen.width;
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > (height << 3) - screen.height)
			yOffset = (height << 3) - screen.height;

		screen.setOffset(xOffset, yOffset);

		for (int y = (yOffset >> 3); y < ((yOffset + screen.height) >> 3) + 1; y++) {
			for (int x = (xOffset >> 3); x < ((xOffset + screen.width) >> 3) + 1; x++) {
				getTile(x, y).render(screen, this, x << 3, y << 3);
			}
		}
	}

	/**
	 * Gets the tile.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the tile
	 */
	public Tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height)
			return Tile.VOID;
		return Tile.tiles[tiles[x + y * width]];
	}

	/**
	 * Adds the entity.
	 *
	 * @param entity
	 *            the entity
	 */
	public void addEntity(Entity entity) {
		this.getEntities().add(entity);
	}

	/**
	 * Removes the playerMP.
	 *
	 * @param userName
	 *            the username
	 */
	public void removePlayerMP(String username) {
		int index = 0;
		for (Entity e : getEntities()) {
			if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equalsIgnoreCase(username)) {
				break;
			}
			index++;
		}
		this.getEntities().remove(index);
	}

	/**
	 * Move player.
	 *
	 * @param username
	 *            the username
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param numSteps
	 *            the num steps
	 * @param isMoving
	 *            the is moving
	 * @param movingDir
	 *            the moving dir
	 */
	public void movePlayer(String username, int x, int y, int numSteps, boolean isMoving, int movingDir) {
		PlayerMP player = (PlayerMP) this.getEntities().get(getPlayerMPIndex(username));
		player.x = x;
		player.y = y;
		player.setMoving(isMoving);
		player.setNumSteps(numSteps);
		player.setMovingDir(movingDir);
	}

	/**
	 * Gets the player MP index.
	 *
	 * @param username
	 *            the username
	 * @return the player MP index
	 */
	private int getPlayerMPIndex(String username) {
		int index = 0;
		for (Entity e : getEntities()) {
			if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equalsIgnoreCase(username)) {
				break;
			}
			index++;
		}
		return index;
	}

	/**
	 * Gets the entities.
	 *
	 * @return the entities
	 */
	public synchronized List<Entity> getEntities() {
		return this.entities;
	}

	/**
	 * Save level to file.
	 */
	@SuppressWarnings("unused")
	private void saveLevelToFile() {
		try {
			ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Alter tile.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param tile
	 *            the tile
	 */
	public void alterTile(int x, int y, Tile tile) {
		this.tiles[x + y * width] = tile.getId();
		image.setRGB(x, y, tile.getLevelColour());
	}

}
