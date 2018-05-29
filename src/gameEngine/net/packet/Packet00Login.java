package gameEngine.net.packet;

import gameEngine.net.GameClient;
import gameEngine.net.GameServer;

/**
 * The Packet00Login.
 */
public class Packet00Login extends Packet {

	/** The username. */
	private String username;

	/** The y. */
	private int x, y;

	/** The num steps. */
	private int numSteps = 0;

	/** The is moving. */
	private boolean isMoving;

	/** The moving dir. */
	private int movingDir = 1;

	/**
	 * Instantiates a new packet00Login.
	 *
	 * @param data
	 *            the data
	 */
	public Packet00Login(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
		this.numSteps = Integer.parseInt(dataArray[3]);
		this.isMoving = Integer.parseInt(dataArray[4]) == 1;
		this.movingDir = Integer.parseInt(dataArray[5]);
	}

	/**
	 * Instantiates a new packet00Login.
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
	public Packet00Login(String username, int x, int y, int numSteps, boolean isMoving, int movingDir) {
		super(00);
		this.username = username;
		this.x = x;
		this.y = y;
		this.numSteps = numSteps;
		this.isMoving = isMoving;
		this.movingDir = movingDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gameEngine.net.packet.Packet#writeData(gameEngine.net.GameClient)
	 */
	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gameEngine.net.packet.Packet#writeData(gameEngine.net.GameServer)
	 */
	@Override
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gameEngine.net.packet.Packet#getData()
	 */
	@Override
	public byte[] getData() {
		return ("00" + this.username + "," + this.x + "," + this.y + "," + this.numSteps + "," + (this.isMoving ? 1 : 0)
				+ "," + this.movingDir).getBytes();
	}

	// GETTERS AND SETTERS
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the num steps.
	 *
	 * @return the num steps
	 */
	public int getNumSteps() {
		return numSteps;
	}

	/**
	 * Checks if is moving.
	 *
	 * @return true, if is moving
	 */
	public boolean isMoving() {
		return isMoving;
	}

	/**
	 * Gets the moving dir.
	 *
	 * @return the moving dir
	 */
	public int getMovingDir() {
		return movingDir;
	}

}
