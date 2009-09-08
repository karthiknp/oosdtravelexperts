package com.travelexperts;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class AgentsFrame extends JInternalFrame {

	Object data[][] = {
			{ 1, "Ingrid", "Calgary NW", "(403)123-4567", "ingrid@gmail.com", true },
			{ 1, "Jacque", "Calgary NW", "(403)123-4567", "ingrid@gmail.com", true },
			{ 1, "Michelle", "Calgary NW", "(403)123-4567", "ingrid@gmail.com", false },
			{ 1, "Will", "Calgary NW", "(403)123-4567", "ingrid@gmail.com", false },
	};
	
	JTable agentsTable = new JTable(data, new String[] {"Agent ID", "Name", "Agency", "Phone", "Email", "Active" } );	
	JList lstCustomers = new JList(new Object[] { "Bob Smith", "Fred Anchorman", "Mary-Jane Watson", "George Stroumbolopolis" } );

	JPanel customerPanel = new JPanel(new BorderLayout());
	
	public AgentsFrame() {
		super("Agent Management", true, true, true, true);
		
		add(new JScrollPane(agentsTable));
		
		JCheckBox active = new JCheckBox();
		
		agentsTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(active));
					
    	lstCustomers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Clients"));
    	lstCustomers.setSize(300, 200);
    	
    	customerPanel.add(new JScrollPane(lstCustomers), BorderLayout.CENTER);
    	customerPanel.add(new JButton("Unassign"), BorderLayout.SOUTH);
		
		add(new JLabel("Viewing All Agents"), BorderLayout.NORTH);
		add(new JScrollPane(agentsTable), BorderLayout.CENTER);
		add(customerPanel, BorderLayout.EAST);
		
		pack();
	}
}
