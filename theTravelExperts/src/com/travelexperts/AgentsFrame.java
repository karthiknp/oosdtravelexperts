package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.application.Application;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
@SuppressWarnings("serial")
public class AgentsFrame extends JInternalFrame {

	private Vector<String> vColumnNames = new Vector<String>();
	private JTable agentsTable;
	private JList lstCustomers = new JList();
   	private JScrollPane jspCustomers = new JScrollPane(lstCustomers);
    	
	private JPanel customerPanel = new JPanel(new BorderLayout());
    private Vector<Vector<String>> vvAgents = new Vector<Vector<String>>();
    private DefaultTableModel jTableModel;
    private JButton jButDelete;
    private JButton jButUpdate;
    private JButton jButSave;
    private JButton jButNew;
    private JPanel buttonPanel;
    private JTextField jTextAgtPosition;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JTextField jTextAgtEmail;
    private JTextField jTextAgtPhone;
    private JLabel jLabel8;
    private JLabel jLabel7;
    private JLabel jLabel6;
    private JTextField jTextAgtLName;
    private JTextField jTextAgtInitial;
    private JTextField jTextAgtFName;
    private JTextField jTextAgtID;
    private JLabel jLabel3;
    private JLabel jLabel2;
    private JLabel jLabel1;
    private JPanel fieldPanel;
    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JScrollPane jScrollPane_IL2;
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private Hashtable<Integer,Vector<Integer>> hCustID = new Hashtable<Integer,Vector<Integer>>();
    private Hashtable<Integer,Vector<String>> hCustName = new Hashtable<Integer,Vector<String>>();
    private boolean newButPressed;
    private boolean agentRowSelected;
    private int agentsTableRow;
    private Vector<Integer> CboItems = new Vector<Integer>();
    private JComboBox jCboAgencyID = new JComboBox();		// Link agent model to combobox

