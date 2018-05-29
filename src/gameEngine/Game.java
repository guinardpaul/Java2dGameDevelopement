package gameEngine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gameEngine.entities.Player;
import gameEngine.entities.PlayerMP;
import gameEngine.graphics.Screen;
import gameEngine.graphics.SpriteSheet;
import gameEngine.level.Level;
import gameEngine.net.GameClient;
import gameEngine.net.GameServer;
import gameEngine.net.packet.Packet00Login;

/**
 * The Main Class Game.
 */
public class Game extends Canvas implements Runnable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The width. */
	public static int WIDTH = 160;

	/** The height. */
	public static int HEIGHT = WIDTH / 12 * 9;

	/** The scale. */
	public static int SCALE = 3;

	/** The name. */
	public static String NAME = "Game";

	/** The game. */
	public static Game game;

	/** The frame. */
	public JFrame frame;

	/** The running. */
	public boolean running = false;

	/** The tick count. */
	public int tickCount = 0;

	/** The image. */
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

	/** The pixels. */
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	/** The colours. */
	private int[] colours = new int[6 * 6 * 6];

	/** The screen. */
	private Screen screen;

	/** The input. */
	public InputHandler input;

	/** The window handler. */
	public WindowHandler windowHandler;

	/** The level. */
	public Level level;

	/** The player. */
	public Player player;
	// Multiplayer
	/** The socket client. */
	public GameClient socketClient;

	/** The socket server. */
	public GameServer socketServer;

	/**
	 * Instantiates a new game.
	 */
	public Game() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		frame = new JFrame(NAME);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Inits the Game.
	 */
	public void init() {
		game = this;

		int index = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);

					colours[index++] = rr << 16 | gg << 8 | bb;
				}
			}
		}

		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"));
		input = new InputHandler(this);
		windowHandler = new WindowHandler(this);
		level = new Level("/levels/water_test_level.png");
		player = new PlayerMP(level, 100, 100, input, JOptionPane.showInputDialog(this, "Please enter a username"),
				null, -1);
		level.addEntity(player);
		Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.x, player.y, player.getNumSteps(),
				player.isMoving(), player.getMovingDir());

		if (socketServer != null) {
			socketServer.addConnection((PlayerMP) player, loginPacket);
		}
		// socketClient.sendData("ping".getBytes());
		loginPacket.writeData(socketClient);
	}

	/**
	 * Start.
	 */
	public synchronized void start() {
		running = true;
		new Thread(this).start();

		if (JOptionPane.showConfirmDialog(this, "Do you want to run the server") == 0) {
			socketServer = new GameServer(this);
			socketServer.start();
		}

		socketClient = new GameClient(this, "localhost");
		socketClient.start();
	}

	/**
	 * Stop.
	 */
	public synchronized void stop() {
		running = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		final double nsPerTick = 1000000000D / 60D;

		int frames = 0;
		int ticks = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		init();
		requestFocus();

		// Game loop
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				frame.setTitle(ticks + " ticks, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}
		}
	}

	/**
	 * Tick.
	 */
	public void tick() {
		tickCount++;

		level.tick();
	}

	/**
	 * Render.
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		int xOffset = player.x - (screen.width / 2);
		int yOffset = player.y - (screen.height / 2);

		level.renderTile(screen, xOffset, yOffset);

		// for (int x = 0; x < level.width; x++) {
		// int colour = Colours.get(-1, -1, -1, 000);
		// if (x % 10 == 0 && x != 0) {
		// colour = Colours.get(-1, -1, -1, 500);
		// }
		// Font.render((x % 10) + "", screen, 0 + (x * 8), 0, colour);
		// }

		level.renderEntities(screen);

		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colourCode = screen.pixels[x + y * screen.width];
				if (colourCode < 255) {
					pixels[x + y * WIDTH] = colours[colourCode];
				}
			}
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		g.dispose();
		bs.show();
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		new Game().start();
	}

}
