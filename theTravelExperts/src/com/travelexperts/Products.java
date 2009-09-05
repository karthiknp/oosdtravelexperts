package com.travelexperts;

public class Products
{
	// Packages class definition
	private int productID;
	private String prodName;
	
	// Products class constructor
	public Products(int prodID, String prodName)
	{
		this.setProductID(prodID);
		this.setProdName(prodName);
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
	 * @param prodName the prodName to set
	 */
	public void setProdName(String prodName)
	{
		this.prodName = prodName;
	}

	/**
	 * @return the prodName
	 */
	public String getProdName()
	{
		return prodName;
	}
}
