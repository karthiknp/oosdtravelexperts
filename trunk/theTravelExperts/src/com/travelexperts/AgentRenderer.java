package com.travelexperts;

import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author Will Dixon
 *
 *  Custom cell renderer for AgentID in the Customers table
 *  
 */
public class AgentRenderer extends JLabel implements TableCellRenderer{
	
	private final static String QUERY_AGENTS =
		"SELECT AgentId, AgtLastName, AgtFirstName FROM Agents";
	
	// HashMap containing AgentId => AgentName
	private final static HashMap<Integer, String> hmAgents = new HashMap<Integer, String>();
	
	static {
		refreshAgents();
	}
	
	// Load agentId => AgtName into HashMap
	// Fire this after Agent table is modified 
	static public void refreshAgents() {
		try {
			ResultSet rs = TXConnection.getConnection().createStatement().executeQuery(QUERY_AGENTS);
			while(rs.next()) {
				hmAgents.put(rs.getInt("AgentId"), 
						rs.getString("AgtLastName") + ", " + rs.getString("AgtFirstName"));
				System.out.println("Loaded " + hmAgents.get(rs.getInt("AgentId")));
			}
		} catch (SQLException e) {
			TXLogger.logError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// Handle null values
		if(value == null) {
			this.setText("Unassigned");
			return this;
		}
		
		try {
			Integer agentId = new Integer(value.toString());
			String agentName = hmAgents.get(agentId);
			if(agentName != null)
				this.setText(agentName);
			else
				this.setText("Agent ID: " + agentId + " not found");
		}
		catch(NumberFormatException nfe) {
			this.setText(nfe.getMessage());
		}
		
		return this;		// Return Jlabel with Agent name as text
	}
}
