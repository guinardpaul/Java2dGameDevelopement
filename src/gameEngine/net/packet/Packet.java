package gameEngine.net.packet;

import gameEngine.net.GameClient;
import gameEngine.net.GameServer;

public abstract class Packet {

	public static enum PacketTypes {
		INVALID(-1), LOGIN(00), DISONNECT(01);

		public int packetId;

		private PacketTypes(int packetId) {
			this.packetId = packetId;
		}

		public int getPacketId() {
			return packetId;
		}
	}

	public byte packetId;

	public Packet(int packetId) {
		this.packetId = (byte) packetId;
	}

	public abstract void writeData(GameClient client);

	public abstract void writeData(GameServer server);

	public abstract byte[] getData();

	public String readData(byte[] data) {
		String message = new String(data).trim();
		return message.substring(2);
	}

	public static PacketTypes lookupPacket(String id) {
		try {
			return lookupPacket(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return PacketTypes.INVALID;
		}
	}

	public static PacketTypes lookupPacket(int id) {
		for (PacketTypes p : PacketTypes.values()) {
			if (p.getPacketId() == id) {
				return p;
			}
		}
		return PacketTypes.INVALID;
	}

}
