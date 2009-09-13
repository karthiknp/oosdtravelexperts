package com.travelexperts;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JOptionPane;
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
	protected Vector<Object> dataVector;

	/** The <code>Vector</code> of column identifiers. */
	protected Vector<Object> columnIdentifiers;

	/**
	 * Add by newinone 2009-09-08 Start
	 */
	private static ResultSet rs_packages;
	private int rows;
	private int columns;

	private Connection sqlConn = new TXConnection().getInstance();

	private int maxPkgID = 0;

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

	public PackagesTableModel()
	{
		super();
		String sql1 = "SELECT PACKAGEID ID," + "PKGNAME Name, "
				+ "PKGSTARTDATE StartDate, " + "PKGENDDATE EndDate, "
				+ "PKGDESC Description, " + "PKGBASEPRICE Price,"
				+ "PKGAGENCYCOMMISSION Commission FROM packages ORDER BY ID";
		try
		{
			rs_packages = sqlConn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE).executeQuery(sql1);
			rs_packages.last();
			rows = rs_packages.getRow();
			columns = rs_packages.getMetaData().getColumnCount();
			sqlConn.commit();
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
		String o = "";
		try
		{
			// Result sets start at 1,1 instead of 0,0
			rowIndex++;
			columnIndex++;
			// System.out.println("<"+rowIndex+","+columnIndex+">"+rs_packages.getString(columnIndex));
			rs_packages.absolute(rowIndex);
			if (columnIndex == START_DATE + 1 || columnIndex == END_DATE + 1)
			{
//				NumberFormat nf = NumberFormat.getNumberInstance();
//				nf.format(00); 
				// get formatted string for Date
				if (rs_packages.getString(columnIndex) != null)
				{
					o = rs_packages.getString(columnIndex).split(" ")[0];
					String[] strDate= o.split("-");
					if(strDate[1].length()==1)
					{ strDate[1] = "0"+strDate[1];}
					if(strDate[2].length()==1)
					{ strDate[2] = "0"+strDate[2];}
					o = strDate[0]+"-"+strDate[1]+"-"+strDate[2];
				}
				else
				{
					o="";
				}
			}
			else if (columnIndex == PRICE + 1 || columnIndex == COMISSION + 1)
			{
				if (rs_packages.getString(columnIndex) == null
						|| rs_packages.getString(columnIndex) == "")
				{
					o = Integer.toString(0);
				}
				else
				{
					o = rs_packages.getString(columnIndex);
				}
			}
			else
			{
				System.out.println("columnIndex:"+columnIndex+"rowIndex:"+rowIndex);
				o = rs_packages.getString(columnIndex);
			}
			sqlConn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return o == null ? "" : o;
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
		else
		{
			return true;
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex)
	{
		if (!validateTable(value, rowIndex, columnIndex))
		{
			System.out.println("validateTable failed: " + rowIndex + ","
					+ columnIndex);
			return;
		}
		try
		{
			rs_packages.absolute(rowIndex + 1);
			if (columnIndex == START_DATE || columnIndex == END_DATE)
				if(value!=null && (!value.toString().equals("")))
				{rs_packages.updateDate(columnIndex + 1, (Date) value);}
				else
				{rs_packages.updateDate(columnIndex+1,null);}
			else
			{
				rs_packages
						.updateString(columnIndex + 1, String.valueOf(value));
			}
			rs_packages.updateRow(); // Save to underlying database
			fireTableCellUpdated(rowIndex, columnIndex);
			rs_packages.moveToInsertRow();
			sqlConn.commit();
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
				stmt1.executeQuery(sql1);
				rs_packages.absolute(aryRows[i] + 1);
				rs_packages.deleteRow();
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

	public void addEmptyRow(int newRowIndex)
	{
//		System.out.println("addEmptyRow------------------started");
		if (hasEmptyRow())
		{
			return;
		}
		String sql1 = "SELECT max(packageid) FROM Packages";
		Statement stmt1;
		 maxPkgID  = 0;
		String sql2 = "SELECT PACKAGEID ID," + "PKGNAME Name, "
				+ "PKGSTARTDATE StartDate, " + "PKGENDDATE EndDate, "
				+ "PKGDESC Description, " + "PKGBASEPRICE Price,"
				+ "PKGAGENCYCOMMISSION Commission FROM packages ORDER BY ID";
		try
		{
			stmt1 = sqlConn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(sql1);
			rs1.next();
			maxPkgID = rs1.getInt(1);
			rs1.close();
			rs_packages.moveToInsertRow();
			rs_packages.updateInt(1, maxPkgID + 1);
			rs_packages.updateString(2, "value required");
			rs_packages.updateString(3, "");
			rs_packages.updateString(4, "");
			rs_packages.updateString(5, "");
			rs_packages.updateInt(6, 0);
			rs_packages.updateInt(7, 0);
//			System.out.println("addEmptyRow------------------1");
			rs_packages.insertRow();
			sqlConn.commit();
			fireTableRowsInserted(newRowIndex-1, newRowIndex-1);
//			System.out.println("addEmptyRow------------------2");

			rows++;
			rs_packages = sqlConn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE).executeQuery(sql2);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		//System.out.println("addEmptyRow------------------ended");
	}

	public boolean hasEmptyRow()
	{
		// empty table
		if (rows == 0)
			return false;
		// package name not "value required" and DESCRIPTION not null
		if (!validateTable(getValueAt(rows - 1, PACKAGE_NAME).toString(),
				rows - 1, PACKAGE_NAME))
		{
			return true;
		}
		return !validateTable(getValueAt(rows - 1, DESCRIPTION).toString(),
				rows - 1, DESCRIPTION);
	}

	// validate inputs in jtable
	public boolean validateTable(Object value, int rowIndex, int columnIndex)
	{
		String validationMsg = "";

		System.out.println("validateTable");
		boolean flgValidate = true;
		BigDecimal price;
		BigDecimal commission;
		switch (columnIndex) {
		// package name not null
		case PACKAGE_NAME:
			if (value == null || value.toString().trim().equals("")
					|| value.toString().trim().equals("value required"))
				flgValidate = false;
			validationMsg = "Package Name: " + Messages.VALUE_REQUIRED;
			break;
		// description not null
		case DESCRIPTION:
			if (value == null || value.toString().trim().equals(""))
				flgValidate = false;
			validationMsg = "Package Description: " + Messages.VALUE_REQUIRED;
			break;
		// start date not later than end date
		case START_DATE:
			if (value != null && getValueAt(rowIndex, END_DATE) != null
					&& getValueAt(rowIndex, END_DATE) != "")
			{
				Calendar startDate = Calendar.getInstance();
				startDate.set(Calendar.YEAR, Integer.parseInt(value.toString()
						.split("-")[0]));
				startDate.set(Calendar.MONTH, Integer.parseInt(value.toString()
						.split("-")[1]));
				startDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(value
						.toString().split("-")[2]));
				Calendar endDate = Calendar.getInstance();
				endDate.set(Calendar.YEAR, Integer.parseInt(getValueAt(
						rowIndex, END_DATE).toString().split("-")[0]));
				endDate.set(Calendar.MONTH, Integer.parseInt(getValueAt(
						rowIndex, END_DATE).toString().split("-")[1]));
				endDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(getValueAt(
						rowIndex, END_DATE).toString().split("-")[2]));
				if (startDate.compareTo(endDate) > 0)
				{
					JOptionPane.showMessageDialog(null, Messages
							.getValidateMsg_A_CANT_GREATERTHAN_B("Start Date",
									"End Date"));
					flgValidate = false;
					validationMsg = Messages
							.getValidateMsg_A_CANT_GREATERTHAN_B("Start Date",
									"End Date");
				}
			}
			break;
		case END_DATE:
			if (getValueAt(rowIndex, START_DATE) != null
					&& getValueAt(rowIndex, START_DATE) != "" && value != null&& value !="")
			{
				Calendar startDate = Calendar.getInstance();
				startDate.set(Calendar.YEAR, Integer.parseInt(getValueAt(
						rowIndex, START_DATE).toString().split("-")[0]));
				startDate.set(Calendar.MONTH, Integer.parseInt(getValueAt(
						rowIndex, START_DATE).toString().split("-")[1]));
				startDate.set(Calendar.DAY_OF_MONTH, Integer
						.parseInt(getValueAt(rowIndex, START_DATE).toString()
								.split("-")[2]));
				Calendar endDate = Calendar.getInstance();
				endDate.set(Calendar.YEAR, Integer.parseInt(value.toString()
						.split("-")[0]));
				endDate.set(Calendar.MONTH, Integer.parseInt(value.toString()
						.split("-")[1]));
				endDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(value
						.toString().split("-")[2]));
				if (startDate.compareTo(endDate) > 0)
				{
					flgValidate = false;
					validationMsg = Messages
							.getValidateMsg_A_CANT_GREATERTHAN_B("Start Date",
									"End Date");
				}
			}
			break;
		// commission not greater than price
		case PRICE:
			if (value == null || value.toString().trim().equals(""))
			{
				value = 0;
			}
			price = new BigDecimal(value.toString());
			commission = new BigDecimal(getValueAt(rowIndex, COMISSION)
					.toString());
			if (commission.compareTo(price) > 0)
			{
				flgValidate = false;
				validationMsg = Messages.getValidateMsg_A_CANT_GREATERTHAN_B(
						"Commission", "Package Price");
			}
			break;
		case COMISSION:
			if (value == null || value.toString().trim().equals(""))
			{
				value = 0;
			}
			price = new BigDecimal(getValueAt(rowIndex, PRICE).toString());
			commission = new BigDecimal(value.toString());
			if (commission.compareTo(price) > 0)
			{
				flgValidate = false;
				validationMsg = Messages.getValidateMsg_A_CANT_GREATERTHAN_B(
						"Commission", "Package Price");
			}
			break;
		default:
			break;
		}
		if (!flgValidate)
		{
			JOptionPane.showMessageDialog(null, validationMsg);
		}
		return flgValidate;
	}

	public Vector<Object> getRowValueFrom(int rowIndex)
	{
//		System.out.println("getRowValueFrom------------------started");
		Vector<Object> rowVector = new Vector<Object>();
		for (int i = 0; i < columns; i++)
		{
			rowVector.addElement(getValueAt(rowIndex, i));
		}
//		System.out.println("getRowValueFrom------------------ended");
		return rowVector;
	}

	public void setRowValueTo(Vector<Object> rowVector,
			Vector<Products_Suppliers> v_psInc, int rowIndex)
	{
//		System.out.println("setRowValueTo------------------started");
		for (int i = 1; i < columns; i++)
		{
			if (i == START_DATE || i == END_DATE)
			{
				if(!rowVector.elementAt(i).equals(""))
				{
					String[] strDate = rowVector.elementAt(i).toString().split("-");
					Calendar cal = Calendar.getInstance();
					cal.set(Integer.parseInt(strDate[0]), Integer
							.parseInt(strDate[1]), Integer.parseInt(strDate[2]));
					java.sql.Date d = new java.sql.Date(cal.getTimeInMillis());
					setValueAt(d, rowIndex, i);
				}
				else
				{
					setValueAt("", rowIndex, i);
				}
			}
			else
			{
				setValueAt(rowVector.elementAt(i), rowIndex, i);
			}
		}
		// add products to the new package
		if(v_psInc==null ||v_psInc.size()==0)
		{
			return;
		}
		int pkgIdTo = maxPkgID+1;
		Vector<String> v_sql1 = new Vector<String>();

		for (int i = 0; i < v_psInc.size(); i++)
		{
			v_sql1
					.addElement("INSERT INTO Packages_Products_Suppliers VALUES ("
							+ pkgIdTo
							+ ","
							+ v_psInc.elementAt(i).getProductSupplierID() + ")");
		}

		try
		{
			for (int i = 0; i < v_sql1.size(); i++)
			{
				System.out.println(v_sql1.elementAt(i));
				sqlConn.createStatement().executeUpdate(v_sql1.elementAt(i));
				sqlConn.commit();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
//		System.out.println("setRowValueTo------------------ended");
	}

	public void searchPackages(String keyWords)
	{
		String sql1 = "SELECT PACKAGEID ID,"
				+ "PKGNAME Name, "
				+ "PKGSTARTDATE StartDate, "
				+ "PKGENDDATE EndDate, "
				+ "PKGDESC Description, "
				+ "PKGBASEPRICE Price,"
				+ "PKGAGENCYCOMMISSION Commission FROM packages "
				+ "WHERE PKGNAME ||to_char(PKGSTARTDATE,'yyyy-mm-dd') || to_char(PKGENDDATE,'yyyy-mm-dd') "
				+ "|| PKGDESC || PKGBASEPRICE || PKGAGENCYCOMMISSION LIKE '%"
				+ keyWords + "%' ORDER BY ID";
		System.out.println(sql1);
		try
		{
			rs_packages = sqlConn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE).executeQuery(sql1);
			rs_packages.last();
			rows = rs_packages.getRow();
			columns = rs_packages.getMetaData().getColumnCount();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		fireTableDataChanged();
	}
}
