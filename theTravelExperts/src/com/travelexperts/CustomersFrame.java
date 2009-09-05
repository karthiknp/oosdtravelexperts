package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JTextFieldDateEditor;

/**
 * 
 * @author Will_Dixon
 * Customer Editing and Search Form 
 *
 */
@SuppressWarnings("serial")
public class CustomersFrame extends JInternalFrame {
	
	// This IFrame will use default border layout with these panels inside 
	JPanel northPanel = new JPanel();
	JPanel westPanel = new JPanel();
	JPanel centerPanel = new JPanel(new GridLayout(11, 2));
	JPanel eastPanel = new JPanel();
	JPanel southPanel = new JPanel(new FlowLayout());
	
	// The list of customers
	JList lstCustomers = new JList();
	JScrollPane jspCustomers = new JScrollPane(lstCustomers);
	DefaultListModel dlmCustomers = new DefaultListModel();
	
	// The database connection and result set
	Connection conCustomers = new TXConnection().getInstance();
	ResultSet rtsCustomers;
	ResultSet rtsAgents;
	
	// Field objects
	private final int TEXTFIELD_WIDTH = 25;
	JTextField txtCustFirstName = new JTextField(TEXTFIELD_WIDTH);
	JTextField txtCustLastName = new JTextField(TEXTFIELD_WIDTH);
	JTextField txtCustAddress = new JTextField(TEXTFIELD_WIDTH);
	JTextField txtCustCity = new JTextField(TEXTFIELD_WIDTH);
	JTextField txtCustRegion = new JTextField(TEXTFIELD_WIDTH);
	JTextField txtCustPostal = new JTextField(TEXTFIELD_WIDTH);
	JTextField txtCustCountry = new JTextField(TEXTFIELD_WIDTH);
	JTextField txtCustHomePhone = new JTextField(TEXTFIELD_WIDTH);
	JTextField txtCustBusPhone = new JTextField(TEXTFIELD_WIDTH);
	JTextField txtCustEmail = new JTextField(TEXTFIELD_WIDTH);
	ComboBoxModel cbmAgents = new DefaultComboBoxModel();
	JComboBox cboAgents = new JComboBox(cbmAgents);
	
	// for testing
	// This hash table will contain the agent id as keys and agent names 
	Hashtable<Integer, String> hstAgents = new Hashtable<Integer, String>();
	Vector<Object> vAgents = new Vector<Object>();
	
	// Indicates whether save button will create new row or update existing row
	boolean isInsertMode; 
	
	// CRUD Buttons
	JButton btnNew = 	new JButton("New");
	JButton btnSave = 	new JButton("Save");
	JButton btnEdit = 	new JButton("Edit");
	JButton btnDelete = new JButton("Delete");
		
	private void toggleEditMode(boolean editModeEnabled) {
		if(editModeEnabled) {
			btnSave.setEnabled(true);
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(true);
			
			for(Component c : centerPanel.getComponents())
				if(c instanceof JTextField)
					((JTextField) c).setEnabled(true);
		}
		else {
			btnSave.setEnabled(false);
			btnEdit.setEnabled(true);
			btnDelete.setEnabled(false);
			for(Component c : centerPanel.getComponents())
				if(c instanceof JTextField)
					((JTextField) c).setEnabled(false);
		}
	}
	
	/**
	 * Initialize the resultset from database and link it to customer list
	 * Also populate agent combo box
	 */
	private void initList() {
		lstCustomers.setModel(dlmCustomers);
		// Select only 1 item at a time
		lstCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Retrieve an update enabled and traversible resultset of the customers table
		// MUST USE COLUMN NAMES FOR RESULTSET TO BE UPDATABLE!! (select * makes it readonly)
				
		try {
			rtsCustomers =  conCustomers.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
						.executeQuery("SELECT CustomerId, CustFirstName, CustLastName, CustAddress, CustCity, CustProv, CustCountry, CustPostal, CustHomePhone, CustBusPhone, CustEmail, AgentId FROM Customers");
			rtsAgents = conCustomers.createStatement().executeQuery("SELECT * FROM Agents");

			while(rtsAgents.next()) {
								
				System.out.println("Agentid:" + rtsAgents.getInt("AgentID"));
				System.out.println("Agentname:" + rtsAgents.getString("AgtFirstName"));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

				
		// Called when list item is selected
		// Load fields
		lstCustomers.addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					selectRowFromList();
					
					// Load the fields
					txtCustFirstName.setText(rtsCustomers.getString("CustFirstName"));
					txtCustLastName.setText(rtsCustomers.getString("CustLastName"));
					txtCustAddress.setText(rtsCustomers.getString("CustAddress"));
					txtCustCity.setText(rtsCustomers.getString("CustCity"));
					txtCustRegion.setText(rtsCustomers.getString("CustProv"));
					txtCustPostal.setText(rtsCustomers.getString("CustPostal"));
					txtCustCountry.setText(rtsCustomers.getString("CustCountry"));
					txtCustHomePhone.setText(rtsCustomers.getString("CustHomePhone"));
					txtCustBusPhone.setText(rtsCustomers.getString("CustBusPhone"));
					txtCustEmail.setText(rtsCustomers.getString("CustEmail"));

					toggleEditMode(false);
					btnNew.setEnabled(true);
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		// Make the scroll panel big enough 
		jspCustomers.setPreferredSize(new Dimension(500, 300));
		
		// Add a customer border to the scroll pane containing the list
		jspCustomers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "All Customers"));
	}
	
