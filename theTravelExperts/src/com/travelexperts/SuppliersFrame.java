package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


@SuppressWarnings("serial")
public class SuppliersFrame extends JInternalFrame {
	 
	// Padding between elements
	private final int PADDING_X = 25;
	private final int PADDING_Y = 25;
	
	JList lstsuppliers = new JList();
	JList lstproducts = new JList();
	JList lstproducts_suppliers = new JList();
	JPanel editPanel = new JPanel(new GridLayout(2, 2, PADDING_X, PADDING_Y));
	JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING_X, PADDING_Y));
	JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING_X, PADDING_Y));
	
	JPanel pnlLeftButtons = new JPanel(new GridLayout(2, 1, PADDING_X, PADDING_Y));
	JPanel pnlRightButtons = new JPanel(new GridLayout(2, 1, PADDING_X, PADDING_Y));
	JButton btnMoveLeft = new JButton("<");
	JButton btnMoveRight = new JButton(">");
	JButton btnMoveAllLeft = new JButton("<<");
	JButton btnMoveAllRight = new JButton(">>");
	//mh add
	JButton btnNew = new JButton("New");
	JButton btnUpdate = new JButton("Update");
	JButton btnClear = new JButton("Clear");
	JButton btnDelete = new JButton("Delete");
	//mh change
	JTextField supplierName = new JTextField("");
	JTextField textID = new JTextField("");
	
	
	JScrollPane scrProducts = new JScrollPane(lstproducts);
	JScrollPane scrSuppliers = new JScrollPane(lstsuppliers);
	JScrollPane scrProductsSupplers = new JScrollPane(lstproducts_suppliers);
	//mh added
	DefaultListModel ProductData = new DefaultListModel();
	DefaultListModel productSupplierData = new DefaultListModel();
	DefaultListModel supplierData = new DefaultListModel();	
	
	public SuppliersFrame() {
		super("Suppliers", true, true, true, true);

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		setMinimumSize(new Dimension(400, 300));
		
		//method to refresh all products and all suppliers
		lstproducts.setModel(ProductData);
		
		lstsuppliers.setModel(supplierData);
		lstproducts_suppliers.setModel(productSupplierData);
		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent evt) {
				System.out.println("this.componentShown, event="+evt);
				//TODO add your code for this.componentShown
				refresh();
			}
		});
	
