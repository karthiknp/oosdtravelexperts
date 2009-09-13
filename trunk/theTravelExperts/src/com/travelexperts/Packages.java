package com.travelexperts;

import java.util.Calendar;

public class Packages
{
	// Packages class definition
	private int packageID;
	private String pkgName;
	private Calendar pkgStartDate;
	private Calendar pkgEndDate;
	private String pkgDesc;
	private float pkgBasePrice;
	private float pkgAgentComm;
	
	// Packages class constructor
	public Packages(int pkgID, String pkgName, Calendar pkgStartDate, Calendar pkgEndDate, String pkgDesc, float pkgBasePrice, float pkgAgentComm)
	{
		this.setPackageID(pkgID);
		this.setPkgName(pkgName);
		this.setPkgStartDate(pkgStartDate);
		this.setPkgEndDate(pkgEndDate);
		this.setPkgDesc(pkgDesc);
		this.setPkgBasePrice(pkgBasePrice);
		this.setPkgAgentComm(pkgAgentComm);
	}
	public Packages(int pkgID)
	{
		this.setPackageID(pkgID);
		this.setPkgName("");
		this.setPkgStartDate(null);
		this.setPkgEndDate(null);
		this.setPkgDesc("");
		this.setPkgBasePrice(0);
		this.setPkgAgentComm(0);
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
	 * @param pkgName the pkgName to set
	 */
	public void setPkgName(String pkgName)
	{
		this.pkgName = pkgName;
	}

	/**
	 * @return the pkgName
	 */
	public String getPkgName()
	{
		return pkgName;
	}

	/**
	 * @param pkgStartDate the pkgStartDate to set
	 */
	public void setPkgStartDate(Calendar pkgStartDate)
	{
		this.pkgStartDate = pkgStartDate;
	}

	/**
	 * @return the pkgStartDate
	 */
	public Calendar getPkgStartDate()
	{
		return pkgStartDate;
	}

	/**
	 * @param pkgEndDate the pkgEndDate to set
	 */
	public void setPkgEndDate(Calendar pkgEndDate)
	{
		this.pkgEndDate = pkgEndDate;
	}

	/**
	 * @return the pkgEndDate
	 */
	public Calendar getPkgEndDate()
	{
		return pkgEndDate;
	}

	/**
	 * @param pkgDesc the pkgDesc to set
	 */
	public void setPkgDesc(String pkgDesc)
	{
		this.pkgDesc = pkgDesc;
	}

	/**
	 * @return the pkgDesc
	 */
	public String getPkgDesc()
	{
		return pkgDesc;
	}

	/**
	 * @param pkgBasePrice the pkgBasePrice to set
	 */
	public void setPkgBasePrice(float pkgBasePrice)
	{
		this.pkgBasePrice = pkgBasePrice;
	}

	/**
	 * @return the pkgBasePrice
	 */
	public float getPkgBasePrice()
	{
		return pkgBasePrice;
	}

	/**
	 * @param pkgAgentComm the pkgAgentComm to set
	 */
	public void setPkgAgentComm(float pkgAgentComm)
	{
		this.pkgAgentComm = pkgAgentComm;
	}

	/**
	 * @return the pkgAgentComm
	 */
	public float getPkgAgentComm()
	{
		return pkgAgentComm;
	}
}
