package com.travelexperts;

import java.awt.Component;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author Will Dixon

 *  TODO: Refactor HashMap out of AgentCellRenderer into it's own class.
 *
 *  Custom cell renderer for AgentID in the Customers table
 *  
 */
public class AgentCellRenderer 
	extends JLabel 
	implements TableCellRenderer {

	protected static final String TEXT_AGENT_INACTIVE = // Blue
		"<html><font color='#0000FF'><em>Agent Inactive</em></font></html>";
	
	protected static final String TEXT_AGENT_UNASSIGNED = // Red
	"<html><font color='#FF0000'><strong>Unassigned</strong></font></html>";
	
	private static final String QUERY_ACTIVE_AGENTS =
		"SELECT AgentId, AgtLastName, AgtMiddleInitial, AgtFirstName " +
		"FROM Agents " +
		"WHERE {fn LCASE(AgtPosition)} <> 'inactive'";
	
	// HashMap containing AgentId => AgentName
	public final static HashMap<Integer, String> hmAgents = new HashMap<Integer, String>();
	
	static {
		refreshAgents();
	}
	
	public AgentCellRenderer() {
		// JLabel font looks too heavy in the Jtable, so change font		
		this.setFont(new Font("Helvetica", 0, 12));		
	}
	
	// Load agentId => AgtName into HashMap
	// Fire this after Agent table is modified 
	static public void refreshAgents() {
		try {
			ResultSet rs = TXConnection.getConnection().createStatement()
				.executeQuery(QUERY_ACTIVE_AGENTS);
			hmAgents.clear();
			while(rs.next()) {
				hmAgents.put(rs.getInt("AgentId"), 
						rs.getString("AgtLastName") + ", " + rs.getString("AgtFirstName"));
			}
		} catch (SQLException e) {
			TXLogger.logError(e.getMessage());
		}
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		// Handle null AgentIds
		if(value == null) {
			this.setText(TEXT_AGENT_UNASSIGNED);
			return this;
		}
		
		try {
			Integer agentId = new Integer(value.toString());
			String agentName = hmAgents.get(agentId);
			if(agentName != null)
				// Set to Agent Name
				this.setText(agentName);
			else	
				// Handle inactive/invalid agents
				this.setText(TEXT_AGENT_INACTIVE);
		}
		catch(NumberFormatException nfe) {
			// Will be hard to not see this exception lolz
			this.setText(nfe.getMessage());
		}
				
		return this;		// Return Jlabel with Agent name as text
	}
}
