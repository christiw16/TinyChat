package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TinyChatListener {
	//client/server stuff
	private DataOutputStream serverOut;
	private BufferedReader serverIn;
	@SuppressWarnings("unused")
	private String host;
	private Socket clientSocket;
	//Scanner scanner;

	public TinyChatListener(String host) throws IOException {
		this.host = host;
		

			// create socket connection to given host at at port 9999

			clientSocket = new Socket(host, 9999);
			serverOut = 
					new DataOutputStream(clientSocket.getOutputStream());

			// create stream for reading data from server
			serverIn = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
		
	
	}

	public void send(String message){
		// send data to server
		try{
			serverOut.writeBytes(message);
		}
		catch(Exception e) {
			System.out.println("Exception occurred: " + e.getMessage());
		}
	}

	//reads from server
	public String readLine() {
		String line;
		try                 { line = serverIn.readLine();}

		catch (Exception e) { line = null;               }
		return line;

	}

	//closes socket
	public void close(){
		try{
			clientSocket.close();
		}
		catch(Exception e){
			System.out.println("Exception occurred: " + e.getMessage());

		}
	}
}





