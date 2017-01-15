package com.ptga123.Desbian.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.ptga123.Desbian.Game;
import com.ptga123.Desbian.entity.mob.Player;
import com.ptga123.Desbian.net.player.NetPlayer;
import com.ptga123.Desbian.util.BinaryWriter;

import core.RCDatabase;

public class Client extends Thread {

	private final static byte[] PACKET_HEADER = new byte[] { 0x40, 0x40 };
	private final static byte[] PACKET_HEADER_ACTION = new byte[] { 0x20, 0x20 };
	private final static byte PACKET_TYPE_CONNECT = 0x0;
	private final static byte PACKET_TYPE_DISCONNECT = 0x1;
	private final static byte PACKET_TYPE_MOVE = 0x2;

	public enum Error {
		NONE, INVALID_HOST, SOCKET_EXECEPTION
	}

	private String ipAddress;
	private int port;
	private Game game;
	private Player player;
	private Error errorCode = Error.NONE;
	private InetAddress serverAddress;
	private boolean listening = false;
	private Thread listenThread;
	private DatagramSocket socket;

	/*
	 * Host: 192.168.1.1:5000
	 */
	public Client(String host) {
		String[] parts = host.split(":");
		if (parts.length != 2) {
			errorCode = Error.INVALID_HOST;
			return;
		}
		ipAddress = parts[0];
		try {
			port = Integer.parseInt(parts[1]);
		} catch (NumberFormatException e) {
			errorCode = Error.INVALID_HOST;
			return;
		}
	}

	/*
	 * Host: 192.168.1.1 Port: 5000
	 */
	public Client(Game game, String host, int port) {
		this.game = game;
		this.ipAddress = host;
		this.port = port;

	}

	public boolean connect() {
		try {
			serverAddress = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			errorCode = Error.INVALID_HOST;
			return false;
		}

		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			errorCode = Error.SOCKET_EXECEPTION;
			return false;
		}
		listening = true;
		listenThread = new Thread(() -> listen(), "Client - ListenThread");
		listenThread.start();
		sendPacket(PACKET_TYPE_CONNECT);
		return true;
	}

	public void disconnect() {
		try {
			listening = false;
			listenThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sendPacket(PACKET_TYPE_DISCONNECT);
	}

	public void sendPacket(byte packetType) {
		int x = game.player.getX() / 16;
		int y = game.player.getY() / 16;
		String username = game.player.getUsername();
		BinaryWriter writer = new BinaryWriter();
		writer.write(PACKET_HEADER);
		writer.write(packetType);
		writer.write(x);
		writer.write(y);
		writer.write(username.getBytes());
		send(writer.getBuffer());
	}

	public void sendMovePacket(byte packetType, boolean walking, int direction, int x, int y) {
		String username = game.player.getUsername();
		BinaryWriter writer = new BinaryWriter();
		writer.write(PACKET_HEADER_ACTION);
		writer.write(packetType);
		writer.write(x);
		writer.write(y);
		writer.write(walking);
		writer.write(direction);
		writer.write(username.getBytes());
		send(writer.getBuffer());
	}

	public void listen() {
		while (listening) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			process(packet);
			// System.out.println("Server > " + new String(packet.getData()));
		}
	}

	private void send(byte[] data) {
		assert (socket.isConnected());
		DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(RCDatabase database) {
		byte[] data = new byte[database.getSize()];
		database.getBytes(data, 0);
		send(data);
	}

	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		if (data[0] == 0x40 && data[1] == 0x40) {
			int x = java.nio.ByteBuffer.wrap(data, 3, 6).getInt();
			int y = java.nio.ByteBuffer.wrap(data, 7, 10).getInt();
			String username = new String(data, 11, packet.getLength());
			switch (data[2]) {
			case 0x00:
				System.out.println("[" + address.getHostAddress() + ":" + port + "] " + username
						+ "has joined the game at :" + x + ", " + y);
				NetPlayer player = new NetPlayer(address, port, username, x * 16, y * 16);
				game.level.add(player);
				break;
			case 0x01:
				System.out.println(
						"[" + address.getHostAddress() + ":" + port + "] " + username + "has left the game...");
				game.level.removeNetPlayer(username);
				break;
			}
		}
		if (data[0] == 0x20 && data[1] == 0x20) {
			int x = java.nio.ByteBuffer.wrap(data, 3, 6).getInt();
			int y = java.nio.ByteBuffer.wrap(data, 7, 10).getInt();
			boolean walking = data[11] != 0;
			int direction = java.nio.ByteBuffer.wrap(data, 12, 16).getInt();
			String username = new String(data, 17, packet.getLength());
			switch (data[2]) {
			case 0x02:
				handleMove(x, y, username, walking, direction);
				break;
			}
		}
	}

	private void handleMove(int x, int y, String username, boolean walking, int direction) {
		System.out.println("riperino");
		this.game.level.movePlayer(x, y, username, walking, direction);
	}

	public Error getErrorCode() {
		return errorCode;
	}

}
