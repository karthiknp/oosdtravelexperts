package com.travelexperts;

//Jacque Pierre

public class Packages_Products_Suppliers
{
	//Packages_Products_Suppliers class definition
	private int packageID;
	private int productSupplierID;

	// Packages_Products_Suppliers class constructor
	public Packages_Products_Suppliers(int pkgID, int prodSuppID)
	{
		this.setPackageID(pkgID);
		this.setProductSupplierID(prodSuppID);
	}

	/**
	 * @param pkgID the packageID to set
	 */
	public void setPackageID(int pkgID)
	{
		this.packageID = pkgID;
	}

	/**
	 * @return the packageID
	 */
	public int getPackageID()
	{
		return packageID;
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
}
