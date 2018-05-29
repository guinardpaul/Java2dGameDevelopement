package gameEngine.net.packet;

import gameEngine.net.GameClient;
import gameEngine.net.GameServer;

/**
 * The Abstract Packet.
 */
public abstract class Packet {

	/**
	 * The Enum PacketTypes.
	 */
	public static enum PacketTypes {

		/** The invalid. */
		INVALID(-1),
		/** The login. */
		LOGIN(00),
		/** The disconnect. */
		DISCONNECT(01),
		/** The move. */
		MOVE(02);

		/** The packet id. */
		public int packetId;

		/**
		 * Instantiates a new packet types.
		 *
		 * @param packetId
		 *            the packet id
		 */
		private PacketTypes(int packetId) {
			this.packetId = packetId;
		}

		/**
		 * Gets the packet id.
		 *
		 * @return the packet id
		 */
		public int getPacketId() {
			return packetId;
		}
	}

	/** The packet id. */
	public byte packetId;

	/**
	 * Instantiates a new packet.
	 *
	 * @param packetId
	 *            the packet id
	 */
	public Packet(int packetId) {
		this.packetId = (byte) packetId;
	}

	/**
	 * Write data to the client.
	 *
	 * @param client
	 *            the client
	 */
	public abstract void writeData(GameClient client);

	/**
	 * Write data to the server.
	 *
	 * @param server
	 *            the server
	 */
	public abstract void writeData(GameServer server);

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public abstract byte[] getData();

	/**
	 * Read data.
	 *
	 * @param data
	 *            the data
	 * @return the string
	 */
	public String readData(byte[] data) {
		String message = new String(data).trim();
		return message.substring(2);
	}

	/**
	 * Get the packet type.
	 *
	 * @param id
	 *            the id
	 * @return the packet types
	 */
	public static PacketTypes lookupPacket(String type) {
		try {
			return lookupPacket(Integer.parseInt(type));
		} catch (NumberFormatException e) {
			return PacketTypes.INVALID;
		}
	}

	/**
	 * Get the packet type.
	 *
	 * @param id
	 *            the id
	 * @return the packet types
	 */
	public static PacketTypes lookupPacket(int id) {
		for (PacketTypes p : PacketTypes.values()) {
			if (p.getPacketId() == id) {
				return p;
			}
		}
		return PacketTypes.INVALID;
	}

}
