package gameEngine.net.packet;

import gameEngine.net.GameClient;
import gameEngine.net.GameServer;

/**
 * The Packet01Disconnect.
 */
public class Packet01Disconnect extends Packet {

	/** The username. */
	private String username;

	/**
	 * Instantiates a new Packet01Disconnect.
	 *
	 * @param data
	 *            the data
	 */
	public Packet01Disconnect(byte[] data) {
		super(01);
		this.username = readData(data);
	}

	/**
	 * Instantiates a new Packet01Disconnect.
	 *
	 * @param username
	 *            the username
	 */
	public Packet01Disconnect(String username) {
		super(01);
		this.username = username;
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
		return ("01" + this.username).getBytes();
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

}
