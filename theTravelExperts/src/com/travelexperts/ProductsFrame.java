package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class ProductsFrame extends JInternalFrame {

	// Padding between elements
	private final int PADDING_X = 25;
	private final int PADDING_Y = 25;
	
	JList lstSuppliers = new JList();
	JList lstProducts = new JList();
	JList lstProductsSuppliers = new JList();
	
	JPanel editPanel = new JPanel(new GridLayout(2, 2, PADDING_X, PADDING_Y));
	JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING_X, PADDING_Y));
	JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING_X, PADDING_Y));
	
	JPanel pnlLeftButtons = new JPanel(new GridLayout(2, 1, PADDING_X, PADDING_Y));
	JPanel pnlRightButtons = new JPanel(new GridLayout(2, 1, PADDING_X, PADDING_Y));
	JButton btnMoveLeft = new JButton("<");
	JButton btnMoveRight = new JButton(">");
	JButton btnMoveAllLeft = new JButton("<<");
	JButton btnMoveAllRight = new JButton(">>");

	JTextField supplierName = new JTextField("test");
	
	JScrollPane scrProducts = new JScrollPane(lstProducts);
	JScrollPane scrSuppliers = new JScrollPane(lstSuppliers);
	JScrollPane scrProductsSupplers = new JScrollPane(lstProductsSuppliers);
	
	public ProductsFrame() {
		super("Products", true, true, true, true);
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		setMinimumSize(new Dimension(400, 300));
		
		Object listData2[] = { "product #1", "product #2", "product #3" };
		Object listData3[] = { "Supplier that is associated with product", "lstProductsupplier #2", "lstProductsupplier #3" };
		
		DefaultListModel supplierData = new DefaultListModel();
		supplierData.addElement("American Airlines");
		supplierData.addElement("Northwind Cruise Lines");
		supplierData.addElement("Westjet");
		supplierData.addElement("Budget Car Rentals");
		
		DefaultListModel productSupplierData = new DefaultListModel();
		

		lstProducts.setListData(listData2);	// fix this
		
		lstSuppliers.setModel(supplierData);
		lstProductsSuppliers.setModel(productSupplierData);
		
		lstSuppliers.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				supplierName.setText((String) lstSuppliers.getSelectedValue());				
			}
		});

		// Add components to Edit panel
		editPanel.add(new JLabel("Product ID"));
		editPanel.add(new JTextField("12345"));
		editPanel.add(new JLabel("Product Name"));
		editPanel.add(supplierName);
		
		// Add buttons to CRUD panel
		crudPanel.add(new JButton("New"));
		crudPanel.add(new JButton("Edit"));
		crudPanel.add(new JButton("Update"));
		crudPanel.add(new JButton("Delete"));
		
		// Buttons to transfer between lists
		pnlLeftButtons.add(btnMoveLeft);
		pnlRightButtons.add(btnMoveRight);

		// CODE FOR THE MOVE LEFT BUTTON
		btnMoveLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		// CODE FOR THE MOVE RIGHT BUTTON
		btnMoveRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(lstProductsSuppliers.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Please select a supplier.");
				}
				else {
					JOptionPane.showMessageDialog(null, "This will move the list item...");
				}
			}
		});
		
		// Use a flow panel to lay out these items horizontally
		flowPanel.add(scrProductsSupplers);
		flowPanel.add(pnlLeftButtons);
		flowPanel.add(pnlRightButtons);
		flowPanel.add(scrSuppliers);
		
		// Give lists a nice looking border
    	lstProducts.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "All Products"));
    	lstSuppliers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Available Suppiers"));
    	lstProductsSuppliers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Current Suppliers"));
    	
    	// Set list sizes through their respective scrollpanes
    	scrProducts.setSize(400, 200);
    	scrSuppliers.setSize(600, 600);
    	scrProductsSupplers.setSize(400, 200);
    	scrSuppliers.validate();

    	// Add all panels to frame
		add(editPanel, BorderLayout.NORTH);
    	add(scrProducts, BorderLayout.CENTER);
		add(flowPanel, BorderLayout.SOUTH);
				
		pack();
	}
}
