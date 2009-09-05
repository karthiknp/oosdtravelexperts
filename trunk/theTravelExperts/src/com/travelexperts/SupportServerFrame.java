package com.travelexperts;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.net.ssl.SSLServerSocketFactory;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * 
 * @author Will Dixon 
 *
 *  The chat server listens for incoming connections in a separate thread
 *  so as to not hinder the GUI 
 * 
 *  All code by Will Dixon
 *  
 */
@SuppressWarnings("serial")
public class SupportServerFrame extends JInternalFrame implements Runnable {
	
	public final static int SERVER_PORT = 3456;	// Ports to listen on
	public final static int SERVER_SSL_PORT = 4567; // SSL not implemented yet
	
	ServerSocket serverSocket = null;		// Initialized in constructor
	ServerSocket sslServerSocket = null;

	// This thread group will contain all connected clients
	ThreadGroup clientThreads = new ThreadGroup("Clients");

	// Contain all running chat clients in an iterable, growable array
	private static Vector<handleClient> allClients = new Vector<handleClient>(10, 5);
	
	// GUI stuff
	private final JTextArea txtMessages = new JTextArea(10, 80);
	JList lstUsers = new JList();				// List of online users (from web client)
	JComboBox cboSendTo = new JComboBox();		// Who to send message to
	JTextField txtInput = new JTextField(70);	// Input area for messages
	JButton btnSend = new JButton("Send");		// Button to send the messages
	
	JPanel pnlCenter = new JPanel();				// Contains main message window
	JPanel pnlEast = new JPanel();					// Contains user list
	JPanel pnlSouth = new JPanel(new FlowLayout());	// Contains input and send button
	
	private volatile boolean serverOnline = true;	// Set to false to shut down server

	private String serverLog = new String();

	public SupportServerFrame() throws IOException, InterruptedException {
		super("Travel Experts Online Support", true,  true, true, true);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		// Set up server socket to listen on
		serverSocket = new ServerSocket(SERVER_PORT);
		sslServerSocket = SSLServerSocketFactory.getDefault().createServerSocket(SERVER_SSL_PORT);
		
		// Call the run method in new thread so that GUI is still usable
		// (because it is an infinite loop)
		// Run as daemon: server will exit automatically when all other threads are closed
		Thread serverThread = new Thread(this);
		serverThread.setDaemon(true);
		serverThread.start();
		
		pnlCenter.add(new JScrollPane(txtMessages));
		
		pnlSouth.add(cboSendTo);
		pnlSouth.add(new JScrollPane(txtInput));
		pnlSouth.add(btnSend);
		
		// Make cool border
		lstUsers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Online Users:"));
		JScrollPane jspUsers = new JScrollPane(lstUsers);
		jspUsers.setPreferredSize(new Dimension(300, 300));
		pnlEast.add(jspUsers);		// List should be in a scroll pane
		
		initComponents();	// Add action handlers to components
		
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		add(pnlEast, BorderLayout.EAST);
		
		pack();		// Auto-size the frame based on computed size of components
		setVisible(true);
		
		// Handler for frame disposal, confirms then ensures connections get closed  
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Any connected users will be disconnected!  Proceed?") == JOptionPane.OK_OPTION) {
					serverOnline = false;
					setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				}
			}
		});
	}
	
	// Just adds some event handling to chat frame components
	public void initComponents() {
		
		// Input 
		txtInput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				messageToAll(txtInput.getText());
				txtInput.setText("");
			}
		});
		
		// Send button just does the same thing
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
	
	private void logMessage(String message) {
		serverLog += message;
		txtMessages.setText(serverLog);
		System.out.println(message);
	}
	
	// Refresh lstUsers to show all online users
	private void refreshUsers() {
		synchronized (allClients) {
			
			// Use event queue as JList methods are not thread safe
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					DefaultListModel listModel = new DefaultListModel();
					
					// Add connected client names to list
					Iterator<handleClient> i = allClients.iterator();
					while(i.hasNext()) {
						listModel.addElement(i.next().username);
					}
					lstUsers.setModel(listModel);
					lstUsers.revalidate();
				}
			});			
		}
	}
	
	private String formatMessage(String unformattedMessage) {
		// Format with sender and date
		return Thread.currentThread().getName() + "( " +
			DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date())+ ") :" +
			unformattedMessage + "\r\n";
		
	}
	
	private void messageToAll(String outgoingMessage) {
		synchronized(allClients) {
			outgoingMessage = formatMessage(outgoingMessage);
			// Send the message to other threads (contained in allClients)
			Iterator<handleClient> i = allClients.iterator();
			while(i.hasNext()) {
				i.next().sendMessage(outgoingMessage);
			}
			
			// This method only calls JTextComponent.setText
			// which is one of the few thread-safe Swing methods
			logMessage(outgoingMessage);
		}
	}
	
	// Called when run as a thread
	@Override
	public void run() {
		// Set the timeout for the server socket to 5 seconds
		// This prevents the ServerSocket.accept() method from blocking indefinitely
		// which will prevent it from closing
		try {
			serverSocket.setSoTimeout(100);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		
		while(serverOnline) {
			try {
				
				// Listen for a connection
				Socket clientSocket = serverSocket.accept();
				
				System.out.println(clientSocket.getInetAddress().getHostName() + " connected!");
				
				// Create thread, add to group and use IP address as thread name
				Thread clientThread = new Thread(clientThreads, new handleClient(clientSocket), clientSocket.getRemoteSocketAddress().toString());
				clientThread.start();
				refreshUsers();
			}
			catch (SocketTimeoutException e) {	// Must be caught before IOException
				// Check if server should still be online then continue listening
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		} // End while
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Internal class that will handle connections from clients
	class handleClient implements Runnable {
		
		private BufferedReader clientReader = null;
		private BufferedWriter clientWriter = null;
		
		public String username = "anonymous";
		Socket clientSocket;
		
		public handleClient(Socket newClientSocket) throws IOException {
			clientSocket = newClientSocket;

			// Initialize buffered input/output streams from socket
			clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			clientWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		}
		
		// Method to print a message to this client from other threads
		// Called from outside
		public void sendMessage(String message) {
			String output = Thread.currentThread().getName() + " : " + message + "\r\n"; 
			try {
				clientWriter.write(output);
				clientWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run () {
			// User name = thread name , dirty?
			username = Thread.currentThread().getName();
			String receivedMessage = "";
			// Synchronize lock allClients object to prevent concurrent access from other threads
			synchronized(allClients) {
				// Add this instance to the list
				allClients.add(this);
			}
			try {
				clientWriter.write("Welcome to Travel Experts online support!");
				clientWriter.flush();
				
				// Loop until client sends "/disc" (like IRC) 
				do {
					// Is there any input from client?
					if(clientReader.ready()) {
						
						// Read the message from the stream
						receivedMessage = clientReader.readLine().trim();
						
						messageToAll(receivedMessage);
					}
					else
						// Yield since there's nothing to do  
						Thread.yield();
					
				} while(receivedMessage.compareTo("/quit") != 0);

				clientWriter.close();
				clientReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				allClients.remove(this);
			}
		}
		
	}
}
