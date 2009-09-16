package com.travelexperts;
/***********************************************************************

Gui by Will Dixon, Code by Michelle Hasna


************************************************************************/


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
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


@SuppressWarnings("serial")
public class SuppliersFrame extends JInternalFrame {
	 
	// Padding between elements
	private final int PADDING_X = 25;
	private final int PADDING_Y = 25;
	//DefaultListModel ProductData = new DefaultListModel();
	
		
	JList lstSuppliers = new JList();
	JList lstProducts = new JList();
	JList lstProductsSuppliers = new JList();
	
	
	JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING_X, PADDING_Y));
	JPanel editPanel = new JPanel(new GridLayout(2, 2, 15, PADDING_Y));
	//JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, PADDING_X, PADDING_Y));
	//JPanel crudPanel = new JPanel(new GridLayout(1, 4, PADDING_X, PADDING_Y));
	JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING_X, PADDING_Y));
	JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, PADDING_X, PADDING_Y));
	private JScrollPane jScrollPane3;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane1;
	private JLabel jLabel_IL;
	private JLabel jLabel_IL1;

	//JPanel pnlLeftButtons = new JPanel(new GridLayout(2, 1, PADDING_X, PADDING_Y));
	//JPanel pnlRightButtons = new JPanel(new GridLayout(2, 1, PADDING_X, PADDING_Y));
	JButton btnMoveLeft = new JButton("<");
	JButton btnMoveRight = new JButton(">");
	JTextField textID = new JTextField("");
	JTextField supplierName = new JTextField("");
	//supplierName.setBounds(383, 5, 183, 20);
	//supplierName.setName("supplierName");
	JButton btnNew = new JButton("New");
	JButton btnUpdate = new JButton("Update");
	JButton btnClear = new JButton("Clear");
	JButton btnDelete = new JButton("Delete");
		
	DefaultListModel ProductData = new DefaultListModel();
	DefaultListModel productSupplierData = new DefaultListModel();
	DefaultListModel supplierData = new DefaultListModel();	
	//Products p1 = new Products();
	
	public SuppliersFrame() {
		super("Suppliers", true, true, true, true);
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		setMinimumSize(new Dimension(750, 417));
		getContentPane().setLayout(null);
		this.setPreferredSize(new java.awt.Dimension(750, 417));
		this.setBounds(0, 0, 750, 417);
		this.setName("this");

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
		
		lstSuppliers.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				
				reloadPSlist();
			}
		});

		// Add components to Edit panel
		editPanel.add(textID);		//textID.setBounds(200, 200, 200, 200);
		textID.setEnabled(false);
		textID.setBounds(129, 5, 71, 20);
		supplierName.setBounds(383, 5, 183, 20);
		supplierName.setName("supplierName");
		//supplierName.setBounds(129,5,71,20);
		editPanel.add(supplierName);
		
		crudPanel.add(btnNew);
		//
	
		crudPanel.add(btnUpdate);
		crudPanel.add(btnClear);
		crudPanel.add(btnDelete);
		crudPanel.setBounds(57, 334, 587, 49);
		flowPanel.add(editPanel);
		//
		{
			jScrollPane2 = new JScrollPane();
			flowPanel.add(jScrollPane2);
			//scrProductsSupplers.setViewportView(jScrollPane2);
			jScrollPane2.setPreferredSize(new java.awt.Dimension(256, 128));
			jScrollPane2.setBounds(13, 25, 256, 128);
			jScrollPane2.setViewportView(lstProductsSuppliers);
		}
		flowPanel.add(btnMoveLeft);
		btnMoveLeft.setBounds(294, 78, 24, 21);
		flowPanel.add(btnMoveRight);
		btnMoveRight.setBounds(343, 78, 24, 21);
		//flowPanel.add(pnlLeftButtons);
		//flowPanel.add(pnlRightButtons);
		//
		
		
		// Give lists a nice looking border
    	lstProducts.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Available Products"));
    	lstSuppliers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "All Suppiers"));
    	lstProducts.setPreferredSize(new java.awt.Dimension(203, 128));
    	lstProductsSuppliers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Current Products"));
    	lstProductsSuppliers.setPreferredSize(new java.awt.Dimension(250, 128));

    	{
    		
    		jScrollPane3 = new JScrollPane();
    		//scrSuppliers.setViewportView(jScrollPane3);
    		jScrollPane3.setPreferredSize(new java.awt.Dimension(256, 128));
    		jScrollPane3.setViewportView(lstProducts);
    	}
    	//scrProductsSupplers.setSize(400, 200);
    	//scrSuppliers.validate();

    	// Add all panels to frame
		//add(editPanel, BorderLayout.NORTH);
   
    	getContentPane().add(topPanel, "North");
    	topPanel.setBounds(12, -14, 683, 135);
    	lstProducts.setBounds(67, 149, 573, 110);
    	{
    		jScrollPane1 = new JScrollPane();
    		topPanel.add(jScrollPane1);
    		flowPanel.add(jScrollPane3);
    		jScrollPane3.setBounds(392, 25, 256, 128);
    		jScrollPane1.setPreferredSize(new java.awt.Dimension(576, 113));
    		jScrollPane1.getHorizontalScrollBar().setOrientation(SwingConstants.VERTICAL);
    		jScrollPane1.setViewportView(lstSuppliers);
    	}
    	editPanel.setLayout(null);
    	//add(scrProductsSupplers, BorderLayout.WEST);
    	//add(scrSuppliers, BorderLayout.EAST);
		getContentPane().add(flowPanel, "East");
		flowPanel.setBounds(40, 177, 661, 158);
		flowPanel.setLayout(null);
		getContentPane().add(crudPanel, "South");
		getContentPane().add(editPanel, "West");
		editPanel.setBounds(57, 133, 623, 34);
		{
			jLabel_IL = new JLabel("Supplier ID");
			editPanel.add(jLabel_IL);
			jLabel_IL.setBounds(23, 4, 88, 23);
			jLabel_IL.setName("jLabel_IL");
		}
		{
			jLabel_IL1 = new JLabel("Supplier Name");
			editPanel.add(jLabel_IL1);
			jLabel_IL1.setBounds(258, 4, 107, 22);
			jLabel_IL1.setName("jLabel_IL1");
		}
		
		pack();
		
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
				            
				            //Application.getInstance().getContext().getResourceMap(getClass()).injectComponents(this);
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null,"Update was unsuccessful");
				}
				clear();
				refresh();
				
				//System.out.println(lstProducts.getSelectionMode());
				
				
				
			}});
		
		btnDelete.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				
				
				
				Connection txdb = new TXConnection().getInstance();
				try {
					
					Statement stmt1 = txdb.createStatement();
					String sql = "Delete from Suppliers where (supplierID = '" + textID.getText()+"')";					   
						   int rs = stmt1.executeUpdate(sql);

				            // (5) Process the int.
							if (rs == 0)
							{
							   
								JOptionPane.showMessageDialog(null,Messages.getValidateMsg_Delete(supplierName.getText()));
							}
				            System.out.println();

				            // Cleanup
				            txdb.commit();
				            txdb.close();
					     
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null,"Delete was unsuccessful");
				}
				clear();
				refresh();
				
				//System.out.println(lstProducts.getSelectionMode());
				}});
		
		btnNew.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				
				if (supplierName.getText().equals(""))
				{
					
					JOptionPane.showMessageDialog(null,Messages.VALUE_REQUIRED);
				}
				
				else 
				{
					/*for (int j=0;j<supplierData.getSize();j++)
					{
												
						String tempName = ((Suppliers)supplierData.elementAt(j)).getSupName();


							if (supplierName.getText().contains(tempName))
							{
								JOptionPane.showMessageDialog(null,"This supplier already exists");
								return;
							}
							
					}*/
					
							Connection txdb = new TXConnection().getInstance();
							try {
								
								Statement stmt1 = txdb.createStatement();
								//
								 String sql = "Insert Into Suppliers (SupplierID,supName) Values('" 
									 + (((Suppliers)supplierData.lastElement()).getSupplierID()+1)
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
				

		// CODE FOR THE MOVE LEFT BUTTON
		// add item to product suppliers
		btnMoveLeft.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				Connection txdb = new TXConnection().getInstance();
				try {
					
					Statement stmt1 = txdb.createStatement();
					
					 String sql = "Insert Into products_suppliers (PRODUCTSUPPLIERID,SUPPLIERID, PRODUCTID) Values(S_109_1_PRODUCTS_SUPPLIERS.nextval,'"
						 + textID.getText()
						 + "','"
						 + ((Products)lstProducts.getSelectedValue()).getProductID()
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
					JOptionPane.showMessageDialog(null,"Insert was unsuccessful");
				}
				
				refreshSupplier();
				reloadPSlist();
				//refresh();
			}});		
		// delete from productsuppliers
		btnMoveRight.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
			int psId = ((Products_Suppliers)lstProductsSuppliers.getSelectedValue()).getProductSupplierID();
			System.out.println(psId);	
			removeAllPS(psId);
			}
		});
		
		// Use a flow panel to lay out these items horizontally
		
	}
	public void refresh()
	{
		//System.out.println("I'm made it to the refresh method");
		supplierData.removeAllElements();
		
		Connection txdb = new TXConnection().getInstance();
		
		
		
		try {
			
			Statement stmt1 = txdb.createStatement();
			 String sql = "SELECT * FROM Suppliers ORDER BY supName";
			    
			      ResultSet rs = stmt1.executeQuery(sql);
			      while (rs.next())
			      {
			    	
			    	  supplierData.addElement(new Suppliers(rs.getInt(1), rs.getString(2)));

			      }
			    		    
			      txdb.close();
			     
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Select was unsuccessful");
		}
		refreshSupplier();
		
		
		
	}
	public void refreshSupplier()
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
		refreshSupplier();
	}
	public void reloadPSlist()
	{
		productSupplierData.removeAllElements();
		refreshSupplier();
		
		supplierName.setText(((Suppliers)lstSuppliers.getSelectedValue()).getSupName());
		textID.setText(((Suppliers)lstSuppliers.getSelectedValue()).getSupplierID()+"");
		Connection txdb = new TXConnection().getInstance();
		Statement stmt1;
		try {
			stmt1 = txdb.createStatement();
			String sql =  "SELECT prodName, productsupplierid, " +
			"products.productid FROM Products_Suppliers join " +
			"Products on Products_Suppliers.productID = Products.productID" +
			" where Products_Suppliers.supplierId = '"+ textID.getText()+ "' order by prodName";
			
		    
		      ResultSet rs = stmt1.executeQuery(sql);
		      while (rs.next())
		      {
		    	  
		    	  productSupplierData.addElement(new Products_Suppliers(rs.getInt(2),rs.getString(1),rs.getInt(3)) {public String toString() { return this.getPsProdName(); } });

		      }
		     
		      txdb.close();
			
		} catch (SQLException e) {
			
			
			e.printStackTrace();
		}
	
		if(productSupplierData.isEmpty())
		{
			return;
		}
		else
		{
		assignedSup();
		}
	}
	/**
	 * @param psId
	 */
	public void removeAllPS(int psId)
	{
		
		Connection txdb = new TXConnection().getInstance();
		try {
			
			Statement stmt1 = txdb.createStatement();
			//PreparedStatement pst = txdb.prepareStatement("DELETE FROM products_suppliers WHERE productsupplierid = ?");
			//pst.setInt(1, psId);
			//pst.executeQuery();
			String sql = "Delete from products_suppliers where (PRODUCTSUPPLIERID = '" + psId +"')";
			System.out.println(psId);																		
			int rs = stmt1.executeUpdate(sql);

		            // (5) Process the int.
					if (rs == 0)
					{
					   System.out.println("no rows updated");
					}
		         
		            // Cleanup
		            txdb.commit();
		            txdb.close();
			     
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Delete was unsuccessful");
		}
		refreshSupplier();
		if(productSupplierData.isEmpty())
		{
			return;
		}
		else
		{
		assignedSup();
		}
		
		//supplierData.addElement((Products_Suppliers)lstProductsSuppliers.getSelectedValue());
		productSupplierData.removeElementAt(lstProductsSuppliers.getSelectedIndex());
	
	}
	public void assignedSup()
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
