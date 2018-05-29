package gameEngine.entities;

import java.net.InetAddress;

import gameEngine.InputHandler;
import gameEngine.level.Level;

/**
 * The Class PlayerMP.
 */
public class PlayerMP extends Player {

	/** The ip address. */
	public InetAddress ipAddress;

	/** The port. */
	public int port;

	/**
	 * Instantiates a new player.
	 *
	 * @param level
	 *            the level
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param input
	 *            the input
	 * @param username
	 *            the username
	 * @param ipAddress
	 *            the ip address
	 * @param port
	 *            the port
	 */
	public PlayerMP(Level level, int x, int y, InputHandler input, String username, InetAddress ipAddress, int port) {
		super(level, x, y, input, username);
		this.ipAddress = ipAddress;
		this.port = port;
	}

	/**
	 * Instantiates a new player MP.
	 *
	 * @param level
	 *            the level
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param username
	 *            the username
	 * @param ipAddress
	 *            the ip address
	 * @param port
	 *            the port
	 */
	public PlayerMP(Level level, int x, int y, String username, InetAddress ipAddress, int port) {
		super(level, x, y, null, username);
		this.ipAddress = ipAddress;
		this.port = port;
	}

	/**
	 * Instantiates a new player MP for Packet00Login..
	 *
	 * @param level
	 *            the level
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param username
	 *            the username
	 * @param ipAddress
	 *            the ip address
	 * @param port
	 *            the port
	 * @param numSteps
	 *            the num steps
	 * @param isMoving
	 *            the is moving
	 * @param movingDir
	 *            the moving dir
	 */
	public PlayerMP(Level level, int x, int y, String username, InetAddress ipAddress, int port, int numSteps,
			boolean isMoving, int movingDir) {
		super(level, x, y, null, username);
		this.ipAddress = ipAddress;
		this.port = port;
		this.numSteps = numSteps;
		this.isMoving = isMoving;
		this.movingDir = movingDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gameEngine.entities.Player#tick()
	 */
	@Override
	public void tick() {
		super.tick();
	}

}
