package com.travelexperts;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * This class uses all static methods so other objects may easily
 * find the current user logged in. 
 * 
 * @author Will_Dixon
 *
 */
public class LoginSystem extends JInternalFrame {
	
	private static final long serialVersionUID = -3491761888255913589L;

	public static int POSITION_INACTIVE = 0; 
	public static int POSITION_JUNIOR = 1; 
	public static int POSITION_INTERMEDIATE = 2; 
	public static int POSITION_SENIOR = 3; 

	public static int INPUT_WIDTH = 15; 

	private static String username = null;
	
	JTextField txtUsername = new JTextField(INPUT_WIDTH);
	JPasswordField txtPassword = new JPasswordField(INPUT_WIDTH);
	
	public LoginSystem(final TravelExpertsGUI parentFrame) {
		super("Agent Authentication", false, false, false, false);

		setLayout(new GridLayout(3, 2));	// Will auto-cCenter items

		add(new JLabel("Username"), "help");
		add(txtUsername);
		add(new JLabel("Password:"));
		add(txtPassword);
		add(new JLabel("Hint: Password is disabled, enter any active agent last name"));
		
		JButton btnLogin = new JButton("Log In");
		add(btnLogin);

		// Code for login validation
		btnLogin.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				try {
					
					// Login not case sensitive
					// Use JDBC lcase function for compatability
					PreparedStatement pst = TXConnection.getConnection().prepareStatement("SELECT * FROM Agents WHERE {fn LCASE(AgtLastName)} = ? AND AgtPosition <> 'Inactive'" );
					pst.setString(1, txtUsername.getText().toLowerCase());
					// Password checking disabled until table design is finalized with a password field
					/*
					 * pst.setString(2, String.copyValueOf(txtPassword.getPassword()));
					 */
					ResultSet rs = pst.executeQuery();
					
					// If login successfull
					if(rs.next()) {
						parentFrame.loadAllForms();
						setUsername(rs.getString("AgtLastName"));
						TXLogger.logInfo("Logged in as " + username);
					}
					else {
						TXLogger.logInfo("Login attempt failed for: " + txtUsername.getText() );
					}
					// Clean up
					rs.getStatement().close();
					rs.close();
					
					if(getCurrentUsername() != null) dispose();	// Done, so close frame if login successful

				} catch (SQLException e1) {
					TXLogger.logError(e1.getMessage());
					e1.printStackTrace();
				}
				
			}
		});
		
		pack();
		setLocation(200, 200);
		setVisible(true);
	}
	
	private static void setUsername(String s) {
		username = s;
	}
	
	static String getCurrentUsername() {
		if(username == null) return null;
		return username;
	}

}
