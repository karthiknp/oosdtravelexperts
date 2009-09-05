package com.travelexperts;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginFrame extends JOptionPane {

	boolean authenticated = false;
	String strLogin;
	String strPassword;
	
	JPanel pnlLogin = new JPanel(new GridLayout(3, 2, 20, 20));
	JTextField txtLogin = new JTextField(20);
	JPasswordField txtPassword = new JPasswordField(20);
	
	public LoginFrame() {
		super();
		
		pnlLogin.add(new JLabel("Username:"));
		pnlLogin.add(txtLogin);
		pnlLogin.add(new JLabel("Password:"));
		pnlLogin.add(txtPassword);
	}
	
	public boolean showLogin() {
		// disabled for now
		return true;
		
		/*
		showConfirmDialog(null, pnlLogin);
		
		// Return true if login successful
		if(String.copyValueOf(txtPassword.getPassword()).compareTo("oosd") == 0) { // Returns 0 if equal
			return true;
		}
		
		return false;
		*/
	}
}
