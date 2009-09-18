package com.travelexperts;

//Jacque Pierre

public class Agents
{
	// Agents class definition
	private int agentID;
	private String agtFirstName;
	private String agtMiddleInitial;
	private String agtLastName;
	private String agtBusPhone;
	private String agtEmail;
	private String agtPosition;
	private int agencyID;
	
	// Agents class constructors
	public Agents()
	{
		super();
	}
	
	
	public Agents(int agtID, String agtFName, String agtMInitial, String agtLName, String agtBPhone, String agtEmail, String agtPos, int agID)
	{
		this.setAgentID(agtID);
		this.setAgtFirstName(agtFName);
		this.setAgtMiddleInitial(agtMInitial);
		this.setAgtLastName(agtLName);
		this.setAgtBusPhone(agtBPhone);
		this.setAgtEmail(agtEmail);
		this.setAgtPosition(agtPos);
		this.setAgencyID(agID);
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

	/**
	 * @param agtFName the agtFirstName to set
	 */
	public void setAgtFirstName(String agtFName)
	{
		this.agtFirstName = agtFName;
	}

	/**
	 * @return the agtFirstName
	 */
	public String getAgtFirstName()
	{
		return agtFirstName;
	}

	/**
	 * @param agtMInitial the agtMiddleInitial to set
	 */
	public void setAgtMiddleInitial(String agtMInitial)
	{
		this.agtMiddleInitial = agtMInitial;
	}

	/**
	 * @return the agtMiddleInitial
	 */
	public String getAgtMiddleInitial()
	{
		return agtMiddleInitial;
	}

	/**
	 * @param agtLName the agtLastName to set
	 */
	public void setAgtLastName(String agtLName)
	{
		this.agtLastName = agtLName;
	}

	/**
	 * @return the agtLastName
	 */
	public String getAgtLastName()
	{
		return agtLastName;
	}

	/**
	 * @param agtBPhone the agtBusPhone to set
	 */
	public void setAgtBusPhone(String agtBPhone)
	{
		this.agtBusPhone = agtBPhone;
	}

	/**
	 * @return the agtBusPhone
	 */
	public String getAgtBusPhone()
	{
		return agtBusPhone;
	}

	/**
	 * @param agtEmail the agtEmail to set
	 */
	public void setAgtEmail(String agtEmail)
	{
		this.agtEmail = agtEmail;
	}

	/**
	 * @return the agtEmail
	 */
	public String getAgtEmail()
	{
		return agtEmail;
	}

	/**
	 * @param agtPos the agtPosition to set
	 */
	public void setAgtPosition(String agtPos)
	{
		this.agtPosition = agtPos;
	}

	/**
	 * @return the agtPosition
	 */
	public String getAgtPosition()
	{
		return agtPosition;
	}

	/**
	 * @param agID the agencyID to set
	 */
	public void setAgencyID(int agID)
	{
		this.agencyID = agID;
	}

	/**
	 * @return the agencyID
	 */
	public int getAgencyID()
	{
		return agencyID;
	}
	
	public String toString()
	{
		return(this.getAgentID() + "\n" + this.getAgtFirstName() + " " + (this.getAgtMiddleInitial() == null ? "" : (this.getAgtMiddleInitial() + " ")) +
			   this.getAgtLastName() + "\n" + this.getAgtBusPhone() + "\n" + this.getAgtEmail() + "\n" + this.getAgtPosition() + "\n" + this.getAgentID()) + "\n";
	}
}
