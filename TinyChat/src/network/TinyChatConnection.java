package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
//Uses code and ideas from http://introcs.cs.princeton.edu/java/84network/Connection.java.html
//deals with individual connections
public class TinyChatConnection extends Thread {
	private String clientMessage;
	private Socket socket;
	private BufferedReader clientIn;
	private DataOutputStream clientOut;
	
	public TinyChatConnection(Socket socket){
		this.socket = socket;
	}
	
	//sends to clients
	public void print(String s){
        try {
			clientOut.writeBytes(s + " \n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//gets input
	
	public void run(){
		try {
			String s;
	       
			while(true) {
				 clientIn = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));

				 clientOut = 
					
						new DataOutputStream(socket.getOutputStream());
				 while ((s = clientIn.readLine()) != null) {
			            setMessage(s);
			        }				
			}
		}
		catch (Exception e) {
			System.out.println("An error occurred while creating server socket or reading data from client.");
		}
	}
	
	//synched so no getting and setting of message at the same time
	//Used to allow TinyChatConnectionListener to get message
    public synchronized String getMessage() {
    	if (clientMessage == null) return null;
        String toSend = clientMessage;
        clientMessage = null;
        notifyAll();
        return toSend;
    }
	//used to send
	public synchronized void setMessage(String s){
		 if (clientMessage != null) {
	            try                  { wait();               }
	            catch (Exception ex) { ex.printStackTrace(); }
	        }
		 clientMessage = s;
	    }
}
