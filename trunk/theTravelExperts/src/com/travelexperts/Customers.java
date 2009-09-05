package com.travelexperts;

public class Customers {

	// Customers class definition
	private int customerID;
	private String custFirstName;
	private String custLastName;
	private String custAddress;
	private String custCity;
	private String custProv;
	private String custPostal;
	private String custCountry;
	private String custHomePhone;
	private String custBusPhone;
	private String custEmail;
	private int agentID;
	
	// Customers class constructor
	public Customers(int custID, String cFName, String cLName, String cAddr, String cCity,
			String cProv, String cCountry, String cHPhone, String cBPhone, String cEmail, int agtID)
	{
		this.setCustomerID(custID);
		this.setCustFirstName(cFName);
		this.setCustLastName(cLName);
		this.setCustAddress(cAddr);
		this.setCustCity(cCity);
		this.setCustProv(cProv);
		this.setCustCountry(cCountry);
		this.setCustHomePhone(cHPhone);
		this.setCustBusPhone(cBPhone);
		this.setCustEmail(cEmail);
		this.setAgentID(agtID);
	}

	/**
	 * @param custID the customerID to set
	 */
	public void setCustomerID(int custID)
	{
		this.customerID = custID;
	}

	/**
	 * @return the customerID
	 */
	public int getCustomerID()
	{
		return customerID;
	}

	/**
	 * @param cFName the custFirstName to set
	 */
	public void setCustFirstName(String cFName)
	{
		this.custFirstName = cFName;
	}

	/**
	 * @return the custFirstName
	 */
	public String getCustFirstName()
	{
		return custFirstName;
	}

	/**
	 * @param cLName the custLastName to set
	 */
	public void setCustLastName(String cLName)
	{
		this.custLastName = cLName;
	}

	/**
	 * @return the custLastName
	 */
	public String getCustLastName()
	{
		return custLastName;
	}

	/**
	 * @param cAddress the custAddress to set
	 */
	public void setCustAddress(String cAddress)
	{
		this.custAddress = cAddress;
	}

	/**
	 * @return the custAddress
	 */
	public String getCustAddress()
	{
		return custAddress;
	}

	/**
	 * @param cCity the custCity to set
	 */
	public void setCustCity(String cCity)
	{
		this.custCity = cCity;
	}

	/**
	 * @return the custCity
	 */
	public String getCustCity()
	{
		return custCity;
	}

	/**
	 * @param cProv the custProv to set
	 */
	public void setCustProv(String cProv)
	{
		this.custProv = cProv;
	}

	/**
	 * @return the custProv
	 */
	public String getCustProv()
	{
		return custProv;
	}

	/**
	 * @param cPostal the custPostal to set
	 */
	public void setCustPostal(String cPostal)
	{
		this.custPostal = cPostal;
	}

	/**
	 * @return the custPostal
	 */
	public String getCustPostal()
	{
		return custPostal;
	}

	/**
	 * @param cCountry the custCountry to set
	 */
	public void setCustCountry(String cCountry)
	{
		this.custCountry = cCountry;
	}

	/**
	 * @return the custCountry
	 */
	public String getCustCountry()
	{
		return custCountry;
	}

	/**
	 * @param cHPhone the custHomePhone to set
	 */
	public void setCustHomePhone(String cHPhone)
	{
		this.custHomePhone = cHPhone;
	}

	/**
	 * @return the custHomePhone
	 */
	public String getCustHomePhone()
	{
		return custHomePhone;
	}

	/**
	 * @param cBPhone the custBusPhone to set
	 */
	public void setCustBusPhone(String cBPhone)
	{
		this.custBusPhone = cBPhone;
	}

	/**
	 * @return the custBusPhone
	 */
	public String getCustBusPhone()
	{
		return custBusPhone;
	}

	/**
	 * @param cEmail the custEmail to set
	 */
	public void setCustEmail(String cEmail)
	{
		this.custEmail = cEmail;
	}

	/**
	 * @return the custEmail
	 */
	public String getCustEmail()
	{
		return custEmail;
	}

	/**
	 * @param agtID the agentID to set
	 */
	public void setAgentID(int agtID)
	{
		this.agentID = agtID;
	}

	/**
	 * @return the agentID
	 */
	public int getAgentID()
	{
		return agentID;
	}
	
	@Override
	public String toString() {
		return getCustLastName() + ", " + getCustFirstName();
	}
}
