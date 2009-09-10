package com.travelexperts;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * This class uses all static methods so other objects may easily
 * find the current user logged in. 
 * 
 * @author Will_Dixon
 *
 */
public class LoginSystem {
	
	private static final int TEXTFIELD_WIDTH = 25; 
	
	private static String currentUsername = "Travel Experts Agent";
	private static int currentAgentID = 0;

	// Stuff for the login form
	private static JTextField txtUsername = new JTextField(TEXTFIELD_WIDTH);
	private static JPasswordField txtPassword = new JPasswordField(TEXTFIELD_WIDTH);
	private static JPanel mainPanel = new JPanel(new GridLayout(2, 2));

	static {
		mainPanel.add(new JLabel("Username:"));
		mainPanel.add(txtUsername);
		mainPanel.add(new JLabel("Password:"));
		mainPanel.add(txtPassword);
		mainPanel.setLayout(new GridBagLayout());
	}
	
	// Method to show popup and return if authentication successfull
	static public boolean showForm() {
		JOptionPane.showMessageDialog(null, mainPanel);
		
		return authenticateUser(txtUsername.getText(), String.copyValueOf(txtPassword.getPassword()) );
	}
	
	static public boolean authenticateUser(String username, String password) {
		
		PreparedStatement authStatement;
		boolean isAuthenticated = false;
		try {
			authStatement = TXConnection.getConnection()
				.prepareStatement("SELECT * FROM Agents WHERE AgtLastName = ? AND Password = ?");
			authStatement.setString(1, username);
			authStatement.setString(2, password);
			ResultSet rs = authStatement.executeQuery();
			
			if(rs.next()) {		// Any results? 
				System.out.println("Found: " + rs.getString("AgtLastName"));
				isAuthenticated = true;	// Auth passed!
			}
			
			authStatement.getConnection().close();
			authStatement.close();
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAuthenticated;	// Auth failed otherwise
	}

	public static int getCurrentAgentID() {
		return currentAgentID;
	}
	
	public static String getCurrentUsername() {
		return currentUsername;
	}

}
