package com.travelexperts;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * 
 * @author WillDixon
 * 
 * Custom cell editor for AgentId for Customers form 
 *
 */
public class AgentCellEditor
	extends AbstractCellEditor 
	implements TableCellEditor, ActionListener {
	
	protected static final String ACTION_COMMAND = "SELECT_AGENT"; 
	
	// Use int wrapper for handy methods
	private Integer selectedAgentId = new Integer(0);

	JButton btnCellEditor;
	
	// Contains the Agents to select from
	JComboBox cboAgents = new JComboBox();
	
	public AgentCellEditor() {
		btnCellEditor = new JButton("Editing");
		btnCellEditor.addActionListener(this);
		
		cboAgents.setActionCommand(ACTION_COMMAND);
		
		// Build combo box from Agents hashmap 
		Set<Integer> set = AgentCellRenderer.hmAgents.keySet();
		Iterator<E> i = set.iterator();
		
		while(i.hasNext()) {
			cboAgents.addItem()
		}
		
	}
	
	// 
	@Override public Object getCellEditorValue() {
		// Use array from this class, should be refactored to its own class
		
		return selectedAgentId;
	}

	//
	@Override public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		
		JOptionPane.showMessageDialog(null, "getTableCellEditorComponent");
		
		
		return cboAgents;
	}

	// Handles cell editing and combo box selections
	@Override public void actionPerformed(ActionEvent arg0) {
	
	}
}
