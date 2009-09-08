package com.travelexperts;

public class Messages
{
	private static final long serialVersionUID = 1L;
	public static final String VALUE_REQUIRED	 	= "Value required.";
	public static final String  MAXLENGTH_EXCEEDED 	= "Maxmin length exceeded.";
 	public static final String MAXVALUE_EXCEEDED 	= "Maxmin value exceeded.";
 	public static final String NUM_REQUIRED 		= "Numeric value required.";
 	public static final String POSTALCD_REQUIRED 	= "Postal format required. Example: a1b 2c3 or A1B 2C3.";
 	public static final String PHONENUM_REQUIRED 	= "Phone number format required. Example: 403-123-4567 or 4031234567.";
	public static final String  USER_PW_REQUIRED 	= "You must enter a valid UserId and password to enter the system.";
	public static final String  SELECT_ITEM_TOMOVE 	= "Please select an item to move";
	public static final String  INVALID_USER_PW 	= "You have entered an invalid username and password. Please contact you system administrator.";
	
 	public static final String DB_EXCEPTION 		= "Database error occurred. Please contact you system administrator.";
 	public static final String UNKNOWN_EXCEPTION 	= "Exception occurred. Please contact you system administrator.";
	
 
 	// validation messages
 	public static String getValidateMsg_A_CANT_GREATERTHAN_B(String strA,String strB)
 	{
 		return strA + " can't greater than " + strB + ". Please try again.";
 	}
 	public static String getValidateMsg_Delete(String itemName)
 	{
 		return "Please select a(an) " + itemName + " before tring to delete.";
 	}
 	public static String getValidateMsg_Update(String itemName)
 	{
 		return "Please select a(an) " + itemName + " before tring to update.";
 	}
 	public static String getValidateMsg_DuplicateInList(String itemName)
 	{
 		return "This "+ itemName + " has already been added to the " + itemName + " list.";
 	}
 	public static String getValidateMsg_SelectToAdd(String source, String dest)
 	{
 		return "Please select a " + source + " to which the " + dest +" will be added.";
 	}	
 	
 	// confirm messages 
 	public static String getConfirmMsg_Delete(String itemName)
 	{
 		return "Deleting " + itemName + " from database. Is this really what you want to do?";
 	}
 	public static String getConfirmMsg_Update(String itemName)
 	{
 		return "Updating " + itemName + " to database. Is this really what you want to do?";
 	}
 	public static String getConfirmMsg_Insert(String itemName)
 	{
 		return "Inserting " + itemName + " to database. Is this really what you want to do?";
 	}
 	
 	// worning messages
 	public static String getWornMsg_NoRecord(String itemName)
 	{
 		return "No availiable " + itemName + " in database.";
 	}
 	
}
