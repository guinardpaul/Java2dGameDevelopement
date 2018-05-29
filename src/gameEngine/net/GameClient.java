package gameEngine.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import gameEngine.Game;
import gameEngine.entities.PlayerMP;
import gameEngine.net.packet.Packet;
import gameEngine.net.packet.Packet.PacketTypes;
import gameEngine.net.packet.Packet00Login;
import gameEngine.net.packet.Packet01Disconnect;
import gameEngine.net.packet.Packet02Move;

public class GameClient extends Thread {

	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Game game;

	public GameClient(Game game, String ipAddress) {
		this.game = game;
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
			// System.out.println("SERVER > " + new String(packet.getData()));
		}
	}

	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
		Packet packet;
		switch (type) {
		default:
			break;
		case INVALID:
			break;
		case LOGIN:
			packet = new Packet00Login(data);
			System.out.println("[" + address.getHostAddress() + ":" + port + "] "
					+ ((Packet00Login) packet).getUserName() + " has joined the game...");
			PlayerMP player = new PlayerMP(game.level, 100, 100, ((Packet00Login) packet).getUserName(), address, port);
			game.level.addEntity(player);
			break;
		case DISONNECT:
			packet = new Packet01Disconnect(data);
			System.out.println("[" + address.getHostAddress() + ":" + port + "] "
					+ ((Packet01Disconnect) packet).getUserName() + " has left the game...");
			game.level.removePlayerMP(((Packet01Disconnect) packet).getUserName());
			break;
		case MOVE:
			packet = new Packet02Move(data);
			handlePacket((Packet02Move) packet);
			break;
		}
	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handlePacket(Packet02Move packet) {
		this.game.level.movePlayer(packet.getUserName(), packet.getX(), packet.getY());
	}

}
