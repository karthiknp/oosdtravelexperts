package com.travelexperts;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class SimpleClient extends JInternalFrame implements KeyListener
{
    private JTextArea userInputTextArea, userOutputTextArea;
    private JScrollPane inputScrollPane, outputScrollPane;
    private TitledBorder inputBorder, outputBorder;
	
    private InputStream input;
    private OutputStream output;
	
    public SimpleClient(String ip, int port)
    {	
        super("SimpleClient");
        createGUI();
        connect(ip, port);
    }
	
    private void createGUI()
    {
        userInputTextArea = new JTextArea();
        userInputTextArea.addKeyListener(this);
        userOutputTextArea = new JTextArea();
        userOutputTextArea.setEditable(false);
        inputScrollPane = new JScrollPane(userInputTextArea);
        outputScrollPane = new JScrollPane(userOutputTextArea);
	
        inputBorder = new TitledBorder("User Input");
        inputScrollPane.setBorder(inputBorder);

        outputBorder = new TitledBorder("Server Output");
        outputScrollPane.setBorder(outputBorder);

        getContentPane().setLayout(new GridLayout(2,1));
        getContentPane().add(inputScrollPane);
        getContentPane().add(outputScrollPane);
		
        // addWindowListener(new MyWindowListener());
        setSize(200,400);
        setVisible(true);
              
    }

    // Attempt connection to server.  If successful, obtain the
    // input and output streams.

    private void connect(String ip, int port)
    {
        try
        {
            System.err.println("Attempting connection to " + ip + ":" + port);
            Socket clientSocket = new Socket(ip, port);
            System.err.println("Now connected to " + ip + ":" + port);
            output = clientSocket.getOutputStream();
            input = clientSocket.getInputStream();
        }
        catch(IOException e)
        {
            System.err.println("Error setting up connection or streams: " +
                e.getMessage());
            System.exit(1);
        }
    }

    // to satisfy the KeyListener interface
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    // This method is triggered whenever the user types a key in the
    // userInputTextArea.  The key is retrieved from the KeyEvent and
    // written to the output stream connected to the server.  Then 
    // the input stream is read, which should produce an echo of the
    // character just written.  The received character is appended to
    // the userOutputTextArea.  Upon exit from this method, the system
    // finally displays the character typed by the user in the 
    // userInputTextArea.

    public void keyTyped(KeyEvent e)
    {
        int ch;
		
        try
        {
            output.write( e.getKeyChar() );
            ch=input.read();
			
            if ( ch != -1)
            {
                String temp = new String((char)ch + "");
                userOutputTextArea.append(temp);
            }
        }
        catch (IOException ex)
        {
            System.err.println("Communications Error: " + ex.getMessage());
        }
    }
    // inner member class to represent the listener object for WindowEvent's
    public class MyWindowListener extends WindowAdapter
    {
	    // Be nice to the server.  Close the streams before exit.
          // This avoids an exception being thrown in the server.

	    public void windowClosing(WindowEvent e)
	    {
                  try
                      {
                          if(output != null) output.close();
                          if(input != null) input.close();
                      }
                      catch(Exception excp)
                      { }

	                System.exit(0);
	     }
    }// end of inner class

    public static void main(String args[]) throws Exception
    {
        if (args.length == 2 )
        {
            SimpleClient client = new SimpleClient(args[0], Integer.parseInt(args[1]));
        }
        else
        {
            System.out.println("Usage  : java SimpleClient ip-address port-number");
            System.out.println("Example: java SimpleClient 192.168.2.1 4555");
        }
    }
}
