package com.travelexperts;


public class Products_Suppliers
{
	// Products_Suppliers class definition
	private int  productSupplierID;
	private int productID;
	private int supplierID;
	private String psProdName;
	private String psSuppName;

	public String getPsProdName()
	{
		return psProdName;
	}
	public void setPsProdName(String psProdName)
	{
		this.psProdName = psProdName;
	}
	public String getPsSuppName()
	{
		return psSuppName;
	}
	public void setPsSuppName(String psSuppName)
	{
		this.psSuppName = psSuppName;
	}
	// Products_Suppliers class definition
	public Products_Suppliers(int prodSuppID, int prodID, int suppID)
	{
		this.setProductSupplierID(prodSuppID);
		this.setProductID(prodID);
		this.setSupplierID(suppID);
	}
	// Products_Suppliers class definition
	public Products_Suppliers()
	{
		this.productSupplierID=0;
		this.productID=0;
		this.supplierID=0;
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
	public Products_Suppliers(String name, int psID, int suppID)
	{
		this.setPsSuppName(name);
		this.setProductSupplierID(psID);
		this.setProductID(suppID);
		
	}
	public Products_Suppliers(int psID, String name, int suppID)
	{
		this.setPsProdName(name);
		this.setProductSupplierID(psID);
		this.setProductID(suppID);
		
	}
	public String toString()
	{
		String strProdSupp= this.psProdName+"--"+this.psSuppName;
		return strProdSupp;
	}
	public String toString2()
	{
		
		return this.psSuppName;
	}

}



