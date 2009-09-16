package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

/*
 * Frame to view customers who do not have an active Travel Agent
 * 
 * Will Dixon
 */
@SuppressWarnings("serial")
public class UACustomersFrame extends JInternalFrame {
	
	private static final String QUERY_INACTIVE_CUSTOMERS = 
	"SELECT CustomerID, CustFirstName, CustLastName, AgentID FROM Customers WHERE AgentID IS NULL OR AgentID IN (SELECT AgentID FROM Agents WHERE AgtPosition='Inactive')";

	private static final String QUERY_ACTIVE_AGENTS =
	"SELECT AgentId FROM Agents WHERE AgtPosition <> 'Inactive'";
	
	private static final String QUERY_AGENT_NAMES =
	"SELECT AgentId, AgtLastName, AgtFirstName FROM Agents";

	private static final int COL_INDEX_LASTNAME = 2;		// Column index for CustLastName in JTable
	private static final int COL_INDEX_AGENTID = 3;		// Column index for AgentID in JTable
	
	public static Vector<String> vAgentNames = new Vector<String>(300);	// Contains agent names
	
	ResultSet rsCustomers;
	ResultSet rsActiveAgents;
	
	CustomersTableModel ctmCustomers;
	JTable tblCustomers;
	JScrollPane spCustomers;

	DefaultComboBoxModel cbmAgents;
	JComboBox cboAgents;			// Combo box to select agent id

	JPanel pnlNorth = new JPanel();
	JPanel pnlCenter = new JPanel();
	JPanel pnlSouth = new JPanel();
	
	JTextField txtFilter = new JTextField(20);	
	
	public UACustomersFrame() {
		super("Viewing Customers with No Travel Agents", true, true, true, true);
		
		// Agent combo box init
		initAgentVector();
		cboAgents = new JComboBox();					// Link agent model to combobox
		cboAgents.setEditable(false);
		refreshAgents();
		
		// Customer table init
		refreshCustomers();									
		ctmCustomers = new CustomersTableModel(rsCustomers) {
			// Override column names 
			@Override public String getColumnName(int colIndex) {
				try {	
					return this.getCustomers().getMetaData().getColumnName(colIndex+1);
				} catch (SQLException e) {
					e.printStackTrace();
					return "Undefined Column Name";
				}
			}
		};														// Link model to resultset
		
		tblCustomers = new JTable(ctmCustomers);				// Link table to model
		tblCustomers.getColumnModel().getColumn(COL_INDEX_AGENTID)
			.setCellEditor(new DefaultCellEditor(cboAgents));	// Combo box for agents
		
		tblCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	// Select only one 
		tblCustomers.setFillsViewportHeight(true);				// Fill scrollpane
		
		spCustomers = new JScrollPane(tblCustomers);			// Put table in scrollpane
		
		initFilter();	// Initialize search handle 
		
		pnlCenter.add(spCustomers);				// Add components to panels
		
		pnlSouth.add(new JLabel("Find by Last Name: "));
		pnlSouth.add(txtFilter);
		
		add(pnlNorth, BorderLayout.NORTH);		// Add panels to frame
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
		
		pack();
		setVisible(true);
	}
	
	public void initFilter() {
		final TableRowSorter<CustomersTableModel> sorter 
		= new TableRowSorter<CustomersTableModel>(ctmCustomers);
		
		// Filter text 
		txtFilter.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				
				
				RowFilter<CustomersTableModel, Object> rf = null;
				try {
					rf = RowFilter.regexFilter(txtFilter.getText(), COL_INDEX_LASTNAME);
				}
				catch(PatternSyntaxException e) {
					TXLogger.logError(e.getMessage());
					e.printStackTrace();
				}
				sorter.setRowFilter(rf);
			}
		});
		/* Not really needed, default sorting by first column (AgentID) is ok for now
		List<RowSorter.SortKey> sortKeys =
			new ArrayList<RowSorter.SortKey>();
		
		sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
		*/

		tblCustomers.setRowSorter(sorter);		
	}
	
	private void initAgentVector() {
		try {
			ResultSet rs = TXConnection.getConnection().createStatement().executeQuery
				(QUERY_AGENT_NAMES);
			
			while(rs.next()) {
				// vAgent[agentID] = AgtLastName, AgtFirstName
				//vAgentNames.add(rs.getInt("AgentId"), rs.getString("AgtLastName") + ", " + rs.getString("AgtFirstName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void refreshCustomers() {
		try {
			PreparedStatement ps = TXConnection.getConnection().prepareStatement
				(QUERY_INACTIVE_CUSTOMERS, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rsCustomers = ps.executeQuery();
						
		} catch (SQLException e) {
			TXLogger.logError("UACustomersFrame: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void refreshAgents() {
		// Build combo box containing active agents
		try {
			rsActiveAgents = TXConnection.getConnection().createStatement().executeQuery
				(QUERY_ACTIVE_AGENTS);
			cboAgents.removeAllItems();
			while(rsActiveAgents.next()) {
				// cbmAgents.addElement(rsActiveAgents.getString("AgentId"));
				cboAgents.addItem(rsActiveAgents.getString("AgentId"));
			}
		} catch (SQLException e) {
			TXLogger.logError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	// 
	public class AgentRenderer extends JLabel implements TableCellRenderer {
		@Override public Component getTableCellRendererComponent(JTable arg0,
				Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) 
		{
			setText("Name:");
			setVisible(true);
			
			return this;
		}
		
	}
	
}
