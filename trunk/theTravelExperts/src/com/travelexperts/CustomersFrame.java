package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * 
 * @author will_ad
 *
 */
@SuppressWarnings("serial")
public class CustomersFrame extends JInternalFrame {
	
	private final static String CUSTOMER_QUERY = 
		"SELECT CustomerID, CustFirstName, CustLastName, CustAddress, CustCity, CustPostal, CustCountry, CustHomePhone, CustBusPhone, CustEmail, AgentId FROM Customers";
	
	private Connection dbConnection = new TXConnection().getInstance();
	
	CustomersTableModel ctmCustomers;
	ResultSet rssCustomers;
	JTable tblCustomers;

	JComboBox cboAgents = new JComboBox();
	
	
	public CustomersFrame() {
		super("Customers", true, true, true, true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		try {
			rssCustomers = dbConnection
			.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)
			.executeQuery(CUSTOMER_QUERY);
			//rssCustomers.getStatement().close();
			
			// Create the agent combo box
			// Use JDBC interpreted SQL
			ResultSet rs = dbConnection.createStatement()
			.executeQuery("SELECT AgentId, {fn concat(AgtFirstName, AgtLastName)} AS AgentName FROM Agents");
			while(rs.next()) {
			}
			rs.getStatement().close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		// 
		ctmCustomers = new CustomersTableModel(rssCustomers);
		tblCustomers = new JTable(ctmCustomers);
		
		String lblUsage = "<html><strong>Insert</strong> - Create new customer<br/>" +
				"<strong>Delete<strong> - Delete customer<br/>" +
				"<strong>Edit</strog> Click on field to edit</html>";
			
		add(cboAgents, BorderLayout.NORTH);
		add(new JScrollPane(tblCustomers), BorderLayout.CENTER);
		add(new JLabel(lblUsage), BorderLayout.SOUTH);
		
		tblCustomers.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_DELETE)
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this customer?") == JOptionPane.OK_OPTION) {
					try {
						int row = tblCustomers.getSelectedRow() + 1;
						rssCustomers.absolute(row);
						rssCustomers.deleteRow();
						ctmCustomers.fireTableDataChanged();					
												
					} catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
				else if(e.getKeyChar() == KeyEvent.VK_INSERT);
			}
		});
		
		pack();
	}

}
