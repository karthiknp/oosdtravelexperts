package com.travelexperts;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

// copied from Default Table Model only changed a lit bit
public class PackagesTableModel extends AbstractTableModel implements
		Serializable
{

	/**
			 * 
			 */
	private static final long serialVersionUID = 4008206538446886875L;

	/**
	 * The <code>Vector</code> of <code>Vectors</code> of <code>Object</code>
	 * values.
	 */
	protected Vector dataVector;

	/** The <code>Vector</code> of column identifiers. */
	protected Vector columnIdentifiers;

	/**
	 * Add by newinone 2009-09-08 Start
	 */
	private ResultSet rs_packages;
	private int rows;
	private int columns;

	private Connection sqlConn = new TXConnection().getInstance();

	private int newRow;
	//
	// Instance Variables
	//
	public static final int PACKAGE_ID = 0;
	public static final int PACKAGE_NAME = 1;
	public static final int START_DATE = 2;
	public static final int END_DATE = 3;
	public static final int DESCRIPTION = 4;
	public static final int PRICE = 5;
	public static final int COMISSION = 6;

	public PackagesTableModel(ResultSet newTable)
	{
		super();

		rs_packages = newTable;
		try
		{
			rs_packages.last();
			rows = rs_packages.getRow();
			columns = rs_packages.getMetaData().getColumnCount();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public Class<?> getColumnClass(int arg0)
	{
		// All columns will be strings
		return String.class;
	}

	public int getColumnCount()
	{
		return columns;
	}

	public String getColumnName(int arg0)
	{
		switch (arg0) {
		case 0:
			return "ID";
		case 1:
			return "Name";
		case 2:
			return "Start Date";
		case 3:
			return "End Date";
		case 4:
			return "Description";
		case 5:
			return "Price";
		case 6:
			return "Commission";
		default:
			return "Unnamed Column";
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex)
	{
		if (rowIndex == newRow)
			return null;
		try
		{
			// Result sets start at 1,1 instead of 0,0
			rowIndex++;
			columnIndex++;

			rs_packages.absolute(rowIndex);
			String o = null;
			if (columnIndex == START_DATE + 1 || columnIndex == END_DATE + 1)
			{
				if (rs_packages.getString(columnIndex) != null)
				{
					o = rs_packages.getString(columnIndex).split(" ")[0];
				}
			}
			else
			{
				o = rs_packages.getString(columnIndex);
			}
			if (o != null)
				return o;
			else
				return null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public int getRowCount()
	{
		return rows;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		if (columnIndex == PACKAGE_ID || columnIndex == START_DATE
				|| columnIndex == END_DATE)
		{
			return false;
		}
		// else if (editableRow == rowIndex)
		// {
		// return true;
		// }
		else
		{
			return true;
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		if (rowIndex == newRow)
			return;
		try
		{
			rs_packages.absolute(rowIndex + 1);
			if (columnIndex == START_DATE || columnIndex == END_DATE)
				rs_packages.updateDate(columnIndex + 1, (Date) value);
			else
			{
				rs_packages
						.updateString(columnIndex + 1, String.valueOf(value));
			}
			rs_packages.updateRow(); // Save to underlying database
			fireTableCellUpdated(rowIndex, columnIndex);
			rs_packages.moveToInsertRow();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void deleteRow(int[] aryRows)
	{

		for (int i = aryRows.length - 1; i >= 0; i--)
		{
			String sql1 = "DELETE FROM Packages_Products_Suppliers "
					+ " WHERE PackageId = "
					+ getValueAt(aryRows[i], PACKAGE_ID);
			try
			{
				Statement stmt1 = sqlConn.createStatement();
				sqlConn.setAutoCommit(false);
				rs_packages.absolute(aryRows[i] + 1);
				rs_packages.deleteRow();

				stmt1.executeQuery(sql1);
				sqlConn.commit();
				sqlConn.setAutoCommit(true);
				fireTableRowsDeleted(aryRows[i], aryRows[i]);
				rows--;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				try
				{
					sqlConn.rollback();
				}
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}

	public int addEmptyRow(int newRowIndex)
	{
		// dataVector.add(new Vector());
		// try
		// {
		// rs_packages.afterLast();
		// rs_packages.insertRow();
		// }
		// catch (SQLException e)
		// {
		// e.printStackTrace();
		// }

		String sql1 = "SELECT max(package_id) FROM Packages";
		Statement stmt1;
		int maxPkgID=0;
		try
		{
			stmt1 = sqlConn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			maxPkgID=rs1.getInt(1);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		rows += 1;
		fireTableRowsInserted(rows - 1, rows - 1);
		newRow = newRowIndex;
		return maxPkgID+1;
	}

	public boolean hasEmptyRow()
	{

		if (dataVector.size() == 0)
			return false;
		Object obj = dataVector.get(dataVector.size() - 1);
		if (obj.toString().trim().equals("[]"))
		{
			return true;
		}
		else
			return false;
	}



}
