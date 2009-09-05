package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SQLFrame extends JInternalFrame {

	// Database stuff, refactor later
	Connection txdb = new TXConnection().getInstance();
	Statement sqlQuery;
	ResultSet sqlResults;
	
	JTable tblCustomers = new JTable();
	JTextField txtSQL = new JTextField("SELECT * FROM Customers");
	
	JLabel lblResults = new JLabel("Results: ");
	
	public SQLFrame() {
		super("Customer Management", true, true, true, true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		
		txtSQL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					sqlQuery = txdb.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
										
					sqlResults = sqlQuery.executeQuery(txtSQL.getText());

					/*
					while(sqlResults.next()) {
						lblResults.setText(lblResults.getText() + sqlResults.getString("custLastName"));
					}
					*/
					
					tblCustomers.setModel(new ResultSetTableModel(sqlResults));
					tblCustomers.revalidate();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(SQLFrame.this, e.getMessage());
					e.printStackTrace();
				}
			}
		});
		
		
		add(txtSQL, BorderLayout.NORTH);
		add(new JScrollPane(tblCustomers), BorderLayout.CENTER);
		add(lblResults, BorderLayout.SOUTH);
		
		pack();
		
	}

}
