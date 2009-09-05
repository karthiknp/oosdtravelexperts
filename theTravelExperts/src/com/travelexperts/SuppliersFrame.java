package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


@SuppressWarnings("serial")
public class SuppliersFrame extends JInternalFrame {
	 
	// Padding between elements
	private final int PADDING_X = 25;
	private final int PADDING_Y = 25;
	
	JList suppliers = new JList();
	JList products = new JList();
	JList products_suppliers = new JList();
	JPanel editPanel = new JPanel(new GridLayout(2, 2, PADDING_X, PADDING_Y));
	JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING_X, PADDING_Y));
	JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING_X, PADDING_Y));
	
	JPanel pnlLeftButtons = new JPanel(new GridLayout(2, 1, PADDING_X, PADDING_Y));
	JPanel pnlRightButtons = new JPanel(new GridLayout(2, 1, PADDING_X, PADDING_Y));
	JButton btnMoveLeft = new JButton("<");
	JButton btnMoveRight = new JButton(">");
	JButton btnMoveAllLeft = new JButton("<<");
	JButton btnMoveAllRight = new JButton(">>");

	JTextField supplierName = new JTextField("test supplier");
	
	JScrollPane scrProducts = new JScrollPane(products);
	JScrollPane scrSuppliers = new JScrollPane(suppliers);
	JScrollPane scrProductsSupplers = new JScrollPane(products_suppliers);
	
	public SuppliersFrame() {
		super("Suppliers", true, true, true, true);

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		setMinimumSize(new Dimension(400, 300));
		
		Object listData[] = { "Supplier that is not associated", "supplier #2", "supplier #3" };
		Object listData2[] = { "product #1", "product #2", "product #3" };
		Object listData3[] = { "Supplier that is associated with product", "productsupplier #2", "productsupplier #3" };
		
		suppliers.setListData(listData3);
		products.setListData(listData2);
		products_suppliers.setListData(listData);
		
		suppliers.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				supplierName.setText((String) suppliers.getSelectedValue());				
			}
		});

		// Add components to Edit panel
		editPanel.add(new JLabel("Supplier ID"));
		editPanel.add(new JTextField("432231"));
		editPanel.add(new JLabel("Supplier Name"));
		editPanel.add(supplierName);
		
		// Add buttons to CRUD panel
		crudPanel.add(new JButton("New"));
		crudPanel.add(new JButton("Edit"));
		crudPanel.add(new JButton("Update"));
		crudPanel.add(new JButton("Delete"));
		
		// Buttons to transfer between lists
		pnlLeftButtons.add(btnMoveLeft);
		//pnlLeftButtons.add(btnMoveAllLeft);
		pnlRightButtons.add(btnMoveRight);
		//pnlRightButtons.add(btnMoveAllRight);

		// Use a flow panel to lay out these items horizontally
		flowPanel.add(scrProducts);
		flowPanel.add(pnlLeftButtons);
		flowPanel.add(pnlRightButtons);
		flowPanel.add(scrProductsSupplers);
		
		// Give lists a nice looking border
    	suppliers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "All Suppiers"));
    	products.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Available Products"));
    	products_suppliers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Current Products"));
    	
    	// Set list sizes through their respective scrollpanes
    	scrProducts.setSize(400, 200);
    	scrSuppliers.setSize(600, 600);
    	scrProductsSupplers.setSize(400, 200);

    	// Add all panels to frame
		add(editPanel, BorderLayout.NORTH);
    	add(scrSuppliers, BorderLayout.CENTER);
		add(flowPanel, BorderLayout.SOUTH);
				
		pack();
	}
}
