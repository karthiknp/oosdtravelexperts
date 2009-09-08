package com.travelexperts;

import java.awt.CardLayout;
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
 * @author will_ad
 *
 */
@SuppressWarnings("serial")
public class LoginSystem {
	
	private static int agentID = 0;
	
	private static final int TEXTFIELD_WIDTH = 25; 
	
	private static String currentUser = "Not logged in";

	// Stuff for the login form
	private static JTextField txtUsername = new JTextField(TEXTFIELD_WIDTH);
	private static JPasswordField txtPassword = new JPasswordField(TEXTFIELD_WIDTH);
	private static JPanel mainPanel = new JPanel(new GridLayout(2, 2));

	static {
		mainPanel.add(new JLabel("Username:"));
		mainPanel.add(txtUsername);
		mainPanel.add(new JLabel("Password:"));
		mainPanel.add(txtPassword);
	}
	
	static public boolean showForm() {
		
		//JOptionPane.showMessageDialog(null, mainPanel, "Please Log In", JOptionPane.PLAIN_MESSAGE);
		JOptionPane.showMessageDialog(null, mainPanel);
		
		return authenticateUser(txtUsername.getText(), txtPassword.getPassword().toString());
	}
	
	static public boolean authenticateUser(String username, String password) {
		
		PreparedStatement authStatement;		
		try {
			authStatement = new TXConnection().getInstance()
			.prepareStatement("SELECT * FROM Agents WHERE AgtLastName = 'Dalton'");
			authStatement.setString(1, username);
			ResultSet rs = authStatement.executeQuery();
			
			if(rs.next()) {		// Any results? 
				System.out.println("Found: " + rs.getString("AgtLastName"));
				return true;	// Auth passed!
			}
			
			authStatement.getConnection().close();
			authStatement.close();
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;	// Auth failed otherwise
	}

	public static int getAgentID() {
		return agentID;
	}

}
