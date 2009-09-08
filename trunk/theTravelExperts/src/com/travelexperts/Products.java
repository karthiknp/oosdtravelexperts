package com.travelexperts;

//import java.awt.Component;

//import javax.swing.JLabel;
//import javax.swing.ListCellRenderer;

public class Products
{
	// Packages class definition
	private int productID;
	private String prodName;
	private int productSupplierId; 
	
	// Products class constructor
	public Products(int prodID, String prodName)
	{
		this.setProductID(prodID);
		this.setProdName(prodName);
	}
	public Products(int prodID, String prodName, int psID)
	{
		this.setProductID(prodID);
		this.setProdName(prodName);
		this.setProductSupplierId(psID);
	}
	public Products()
	{
		
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
	public String toString()
	{
		return prodName;
	}
	public void setProductSupplierId(int productSupplierId) {
		this.productSupplierId = productSupplierId;
	}
	public int getProductSupplierId() {
		return productSupplierId;
	}
}

