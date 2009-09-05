package com.travelexperts;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
	JPanel pnlSouth = new JPanel(new FlowLayout());	// Contains input and send button
	
	private volatile boolean serverOnline = true;	// Set to false to shut down server

	private String serverLog = new String();

	public static void main(String[] args) {
		// AWT friendly way of starting a new JFRame
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new SupportServerFrame();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
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
		pnlSouth.add(txtInput);
		pnlSouth.add(btnSend);
		
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
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
	
	// Called when run as a thread
	@Override
	public void run() {
		// Set the timeout for the server socket to 5 seconds
		// This prevents the ServerSocket.accept() method from blocking indefinitely
		// which will prevent it from closing
		// Note: also throttles connections to (100+100)/1000 = 10 per second (5 per port)
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
			}
			catch (SocketTimeoutException e) {	// Must be caught before IOException
				// Do nothing
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
	
	private void logMessage(String message) {
		serverLog += message;
		txtMessages.setText(serverLog);
		System.out.println(message);
	}

	// Internal class that will handle connections from clients
	class handleClient implements Runnable {
		
		private BufferedReader clientReader = null;
		private BufferedWriter clientWriter = null;
		
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
						
						// Format with sender and date
						String outgoingMessage = Thread.currentThread().getName() + "( " +
							DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date())+ ") :" +
							receivedMessage + "\r\n";
						
						// Lock thread while sending messages to other threads
						synchronized(allClients) {
							// Send the message to other threads (contained in allClients)
							Iterator<handleClient> i = allClients.iterator();
							while(i.hasNext()) {
								i.next().sendMessage(outgoingMessage);
							}
							
							// This method only calls JTextComponent.setText
							// which is one of the few thread-safe Swing methods
							logMessage(outgoingMessage);
							
							// This method might interact with Swing components that are not thread safe
							// It must be processed by the EventQueue
							// Alternative: implement SwingWorker interface
							/* TODO: fix this by changing increasing scope of outgoingMessage
							EventQueue.invokeLater(new Runnable() {
								@Override
								public void run() {
									logMessage(outgoingMessage);
								}
							});
							*/
						}
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
