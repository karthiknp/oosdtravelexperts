package com.travelexperts;

public class Suppliers
{
	// Suppliers class definition
	private int supplierID;
	private String supName;

	// Suppliers class constructor
	public Suppliers(int suppID, String supName)
	{
		this.setSupplierID(suppID);
		this.setSupName(supName);
	}

	/**
	 * @param suppID the supplierID to set
	 */
	public void setSupplierID(int suppID)
	{
		this.supplierID = suppID;
	}

	/**
	 * @return the supplierID
	 */
	public int getSupplierID()
	{
		return supplierID;
	}

	/**
	 * @param supName the supName to set
	 */
	public void setSupName(String supName)
	{
		this.supName = supName;
	}

	/**
	 * @return the supName
	 */
	public String getSupName()
	{
		return supName;
	}
}