//action to populate suppliers associated with selected product
		
		lstsuppliers.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				
				reloadPSlist();
			
			}
		});
		
		

		// Add components to Edit panel
		editPanel.add(new JLabel("Supplier ID"));
		editPanel.add(textID);
		textID.setEnabled(false);
		//editPanel.add(new JTextField("432231"));
		editPanel.add(new JLabel("Supplier Name"));
		editPanel.add(supplierName);
		//mh add
		editPanel.add(btnNew);
		editPanel.add(btnUpdate);
		editPanel.add(btnClear);
		editPanel.add(btnDelete);
		
		btnUpdate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				
	
				supplierData.removeAllElements();
				
				Connection txdb = new TXConnection().getInstance();
				try {
					
					Statement stmt1 = txdb.createStatement();
					
					
					   String sql = "Update Suppliers set supName = '"
						   +supplierName.getText().toString() +"' where (supplierID = '" + textID.getText()+"')";					   
						   int rs = stmt1.executeUpdate(sql);

				            // (5) Process the int.
							if (rs == 0)
							{
							   System.out.println("no rows updated");
							}
				            System.out.println();

				            // Cleanup
				            txdb.commit();
				            txdb.close();
					     
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null,"Update was unsuccessful");
				}
				clear();
				refresh();
	
			}});
		btnNew.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				/*Connection txdb = new TXConnection().getInstance();
				try {
					
					Statement stmt1 = txdb.createStatement();
					
					 String sql = "Insert Into Suppliers (SupplierID,supNAME) Values('" 
						 + (supplierData.getSize()+1)
						 + "','"
						 + supplierName.getText()
						 +"')";

					   
					      int rs = stmt1.executeUpdate(sql);

				            // (5) Process the int.
							if (rs == 0)
							{
							   System.out.println("no rows inserted");
							}
				            System.out.println();

				            // Cleanup
				            txdb.commit();
				            txdb.close();
					     
				} catch (SQLException e) {
					e.printStackTrace();
				}
				refresh();*/
				if (supplierName.getText().equals(""))
				{
					
					JOptionPane.showMessageDialog(null,Messages.VALUE_REQUIRED);
				}
				
				else 
				{
					for (int j=0;j<supplierData.getSize();j++)
					{
												
						String tempName = ((Suppliers)supplierData.elementAt(j)).getSupName();


							if (supplierName.getText().contains(tempName))
							{
								JOptionPane.showMessageDialog(null,"This supplier already exists");
								return;
							}
							
					}
							Connection txdb = new TXConnection().getInstance();
							try {
								
								Statement stmt1 = txdb.createStatement();
								//
								 String sql = "Insert Into Suppliers (SupplierID,supNAME) Values('" 
						 + (supplierData.getSize()+1)
						 + "','"
						 + supplierName.getText()
						 +"')";

								   
								      int rs = stmt1.executeUpdate(sql);
								      if (rs == 0)
								      {
								    	  System.out.println("Insert was unsuccessful");
								      }
							            txdb.commit();
							            txdb.close();
								     
							} catch (SQLException e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(null,"Insert was unsuccessful");
							}
							refresh();
							clear();
							}
						
					
			}});
		
		btnClear.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				clear();
			}});		
	
		// Buttons to transfer between lists
		pnlLeftButtons.add(btnMoveLeft);
		//pnlLeftButtons.add(btnMoveAllLeft);
		pnlRightButtons.add(btnMoveRight);
		//pnlRightButtons.add(btnMoveAllRight);

		// Use a flow panel to lay out these items horizontally
		flowPanel.add(scrProductsSupplers);
		flowPanel.add(pnlLeftButtons);
		flowPanel.add(pnlRightButtons);
		flowPanel.add(scrProducts);
		
		// Give lists a nice looking border
    	lstsuppliers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "All Suppiers"));
    	lstproducts.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Available Products"));
    	lstproducts_suppliers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Current Products"));
    	
    	// Set list sizes through their respective scrollpanes
    	scrProductsSupplers.setSize(400, 200);
    	scrProducts.setSize(400, 200);
    	scrSuppliers.setSize(600, 600);
    	

    	// Add all panels to frame
		add(editPanel, BorderLayout.NORTH);
    	add(scrSuppliers, BorderLayout.CENTER);
		add(flowPanel, BorderLayout.SOUTH);
				
		pack();
		
		
	}
	public void refresh()
	{
		System.out.println("I'm made it to the refresh method");
		ProductData.removeAllElements();
		supplierData.removeAllElements();
		
		Connection txdb = new TXConnection().getInstance();
		
		
		
		try {
			
			Statement stmt1 = txdb.createStatement();
			 String sql = "SELECT * FROM Products order by prodName";
			 String sql2 = "SELECT * FROM Suppliers order by supName";
			    
			      ResultSet rs = stmt1.executeQuery(sql);
			      while (rs.next())
			      {
			    	 
			    	  ProductData.addElement(new Products(rs.getInt(1), rs.getString(2)));

			      }
			      ResultSet rs2 = stmt1.executeQuery(sql2);
			      while (rs2.next())
			      {

			    	  supplierData.addElement(new Suppliers(rs2.getInt(1), rs2.getString(2)));

			      }
			      
			      txdb.close();
			     
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
	public void refreshProducts()
	{
		ProductData.removeAllElements();
		
		Connection txdb = new TXConnection().getInstance();

		try {
			
			Statement stmt1 = txdb.createStatement();
			
			 String sql2 = "SELECT * FROM Products order by prodName";
			    
			      ResultSet rs2 = stmt1.executeQuery(sql2);
			      while (rs2.next())
			      {

			    	  ProductData.addElement(new Products(rs2.getInt(1), rs2.getString(2)));

			      }
			      
			      txdb.close();
			     
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Select was unsuccessful");
		}
	}
	public void clear()
	{
				
		textID.setText("");
		supplierName.setText("");
		productSupplierData.removeAllElements();
		refreshProducts();
		
	}
	public void reloadPSlist()
	{
		
		productSupplierData.removeAllElements();
		refreshProducts();
		
		textID.setDisabledTextColor(Color.DARK_GRAY);
		supplierName.setText(((Suppliers)lstsuppliers.getSelectedValue()).getSupName());
		textID.setText(((Suppliers)lstsuppliers.getSelectedValue()).getSupplierID()+"");
		Connection txdb = new TXConnection().getInstance();
		Statement stmt1;
		try {
			stmt1 = txdb.createStatement();
			String sql = "SELECT prodName, productsupplierid, " +
					"products.productid FROM Products_Suppliers join " +
					"Products on Products_Suppliers.productID = Products.productID" +
					" where Products_Suppliers.supplierId = '"+ textID.getText()+ "' order by prodName";
			
		    
		      ResultSet rs = stmt1.executeQuery(sql);
		      while (rs.next())
		      {
		    	  System.out.println("I'm try to insert");
		    	  productSupplierData.addElement(new Products_Suppliers(rs.getInt(2),rs.getString(1),rs.getInt(3)) {public String toString() { return this.getPsProdName(); } });

		      }
		     
		      txdb.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
		
		assignedProd();
	}
	/**
	 * @param psId
	 */
	public void removeAllPS(int psId)
	{
		
		Connection txdb = new TXConnection().getInstance();
		try {
			
			Statement stmt1 = txdb.createStatement();
			
			String sql = "Delete from products_suppliers where (PRODUCTSUPPLIERID = '" + psId +"')";
			System.out.println(psId);																		
			int rs = stmt1.executeUpdate(sql);

		            // (5) Process the int.
					if (rs == 0)
					{
					   System.out.println("no rows updated");
					}
		            System.out.println();

		            // Cleanup
		            txdb.commit();
		            txdb.close();
			     
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Delete was unsuccessful");
		}
		refreshProducts();
		assignedProd();
		
		//supplierData.addElement((Products_Suppliers)lstProductsSuppliers.getSelectedValue());
		productSupplierData.removeElementAt(lstproducts_suppliers.getSelectedIndex());
	
	}
	public void assignedProd()
	{
		for (int i =0;i<productSupplierData.getSize();i++ )
		{
			
			
			String psTemp = ((Products_Suppliers)productSupplierData.elementAt(i)).getPsProdName();

			for (int j=0;j<ProductData.getSize();j++)
			{
										
				String tempName = ((Products)ProductData.elementAt(j)).getProdName();

				if (tempName != null)
				{

					if (psTemp.contains(tempName))
					{
						ProductData.removeElementAt(j);
						
					}
					
				}
				
			}
		}
		 
	}
	
}
