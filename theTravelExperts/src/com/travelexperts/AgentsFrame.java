package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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

	Vector<String> vColumnNames = new Vector<String>();
	JTable agentsTable;
	JList lstCustomers = new JList();
   	JScrollPane jspCustomers = new JScrollPane(lstCustomers);
    	
	//= new JList(new Object[] { "Bob Smith", "Fred Anchorman", "Mary-Jane Watson", "George Stroumbolopolis" } );
	Agents agent;
	JPanel customerPanel = new JPanel(new BorderLayout());
    Vector<Vector> vvAgents = new Vector<Vector>();
    DefaultTableModel jTableModel;
    private JButton jButton4;
    private JButton jButton3;
    private JButton jButton2;
    private JButton jButton1;
    private JPanel buttonPanel;
    private JTextField jTextField8;
    private JTextField jTextField7;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JTextField jTextField6;
    private JTextField jTextField5;
    private JLabel jLabel8;
    private JLabel jLabel7;
    private JLabel jLabel6;
    private JTextField jTextField4;
    private JTextField jTextField3;
    private JTextField jTextField2;
    private JTextField jTextField1;
    private JLabel jLabel3;
    private JLabel jLabel2;
    private JLabel jLabel1;
    private JPanel fieldPanel;
    //private JPanel mainPanel = new JPanel(new FlowLayout());
    private JPanel mainPanel = new JPanel(new BorderLayout());
    private JScrollPane jScrollPane_IL2;
    private String driver = "oracle.jdbc.driver.OracleDriver";

	public AgentsFrame() {
		super("Agent Management", true, true, true, true);
		
		//add(new JScrollPane(agentsTable));
		
		//JCheckBox active = new JCheckBox();
		
		//agentsTable.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(active));
		
		loadAgentTable();
			
    	lstCustomers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Customers"));
    	lstCustomers.setSize(300, 200);
    	customerPanel.add(jspCustomers, BorderLayout.CENTER);
    	customerPanel.add(new JButton("Unassign"), BorderLayout.SOUTH);
		
		add(customerPanel, BorderLayout.EAST);
		add(new JLabel("Viewing All Agents"), BorderLayout.NORTH);
		
		//agentsTable.setPreferredSize(new java.awt.Dimension(532, 254));
		jScrollPane_IL2 = new JScrollPane(agentsTable);
		jScrollPane_IL2.setPreferredSize(new java.awt.Dimension(532, 180));
		//jScrollPane_IL2.setPreferredSize(new java.awt.Dimension(532, 254));
		mainPanel.add(jScrollPane_IL2, BorderLayout.NORTH);
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
				jTextField1 = new JTextField();
				fieldPanel.add(jTextField1);
				jTextField1.setBounds(104, 10, 67, 18);
			}
			{
				jTextField2 = new JTextField();
				fieldPanel.add(jTextField2);
				jTextField2.setBounds(104, 30, 113, 18);
			}
			{
				jTextField3 = new JTextField();
				fieldPanel.add(jTextField3);
				jTextField3.setBounds(104, 50, 44, 18);
			}
			{
				jTextField4 = new JTextField();
				fieldPanel.add(jTextField4);
				jTextField4.setBounds(104, 70, 113, 18);
			}
			{
				jLabel5 = new JLabel("Phone:");
				fieldPanel.add(jLabel5);
				jLabel5.setBounds(260, 12, 52, 14);
			}
			{
				jLabel6 = new JLabel("Email:");
				fieldPanel.add(jLabel6);
				jLabel6.setBounds(268, 32, 44, 14);
			}
			{
				jLabel7 = new JLabel("Position:");
				fieldPanel.add(jLabel7);
				jLabel7.setBounds(247, 52, 65, 14);
			}
			{
				jLabel8 = new JLabel("Agency ID:");
				fieldPanel.add(jLabel8);
				jLabel8.setBounds(235, 72, 77, 14);
			}
			{
				jTextField5 = new JTextField();
				fieldPanel.add(jTextField5);
				jTextField5.setBounds(318, 10, 129, 18);
			}
			{
				jTextField6 = new JTextField();
				fieldPanel.add(jTextField6);
				jTextField6.setBounds(318, 30, 190, 18);
			}
			{
				jTextField7 = new JTextField();
				fieldPanel.add(jTextField7);
				jTextField7.setBounds(318, 50, 190, 18);
			}
			{
				jTextField8 = new JTextField();
				fieldPanel.add(jTextField8);
				jTextField8.setBounds(318, 70, 68, 18);
			}
		}
		{
			buttonPanel = new JPanel();
			mainPanel.add(buttonPanel, BorderLayout.SOUTH);
			buttonPanel.setBounds(104, 110, 10, 10);
			buttonPanel.setLayout(null);
			buttonPanel.setPreferredSize(new java.awt.Dimension(532, 49));
			{
				jButton1 = new JButton("New");
				buttonPanel.add(jButton1);
				jButton1.setBounds(25, 12, 93, 24);
				jButton1.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("jButton1.mouseClicked, event="+evt);
						//TODO add your code for jButton1.mouseClicked
						// clear the text fields
						jTextField1.setText(null);
						jTextField2.setText(null);
						jTextField3.setText(null);
						jTextField4.setText(null);
						jTextField5.setText(null);
						jTextField6.setText(null);
						jTextField7.setText(null);
						jTextField8.setText(null);
					}
				});
			}
			{
				jButton2 = new JButton("Save");
				buttonPanel.add(jButton2);
				jButton2.setBounds(157, 12, 87, 24);
			}
			{
				jButton3 = new JButton("Update");
				buttonPanel.add(jButton3);
				jButton3.setBounds(285, 12, 86, 24);
			}
			{
				jButton4 = new JButton("Delete");
				buttonPanel.add(jButton4);
				jButton4.setBounds(414, 12, 82, 24);
			}
		}
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setPreferredSize(new java.awt.Dimension(532, 335));
		// disable the Save, Update and Delete buttons
		jButton2.setEnabled(false);
		jButton3.setEnabled(false);
		jButton4.setEnabled(false);
		pack();
	}
	
	private void loadVectorAgent(Agents agt, Vector<String> vAgt)
	{
		vAgt.add(Integer.toString(agt.getAgentID()));
		vAgt.add(agt.getAgtFirstName());
		vAgt.add(agt.getAgtMiddleInitial());
		vAgt.add(agt.getAgtLastName());
		vAgt.add(agt.getAgtBusPhone());
		vAgt.add(agt.getAgtEmail());
		vAgt.add(agt.getAgtPosition());
		vAgt.add(Integer.toString(agt.getAgencyID()));
	}
	
	private void extractAgent(Agents agt, Vector<String> vAgt)
	{
		agt.setAgentID(Integer.parseInt(vAgt.elementAt(0)));
		agt.setAgtFirstName(vAgt.elementAt(1));
		agt.setAgtMiddleInitial(vAgt.elementAt(2));
		agt.setAgtLastName(vAgt.elementAt(3));
		agt.setAgtBusPhone(vAgt.elementAt(4));
		agt.setAgtEmail(vAgt.elementAt(5));
		agt.setAgtPosition(vAgt.elementAt(6));
		agt.setAgencyID(Integer.parseInt(vAgt.elementAt(7)));
	}
		
	private void loadAgentTable()
	{
		String colNames[] = {"Agent ID", "First Name", "Initial", "Last Name", "Phone", "Email", "Position", "Agency"};
        try {
        	// Changed to use TXConnection - Will
			//Class.forName(driver);
	        Connection connection = TXConnection.getConnection();
	        Statement stmt = connection.createStatement();
	        String sql = "SELECT * FROM Agents";
	        ResultSet rs = stmt.executeQuery(sql);
	        ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 0; i < colNames.length; i++)
			{
				vColumnNames.add(colNames[i]);
			}
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
				vvAgents.add(vAgent);
				//System.out.println(vAgent.toString());
			}
			rs.close();
			//jTableModel = new DefaultTableModel(vData, vColumnNames);
			
			agentsTable = new JTable(vvAgents, vColumnNames);

			agentsTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					System.out.println("agentsTable.mouseClicked, event="+evt);
					//TODO add your code for agentsTable.mouseClicked
					System.out.println("Row " + agentsTable.getSelectedRow() + " is selected.");
					if (agentsTable.getSelectedRow() != -1) performRowSelectedAction(agentsTable.getSelectedRow());
				}
			});
			//add(new JScrollPane(agentsTable));
			//agentsTable.setModel(jTableModel);
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	private void performRowSelectedAction(int row)
	{
		Vector<String> rowAgent = vvAgents.elementAt(row);
		// populate text fields
		jTextField1.setText(rowAgent.elementAt(0));
		jTextField2.setText(rowAgent.elementAt(1));
		jTextField3.setText(rowAgent.elementAt(2));
		jTextField4.setText(rowAgent.elementAt(3));
		jTextField5.setText(rowAgent.elementAt(4));
		jTextField6.setText(rowAgent.elementAt(5));
		jTextField7.setText(rowAgent.elementAt(6));
		jTextField8.setText(rowAgent.elementAt(7));
		
		//load agent's customers
		try {
			Class.forName(driver);
	        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orant11g","ictoosd","ictoosd");
	        Statement stmt = connection.createStatement();
	        String sql = "SELECT CustomerID, CustFirstName, CustLastName FROM Customers WHERE AgentID = " + jTextField1.getText();
	        ResultSet rs = stmt.executeQuery(sql);
	        Vector<Integer> vCustID = new Vector<Integer>();
	        Vector<String> vCustName = new Vector<String>();
			while (rs.next())
			{
				vCustID.add(rs.getInt(1));
				vCustName.add(rs.getString(2) + " " + rs.getString(3));
			}
			rs.close();
			remove(jspCustomers);
			lstCustomers = new JList(vCustName);
			lstCustomers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Customers"));
	    	lstCustomers.setSize(300, 200);
			jspCustomers = new JScrollPane(lstCustomers);
	    	customerPanel.add(jspCustomers, BorderLayout.CENTER);
			add(jspCustomers, BorderLayout.EAST);
			pack();
			add(customerPanel, BorderLayout.EAST);
			//pack();
			
/*	    	lstCustomers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Customers"));
	    	lstCustomers.setSize(300, 200);
	    	
	    	customerPanel.add(new JScrollPane(lstCustomers), BorderLayout.CENTER);
	    	customerPanel.add(new JButton("Unassign"), BorderLayout.SOUTH);
			
			add(customerPanel, BorderLayout.EAST);*/
			//lstCustomers.setVisible(true);
			//lstCustomers.repaint();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
