/*
 <applet code="ChatApplet.class" width="600" height="400"
 */
package com.travelexperts.www;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/***
 * Web Applet that customers can use to connect to the server
 * instantly via the webpage.
 * 
 * @author will_ad
 *
 */
@SuppressWarnings("serial")
public class ClientSupportApplet extends JApplet implements Runnable {
	
	private static final String CHAT_HOST = "localhost";
	private static final int CHAT_PORT = 3456;
	private static final int TEXT_WIDTH = 50; 	
	
	private static final int APPLET_WIDTH = 500; 	
	private static final int APPLET_HEIGHT = 200; 	

	// Username = GuestXXX if no auth info passed
	String username = "Guest" + (int)((Math.random()*899)+100);
	String password = "";
	
	static JTextArea txtChannel = new JTextArea(8, TEXT_WIDTH);	// Chat window
	JTextField txtInput = new JTextField(TEXT_WIDTH);		// Input
	
	JPanel pnlNorth = new JPanel();		// Info
	JPanel pnlCenter = new JPanel();	// Chat Text
	JPanel pnlSouth = new JPanel();		// Input
	
	// Communication stuff
	BufferedReader bfrIncoming;			// Incoming messages from server
	BufferedWriter bfrOutgoing;			// Outoing messages
	
	/** 
	 * Login info can passed from web form when applet is loaded
	 * Ie. param username="delton" password="secret" in <applet> tag
	 * @param username The user to log in as
	 * @param password Their password
	 * 
	 * If no parameters passed, defaults will be used
	 */
	public ClientSupportApplet() {
		super();

		/* 
		// Crashes if unknown source
		if((getParameter("username") != null) && (getParameter("password") != null)) {
			username = getParameter("username");
			password = getParameter("password");
		}
		*/
	}
	
	@Override
	public void init() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				initForm();		// Called in eventqueue for thread safety
			}
		});
	}
	
	@Override
	public void start() {
		Thread listener = new Thread(this);
		listener.setDaemon(true);
		listener.start();
	}
	
	@Override
	public void stop() {
		try {
			bfrIncoming.close();
			bfrOutgoing.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Authentiate login info from server
	 */
	public void authCustomer() {
		// TODO: implement server-side user/password authentication 
	}
	
	public void initForm() 
	{
		pnlNorth.add(new JLabel("Connected as " + username));
		
		txtChannel.setEditable(true);
		pnlCenter.add(txtChannel);
		
		pnlSouth.add(txtInput);
		txtInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String outgoingMessage = username + ": " + txtInput.getText() + "\r\n";
				sendMessage(outgoingMessage);
				txtInput.setText("");
			}
		});
		
		// Attach content panels to main frame 
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);

		setSize(APPLET_WIDTH, APPLET_HEIGHT);
	}

	@Override
	public void run() {
		// Listens for incoming messages and updates applet
		try {
			Socket sktIncoming = new Socket(CHAT_HOST, CHAT_PORT);
			bfrIncoming = new BufferedReader(new InputStreamReader(sktIncoming.getInputStream()));
			bfrOutgoing = new BufferedWriter(new OutputStreamWriter(sktIncoming.getOutputStream()));
			while(true) {
				// Anything coming in?
				if(bfrIncoming.ready()) {
					String messageReceived = bfrIncoming.readLine();
					receiveMessage(messageReceived);
					System.out.println("Incoming: " + messageReceived);
				}
				else
					Thread.yield();
			}
		}
		catch(IOException ex) { 
			receiveMessage("Server Offline: Sorry, no Travel Experts agents are available to help you right now");
			ex.printStackTrace(); 
		}
	}
	
	// Simply echos to channel, server echo's back our messages too 
	// setText is threadsafe so no worries
	public void receiveMessage(String message) {
		txtChannel.setText(txtChannel.getText() + message + "\r\n");
	}
	
	public void sendMessage(String message) {
		try {
			bfrOutgoing.write(message);		// Send to server
			bfrOutgoing.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
