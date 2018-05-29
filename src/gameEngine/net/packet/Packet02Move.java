package gameEngine.net.packet;

import gameEngine.net.GameClient;
import gameEngine.net.GameServer;

public class Packet02Move extends Packet {

	private String username;
	private int x, y;

	public Packet02Move(byte[] data) {
		super(02);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
	}

	public Packet02Move(String username, int x, int y) {
		super(02);
		this.username = username;
		this.x = x;
		this.y = y;
	}

	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("02" + this.username + "," + this.x + "," + this.y).getBytes();
	}

	public String getUserName() {
		return username;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
