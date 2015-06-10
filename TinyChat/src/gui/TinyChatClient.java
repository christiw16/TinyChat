package gui;


/*
 * Using code from http://introcs.cs.princeton.edu/java/84network/ChatClient.java.html and 
 * Dr. VanDeGrift's example TCP server and client code as a general guide.
 * 
 */


import javax.swing.*;

import network.TinyChatListener;
import network.TinyChatServer;

import java.awt.*;
import java.awt.event.*;



public class TinyChatClient  extends JFrame implements ActionListener  {
	private static final long serialVersionUID = 1;
	public String host = "localhost";


	//GUI stuff
	private JTextArea  enteredText = new JTextArea(10, 32);
	private JTextField typedText   = new JTextField(32);
	private TinyChatListener listen;
	private boolean setUpClient = true;
	private boolean setUpName = true;
	private boolean setUpIp = true;
	private boolean madeListener = false;
	private boolean isClient;
	private String userName;
	//private TinyChatClient client;
	public static void main(String[] args) {
		TinyChatClient client = new TinyChatClient();
		client.setUpClient();
		while(client.setUpIp && ! client.madeListener){
			try                 { Thread.sleep(100);   }
			catch (Exception e) { e.printStackTrace(); }
		}
		client.listen();
	}
	//sets up GUI
	public TinyChatClient() {		

		//GUI stuff
		setTitle("TinyChat");
		setSize(600,600); 
		setLocation(10,200);
		enteredText.setEditable(false);
		enteredText.setBackground(Color.WHITE);
		//send text input
		typedText.addActionListener(this);

		//layout
		Container content = getContentPane();
		content.add(new JScrollPane(enteredText), BorderLayout.CENTER);
		content.add(typedText, BorderLayout.SOUTH);
		typedText.requestFocusInWindow();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     



	}
	//Init UX
	public void setUpClient(){
		writeText("Server or Client? \n (0- Server 1 - Client) \n");
	}
	
	public void setUpName(){
		writeText("Please enter a username \n");
	}
	public void setUpIp(){
		if(isClient){
			writeText("Please enter the TinyChat server's IP address \n");//get ip if client
		}
		else{
			setUpIp = false;
			(new Thread(new TinyChatServer())).start(); //if server, create server, leave host as localhost
			writeText("Awaiting connections \n");

		}
	}

	//writes to textfield
	public void writeText(String s){
		enteredText.insert(s, enteredText.getText().length());
		enteredText.setCaretPosition(enteredText.getText().length());
		typedText.setText("");
		typedText.requestFocusInWindow();
	}

	//sending a message  
	@Override
	public void actionPerformed(ActionEvent e) {
		//for set up
		if(setUpClient){
			if(typedText.getText().equals("0")){
				writeText("You are the server \n");
				isClient = false;
				setUpClient = false;
				setUpName();
			}
			else if(typedText.getText().equals("1")){
				writeText("You are a host \n");
				isClient = true;
				setUpClient = false;
				setUpName();
			}
			else{
				writeText("ERROR, try again \n");
				setUpClient();
			}
		

		}
		else if(setUpName){
			userName = typedText.getText();
			writeText("Welcome " +  userName + " \n");
			setUpName = false;
			setUpIp();
		}
		else if(setUpIp){
			host = typedText.getText();
			writeText("Your friend's IP address is " +  host + " \nSay hi. \n");
			setUpIp = false;	
			//(new Thread(new TinyChatServer())).start();//Don't need a server in client mode
		}
		//normal operation
		else{
			listen.send(userName + ": " + typedText.getText() + "\n");
			typedText.setText("");
			typedText.requestFocusInWindow();
		}

	}
//listens for incoming text
	public void listen(){
		String s;
		//network stuff
		try{
			listen = new TinyChatListener(host);
		}
		catch(Exception ex){
			System.out.println("Error");
		}

		while ((s = listen.readLine()) != null) {//reads from listen until nothing left to listen to
			try{
				listen = new TinyChatListener(host);
			}
			catch(Exception ex){
				System.out.println("Error");
			}
			writeText(s + "\n");
		}
		System.out.println("exit");

	}

}