	public AgentsFrame() {
		super("Agent Management", true, true, true, true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		loadAgentCustData();
			
    	lstCustomers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Customers"));
    	//lstCustomers.setSize(300, 200);
    	lstCustomers.setSize(100, 200);
    	customerPanel.add(jspCustomers, BorderLayout.CENTER);
		
		add(customerPanel, BorderLayout.EAST);
		add(new JLabel("Viewing All Agents"), BorderLayout.NORTH);
		{
			agentsTable = new JTable(vvAgents, vColumnNames);
			agentsTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					System.out.println("agentsTable.mouseClicked, event="+evt);
					//TODO add your code for agentsTable.mouseClicked
					agentsTableRow = agentsTable.getSelectedRow();
					System.out.println("Row " + agentsTableRow + " is selected.");
					if (agentsTable.getSelectedRow() != -1) performRowSelectedAction();
				}
			});
		}
		jScrollPane_IL2 = new JScrollPane(agentsTable);
		jScrollPane_IL2.setPreferredSize(new java.awt.Dimension(532, 180));
		addMainPanel();
	}
	
	private void addMainPanel()
	{
		mainPanel.add(jScrollPane_IL2, BorderLayout.NORTH);
		{
			buttonPanel = new JPanel();
			mainPanel.add(buttonPanel, BorderLayout.SOUTH);
			buttonPanel.setBounds(104, 110, 10, 10);
			buttonPanel.setLayout(null);
			buttonPanel.setPreferredSize(new java.awt.Dimension(532, 49));
			{
				jButNew = new JButton("New");
				buttonPanel.add(jButNew);
				jButNew.setBounds(25, 12, 93, 24);
				jButNew.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("jButNew.mouseClicked, event="+evt);
						//TODO add your code for jButNew.mouseClicked
						clearRowSelection();
					}
				});
			}
			{
				jButSave = new JButton("Save");
				buttonPanel.add(jButSave);
				jButSave.setBounds(157, 12, 87, 24);
				jButSave.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("jButSave.mouseClicked, event="+evt);
						//TODO add your code for jButSave.mouseClicked
						// Note that Save is used exclusively to save a new record in the database
						System.out.println("jTextAgtID = " + jTextAgtID.getText());
						byte result = saveNewAgentRecord();
						if (result == 3) clearRowSelection();
					}
				});
			}
			{
				jButUpdate = new JButton("Update");
				buttonPanel.add(jButUpdate);
				jButUpdate.setBounds(285, 12, 86, 24);
				jButUpdate.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("jButUpdate.mouseClicked, event="+evt);
						byte result = updateAgentRecord();
						if (result == 3) clearRowSelection();
					}
				});
			}
			{
				jButDelete = new JButton("Delete");
				buttonPanel.add(jButDelete);
				jButDelete.setBounds(414, 12, 82, 24);
				jButDelete.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("jButDelete.mouseClicked, event="+evt);
						//TODO add your code for jButDelete.mouseClicked
						deleteAgentRecord();
					}
				});
			}
		}
		{
			fieldPanel = new JPanel();
			mainPanel.add(fieldPanel, BorderLayout.CENTER);
			fieldPanel.setLayout(null);
			fieldPanel.setPreferredSize(new java.awt.Dimension(532, 95));
			{
				jLabel1 = new JLabel("Agent ID:");
				fieldPanel.add(jLabel1);
				jLabel1.setBounds(29, 12, 68, 14);
			}
			{
				jLabel2 = new JLabel("First Name:");
				fieldPanel.add(jLabel2);
				jLabel2.setBounds(12, 32, 85, 14);
			}
			{
				jLabel3 = new JLabel("Initial:");
				fieldPanel.add(jLabel3);
				jLabel3.setBounds(48, 52, 49, 14);
			}
			{
				jLabel4 = new JLabel("Last Name:");
				fieldPanel.add(jLabel4);
				jLabel4.setBounds(12, 72, 85, 14);
			}
			{
				jTextAgtID = new JTextField();
				fieldPanel.add(jTextAgtID);
				jTextAgtID.setBounds(104, 10, 44, 18);
			}
			{
				jTextAgtFName = new JTextField();
				fieldPanel.add(jTextAgtFName);
				jTextAgtFName.setBounds(104, 30, 82, 18);
				jTextAgtFName.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("jTextAgtFName.mouseClicked, event="+evt);
						//
						System.out.println("Agent First Name text field clicked");
						// if New Button was pressed enable the Save button
						if (newButPressed) jButSave.setEnabled(true);
						// if agent row was selected enable the Update button
						if (agentRowSelected) jButUpdate.setEnabled(true);
						// disable the delete button
						jButDelete.setEnabled(false);
					}
				});
			}
			{
				jTextAgtInitial = new JTextField();
				fieldPanel.add(jTextAgtInitial);
				jTextAgtInitial.setBounds(104, 50, 44, 18);
				jTextAgtInitial.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("jTextAgtInitial.mouseClicked, event="+evt);
						//
						System.out.println("Agent Initial text field clicked");
						// if New Button was pressed enable the Save button
						if (newButPressed) jButSave.setEnabled(true);
						// if agent row was selected enable the Update button
						if (agentRowSelected) jButUpdate.setEnabled(true);
						// disable the delete button
						jButDelete.setEnabled(false);
					}
				});
			}
			{
				jTextAgtLName = new JTextField();
				fieldPanel.add(jTextAgtLName);
				jTextAgtLName.setBounds(104, 70, 82, 18);
				jTextAgtLName.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("jTextAgtLName.mouseClicked, event="+evt);
						//
						System.out.println("Agent Last Name text field clicked");
						// if New Button was pressed enable the Save button
						if (newButPressed) jButSave.setEnabled(true);
						// if agent row was selected enable the Update button
						if (agentRowSelected) jButUpdate.setEnabled(true);
						// disable the delete button
						jButDelete.setEnabled(false);
					}
				});
			}
			{
				jLabel5 = new JLabel("Phone:");
				fieldPanel.add(jLabel5);
				jLabel5.setBounds(235, 12, 52, 14);
			}
			{
				jLabel6 = new JLabel("Email:");
				fieldPanel.add(jLabel6);
				jLabel6.setBounds(243, 32, 44, 14);
			}
			{
				jLabel7 = new JLabel("Position:");
				fieldPanel.add(jLabel7);
				jLabel7.setBounds(222, 52, 65, 14);
			}
			{
				jLabel8 = new JLabel("Agency ID:");
				fieldPanel.add(jLabel8);
				jLabel8.setBounds(210, 72, 77, 14);
			}
			{
				jTextAgtPhone = new JTextField();
				fieldPanel.add(jTextAgtPhone);
				jTextAgtPhone.setBounds(293, 10, 129, 18);
				jTextAgtPhone.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("jTextAgtPhone.mouseClicked, event="+evt);
						
						System.out.println("Agent Phone text field clicked");
						// if New Button was pressed enable the Save button
						if (newButPressed) jButSave.setEnabled(true);
						// if agent row was selected enable the Update button
						if (agentRowSelected) jButUpdate.setEnabled(true);
						// disable the delete button
						jButDelete.setEnabled(false);
					}
				});
			}
			{
				jTextAgtEmail = new JTextField();
				fieldPanel.add(jTextAgtEmail);
				jTextAgtEmail.setBounds(293, 30, 227, 18);
				jTextAgtEmail.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("jTextAgtEmail.mouseClicked, event="+evt);
						//
						System.out.println("Agent Email text field clicked");
						// if New Button was pressed enable the Save button
						if (newButPressed) jButSave.setEnabled(true);
						// if agent row was selected enable the Update button
						// disable the delete button
						jButDelete.setEnabled(false);
						if (agentRowSelected) jButUpdate.setEnabled(true);
					}
				});
			}
			{
				jTextAgtPosition = new JTextField();
				fieldPanel.add(jTextAgtPosition);
				jTextAgtPosition.setBounds(293, 50, 129, 18);
				jTextAgtPosition.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("jTextAgtPosition.mouseClicked, event="+evt);
						System.out.println("Agent Position text field clicked");
						// if New Button was pressed enable the Save button
						if (newButPressed) jButSave.setEnabled(true);
						// if agent row was selected enable the Update button
						if (agentRowSelected) jButUpdate.setEnabled(true);
						// disable the delete button
						jButDelete.setEnabled(false);
					}
				});
			}
			{
				ComboBoxModel jCboAgencyIDModel = new DefaultComboBoxModel(CboItems);
				//jCboAgencyID = new JComboBox();
			    refreshAgencyID();
				fieldPanel.add(jCboAgencyID);
				jCboAgencyID.setModel(jCboAgencyIDModel);
				jCboAgencyID.setBounds(293, 72, 73, 22);
			    jCboAgencyID.setEditable(false);
			    jCboAgencyID.addMouseListener(new MouseAdapter() {
			    	public void mouseClicked(MouseEvent evt) {
			    		System.out.println("jCboAgencyID.mouseClicked, event="+evt);
			    		System.out.println("Agent Agency ID combobox field clicked.");
			    		// if New Button was pressed enable the Save button
			    		if (newButPressed) jButSave.setEnabled(true);
			    		// if agent row was selected enable the Update button
			    		if (agentRowSelected) jButUpdate.setEnabled(true);
			    		// disable the delete button
			    		jButDelete.setEnabled(false);
			    	}
			    });
			}
		}
		// Adding the main panel onto the main Agent Frame.
		add(mainPanel, BorderLayout.CENTER);
		// Setting the size of the main panel.
		//mainPanel.setPreferredSize(new java.awt.Dimension(532, 335));
		mainPanel.setPreferredSize(new java.awt.Dimension(800, 335));
		// disable the AgentID field
		jTextAgtID.setEnabled(false);
		// disable all the buttons
		newButPressed = true;
		agentRowSelected = false;
		jButNew.setEnabled(false);
		jButSave.setEnabled(false);
		jButUpdate.setEnabled(false);
		jButDelete.setEnabled(false);
		pack();
	}
	
	private void loadAgentCustData()
	{
		String colNames[] = {"Agent ID", "First Name", "Initial", "Last Name", "Phone", "Email", "Position", "Agency"};
		Integer agentID;
        try {
			//Class.forName(driver);
			// Connecting to the database...
	        //Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orant11g","ictoosd","ictoosd");
			Connection connection = TXConnection.getConnection();
	        Statement stmt = connection.createStatement();
	        // Retrieve agents data from the database
	        String sql = "SELECT * FROM Agents";
	        //System.out.println(sql);
	        ResultSet rs = stmt.executeQuery(sql);
	        ResultSetMetaData rsmd = rs.getMetaData();
	        // Transferring the column names into a vector.
			for (int i = 0; i < colNames.length; i++)
			{
				vColumnNames.add(colNames[i]);
			}
			// Extracting the Agents table values into a vector of vectors.
			while (rs.next())
			{
				Vector<String> vAgent = new Vector<String>();
				
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
				{
					if (i == 1 || i == 8)
						vAgent.add(Integer.toString(rs.getInt(i)));
					else
						vAgent.add(rs.getString(i));
				}
				//System.out.println(vAgent);
				vvAgents.add(vAgent);
			}
			// Retrieve customers data from the database
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql = "SELECT CustomerID, CustFirstName, CustLastName, AgentID FROM Customers";
	        //System.out.println(sql);
	        rs = stmt.executeQuery(sql);
	        rsmd = rs.getMetaData();
	        // Extracting the Customers table values into two Hashtables.
	        for (int i = 0; i < vvAgents.size(); i++)
	        {
	        	Vector<Integer> vCustID = new Vector<Integer>();
	        	Vector<String> vCustName = new Vector<String>();
	        	rs.beforeFirst();
	        	agentID = Integer.valueOf((String)vvAgents.get(i).get(0));
	        	while (rs.next())
	        	{
	        		if (agentID == rs.getInt(4))
	        		{
	        			vCustID.add(rs.getInt(1));
	        			vCustName.add(rs.getString(2) + " " + rs.getString(3));
	        		}
	        	}
	        	if (!vCustID.isEmpty())
	        	{
	        		hCustID.put(agentID, vCustID);
	        		hCustName.put(agentID, vCustName);
	        	}
	        }
			rs.close();
    		//System.out.println(hCustID);
    		//System.out.println(hCustName);
			
	    } catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
    public void refreshAgencyID()
    {
        // Build combo box containing active agents
        try {
			//Class.forName(driver);
			// Connecting to the database...
	        //Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orant11g","ictoosd","ictoosd");
			Connection connection = TXConnection.getConnection();
	        Statement stmt = connection.createStatement();
	        // Retrieve agents data from the database
	        String sql = "SELECT UNIQUE AgencyID FROM Agencies";
	        ResultSet rs = stmt.executeQuery(sql);
            jCboAgencyID.removeAllItems();
            while(rs.next())
            {
            	jCboAgencyID.addItem(rs.getInt("AgencyId"));
            	CboItems.add(rs.getInt("AgencyId"));
            }
            System.out.println("Combo box items: " + CboItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	private void clearRowSelection()
	{
		// clear table row selection
		agentsTable.clearSelection();
		Vector<String> vCustList = new Vector<String>();
		reDisplayCustomerList(vCustList);
		// clear the text fields
		jTextAgtID.setText(null);
		jTextAgtFName.setText(null);
		jTextAgtInitial.setText(null);
		jTextAgtLName.setText(null);
		jTextAgtPhone.setText(null);
		jTextAgtEmail.setText(null);
		jTextAgtPosition.setText(null);
		jCboAgencyID.setSelectedIndex(-1);
		newButPressed = true;
		agentRowSelected = false;
		agentsTableRow = -1;
		// disable all the buttons
		jButNew.setEnabled(false);
		jButSave.setEnabled(false);
		jButUpdate.setEnabled(false);
		jButDelete.setEnabled(false);
	}
	
	
	private void performRowSelectedAction()
	{
		Integer agentID;
		//Vector<Integer> vCustID;
		Vector<String> vCustName = new Vector<String>();
		Vector<String> rowAgent = vvAgents.elementAt(agentsTableRow);
		// populate text fields
		jTextAgtID.setText(rowAgent.elementAt(0));
		jTextAgtFName.setText(rowAgent.elementAt(1));
		jTextAgtInitial.setText(rowAgent.elementAt(2));
		jTextAgtLName.setText(rowAgent.elementAt(3));
		jTextAgtPhone.setText(rowAgent.elementAt(4));
		jTextAgtEmail.setText(rowAgent.elementAt(5));
		jTextAgtPosition.setText(rowAgent.elementAt(6));
		System.out.println(rowAgent);
		System.out.println("Agency ID = " + Integer.valueOf(rowAgent.elementAt(7)));
		jCboAgencyID.setSelectedItem(Integer.valueOf(rowAgent.elementAt(7)));
		
		//load agent's customers
		agentID = Integer.valueOf(rowAgent.get(0));
		if (hCustName.containsKey(agentID))
		{
			vCustName = hCustName.get(agentID);
		}
		reDisplayCustomerList(vCustName);
		
		newButPressed = false;
		agentRowSelected = true;
		// enable the New button
		System.out.println("New button enabled.");
		jButNew.setEnabled(true);
		// enable the Delete button if there are no customers linked to this agent and disable it otherwise
		if (!hCustName.containsKey(agentID))
		{
			System.out.println("No customers, delete button enabled.");
			jButDelete.setEnabled(true);
		}
		else
		{
			System.out.println("Customers, delete button disabled.");
			jButDelete.setEnabled(false);
		}
		// disable the Save and Update buttons
		jButSave.setEnabled(false);
		jButUpdate.setEnabled(false);
		// disable the AgentID text field
		jTextAgtID.setEnabled(false);
	}
	
	private void reDisplayCustomerList(Vector<String> vCName)
	{
		remove(jspCustomers);
		lstCustomers = new JList(vCName);
		lstCustomers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Customers"));
    	//lstCustomers.setSize(300, 200);
    	lstCustomers.setSize(100, 200);
		jspCustomers = new JScrollPane(lstCustomers);
		jspCustomers.setPreferredSize(new Dimension(150, 200));
    	customerPanel.add(jspCustomers, BorderLayout.CENTER);
		add(jspCustomers, BorderLayout.EAST);
		pack();
		add(customerPanel, BorderLayout.EAST);
	}
	
	private Integer getNextAgentNum()
	{
		Integer maxAgentNum = 0;
		for (int i = 1; i < vvAgents.size(); i++)
		{
				maxAgentNum = (maxAgentNum.compareTo(Integer.valueOf(vvAgents.get(i).get(0))) < 0) ? Integer.valueOf(vvAgents.get(i).get(0)) : maxAgentNum;
		}
		return ++maxAgentNum;
	}
	
	private byte validateFields()
	{
		boolean validate = true;
		String message = "";
		Pattern fNamePattern = Pattern.compile("^[A-Za-z][\\.]?[A-Za-z]?[\\.]?[A-Za-z]*$");
		Pattern initialPattern = Pattern.compile("^([A-Z][\\.])?$");
		Pattern lNamePattern = Pattern.compile("^[A-Za-z]+$");
		Pattern phonePattern = Pattern.compile("^[\\(][\\d]{3}[\\)] [\\d]{3}-[\\d]{4}$");
		Pattern emailPattern = Pattern.compile("^[\\w]+[[\\w]-[\\.]]*[\\@][\\w]+((-[\\w]+)|([\\w]*))[\\.][a-z]{2,3}$");
		Matcher fNameMatcher = fNamePattern.matcher(jTextAgtFName.getText());
		Matcher initialMatcher = initialPattern.matcher(jTextAgtInitial.getText());
		Matcher lNameMatcher = lNamePattern.matcher(jTextAgtLName.getText());
		Matcher phoneMatcher = phonePattern.matcher(jTextAgtPhone.getText());
		Matcher emailMatcher = emailPattern.matcher(jTextAgtEmail.getText());
						
		if (!fNameMatcher.matches())
		{
			validate = false;
			message = message + "Please enter the agent's first name.";
		}
		if (!initialMatcher.matches())
		{
			validate = false;
			message = message + "\nPlease enter or update the agent's initial. (Format: A. or <blank>)";
		}
		if (!lNameMatcher.matches())
		{
			validate = false;
			message = message + "\nPlease enter or update the agent's last name.";
		}
		if (!phoneMatcher.matches())
		{
			validate = false;
			message = message + "\nPlease enter or update the agent's phone number. (Format: (999) 999-9999)";
		}
		if (!emailMatcher.matches())
		{
			validate = false;
			message = message + "\nPlease enter or update the agent's email address. (Format: fname.lname@travelexperts.com)";
		}
		if ((!jTextAgtPosition.getText().equals("Senior Agent")) && (!jTextAgtPosition.getText().equals("Intermediate Agent")) && (!jTextAgtPosition.getText().equals( "Junior Agent")) && (!jTextAgtPosition.getText().equals("Inactive")))
		{
			System.out.println("Position: " + jTextAgtPosition.getText());
			validate = false;
			message = message + "\nPlease enter or update the agent's position. (Format: 'Senior Agent', 'Intermediate Agent', 'Junior Agent' or 'Inactive'";
		}
		if (!CboItems.contains((Integer)jCboAgencyID.getSelectedItem()))
		{
			validate = false;
			message = message + "\nPlease select the agency ID.";
		}
		if (!validate)
		{
			message = message + "\n\nSelect OK to correct the faulted fields or CANCEL to cancel the data entry.";
			int selection = JOptionPane.showConfirmDialog(null, message, "Agent Validation Error", JOptionPane.OK_CANCEL_OPTION);
			if (selection == JOptionPane.CANCEL_OPTION)
			{
				return 3;
			}
			else return 2;
		}
		return 1;
	}
	
	private byte saveNewAgentRecord()
	{
		byte choice = validateFields();
		if (choice != 1) return choice;
		try {
			//Class.forName(driver);
	        //Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orant11g","ictoosd","ictoosd");
			Connection connection = TXConnection.getConnection();
			
	        Integer NextAgentNo = getNextAgentNum();
	        String sql = "INSERT INTO Agents VALUES ('" + NextAgentNo + "','" + jTextAgtFName.getText() + "','" 
	        	+ jTextAgtInitial.getText() + "','" + jTextAgtLName.getText() + "','"
	        	+ jTextAgtPhone.getText() + "','" + jTextAgtEmail.getText() + "','"
	        	+ jTextAgtPosition.getText() + "','" + jCboAgencyID.getSelectedItem() + "')";
	        System.out.println(sql);
	        Statement stmt = connection.createStatement();
	        int numRows = stmt.executeUpdate(sql);
	        System.out.println("New record saved: " + numRows + " record(s) added.");
	        if (numRows == 1)		// successfully saved the new record
	        {
	        	// add the new record into the vvAgents vector of vectors
	        	Vector<String> vAgent = new Vector<String>();
	        	vAgent.add(NextAgentNo.toString());
	        	vAgent.add(jTextAgtFName.getText());
	        	vAgent.add(jTextAgtInitial.getText());
	        	vAgent.add(jTextAgtLName.getText());
	        	vAgent.add(jTextAgtPhone.getText());
	        	vAgent.add(jTextAgtEmail.getText());
	        	vAgent.add(jTextAgtPosition.getText());
	        	vAgent.add(""+jCboAgencyID.getSelectedItem());
	        	vvAgents.add(vAgent);
	        	// display the updated agents table
	        	jButSave.setEnabled(false);
	        	validate();
	        	pack();
	        	return 1;
	        }
	        else			// error in saving the new record
	        {
				Object options[] = {"OK", "Cancel"};
				JOptionPane.showOptionDialog(null, "Error in saving a new Agent record. Report the incident to the Help Desk.", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				return 3;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			return 3;
		}
	}
	
	private byte updateAgentRecord()
	{
		byte choice = validateFields();
		if (choice != 1) return choice;
		try {
			//Class.forName(driver);
	        //Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orant11g","ictoosd","ictoosd");
			Connection connection = TXConnection.getConnection();

			String sql = "UPDATE Agents SET AgtFirstName='" + jTextAgtFName.getText() + "',AgtMiddleInitial='" +
	        	jTextAgtInitial.getText() + "',AgtLastName='" + jTextAgtLName.getText() + "',AgtBusPhone='" +
	        	jTextAgtPhone.getText() + "',AgtEmail='" + jTextAgtEmail.getText() + "',AgtPosition='" +
	        	jTextAgtPosition.getText() + "',AgencyID='" + jCboAgencyID.getSelectedItem() + "' WHERE AgentID='" +
	        	jTextAgtID.getText() + "'";
	        System.out.println(sql);
	        Statement stmt = connection.createStatement();
	        int numRows = stmt.executeUpdate(sql);
	        System.out.println("Agent record updated: " + numRows + " record(s) updated.");
	        if (numRows == 1)		// successfully updated the agent record
	        {
	        	// update the record in the vvAgents vector of vectors
	        	Vector<String> vAgent = vvAgents.get(agentsTableRow);
	        	vAgent.set(1, (vAgent.get(1) != jTextAgtFName.getText() ? jTextAgtFName.getText() : vAgent.get(1)));
	        	vAgent.set(2, (vAgent.get(2) != jTextAgtInitial.getText() ? jTextAgtInitial.getText() : vAgent.get(2)));
	        	vAgent.set(3, (vAgent.get(3) != jTextAgtLName.getText() ? jTextAgtLName.getText() : vAgent.get(3)));
	        	vAgent.set(4, (vAgent.get(4) != jTextAgtPhone.getText() ? jTextAgtPhone.getText() : vAgent.get(4)));
	        	vAgent.set(5, (vAgent.get(5) != jTextAgtEmail.getText() ? jTextAgtEmail.getText() : vAgent.get(5)));
	        	vAgent.set(6, (vAgent.get(6) != jTextAgtPosition.getText() ? jTextAgtPosition.getText() : vAgent.get(6)));
	        	vAgent.set(7, (vAgent.get(7) != Integer.toString((Integer)jCboAgencyID.getSelectedItem()) ? Integer.toString((Integer)jCboAgencyID.getSelectedItem()) : vAgent.get(7)));
	        	vvAgents.set(agentsTableRow, vAgent);
	        	// display the updated agents table
	        	validate();
	        	pack();
	        	clearRowSelection();
	        	return choice;
	        }
	        else			// error in saving the new record
	        {
				Object options[] = {"OK", "Cancel"};
				JOptionPane.showOptionDialog(null, "Error in updating a new Agent record. Report the incident to the Help Desk.", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				return 3;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			return 3;
		}
	}

	private void deleteAgentRecord()
	{
		int choice = JOptionPane.showConfirmDialog(null,"Do you really want to delete this agent record?",
				"Agent Deletion Confirmation", JOptionPane.YES_NO_OPTION);
	    if (choice == JOptionPane.NO_OPTION)
	    {
	    	clearRowSelection();
	    }
	    else
	    {
			try {
				//Class.forName(driver);
		        //Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orant11g","ictoosd","ictoosd");
				Connection connection = TXConnection.getConnection();
		        String sql = "DELETE Agents WHERE AgentID='" + jTextAgtID.getText() + "'";
		        System.out.println(sql);
		        Statement stmt = connection.createStatement();
		        int numRows = stmt.executeUpdate(sql);
		        System.out.println("Agent record deleted: " + numRows + " record(s) deleted.");
		        if (numRows == 1)		// successfully updated the agent record
		        {
		        	// delete the record in the vvAgents vector of vectors
		        	vvAgents.remove(agentsTableRow);
		        	// display the updated agents table
		        	validate();
		        	pack();
		        	clearRowSelection();
		        }
		        else			// error in saving the new record
		        {
					Object options[] = {"OK", "Cancel"};
					JOptionPane.showOptionDialog(null, "Error in updating a new Agent record. Report the incident to the Help Desk.", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		        }
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	}

}
