package com.travelexperts;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

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
	public static final int COMMISSION = 6;

	public PackagesTableModel()
	{
		super();
		reload_rs_packages("", "");
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
				// NumberFormat nf = NumberFormat.;
				// nf.format(00);
				// get formatted string for Date
				if (rs_packages.getString(columnIndex) != null
						&& rs_packages.getString(columnIndex) != "")
				{
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Calendar cal = Calendar.getInstance();
					rs_packages.getDate(columnIndex, cal);
					o = df.format((cal.getTime())).toString();
				}
				else
				{
					o = "";
				}
			}
			else if (columnIndex == PRICE + 1 || columnIndex == COMMISSION + 1)
			{
				Format currency = NumberFormat
						.getCurrencyInstance(Locale.CANADA);

				if (rs_packages.getString(columnIndex) == null
						|| rs_packages.getString(columnIndex) == "")
				{
					o = Integer.toString(0);
				}
				else
				{
					o = rs_packages.getString(columnIndex);
				}
				o = currency.format(Double.parseDouble(o.toString()));
			}
			else
			{
				// TXLogger.logger.debug("columnIndex:" + columnIndex +
				// "rowIndex:"
				// + rowIndex);
				// System.out.println("columnIndex:" + columnIndex + "rowIndex:"
				// + rowIndex);
				o = rs_packages.getString(columnIndex);
			}
			sqlConn.commit();
		}
		catch (SQLException e)
		{
			TXLogger.logger.error(e.getMessage());
			// e.printStackTrace();
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
			TXLogger.logger.debug("validateTable failed: " + rowIndex + ","
					+ columnIndex);
			// System.out.println("validateTable failed: " + rowIndex + ","
			// + columnIndex);
			return;
		}
		// NumberFormat currency =
		// NumberFormat.getCurrencyInstance(Locale.CANADA);

		try
		{
			rs_packages.absolute(rowIndex + 1);
			if (columnIndex == START_DATE || columnIndex == END_DATE)
				if (value != null && (!value.toString().equals("")))
				{
					rs_packages.updateDate(columnIndex + 1, (Date) value);
				}
				else
				{
					rs_packages.updateDate(columnIndex + 1, null);
				}
			else if (columnIndex == PRICE || columnIndex == COMMISSION)
			{
				if (value == null || value.toString().trim().equals(""))
				{
					rs_packages.updateBigDecimal(columnIndex + 1,
							new BigDecimal(0));
				}
				else
				{
					// BigDecimal bgValue = new
					// BigDecimal(currency.parse(value.toString()).doubleValue());
					BigDecimal bgValue = new BigDecimal(Double
							.parseDouble(value.toString()));
					rs_packages.updateBigDecimal(columnIndex + 1, bgValue);
				}
			}
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
			TXLogger.logger.error(e.getMessage());
			// e.printStackTrace();
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
				TXLogger.logger.error(e.getMessage());
				// e.printStackTrace();
				try
				{
					sqlConn.rollback();
				}
				catch (SQLException e1)
				{
					TXLogger.logger.error(e1.getMessage());
					// e1.printStackTrace();
				}
			}
		}
	}

	public void addEmptyRow(int newRowIndex, String orderByCol, String keyWords)
	{
		// System.out.println("addEmptyRow------------------started");
		if (hasEmptyRow())
		{
			return;
		}
		String sql1 = "SELECT max(packageid) FROM Packages";
		maxPkgID = 0;
		try
		{
			ResultSet rs1 = sqlConn.createStatement().executeQuery(sql1);
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
			// System.out.println("addEmptyRow------------------1");
			rs_packages.insertRow();
			sqlConn.commit();
			fireTableRowsInserted(newRowIndex - 1, newRowIndex - 1);
			// System.out.println("addEmptyRow------------------2");

			rows++;
			reload_rs_packages(orderByCol, keyWords);
		}
		catch (SQLException e)
		{
			TXLogger.logger.error(e.getMessage());
			// e.printStackTrace();
		}
		// System.out.println("addEmptyRow------------------ended");
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
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		String validationMsg = "";
		TXLogger.logger.debug("validate table");
		SimpleDateFormat dfStart = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dfEnd = new SimpleDateFormat("yyyy-MM-dd");
		// System.out.println("validateTable");
		boolean flgValidate = true;
		BigDecimal price = new BigDecimal(0);
		BigDecimal commission = new BigDecimal(0);
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
				try
				{
					dfStart.parse(value.toString());
					dfEnd.parse(getValueAt(rowIndex, END_DATE).toString());
				}
				catch (ParseException e)
				{
					TXLogger.logger.error(e.getMessage());
					// e.printStackTrace();
				}
				Calendar startDate = dfStart.getCalendar();
				Calendar endDate = dfEnd.getCalendar();
				if (startDate.compareTo(endDate) > 0)
				{
					flgValidate = false;
					validationMsg = Messages
							.getValidateMsg_A_CANT_GREATERTHAN_B("Start Date",
									"End Date");
				}
			}
			break;
		case END_DATE:
			if (getValueAt(rowIndex, START_DATE) != null
					&& getValueAt(rowIndex, START_DATE) != "" && value != null
					&& value != "")
			{
				try
				{
					dfStart.parse(getValueAt(rowIndex, START_DATE).toString());
					dfEnd.parse(value.toString());
				}
				catch (ParseException e)
				{
					TXLogger.logger.error(e.getMessage());
					// e.printStackTrace();
				}
				Calendar startDate = dfStart.getCalendar();
				Calendar endDate = dfEnd.getCalendar();
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
			// price = new
			// BigDecimal(currency.parse(value.toString()).doubleValue());
			try
			{
				commission = new BigDecimal(currency.parse(
						getValueAt(rowIndex, COMMISSION).toString())
						.doubleValue());
			}
			catch (ParseException e1)
			{
				e1.printStackTrace();
			}
			price = new BigDecimal(Double.parseDouble((value.toString())));
			// commission = new BigDecimal((Double.parseDouble(getValueAt(
			// rowIndex, COMMISSION).toString())));

			if (commission.compareTo(price) > 0)
			{
				flgValidate = false;
				validationMsg = Messages.getValidateMsg_A_CANT_GREATERTHAN_B(
						"Commission", "Package Price");
			}
			break;
		case COMMISSION:
			if (value == null || value.toString().trim().equals(""))
			{
				value = 0;
			}
			try
			{
				price = new BigDecimal(currency.parse(
						getValueAt(rowIndex, PRICE).toString()).doubleValue());
				commission = new BigDecimal(Double
						.parseDouble(value.toString()));
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
			// commission = new BigDecimal(currency.parse(value.toString())
			// .doubleValue());
			// price = new BigDecimal(Double.parseDouble(getValueAt(rowIndex,
			// PRICE).toString()));
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
		Vector<Object> rowVector = new Vector<Object>();
		for (int i = 0; i < columns; i++)
		{
			rowVector.addElement(getValueAt(rowIndex, i));
		}
		return rowVector;
	}

	public void setRowValueTo(Vector<Object> rowVector,
			Vector<Products_Suppliers> v_psInc, int rowIndex,String keyWords)
	{
		NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.CANADA);
		double dblPrice = 0;
		double dblCommission = 0;
		try
		{
			dblPrice = currency.parse(rowVector.elementAt(PRICE).toString())
					.doubleValue();
			dblCommission = currency.parse(
					rowVector.elementAt(COMMISSION).toString()).doubleValue();
		}
		catch (ParseException e2)
		{
			e2.printStackTrace();
		}
		int pkgIdTo = maxPkgID + 1;
		String sql1 = "UPDATE packages SET PKGNAME = '"
				+ rowVector.elementAt(PACKAGE_NAME)
				+ "', PKGSTARTDATE= to_date('"
				+ rowVector.elementAt(START_DATE)
				+ "','yyyy-mm-dd'),PKGENDDATE = to_date('"
				+ rowVector.elementAt(END_DATE) + "','yyyy-mm-dd'),PKGDESC = '"
				+ rowVector.elementAt(DESCRIPTION) + "',PKGBASEPRICE = "
				+ dblPrice + ",PKGAGENCYCOMMISSION = " + dblCommission
				+ " WHERE PACKAGEID = " + pkgIdTo;
		TXLogger.logger.debug(sql1);
		// System.out.println(sql1);
		// add products to the new package
		Vector<String> v_sql1 = new Vector<String>();
		if (v_psInc != null)
		{
			for (int i = 0; i < v_psInc.size(); i++)
			{
				v_sql1
						.addElement("INSERT INTO Packages_Products_Suppliers VALUES ("
								+ pkgIdTo
								+ ","
								+ v_psInc.elementAt(i).getProductSupplierID()
								+ ")");
			}
		}

		try
		{
			sqlConn.setAutoCommit(false);
			sqlConn.createStatement().execute(sql1);
			if (v_sql1.size() > 0)
			{
				for (int i = 0; i < v_sql1.size(); i++)
				{
					TXLogger.logger.debug(v_sql1.elementAt(i));
					// System.out.println(v_sql1.elementAt(i));
					sqlConn.createStatement()
							.executeUpdate(v_sql1.elementAt(i));
					sqlConn.commit();
				}
			}
			sqlConn.commit();
			sqlConn.setAutoCommit(true);
			reload_rs_packages( "ID",keyWords);
		}
		catch (SQLException e)
		{
			try
			{
				sqlConn.rollback();
			}
			catch (SQLException e1)
			{
				TXLogger.logger.error(e1.getMessage());
				e1.printStackTrace();
			}
			TXLogger.logger.error(e.getMessage());
			e.printStackTrace();
		}
		// System.out.println("setRowValueTo------------------ended");
	}

	protected void reload_rs_packages(String orderByCol, String keyWords)
	{
		if (orderByCol.equals(""))
		{
			orderByCol = "ID";
		}

		String sql1 = "SELECT PACKAGEID ID,"
				+ "PKGNAME Name, "
				+ "PKGSTARTDATE StartDate, "
				+ "PKGENDDATE EndDate, "
				+ "PKGDESC Description, "
				+ "PKGBASEPRICE Price,"
				+ "PKGAGENCYCOMMISSION Commission FROM packages "
				+ "WHERE PKGNAME ||to_char(PKGSTARTDATE,'yyyy-mm-dd') || to_char(PKGENDDATE,'yyyy-mm-dd') "
				+ "|| PKGDESC || PKGBASEPRICE || PKGAGENCYCOMMISSION LIKE '%"
				+ keyWords + "%' ORDER BY " + orderByCol;
		String sqlCreateView = "CREATE OR REPLACE VIEW v_packages AS " + sql1;
		TXLogger.logger.debug("reload_rs_packages:" + sql1);
		try
		{
			rs_packages = sqlConn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE).executeQuery(sql1);
			rs_packages.last();
			rows = rs_packages.getRow();
			columns = rs_packages.getMetaData().getColumnCount();
			sqlConn.createStatement().execute(sqlCreateView);
			sqlConn.commit();
		}
		catch (SQLException e)
		{
			TXLogger.logger.error(e.getMessage());
			// e.printStackTrace();
		}
		fireTableDataChanged();
	}
}
