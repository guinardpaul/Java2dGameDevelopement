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

/**
 * The Game Client.
 */
public class GameClient extends Thread {

	/** The ip address. */
	private InetAddress ipAddress;

	/** The socket. */
	private DatagramSocket socket;

	/** The game. */
	private Game game;

	/**
	 * Instantiates a new game client.
	 *
	 * @param game
	 *            the game
	 * @param ipAddress
	 *            the ip address
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
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
		}
	}

	/**
	 * Parses the packet and handle it.
	 *
	 * @param data
	 *            the data
	 * @param address
	 *            the address
	 * @param port
	 *            the port
	 */
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
			handleLogin((Packet00Login) packet, address, port);
			break;
		case DISCONNECT:
			packet = new Packet01Disconnect(data);
			handleDisconnect((Packet01Disconnect) packet, address, port);
			break;
		case MOVE:
			packet = new Packet02Move(data);
			handleMove((Packet02Move) packet);
			break;
		}
	}

	/**
	 * Handle login.
	 *
	 * @param packet
	 *            the packet
	 * @param address
	 *            the address
	 * @param port
	 *            the port
	 */
	private void handleLogin(Packet00Login packet, InetAddress address, int port) {
		System.out.println(
				"[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " has joined the game...");

		PlayerMP player = new PlayerMP(game.level, packet.getX(), packet.getY(), packet.getUsername(), address, port,
				packet.getNumSteps(), packet.isMoving(), packet.getMovingDir());
		game.level.addEntity(player);
	}

	/**
	 * Handle disconnect.
	 *
	 * @param packet
	 *            the packet
	 * @param address
	 *            the address
	 * @param port
	 *            the port
	 */
	private void handleDisconnect(Packet01Disconnect packet, InetAddress address, int port) {
		System.out.println(
				"[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " has left the game...");

		game.level.removePlayerMP(packet.getUsername());
	}

	/**
	 * Handle move.
	 *
	 * @param packet
	 *            the packet
	 */
	private void handleMove(Packet02Move packet) {
		this.game.level.movePlayer(packet.getUsername(), packet.getX(), packet.getY(), packet.getNumSteps(),
				packet.isMoving(), packet.getMovingDir());
	}

	/**
	 * Send data.
	 *
	 * @param data
	 *            the data
	 */
	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
