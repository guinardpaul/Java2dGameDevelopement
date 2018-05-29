package gameEngine.level.tiles;

import gameEngine.graphics.Screen;
import gameEngine.level.Level;

public class BasicTile extends Tile {

	private final static int SHEET_WIDTH = 32;
	protected int tileId;
	protected int tileColour;

	public BasicTile(int id, int x, int y, int tileColour, int levelColour) {
		super(id, false, false, levelColour);
		this.tileId = x + y * SHEET_WIDTH;
		this.tileColour = tileColour;
	}

	@Override
	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColour, 0x00, 1);
	}

	@Override
	public void tick() {
	}

}
