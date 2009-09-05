package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

@SuppressWarnings("serial")
public class PackagesFrame extends JInternalFrame {

	// Padding between elements
	private final int PADDING_X = 25;
	private final int PADDING_Y = 25;

	// List that will show the product/suppliers in a package (connect to database later) 
	JList lstItems = new JList(new Object[] { "First Class Flight", "Car Rental", "Scuba Diving Adventure" } );
	
	JPanel headerPanel = new JPanel(new BorderLayout(PADDING_X, PADDING_Y));

	// The main table that will list all the packages
	JTable packagesTable;
	
	// CRUD stuff
	JPanel pnlCrudPanel = new JPanel(new FlowLayout());
	
	JButton btnNew = new JButton("New");
	JButton btnEdit = new JButton("Edit");
	JButton btnSave = new JButton("Save");
	JButton btnDelete = new JButton("Delete");
	
	// Package labels/ textfields
	// Might not be necessary if the table editor contains CRUD methods
	JPanel pnlPackageInfo = new JPanel(new GridLayout(6, 2, 0, 0));

	JLabel lblPackageName = new JLabel("Package Name");
	JLabel lblDescription = new JLabel("Description");
	JLabel lblStartDate= new JLabel("Start Date");
	JLabel lblEndDate = new JLabel("End Date");
	JLabel lblBasePrice = new JLabel("Base Price");
	JLabel lblCommission = new JLabel("Commission");
	
	JTextField txtPackageName = new JTextField(20);
	JTextField txtDescription = new JTextField(20);
	JTextField txtBasePrice = new JTextField(20);
	JTextField txtCommission = new JTextField(20);
	JTextField txtStartDate = new JTextField(20);
	JTextField txtEndDate = new JTextField(20);

	JButton btnStartDate = new JButton("12/12/1999");
	JButton btnEndDate = new JButton("12/12/1999");
	
	
	// Date picker objects
	JCalendar calStartDate = new JCalendar();
	JCalendar calEndDate = new JCalendar();

	// Build the form
	public PackagesFrame() {
		super("Packages", true, true, true, true);
		// Hide the frame when closed, don't destroy it as it is only created once
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		setLayout(new BorderLayout(50,50));
		
		// Dummy data (until database is connected)
    	String columnNames[] = { "Package", "Description", "Avail. Date", "Expire Date", "Price" };
    	Object tableData[][] = { 
    			{ "Package1", "This is package 1", new GregorianCalendar(), new GregorianCalendar(), 299 },
    			{ "Package2", "This is another pkg", new GregorianCalendar(), new GregorianCalendar(), 699 },
    			{ "Package3", "Yet another", new GregorianCalendar(), new GregorianCalendar(), 1299 },
    			{ "Package4", "One more here", new GregorianCalendar(), new GregorianCalendar(), 1699 }
    	};

    	// Load dummy data into main table
    	packagesTable = new JTable(tableData, columnNames );

    	// Test table stuff 
    	JComboBox datePicker = new JComboBox();
    	datePicker.addItem("Today");
    	datePicker.addItem("1 week ago");
    	datePicker.addItem("1 month ago");

    	packagesTable.setIntercellSpacing(new Dimension(5, 5));
    	packagesTable.setRowHeight(40);
    	
    	packagesTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(datePicker));
    	packagesTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(datePicker));
    	
    	// Add  components to package editing panel
    	pnlPackageInfo.add(lblPackageName);
    	pnlPackageInfo.add(txtPackageName);
    	pnlPackageInfo.add(lblDescription);
    	pnlPackageInfo.add(txtDescription);

    	// Date stuff
    	pnlPackageInfo.add(lblStartDate);
    	pnlPackageInfo.add(btnStartDate);
    	btnStartDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, calStartDate);
				btnStartDate.setText(calEndDate.getDate().toLocaleString());
			}
		});
    	pnlPackageInfo.add(lblEndDate);
    	btnEndDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showConfirmDialog(null, calEndDate);
				btnEndDate.setText(calEndDate.getDate().toLocaleString());
			}
		});
    	pnlPackageInfo.add(btnEndDate);
    	
    	pnlPackageInfo.add(lblBasePrice);
    	pnlPackageInfo.add(txtBasePrice);
    	pnlPackageInfo.add(lblCommission);
    	pnlPackageInfo.add(txtCommission);

    	// Add CRUD buttons
    	pnlCrudPanel.add(btnNew);
    	pnlCrudPanel.add(btnEdit);
    	pnlCrudPanel.add(btnSave);
    	pnlCrudPanel.add(btnDelete);
    	
    	// Add all panels to form
    	add(new JLabel("Viewing Packages"), BorderLayout.NORTH);
    	add(lstItems, BorderLayout.EAST);
    	add(pnlCrudPanel ,BorderLayout.SOUTH);
    	add(new JScrollPane(pnlPackageInfo), BorderLayout.WEST);
    	add(packagesTable, BorderLayout.CENTER);
    	
    	// Draw fancy border for the list
    	lstItems.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Contents"));

    	validate();
    	pack();
		
	}

}
