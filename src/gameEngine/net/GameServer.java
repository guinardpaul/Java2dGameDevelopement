package gameEngine.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import gameEngine.Game;
import gameEngine.entities.PlayerMP;
import gameEngine.net.packet.Packet;
import gameEngine.net.packet.Packet.PacketTypes;
import gameEngine.net.packet.Packet00Login;
import gameEngine.net.packet.Packet01Disconnect;
import gameEngine.net.packet.Packet02Move;

/**
 * The Game Server.
 */
public class GameServer extends Thread {

	/** The socket. */
	private DatagramSocket socket;

	/** The game. */
	private Game game;

	/** The connected players. */
	private List<PlayerMP> connectedPlayers = new ArrayList<>();

	/**
	 * Instantiates a new game server.
	 *
	 * @param game
	 *            the game
	 */
	public GameServer(Game game) {
		this.game = game;
		try {
			this.socket = new DatagramSocket(1331);
		} catch (SocketException e) {
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
			handleMove(((Packet02Move) packet));
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
				"[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " has connected...");

		PlayerMP player = new PlayerMP(game.level, 100, 100, packet.getUsername(), address, port, packet.getNumSteps(),
				packet.isMoving(), packet.getMovingDir());
		this.addConnection(player, packet);
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
		System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " has left...");

		this.removeConnection(packet);
	}

	/**
	 * Handle move.
	 *
	 * @param packet
	 *            the packet
	 */
	private void handleMove(Packet02Move packet) {
		if (getPlayerMP(packet.getUsername()) != null) {
			int index = getPlayerMPIndex(packet.getUsername());
			PlayerMP player = this.connectedPlayers.get(index);
			player.x = packet.getX();
			player.y = packet.getY();
			player.setMoving(packet.isMoving());
			player.setMovingDir(packet.getMovingDir());
			player.setNumSteps(packet.getNumSteps());
			packet.writeData(this);
		}
	}

	/**
	 * Adds the connection.
	 *
	 * @param player
	 *            the player
	 * @param packet
	 *            the packet
	 */
	public void addConnection(PlayerMP player, Packet00Login packet) {
		boolean alreadyConnected = false;
		for (PlayerMP p : this.connectedPlayers) {
			if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
				if (p.ipAddress == null) {
					p.ipAddress = player.ipAddress;
				}
				if (p.port == -1) {
					p.port = player.port;
				}
				alreadyConnected = true;
			} else {
				sendData(packet.getData(), p.ipAddress, p.port);
				// Send data to other player
				packet = new Packet00Login(p.getUsername(), p.x, p.y, p.getNumSteps(), p.isMoving(), p.getMovingDir());
				sendData(packet.getData(), player.ipAddress, player.port);
			}
		}
		if (!alreadyConnected) {
			this.connectedPlayers.add(player);
		}
	}

	/**
	 * Removes the connection.
	 *
	 * @param packet
	 *            the packet
	 */
	public void removeConnection(Packet01Disconnect packet) {
		this.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
		packet.writeData(this);
	}

	/**
	 * Send data.
	 *
	 * @param data
	 *            the data
	 * @param ipAddress
	 *            the ip address
	 * @param port
	 *            the port
	 */
	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send data to all clients.
	 *
	 * @param data
	 *            the data
	 */
	public void sendDataToAllClients(byte[] data) {
		for (PlayerMP p : connectedPlayers) {
			sendData(data, p.ipAddress, p.port);
		}
	}

	/**
	 * Gets the playerMP from the connected players.
	 *
	 * @param username
	 *            the username
	 * @return the playerMP
	 */
	public PlayerMP getPlayerMP(String username) {
		for (PlayerMP p : connectedPlayers) {
			if (p.getUsername().equalsIgnoreCase(username)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Gets the playerMP index from the connected players.
	 *
	 * @param username
	 *            the username
	 * @return the playerMP index
	 */
	public int getPlayerMPIndex(String username) {
		int index = 0;
		for (PlayerMP p : connectedPlayers) {
			if (p.getUsername().equalsIgnoreCase(username)) {
				break;
			}
			index++;
		}
		return index;
	}
}
