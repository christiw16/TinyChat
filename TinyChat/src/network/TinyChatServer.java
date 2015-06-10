package network;

import java.io.*;
import java.net.*;
import java.util.Vector;

/**
 * Uses code and ideas from Dr. VanDeGrift's TCP Server example and http://introcs.cs.princeton.edu/java/84network/ChatServer.java.html
 * Starts a server
 */



public class TinyChatServer implements Runnable {
	//for multithreading
	public void run() {
		
		//vector to hold connections
		Vector<TinyChatConnection> connections = new Vector<TinyChatConnection>();
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(9999);

			TinyChatConnectionListener connectionListener = new TinyChatConnectionListener(connections);

			// listener thread started
			connectionListener.start();

			while (true) {

				Socket clientSocket;
				//accepts incoming connection
				clientSocket = serverSocket.accept();

				System.err.println("Connected with client");

				//client gets new thread, added to listener
				TinyChatConnection connection = new TinyChatConnection(clientSocket);
				connections.add(connection);
				connection.start();

			}
		}
		catch (BindException e){
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}
