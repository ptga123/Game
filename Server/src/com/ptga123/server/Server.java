package com.ptga123.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import core.RCDatabase;
import core.RCField;
import core.RCObject;
import core.Type;

public class Server {

	private int port;
	private Thread listenThread;
	private boolean listening = false;
	private DatagramSocket socket;

	private final static byte[] PACKET_HEADER = new byte[] { 0x40, 0x40 };
	private final static byte[] PACKET_HEADER_ACTION = new byte[] { 0x20, 0x20 };
	private final static byte PACKET_TYPE_CONNECT = 0x0;
	private final static byte PACKET_TYPE_DISCONNECT = 0x1;
	private final static byte PACKET_TYPE_MOVE = 0x2;

	private final int MAX_PACKET_SIZE = 1024;
	private byte[] receivedDataBuffer = new byte[MAX_PACKET_SIZE * 10];

	private List<ServerClient> clientsList = new ArrayList<ServerClient>();

	public Server(int port) {
		this.port = port;
	}

	public void start() {
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}

		System.out.println("Started server on port 8192...");

		listening = true;

		listenThread = new Thread(() -> listen(), "Server - ListenThread");
		listenThread.start();
		System.out.println("Server is listening...");
	}

	private void listen() {
		while (listening) {
			DatagramPacket packet = new DatagramPacket(receivedDataBuffer, MAX_PACKET_SIZE);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			process(packet);
		}
	}

	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		// dump(packet);
		if (new String(data, 0, 4).equals("RCDB")) {
			RCDatabase database = RCDatabase.Deserialize(data);
			process(database);
		} else if (data[0] == 0x40 && data[1] == 0x40) {
			int x = java.nio.ByteBuffer.wrap(data, 3, 6).getInt();
			int y = java.nio.ByteBuffer.wrap(data, 7, 10).getInt();
			String username = new String(data, 11, packet.getLength());
			ServerClient client = new ServerClient(address, port, username, x, y, false, 0);
			switch (data[2]) {
			case 0x00:
				System.out.println("[" + address.getHostAddress() + ":" + port + "] " + username + "has connected at : "
						+ x + ", " + y);
				this.addConnection(client);
				break;
			case 0x01:
				System.out.println("[" + address.getHostAddress() + ":" + port + "] " + username + "has left...");
				this.removeConnection(client);
				break;

			}
		} else if (data[0] == 0x20 && data[1] == 0x20) {
			int x = java.nio.ByteBuffer.wrap(data, 3, 6).getInt();
			int y = java.nio.ByteBuffer.wrap(data, 7, 10).getInt();
			boolean walking = data[11] != 0;
			int direction = java.nio.ByteBuffer.wrap(data, 12, 16).getInt();
			String username = new String(data, 17, packet.getLength());
			MovePacket info = new MovePacket(address, port, username, x, y, walking, direction);
			switch (data[2]) {
			case 0x02:
				this.handleMove(info);
				break;
			}
		}
	}

	private void addConnection(ServerClient client) {
		boolean alreadyConnected = false;
		for (ServerClient sc : this.clientsList) {
			if (client.getUsername().equalsIgnoreCase(sc.getUsername())) {
				if (sc.address == null) {
					sc.address = client.address;
				}
				if (sc.port == -1) {
					sc.port = client.port;
				}
				alreadyConnected = true;
			} else {
				sendPacket(client, sc, PACKET_TYPE_CONNECT);
				sendPacket(sc, client, PACKET_TYPE_CONNECT);
			}
		}
		if (!alreadyConnected) {
			this.clientsList.add(client);
		}
	}

	private void removeConnection(ServerClient client) {
		this.clientsList.remove(getServerClientIndex(client.getUsername()));
		for (ServerClient sc : this.clientsList) {
			sendPacket(client, sc, PACKET_TYPE_DISCONNECT);
		}
	}

	public void handleMove(MovePacket packet) {
		// if (getServerClient(client.getUsername()) != null) {
		// System.out.println("rip" + packet);
		int index = getServerClientIndex(packet.getUsername());
		ServerClient sc = this.clientsList.get(index - 1);
		sc.x = packet.getX();
		sc.y = packet.getY();
		sc.setWalking(packet.isWalking());
		sc.setDirection(packet.getDirection());
		sendMovePacket(packet, sc, PACKET_TYPE_MOVE, sc.isWalking(), sc.getDirection());
		// }
	}

	public ServerClient getServerClient(String username) {
		for (ServerClient sc : this.clientsList) {
			if (sc.getUsername().equals(username)) {
				return sc;
			}
		}
		return null;
	}

	public int getServerClientIndex(String username) {
		int index = 0;
		for (ServerClient sc : this.clientsList) {
			if (sc.getUsername().equals(username)) {
				break;
			}
			index++;
		}
		return index;
	}

	private void process(RCDatabase database) {
		System.out.println("Received databases!!!");
		// dump(database);
	}

	public void send(byte[] data, InetAddress address, int port) {
		assert (socket.isConnected());
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendAll(byte[] data) {
		for (ServerClient sc : clientsList) {
			send(data, sc.address, sc.port);
		}
	}

	public void sendPacket(ServerClient client, ServerClient destination, byte packetType) {
		int x = client.getX();
		int y = client.getY();
		String username = client.getUsername();
		BinaryWriter writer = new BinaryWriter();
		writer.write(PACKET_HEADER);
		writer.write(packetType);
		writer.write(x);
		writer.write(y);
		writer.write(username.getBytes());
		send(writer.getBuffer(), destination.address, destination.port);
	}

	public void sendMovePacket(MovePacket packet, ServerClient destination, byte packetType, boolean walking,
			int direction) {
		int x = packet.getX();
		int y = packet.getY();
		String username = packet.getUsername();
		BinaryWriter writer = new BinaryWriter();
		writer.write(PACKET_HEADER_ACTION);
		writer.write(packetType);
		writer.write(x);
		writer.write(y);
		writer.write(walking);
		writer.write(direction);
		writer.write(username.getBytes());
		send(writer.getBuffer(), destination.address, destination.port);
	}

	private void dump(DatagramPacket packet) {
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();

		System.out.println("--------------------------------------------");
		System.out.println("Packet:");
		System.out.println("\t" + address.getHostAddress() + ":" + port);
		System.out.println();
		System.out.println("\tContents:");
		System.out.print("\t\t");
		for (int i = 0; i < packet.getLength(); i++) {
			System.out.printf("%x ", data[i]);
			if ((i + 1) % 16 == 0)
				System.out.println("\n\t\t");
		}
		System.out.println();
		System.out.println("--------------------------------------------");
	}

	private void dump(RCDatabase database) {
		System.out.println("--------------------------------------------");
		System.out.println("                 RCDatabase                 ");
		System.out.println("--------------------------------------------");
		System.out.println("Name: " + database.getName());
		System.out.println("Size: " + database.getSize());
		System.out.println("Object Count: " + database.objects.size());
		System.out.println();
		for (RCObject object : database.objects) {
			System.out.println("\tObject:");
			System.out.println("\tName: " + object.getName());
			System.out.println("\tSize: " + object.getSize());
			System.out.println("\tField Count: " + object.fields.size());
			for (RCField field : object.fields) {
				System.out.println("\t\tField:");
				System.out.println("\t\tName: " + field.getName());
				System.out.println("\t\tSize: " + field.getSize());
				String data = "";
				switch (field.type) {
				case Type.BYTE:
					data += field.getByte();
					break;
				case Type.SHORT:
					data += field.getShort();
					break;
				case Type.CHAR:
					data += field.getChar();
					break;
				case Type.INTEGER:
					data += field.getInt();
					break;
				case Type.LONG:
					data += field.getLong();
					break;
				case Type.FLOAT:
					data += field.getFloat();
					break;
				case Type.DOUBLE:
					data += field.getDouble();
					break;
				case Type.BOOLEAN:
					data += field.getBoolean();
					break;
				}
				System.out.println("\t\tData: " + data);
				System.out.println();
			}
			System.out.println();
		}
		System.out.println("--------------------------------------------");
	}
}
