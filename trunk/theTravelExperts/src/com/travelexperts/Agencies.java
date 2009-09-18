package com.travelexperts;

//Jacque Pierre

public class Agencies
{
	// Agencies class definition
	private int agencyID;
	private String agncyAddress;
	private String agncyCity;
	private String agncyProv;
	private String agncyPostal;
	private String agncyCountry;
	private String agncyPhone;
	private String agncyFax;

	// Agencies class constructor
	public Agencies(int agID, String agAddr, String agCity, String agProv, String agPost, String agCntr, String agPh, String agFax)
	{
		this.setAgencyID(agID);
		this.setAgncyAddress(agAddr);
		this.setAgncyCity(agCity);
		this.setAgncyProv(agProv);
		this.setAgncyPostal(agPost);
		this.setAgncyCountry(agCntr);
		this.setAgncyPostal(agPost);
		this.setAgncyFax(agFax);
	}

	/**
	 * @param agencyID the agencyID to set
	 */
	public void setAgencyID(int agencyID)
	{
		this.agencyID = agencyID;
	}

	/**
	 * @return the agencyID
	 */
	public int getAgencyID()
	{
		return agencyID;
	}

	/**
	 * @param agncyAddress the agncyAddress to set
	 */
	public void setAgncyAddress(String agncyAddress)
	{
		this.agncyAddress = agncyAddress;
	}

	/**
	 * @return the agncyAddress
	 */
	public String getAgncyAddress()
	{
		return agncyAddress;
	}

	/**
	 * @param agncyCity the agncyCity to set
	 */
	public void setAgncyCity(String agncyCity)
	{
		this.agncyCity = agncyCity;
	}

	/**
	 * @return the agncyCity
	 */
	public String getAgncyCity()
	{
		return agncyCity;
	}

	/**
	 * @param agncyProv the agncyProv to set
	 */
	public void setAgncyProv(String agncyProv)
	{
		this.agncyProv = agncyProv;
	}

	/**
	 * @return the agncyProv
	 */
	public String getAgncyProv()
	{
		return agncyProv;
	}

	/**
	 * @param agncyPostal the agncyPostal to set
	 */
	public void setAgncyPostal(String agncyPostal)
	{
		this.agncyPostal = agncyPostal;
	}

	/**
	 * @return the agncyPostal
	 */
	public String getAgncyPostal()
	{
		return agncyPostal;
	}

	/**
	 * @param agncyCountry the agncyCountry to set
	 */
	public void setAgncyCountry(String agncyCountry)
	{
		this.agncyCountry = agncyCountry;
	}

	/**
	 * @return the agncyCountry
	 */
	public String getAgncyCountry()
	{
		return agncyCountry;
	}

	/**
	 * @param agncyPhone the agncyPhone to set
	 */
	public void setAgncyPhone(String agncyPhone)
	{
		this.agncyPhone = agncyPhone;
	}

	/**
	 * @return the agncyPhone
	 */
	public String getAgncyPhone()
	{
		return agncyPhone;
	}

	/**
	 * @param agncyFax the agncyFax to set
	 */
	public void setAgncyFax(String agncyFax)
	{
		this.agncyFax = agncyFax;
	}

	/**
	 * @return the agncyFax
	 */
	public String getAgncyFax()
	{
		return agncyFax;
	}
}
