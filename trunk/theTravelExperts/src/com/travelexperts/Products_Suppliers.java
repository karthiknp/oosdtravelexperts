package com.travelexperts;

public class Products_Suppliers
{
	// Products_Suppliers class definition
	private int productSupplierID;
	private int productID;
	private int supplierID;

	// Products_Suppliers class definition
	public Products_Suppliers(int prodSuppID, int prodID, int suppID)
	{
		this.setProductSupplierID(prodSuppID);
		this.setProductID(prodID);
		this.setSupplierID(suppID);
	}

	/**
	 * @param prodSuppID the productSupplierID to set
	 */
	public void setProductSupplierID(int prodSuppID)
	{
		this.productSupplierID = prodSuppID;
	}

	/**
	 * @return the productSupplierID
	 */
	public int getProductSupplierID()
	{
		return productSupplierID;
	}

	/**
	 * @param prodID the productID to set
	 */
	public void setProductID(int prodID)
	{
		this.productID = prodID;
	}

	/**
	 * @return the productID
	 */
	public int getProductID()
	{
		return productID;
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
}