	private void clearTextFields() {
		// Clear all text fields using a loop
		for(Component c: centerPanel.getComponents()) {
			if(c instanceof JTextField) {
				((JTextField) c).setText("");
			}
		}
	}
	
	private void refreshList() {
		try {
			dlmCustomers.clear();
			// Move cursor to first row and loop through results
			rtsCustomers.first();
			do {
				String custDisplayString = 
					rtsCustomers.getString("CustLastName") + 
					", " +
					rtsCustomers.getString("CustFirstName");
				
				dlmCustomers.addElement(custDisplayString);
			}
			while(rtsCustomers.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Synchronizes the database row cursor with the select index from the Customers list 
	 * Must be called before any database stuff
	 */
	private void selectRowFromList() {
		// List index starts at 0, and result set row index starts at 1, so add 1
		try {
			if(lstCustomers.getSelectedIndex() >= 0)
				rtsCustomers.absolute(lstCustomers.getSelectedIndex() + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	private void initButtons() {
		// New button
		btnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Clear the list selection 
				lstCustomers.setSelectedIndex(-1);
				toggleEditMode(true);
				clearTextFields();
				isInsertMode = true;
				btnNew.setEnabled(false);
				btnDelete.setEnabled(false);
			}
		});
		// Save button
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Determine if INSERTING or UDPATING
					if(isInsertMode) {
						// Use the S_96_1_CUSTOMERS sequence in database to generate primary key
						ResultSet pkCustomerId = conCustomers.createStatement().
							executeQuery("SELECT S_96_1_CUSTOMERS.NextVal FROM DUAL");
						pkCustomerId.next();
						rtsCustomers.moveToInsertRow();
						rtsCustomers.updateInt("CustomerID", pkCustomerId.getInt("NextVal"));
					}
					else
						selectRowFromList();
					
					// Synchronize text fields to the list model
					rtsCustomers.updateString("CustFirstName", txtCustFirstName.getText());
					rtsCustomers.updateString("CustLastName", txtCustLastName.getText());
					rtsCustomers.updateString("CustAddress", txtCustAddress.getText());
					rtsCustomers.updateString("CustCity", txtCustCity.getText());
					rtsCustomers.updateString("CustProv", txtCustRegion.getText());
					rtsCustomers.updateString("CustPostal", txtCustPostal.getText());
					rtsCustomers.updateString("CustCountry", txtCustCountry.getText());
					rtsCustomers.updateString("CustEmail", txtCustEmail.getText());
					rtsCustomers.updateString("CustHomePhone", txtCustHomePhone.getText());
					rtsCustomers.updateString("CustBusPhone", txtCustBusPhone.getText());
					
					// TODO:fix this 
					//rtsCustomers.updateInt("AgentID", null);
					
					if(isInsertMode) 
						// Create and insert new row IF creating new customer
						rtsCustomers.insertRow();
					else
						// OR update existing row if editing existing customer
						rtsCustomers.updateRow();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				toggleEditMode(false);
				refreshList();
			}
		});
		// Edit button
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toggleEditMode(true);
				isInsertMode = false;
			}
		});
		// Delete button
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Select the result row equal to the list index
				try {
					selectRowFromList();
					rtsCustomers.deleteRow();
				}
				catch(SQLException ex) {
					ex.printStackTrace();
				}
				toggleEditMode(false);
				refreshList();
			}
		});
	}
	
	public CustomersFrame() {

		toggleEditMode(false);
		initButtons();
		
		initList();
		refreshList();
		
		// West panel contains customer list
		westPanel.add(jspCustomers);
		
		// Center panel will contain editing labels and components
		centerPanel.add(new JLabel("First Name: "));
		centerPanel.add(txtCustFirstName);
		centerPanel.add(new JLabel("Last Name: "));
		centerPanel.add(txtCustLastName);
		centerPanel.add(new JLabel("Address: "));
		centerPanel.add(txtCustAddress);
		centerPanel.add(new JLabel("City: "));
		centerPanel.add(txtCustCity);
		centerPanel.add(new JLabel("Region: "));
		centerPanel.add(txtCustRegion);
		centerPanel.add(new JLabel("Postal: "));
		centerPanel.add(txtCustPostal);
		centerPanel.add(new JLabel("Country: "));
		centerPanel.add(txtCustCountry);
		centerPanel.add(new JLabel("Home Phone: "));
		centerPanel.add(txtCustHomePhone);
		centerPanel.add(new JLabel("Bus. Phone: "));
		centerPanel.add(txtCustBusPhone);
		centerPanel.add(new JLabel("Email: "));
		centerPanel.add(txtCustEmail);
		centerPanel.add(new JLabel("Agent: "));
		centerPanel.add(cboAgents);
		
		// CRUD on south panel
		southPanel.add(btnNew);
		southPanel.add(btnEdit);
		southPanel.add(btnSave);
		southPanel.add(btnDelete);
		
		// Add Panels to main form
		add(northPanel, BorderLayout.NORTH);
		add(eastPanel, BorderLayout.EAST);
		add(centerPanel, BorderLayout.CENTER);
		add(westPanel, BorderLayout.WEST);
		add(southPanel, BorderLayout.SOUTH);
		
		setSize(600, 400);
		pack();
	}
}
