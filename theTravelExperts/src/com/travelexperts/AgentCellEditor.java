package com.travelexperts;

import java.awt.Component;
import java.util.Iterator;
import java.util.Set;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * 
 * @author WillDixon
 * 
 * Custom cell editor for AgentID in customers table
 *   
 */
public class AgentCellEditor
	extends AbstractCellEditor 
	implements TableCellEditor {
	
	// Contains the Agents to select from
	JComboBox cboAgents = new JComboBox();
	
	public AgentCellEditor() {
		
		// Build combo box from Agents hashmap 
		Set<Integer> set = AgentCellRenderer.hmAgents.keySet();
		Iterator<Integer> i = set.iterator();
		cboAgents.addItem("Please Select:");
		while(i.hasNext()) {
			cboAgents.addItem(AgentCellRenderer.hmAgents.get(i.next()));
		}
	}
	
	// Returns the AgentId based on the selected agent name from textbox
	@Override public Object getCellEditorValue() {
		
		String selectedAgentName = cboAgents.getSelectedItem().toString();
		
		if(AgentCellRenderer.hmAgents.containsValue(selectedAgentName)) {
			// Find agent id (key) from name in hash map
			// There must be a faster way to do this :(
			Set<Integer> set = AgentCellRenderer.hmAgents.keySet();
			Iterator<Integer> i = set.iterator();		
			while(i.hasNext()) {
				Integer agtIndex = i.next();
				// Does selected agtname in combobox have corresponding key (agentid) in hash map?
				if(AgentCellRenderer.hmAgents.get(agtIndex).equals(selectedAgentName)) {
					return agtIndex;
				}
			}
		}
		
		return 0;
	}

	@Override public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		
		return cboAgents;
	}
}
