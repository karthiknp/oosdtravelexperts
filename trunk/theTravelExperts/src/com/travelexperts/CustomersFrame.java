package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 * 
 * @author WillDixon
 *
 */
@SuppressWarnings("serial")
public class CustomersFrame extends JInternalFrame {
	
	private final static String QUERY_ALL_CUSTOMERS = 
		"SELECT CustomerID, CustFirstName, CustLastName, CustAddress, CustCity, CustPostal, CustCountry, CustHomePhone, CustBusPhone, CustEmail, AgentId " +
		"FROM Customers";
	
	private static final String QUERY_INACTIVE_CUSTOMERS = 
		"SELECT CustomerID, CustFirstName, CustLastName, CustAddress, CustCity, CustPostal, CustCountry, CustHomePhone, CustBusPhone, CustEmail, AgentId " +
		"FROM Customers " +
		"WHERE AgentID IS NULL " +
		"OR AgentID IN (SELECT AgentID FROM Agents WHERE AgtPosition='Inactive')";

		
	private static final String TEXT_USAGE =
		"<html><strong>Insert</strong> - Create new customer<br/>" +
		"<strong>Delete<strong> - Delete customer<br/>" +
		"<strong>Edit</strog> - Click on a cell to edit</html>";
	
	private static final int COL_INDEX_LASTNAME = 2;
	private static final int COL_INDEX_AGENTID = 10;
	
	final JPanel pnlNorth = new JPanel();		// Panels
	final JPanel pnlCenter = new JPanel();
	final JPanel pnlSouth = new JPanel();
	
	ResultSet rsAgents = null;
	ResultSet rssCustomers = null;			// These are initialized through refreshTable()		
	CustomersTableModel tmCustomers = null;
	TableRowSorter<CustomersTableModel> sorter;	// Table filter	
	JTable tblCustomers = null;
	JScrollPane spCustomers = null;

	final JComboBox cboAgents = new JComboBox();	// Components
	final JCheckBox chkUnassigned = new JCheckBox("Show Unassigned Customers Only");
	final JTextField txtSearch = new JTextField(20);
	
	public CustomersFrame() {
		super("Customers", true, true, true, true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	
		initFrame();
		
		refreshTable();
		chkUnassigned.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});
	}
	
	public void initFrame() {
		pnlNorth.add(new JLabel(TEXT_USAGE));
		
		pnlSouth.add(new JLabel("Search: "));
		pnlSouth.add(txtSearch);
		pnlSouth.add(chkUnassigned);
		
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
		refreshTable();
		
		// Checkbox action listener to refresh table 
		chkUnassigned.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		});
		
		pack();
	}
	
	// Refresh the table by fetching new result set and linking all objects
	public void refreshTable() {
		// Remove old table
		if(spCustomers != null) pnlCenter.remove(spCustomers);
			
		queryCustomers();
		tmCustomers = new CustomersTableModel(rssCustomers);		// Link table objects
		
		tblCustomers = new JTable(tmCustomers);
		tblCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		spCustomers = new JScrollPane(tblCustomers);
		spCustomers.setPreferredSize(new Dimension(800, 500));

		sorter = new TableRowSorter<CustomersTableModel>(tmCustomers);		// Sorter
		tblCustomers.setRowSorter(sorter);

		// Custom renderer/editor for AgentId
		AgentCellRenderer.refreshAgents();
		tblCustomers.getColumnModel().getColumn(COL_INDEX_AGENTID)
			.setCellRenderer(new AgentCellRenderer());
		tblCustomers.getColumnModel().getColumn(COL_INDEX_AGENTID)
			.setCellEditor(new AgentCellEditor());

		// Set column sizes
		tblCustomers.getColumnModel().getColumn(0).setPreferredWidth(25);
		tblCustomers.getColumnModel().getColumn(1).setPreferredWidth(50);
		tblCustomers.getColumnModel().getColumn(2).setPreferredWidth(60);
		tblCustomers.getColumnModel().getColumn(3).setPreferredWidth(160);
		tblCustomers.getColumnModel().getColumn(4).setPreferredWidth(60);
		tblCustomers.getColumnModel().getColumn(5).setPreferredWidth(50);
		tblCustomers.getColumnModel().getColumn(6).setPreferredWidth(70);
		tblCustomers.getColumnModel().getColumn(7).setPreferredWidth(70);
		tblCustomers.getColumnModel().getColumn(8).setPreferredWidth(60);
		tblCustomers.getColumnModel().getColumn(9).setPreferredWidth(100);
		tblCustomers.getColumnModel().getColumn(10).setPreferredWidth(130);
		
		pnlCenter.add(spCustomers);
		
		initSearch();		
		
		tblCustomers.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_DELETE)
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this customer?") == JOptionPane.OK_OPTION) {
					try {
						int row = tblCustomers.getSelectedRow() + 1;
						rssCustomers.absolute(row);
						rssCustomers.deleteRow();
						tmCustomers.fireTableDataChanged();					
												
					} catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
				else if(e.getKeyChar() == KeyEvent.VK_INSERT);
			}
		});
		
		pack();
	}
	
	private void queryCustomers() {
		try {
			if(chkUnassigned.isSelected()) {
				rssCustomers = TXConnection.getConnection()
				.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)
				.executeQuery(QUERY_INACTIVE_CUSTOMERS);
			}
			else {
				rssCustomers = TXConnection.getConnection()
				.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)
				.executeQuery(QUERY_ALL_CUSTOMERS);
			}
			
		} catch (SQLException e) {
			TXLogger.logError(e.getMessage());
			e.printStackTrace();
		} 
		
	}
		
	public void initSearch() {		
		// TextField action handler: search by last name 
		txtSearch.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				
				RowFilter<CustomersTableModel, Object> rf = null;
				try {
					rf = RowFilter.regexFilter(txtSearch.getText(), COL_INDEX_LASTNAME);
				}
				catch(PatternSyntaxException e) {
					TXLogger.logError(e.getMessage());
					e.printStackTrace();
				}
				sorter.setRowFilter(rf);
			}
		});
	}
	
}
