package network;
import java.util.Vector;
//Uses code and ideas from http://introcs.cs.princeton.edu/java/84network/ConnectionListener.java.html
//listens for messages from connections

public class TinyChatConnectionListener extends Thread {

	private Vector<TinyChatConnection> connections;

	public TinyChatConnectionListener(Vector<TinyChatConnection> connections) {
		this.connections = connections;
	}

	// get incoming messages, broadcast
	public void run() {
		while (true) {
			for (int i = 0; i < connections.size(); i++) {
				TinyChatConnection x = connections.get(i);

				// get rid of dead connections
				if (!x.isAlive())
					connections.remove(i);

				// broadcast
				String message = x.getMessage();
				if (message != null)
					for (TinyChatConnection j : connections)
						j.print(message);

			}

			//share the CPU
			try                 { Thread.sleep(100);   }
			catch (Exception e) { e.printStackTrace(); }
		}
	}


}
