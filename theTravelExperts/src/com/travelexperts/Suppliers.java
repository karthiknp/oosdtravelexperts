package com.travelexperts;

public class Suppliers
{
	// Suppliers class definition
	private int supplierID;
	private String supName;
	private int productsupplierId;

	// Suppliers class constructor
	public Suppliers(int suppID, String supName)
	{
		this.setSupplierID(suppID);
		this.setSupName(supName);
	}
	//Michelle added
	public Suppliers(int suppID, String supName, int psId)
	{
		this.setSupplierID(suppID);
		this.setSupName(supName);
		this.setProductsupplierId(psId);
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
	//Michelle added
	public String toString()
	{
		return supName;
	}
	//Michelle added
	public void setProductsupplierId(int productsupplierId) {
		this.productsupplierId = productsupplierId;
	}
	//Michelle added
	public int getProductsupplierId() {
		return productsupplierId;
	}
}
