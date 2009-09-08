package com.travelexperts;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * 
 * Custom table model for displaying customers
 * 
 * @author Will Dixon
 *
 */
@SuppressWarnings("serial")
public class CustomersTableModel extends AbstractTableModel {
	private ResultSet customers; 
	private int rows;
	private int columns;
	
	public CustomersTableModel(ResultSet newTable) {
		super();
		
		customers = newTable;
		try {
			customers.last();
			rows = customers.getRow();
			columns = customers.getMetaData().getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Class<?> getColumnClass(int arg0) {
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
		try {
			// Result sets start at 1,1 instead of 0,0
			rowIndex++; 
			columnIndex++;
			
			customers.absolute(rowIndex);
			String o = customers.getString(columnIndex);
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
			customers.absolute(rowIndex + 1);
			customers.updateString(columnIndex + 1, String.valueOf(value));
			customers.updateRow();	// Save to underlying database
			fireTableCellUpdated(rowIndex, columnIndex);
			
			customers.moveToInsertRow();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	}
