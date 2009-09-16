package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

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
	
	JPanel pnlNorth = new JPanel();
	JPanel pnlCenter = new JPanel();
	JPanel pnlSouth = new JPanel();
	JButton btnLogin = new JButton("Log In");
	JTextField txtUsername = new JTextField(INPUT_WIDTH);
	JPasswordField txtPassword = new JPasswordField(INPUT_WIDTH);
	
	public LoginSystem(final TravelExpertsGUI parentFrame) {
		super("Employee Log-In", false, false, false, false);

		pnlNorth.add(new JLabel
				("<html><em>Access to this system is for authorized users only.</em><br/><br/>" +
						"Any unauthorized entry or attempt to enter is strictly forbidden <br/>" +
						"and will result in prosecution to the maximum extent allowable<br/>" +
						" by applicable law. <br/><br/>" +
						"Users are reminded not to share passwords and to choose<br/> " +
						"passwords that are unique and difficult to guess.<br/><br/><html>"));
		pnlNorth.setSize(400, 300);
		pnlNorth.revalidate();

		pnlCenter.setLayout(new GridLayout(5, 1));
		pnlCenter.add(new JLabel("Username"), "help");
		pnlCenter.add(txtUsername);
		pnlCenter.add(new JLabel("Password:"));
		pnlCenter.add(txtPassword);

		pnlCenter.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		pnlSouth.add(btnLogin);
		
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		pack();
		
		setLocation(300, 200);
		setVisible(true);
		

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
						setVisible(false);
						JOptionPane.showMessageDialog(null, 
								"Logged in succesfully as " + rs.getString("AgtFirstName") + " " + rs.getString("AgtLastName"));
						TXLogger.logInfo("Logged in as " + username);
					}
					else {
						JOptionPane.showMessageDialog(null, "Invalid username or password.");
						TXLogger.logInfo("Login attempt failed for: " + txtUsername.getText() );
					}
					// Clean up
					rs.getStatement().close();
					rs.close();
					
					if(getCurrentUsername() != null) {
						dispose();	// Done, so close frame if login successful
					}

				} catch (SQLException e1) {
					TXLogger.logError(e1.getMessage());
					e1.printStackTrace();
				}
				
			}
		});

	}
	
	private static void setUsername(String s) {
		username = s;
	}
	
	static String getCurrentUsername() {
		if(username == null) return null;
		return username;
	}

}
