package com.travelexperts;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
    private JTextField jTextAgcyID;
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

	public AgentsFrame() {
		super("Agent Management", true, true, true, true);
		
		loadAgentCustData();
			
    	lstCustomers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Customers"));
    	lstCustomers.setSize(300, 200);
    	customerPanel.add(jspCustomers, BorderLayout.CENTER);
    	customerPanel.add(new JButton("Unassign"), BorderLayout.SOUTH);
		
		add(customerPanel, BorderLayout.EAST);
		add(new JLabel("Viewing All Agents"), BorderLayout.NORTH);
		{
			agentsTable = new JTable(vvAgents, vColumnNames);
			agentsTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					System.out.println("agentsTable.mouseClicked, event="+evt);
					//TODO add your code for agentsTable.mouseClicked
					System.out.println("Row " + agentsTable.getSelectedRow() + " is selected.");
					if (agentsTable.getSelectedRow() != -1) performRowSelectedAction(agentsTable.getSelectedRow());
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
						jTextAgcyID.setText(null);
						newButPressed = true;
						agentRowSelected = false;
						// disable the Save, Update and Delete buttons
						jButSave.setEnabled(false);
						jButUpdate.setEnabled(false);
						jButDelete.setEnabled(false);
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
						saveNewAgentRecord();
					}
				});
			}
			{
				jButUpdate = new JButton("Update");
				buttonPanel.add(jButUpdate);
				jButUpdate.setBounds(285, 12, 86, 24);
			}
			{
				jButDelete = new JButton("Delete");
				buttonPanel.add(jButDelete);
				jButDelete.setBounds(414, 12, 82, 24);
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
						//TODO add your code for jTextAgtFName.mouseClicked
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
						//TODO add your code for jTextAgtInitial.mouseClicked
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
						//TODO add your code for jTextAgtLName.mouseClicked
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
						//TODO add your code for jTextAgtPhone.mouseClicked
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
						//TODO add your code for jTextAgtEmail.mouseClicked
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
						//TODO add your code for jTextAgtPosition.mouseClicked
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
				jTextAgcyID = new JTextField();
				fieldPanel.add(jTextAgcyID);
				jTextAgcyID.setBounds(293, 70, 52, 18);
				jTextAgcyID.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("jTextAgcyID.mouseClicked, event="+evt);
						//TODO add your code for jTextAgcyID.mouseClicked
						System.out.println("Agent Agency ID text field clicked");
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
		mainPanel.setPreferredSize(new java.awt.Dimension(532, 335));
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
			Class.forName(driver);
			// Connecting to the database...
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

	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void performRowSelectedAction(int row)
	{
		Integer agentID;
		//Vector<Integer> vCustID;
		Vector<String> vCustName = new Vector<String>();
		Vector<String> rowAgent = vvAgents.elementAt(row);
		// populate text fields
		jTextAgtID.setText(rowAgent.elementAt(0));
		jTextAgtFName.setText(rowAgent.elementAt(1));
		jTextAgtInitial.setText(rowAgent.elementAt(2));
		jTextAgtLName.setText(rowAgent.elementAt(3));
		jTextAgtPhone.setText(rowAgent.elementAt(4));
		jTextAgtEmail.setText(rowAgent.elementAt(5));
		jTextAgtPosition.setText(rowAgent.elementAt(6));
		jTextAgcyID.setText(rowAgent.elementAt(7));
		
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
    	lstCustomers.setSize(300, 200);
		jspCustomers = new JScrollPane(lstCustomers);
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
	
	private void saveNewAgentRecord()
	{
		if (jTextAgtID.getText().isEmpty())		// this is a new record
		{
			try {
				Class.forName(driver);
		        Connection connection = TXConnection.getConnection();
		        Integer NextAgentNo = getNextAgentNum();
		        String sql = "INSERT INTO Agents VALUES ('" + NextAgentNo + "','" + jTextAgtFName.getText() + "','" 
		        	+ jTextAgtInitial.getText() + "','" + jTextAgtLName.getText() + "','"
		        	+ jTextAgtPhone.getText() + "','" + jTextAgtEmail.getText() + "','"
		        	+ jTextAgtPosition.getText() + "','" + jTextAgcyID.getText() + "')";
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
		        	vAgent.add(jTextAgcyID.getText());
		        	vvAgents.add(vAgent);
		        	// display the updated agents table
		        	jButSave.setEnabled(false);
		        	remove(mainPanel);
		        	addMainPanel();
		        }
		        else			// error in saving the new record
		        {
					Object options[] = {"OK", "Cancel"};
					JOptionPane.showOptionDialog(null, "Error in saving a new Agent record. Report the incident to the Help Desk.", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		        }
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else	// this is a pre-existed record
		{
			Object options[] = {"OK", "Cancel"};
			JOptionPane.showOptionDialog(null, "The Save button is used to save a new record. Use the Update button to update an existing record.", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		}
	}
	
	private void initGUI() {
		try {
			Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
