package com.travelexperts;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 * 
 * Custom table model for displaying customers
 * 
 * @author Will Dixon
 *
 */
@SuppressWarnings("serial")
public class CustomersTableModel extends AbstractTableModel {
	private final int COL_INDEX_AGENTID = 10; 
	private ResultSet customers; 
	protected int rows;
	protected int columns;
	
	public CustomersTableModel(ResultSet newTable) {
		super();
		
		setCustomers(newTable);
		try {
			getCustomers().last();
			rows = getCustomers().getRow();
			columns = getCustomers().getMetaData().getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Class<?> getColumnClass(int colIndex) {
		// All columns will be strings
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return columns;
	}

	@Override
	public String getColumnName(int arg0) {
		switch(arg0) {
			case 0: return "ID";
			case 1: return "First";
			case 2: return "Last";
			case 3: return "Address";
			case 4: return "City";
			case 5: return "Postal";
			case 6: return "Country";
			case 7: return "Home Phone";
			case 8: return "Bus. Phone";
			case 9: return "Email";
			case 10: return "Agent";
			default: return "Unnamed Column";
		}
	}

	@Override
	public int getRowCount() {
		return rows;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == COL_INDEX_AGENTID) {
			
		}
		try {
			// Result sets start at 1,1 instead of 0,0
			rowIndex++; 
			columnIndex++;
			
			getCustomers().absolute(rowIndex);
			String o = getCustomers().getString(columnIndex);
			if(o != null)
				return o;
			else return null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex != 0) {
			return true;
		}
		else {
			JOptionPane.showMessageDialog(null, "You cannot change the customer id!");
			return false;
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		try {
			getCustomers().absolute(rowIndex + 1);
			
			if ( getCustomers().getMetaData().getColumnName(columnIndex+1).equalsIgnoreCase("AgentID")) {
				if(value == null) {
					System.out.println("Not updating null agentid");
				}
				else {
					int agentId;
					try {
						agentId = Integer.parseInt(String.valueOf(value));
						System.out.println("Cust Name " + getCustomers().getString("CustLastName"));
						System.out.println("AgentID: " + agentId);
						getCustomers().updateInt(columnIndex + 1, agentId);
					}
					catch(NumberFormatException nfe) {
						nfe.printStackTrace();
					}
				}
			}
			else {
				getCustomers().updateString(columnIndex + 1, String.valueOf(value));
			}

			getCustomers().updateRow();	// Save to underlying database
			fireTableCellUpdated(rowIndex, columnIndex);
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	protected void setCustomers(ResultSet customers) {
		this.customers = customers;
		fireTableStructureChanged();
		fireTableDataChanged();
	}


	protected ResultSet getCustomers() {
		return customers;
	}
}
