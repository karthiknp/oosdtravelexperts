package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class ProductsFrame extends JInternalFrame {

	// Padding between elements
	private final int PADDING_X = 25;
	private final int PADDING_Y = 25;
	//DefaultListModel ProductData = new DefaultListModel();
	
		
	JList lstSuppliers = new JList();
	JList lstProducts = new JList();
	JList lstProductsSuppliers = new JList();
	
	
	
	JPanel editPanel = new JPanel(new GridLayout(2, 2, PADDING_X, PADDING_Y));
	JPanel crudPanel = new JPanel(new GridLayout(1, 4, PADDING_X, PADDING_Y));
	//JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING_X, PADDING_Y));
	JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING_X, PADDING_Y));
	
	JPanel pnlLeftButtons = new JPanel(new GridLayout(2, 1, PADDING_X, PADDING_Y));
	JPanel pnlRightButtons = new JPanel(new GridLayout(2, 1, PADDING_X, PADDING_Y));
	JButton btnMoveLeft = new JButton("<");
	JButton btnMoveRight = new JButton(">");
	JButton btnMoveAllLeft = new JButton("<<");
	JButton btnMoveAllRight = new JButton(">>");
	JTextField textID = new JTextField("");
	JTextField supplierName = new JTextField("");
	JButton btnNew = new JButton("New");
	JButton btnUpdate = new JButton("Update");
	JButton btnClear = new JButton("Clear");
	JButton btnDelete = new JButton("Delete");
	
	JScrollPane scrProducts = new JScrollPane(lstProducts);
	JScrollPane scrSuppliers = new JScrollPane(lstSuppliers);
	JScrollPane scrProductsSupplers = new JScrollPane(lstProductsSuppliers);
	
	DefaultListModel ProductData = new DefaultListModel();
	DefaultListModel productSupplierData = new DefaultListModel();
	DefaultListModel supplierData = new DefaultListModel();	
	Products p1 = new Products();
	
	public ProductsFrame() {
		super("Products", true, true, true, true);
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		setMinimumSize(new Dimension(400, 300));
		
		
		//method to refresh all products and all suppliers
		lstProducts.setModel(ProductData);
		
		lstSuppliers.setModel(supplierData);
		lstProductsSuppliers.setModel(productSupplierData);
		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent evt) {
				System.out.println("this.componentShown, event="+evt);
				//TODO add your code for this.componentShown
				refresh();
			}
		});
				
		
		
		//action to populate suppliers associated with selected product
		
		lstProducts.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				
				productSupplierData.removeAllElements();
				refreshSupplier();
				
				textID.setDisabledTextColor(Color.DARK_GRAY);
				supplierName.setText(((Products)lstProducts.getSelectedValue()).getProdName());
				textID.setText(((Products)lstProducts.getSelectedValue()).getProductID()+"");
				Connection txdb = new TXConnection().getInstance();
				Statement stmt1;
				try {
					stmt1 = txdb.createStatement();
					String sql = "SELECT supName, productsupplierid, suppliers.supplierid FROM Products_Suppliers join Suppliers on Products_Suppliers.supplierID = Suppliers.supplierID where Products_Suppliers.productId = '"+ textID.getText()+ "' order by supName";
					
				    
				      ResultSet rs = stmt1.executeQuery(sql);
				      while (rs.next())
				      {
				    	  
				    	  productSupplierData.addElement(new Suppliers(rs.getInt(3),rs.getString(1), rs.getInt(2)));

				      }
				     
				      txdb.close();
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				for (int i =0;i<productSupplierData.getSize();i++ )
				{
					
					for (int j=0;j<supplierData.getSize();j++)
					{
						lstSuppliers.setSelectedIndex(j);
						String psTemp = productSupplierData.elementAt(i).toString();
						String tempName = ((Suppliers)lstSuppliers.getSelectedValue()).getSupName();
						if (tempName.contains(psTemp))
						{
						//if(supplierName.setText(((Products)lstProducts.getSelectedValue()).getProdName());)
						supplierData.removeElementAt(j);
						}
					}
				}
				 
			
			}
		});

		// Add components to Edit panel
		editPanel.add(new JLabel("Product ID"));
		editPanel.add(textID);
		textID.setEnabled(false);
		editPanel.add(new JLabel("Product Name"));
		editPanel.add(supplierName);
		editPanel.add(btnNew);
		editPanel.add(btnUpdate);
		editPanel.add(btnClear);
		editPanel.add(btnDelete);
		btnUpdate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				
				ProductData.removeAllElements();
				
				Connection txdb = new TXConnection().getInstance();
				try {
					
					Statement stmt1 = txdb.createStatement();
					
					
					   String sql = "Update Products set prodName = '"
						   +supplierName.getText().toString() +"' where (productID = '" + textID.getText()+"')";					   
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
				}
				clear();
				refresh();
				
				System.out.println(lstProducts.getSelectionMode());
				
				
				
			}});
		
		btnDelete.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				for (int i =0;i<productSupplierData.getSize();i++ )
				{
					
					int psId = ((Suppliers)productSupplierData.get(i)).getProductsupplierId();
					removeAllPS(psId);
				}
				//ProductData.removeAllElements();
				
				Connection txdb = new TXConnection().getInstance();
				try {
					
					Statement stmt1 = txdb.createStatement();
					String sql = "Delete from Products where (productID = '" + textID.getText()+"')";					   
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
				}
				clear();
				refresh();
				
				System.out.println(lstProducts.getSelectionMode());
				}});
		
		btnNew.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				Connection txdb = new TXConnection().getInstance();
				try {
					
					Statement stmt1 = txdb.createStatement();
					
					 String sql = "Insert Into Products (PRODUCTID,PRODNAME) Values(S_110_1_PRODUCTS.nextval,'"
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
				refresh();
			}});
		
		btnClear.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				clear();
			}});		
				
		// Add buttons to CRUD panel
		//crudPanel.add(new JButton("New"));
		//crudPanel.add(new JButton("Edit"));
		//crudPanel.add(new JButton("Update"));
		//crudPanel.add(new JButton("Delete"));
		
		// Buttons to transfer between lists
		pnlLeftButtons.add(btnMoveLeft);
		pnlRightButtons.add(btnMoveRight);

		// CODE FOR THE MOVE LEFT BUTTON
		// add item to product suppliers
		btnMoveLeft.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				Connection txdb = new TXConnection().getInstance();
				try {
					
					Statement stmt1 = txdb.createStatement();
					
					 String sql = "Insert Into products_suppliers (PRODUCTSUPPLIERID,PRODUCTID,SUPPLIERID) Values(S_109_1_PRODUCTS_SUPPLIERS.nextval,'"
						 + textID.getText()
						 + "','"
						 + ((Suppliers)lstSuppliers.getSelectedValue()).getSupplierID()
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
				
				refreshSupplier();
				reloadPSlist();
				//refresh();
			}});		
		// delete from productsuppliers
		btnMoveRight.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
			int psId = ((Suppliers)lstProductsSuppliers.getSelectedValue()).getProductsupplierId();
				removeAllPS(psId);
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
			    	  //p1.setProdName(rs.getString(2));
			    	  //p1.setProductID(rs.getInt(1));
			    	  //ProductData.addElement(p1);
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
	public void refreshSupplier()
	{
		supplierData.removeAllElements();
		
		Connection txdb = new TXConnection().getInstance();
		
		
		
		try {
			
			Statement stmt1 = txdb.createStatement();
			 //String sql = "SELECT * FROM Products order by prodName";
			 String sql2 = "SELECT * FROM Suppliers order by supName";
			    
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
	public void clear()
	{
				
		textID.setText("");
		supplierName.setText("");

		productSupplierData.removeAllElements();
		//ProductData.removeAllElements();
		//lstProducts.removeAll();
		//supplierData.removeAllElements();
		System.out.println("I'm made it to the clear method");
		
	}
	public void reloadPSlist()
	{
		productSupplierData.removeAllElements();
		textID.setDisabledTextColor(Color.DARK_GRAY);
		supplierName.setText(((Products)lstProducts.getSelectedValue()).getProdName());
		textID.setText(((Products)lstProducts.getSelectedValue()).getProductID()+"");
		Connection txdb = new TXConnection().getInstance();
		Statement stmt1;
		try {
			stmt1 = txdb.createStatement();
			String sql = "SELECT supName FROM Products_Suppliers join Suppliers on Products_Suppliers.supplierID = Suppliers.supplierID where Products_Suppliers.productId = '"+ textID.getText()+ "' order by supName";
			 
		    
		      ResultSet rs = stmt1.executeQuery(sql);
		      while (rs.next())
		      {

		    	  productSupplierData.addElement(rs.getString(1));

		      }
		     
		      txdb.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i =0;i<productSupplierData.getSize();i++ )
		{
			//System.out.println(productSupplierData.elementAt(i));
			for (int j=0;j<supplierData.getSize();j++)
			{
				lstSuppliers.setSelectedIndex(j);
				String psTemp = productSupplierData.elementAt(i).toString();
				String tempName = ((Suppliers)lstSuppliers.getSelectedValue()).getSupName();
				if (tempName.contains(psTemp))
				{
				//if(supplierName.setText(((Products)lstProducts.getSelectedValue()).getProdName());)
				supplierData.removeElementAt(j);
				}
			}
		}
	}
	public void removeAllPS(int psId)
	{
		Connection txdb = new TXConnection().getInstance();
		try {
			
			Statement stmt1 = txdb.createStatement();
			//String sql = "Delete from products_suppliers where (PRODUCTSUPPLIERID = '" + ((Suppliers)lstProductsSuppliers.getSelectedValue()).getProductsupplierId()+"')";	
			String sql = "Delete from products_suppliers where (PRODUCTSUPPLIERID = '" + psId +"')";
																					
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
		}
		
		
		supplierData.addElement((Suppliers)lstProductsSuppliers.getSelectedValue());
		productSupplierData.removeElementAt(lstProductsSuppliers.getSelectedIndex());
	}
}
