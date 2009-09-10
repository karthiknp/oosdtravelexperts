package com.travelexperts;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
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
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;
import org.jdesktop.application.Application;

import com.toedter.calendar.JCalendar;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
@SuppressWarnings("serial")
public class PackagesFrame extends JInternalFrame
{
	// List that will show the product/suppliers in a package (connect to
	// database later)
	JList lstProdInc = new JList();

	// The main table that will list all the packages
	JTable tblPackages;

	// CRUD stuff
	JPanel pnlCrudPanel = new JPanel(new FlowLayout());

	JButton btnNew = new JButton("New");
	JButton btnEdit = new JButton("Edit");
	JButton btnSave = new JButton("Save");
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane3;
	private JButton btnExcAll;
	private JButton btnExc;
	private JButton btnIncAll;
	private JButton btnInc;
	JButton btnDelete = new JButton("Delete");

	// Package labels/ textfields
	// Might not be necessary if the table editor contains CRUD methods

	private JList lstProdAvi;
	private JScrollPane jScrollPane1;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JComboBox cmbProdFilter;

	// Date picker objects
	JCalendar calStartDate = new JCalendar();

	// Global variables
	private Connection sqlConn = new TXConnection().getInstance();
	private Statement stmt1;
	private ResultSet rs1;
	private PackagesTableModel pkgTblModel;
	static Logger logger = Logger.getLogger(TXLogger.class.getName());
	private DefaultListModel dlmInc = new DefaultListModel();
	private DefaultListModel dlmAvi;

	private DefaultComboBoxModel cmbProdFilterModel;

	private Vector<Vector> v_initTblData = new Vector<Vector>();

	private Vector<Vector> v_initTblModelData;

	private JTextField cellNumeric;

	// Build the form
	public PackagesFrame()
	{
		super("Packages", true, true, true, true);
		// Hide the frame when closed, don't destroy it as it is only created
		// once
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setSize(800, 550);

		// Add CRUD buttons
		// Add all panels to form
		getContentPane().add(new JLabel("Viewing Packages"), "North");
		getContentPane().add(pnlCrudPanel, "South");
		pnlCrudPanel.setBounds(0, 484, 756, 31);
		pnlCrudPanel.add(btnDelete);
		pnlCrudPanel.add(btnSave);
		pnlCrudPanel.add(btnEdit);
		pnlCrudPanel.add(btnNew);
		{
			jScrollPane1 = new JScrollPane();
			getContentPane().add(jScrollPane1);
			jScrollPane1.setBounds(33, 34, 700, 202);
			btnNew.setBounds(304, 415, 35, 21);
			btnNew.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent evt)
				{
					// add new row to jtable
					addNewRow();
				}
			});
			btnEdit.setBounds(350, 415, 32, 21);
			btnEdit.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent evt) {
					btnEditMouseClicked(evt);
				}
			});
			btnSave.setBounds(393, 415, 38, 21);
			btnSave.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent evt)
				{
					btnSaveMouseClicked(evt);
				}
			});
			btnDelete.setBounds(442, 415, 45, 21);
			btnDelete.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent evt)
				{
					System.out.println("btnDelete.mouseClicked, event=" + evt);
					btnDeleteMouseClicked(evt);
				}
			});
			{
				tblPackages = new JTable();
				jScrollPane1.setViewportView(tblPackages);
				// initialize package table with result set
				ResultSet rs_prackages = get_RS_Packages();
				pkgTblModel = new PackagesTableModel(rs_prackages);
				tblPackages.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent evt)
					{
						tblPackagesMousePressed(evt);
					}
					
					public void mouseClicked(java.awt.event.MouseEvent e)
					{
						int clicked = e.getClickCount();
						if (clicked == 2)
						{
							Point pt = e.getPoint();
							int i = tblPackages.rowAtPoint(pt);
							int j = tblPackages.columnAtPoint(pt);
							// System.out.println("<" + i + "," + j + ">");
							if (j == PackagesTableModel.START_DATE
									|| j == PackagesTableModel.END_DATE)
							{
								// show calendar
								JOptionPane.showConfirmDialog(null,
										calStartDate);
								java.sql.Date d = new java.sql.Date(
										calStartDate.getCalendar()
										.getTimeInMillis());
								pkgTblModel.setValueAt(d, i, j);
							}
						}
					}
				});
				tblPackages.getSelectionModel().addListSelectionListener(
						new ListSelectionListener()
						{
							@Override
							public void valueChanged(ListSelectionEvent e)
							{
								listSelectionChaged(e);
							}
						});
				tblPackages.setModel(pkgTblModel);
				initTable(pkgTblModel, -1);
				final TableRowSorter sorter = new TableRowSorter(pkgTblModel);
				tblPackages.setRowSorter(sorter);


				tblPackages.getSelectionModel().addListSelectionListener(
						new ListSelectionListener()
						{
							@Override
							public void valueChanged(ListSelectionEvent e)
							{
								listSelectionChaged(e);
							}
						});
				tblPackages.setDefaultRenderer(Object.class,
						new EvenOddRenderer());
				 cellNumeric = new JTextField();
				tblPackages.getColumnModel().getColumn(pkgTblModel.PRICE).setCellEditor(
						new DefaultCellEditor(cellNumeric));
//				tblPackages.getColumnModel().getColumn(pkgTblModel.COMISSION).setCellEditor(
//						new DefaultCellEditor(cellNumeric));
				// Column 5 and 6 only accept numeric input
				cellNumeric.addKeyListener(new KeyAdapter()
				{
					public void keyTyped(KeyEvent e)
					{
						System.out.println(	e.getComponent().getParent());
						System.out.println(e.getKeyChar()+e.toString());
						String txtOld = ((JTextField) e.getComponent())
								.getText();

						if (e.getKeyChar() >= KeyEvent.VK_0
								&& e.getKeyChar() <= KeyEvent.VK_9)
						{
							if (txtOld.indexOf('.') == -1
									&& txtOld.length() >= 15)
							{
								e.consume();
								return;
							}
							return;
						}
						else if (e.getKeyChar() == '.')
						{
							if (((JTextField) e.getComponent()).getText()
									.indexOf('.') > -1)
								e.consume();
							System.out.println("index of ."
									+ ((JTextField) e.getComponent()).getText()
											.indexOf('.'));
						}
						else
						{
							e.consume();
						}
					}
				});
				cellNumeric.addFocusListener(new FocusListener()
				{

					@Override
					public void focusGained(FocusEvent e)
					{
						((JTextField) e.getComponent())
								.setBackground(Color.RED);
					}

					@Override
					public void focusLost(FocusEvent e)
					{
					}

				});

				JTextField cellNotNull = new JTextField();
				tblPackages.getColumnModel().getColumn(pkgTblModel.PACKAGE_NAME).setCellEditor(
						new DefaultCellEditor(cellNotNull));
				tblPackages.getColumnModel().getColumn(pkgTblModel.DESCRIPTION).setCellEditor(
						new DefaultCellEditor(cellNotNull));
				// Column 5 and 6 only accept numeric input
				cellNotNull.addKeyListener(new KeyAdapter()
				{
					public void keyTyped(KeyEvent e)
					{
						System.out.println(e.getKeyChar());
						if(((JTextField)e.getComponent())
						.getText().length()>= 100)
						{
							e.consume();
							return;
						}
					}
				});
				cellNotNull.addFocusListener(new FocusListener()
				{

					@Override
					public void focusGained(FocusEvent e)
					{
						if(((JTextField) e.getComponent()).getText().trim().equals("value required"))
						{
							((JTextField) e.getComponent()).setText("");
						}
							((JTextField) e.getComponent())
								.setBackground(Color.RED);
					}

					@Override
					public void focusLost(FocusEvent e)
					{
						if(((JTextField) e.getComponent()).getText().trim().equals(""))
						{
							((JTextField) e.getComponent()).grabFocus();
						}
					}

				});				
				tblPackages.setBounds(32, 12, 700, 203);
				tblPackages.setPreferredSize(new java.awt.Dimension(682, 199));

//				tblPackages.addKeyListener(new KeyAdapter() {
//					public void keyTyped(KeyEvent evt) {
//						System.out.println("tblPackages.keyTyped, event="+tblPackages.hasFocus());
//						//if (evt.getSource())
//						
//					}
//				});
//			    TableColumnModel tcm = tblPackages.getColumnModel(); 
//			    TableColumn tc = tcm.getColumn(6); 
//			    NumericTextField ntf = new NumericTextField();
////			    ntf.addKeyListener();
//			    tc.setCellRenderer(ntf); 

			}
		}
		{
			cmbProdFilterModel = new DefaultComboBoxModel();
			cmbProdFilter = new JComboBox();
			getContentPane().add(cmbProdFilter);
			cmbProdFilter.setModel(cmbProdFilterModel);
			cmbProdFilter.setBounds(436, 274, 204, 21);
			cmbProdFilter.setEditable(true);
			cmbProdFilter.addItemListener(new ItemListener()
			{
				public void itemStateChanged(ItemEvent evt)
				{
					cmbProdFilterItemStateChanged(evt);
				}
			});
		}
		{
			jLabel1 = new JLabel();
			getContentPane().add(jLabel1);
			jLabel1.setBounds(33, 9, 129, 14);
			jLabel1.setName("jLabel1");
		}
		{
			jLabel2 = new JLabel();
			getContentPane().add(jLabel2);
			jLabel2.setBounds(34, 248, 94, 14);
			jLabel2.setName("jLabel2");
		}
		{
			jLabel3 = new JLabel();
			getContentPane().add(jLabel3);
			jLabel3.setBounds(436, 248, 119, 14);
			jLabel3.setName("jLabel3");
		}
		{
			btnInc = new JButton("<");
			getContentPane().add(btnInc);
			btnInc.setBounds(351, 327, 52, 21);
			btnInc.setName("btnInc");
			btnInc.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent evt)
				{
					System.out.println("btnInc.mouseClicked, event=" + evt);
					btnIncMouseClicked(evt);
				}
			});
		}
		{
			btnIncAll = new JButton("<<");
			getContentPane().add(btnIncAll);
			btnIncAll.setBounds(351, 362, 52, 21);
			btnIncAll.setName("btnIncAll");
			btnIncAll.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent evt)
				{
					btnIncAllMouseClicked(evt);
				}
			});
		}
		{
			btnExc = new JButton(">");
			getContentPane().add(btnExc);
			btnExc.setBounds(351, 394, 52, 21);
			btnExc.setName("btnExc");
			btnExc.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent evt)
				{
					btnExcMouseClicked(evt);
				}
			});
		}
		{
			btnExcAll = new JButton(">>");
			getContentPane().add(btnExcAll);
			btnExcAll.setBounds(351, 426, 52, 21);
			btnExcAll.setName("btnExcAll");
			btnExcAll.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent evt)
				{
					btnExcAllMouseClicked(evt);
				}
			});
		}
		{
			jScrollPane3 = new JScrollPane();
			getContentPane().add(jScrollPane3);
			jScrollPane3.setBounds(437, 306, 286, 169);
			{
				ListModel jList1Model = new DefaultComboBoxModel(new String[] {
						"Item One", "Item Two" });
				lstProdAvi = new JList(new Object[] { "First Class Flight",
						"Car Rental", "Scuba Diving Adventure" });
				jScrollPane3.setViewportView(lstProdAvi);
				lstProdAvi.setModel(jList1Model);
				lstProdAvi.setBorder(BorderFactory
						.createTitledBorder(BorderFactory.createBevelBorder(1),
								"Product--Supplier"));
				lstProdAvi.setBounds(436, 284, 286, 169);
				dlmAvi = new DefaultListModel();
				lstProdAvi.setModel(dlmAvi);
			}
		}
		{
			jScrollPane2 = new JScrollPane();
			getContentPane().add(jScrollPane2);
			jScrollPane2.setBounds(34, 303, 286, 166);
			jScrollPane2.setViewportView(lstProdInc);
		}
		// Draw fancy border for the list
		lstProdInc.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createBevelBorder(1), "Product--Supplier"));
		lstProdInc.setBounds(33, 281, 286, 166);
		lstProdInc.setModel(dlmInc);
		validate();
		pack();
		Application.getInstance().getContext().getResourceMap(getClass())
				.injectComponents(this);
		getAllProdList("");
		initCboProdFilter();
		tblPackages.getColumn(pkgTblModel.getColumnName(0)).setPreferredWidth(15);
		tblPackages.getColumn(pkgTblModel.getColumnName(1)).setPreferredWidth(80);
		tblPackages.getColumn(pkgTblModel.getColumnName(2)).setPreferredWidth(40);
		tblPackages.getColumn(pkgTblModel.getColumnName(3)).setPreferredWidth(40);
		tblPackages.getColumn(pkgTblModel.getColumnName(4)).setPreferredWidth(100);
		tblPackages.getColumn(pkgTblModel.getColumnName(5)).setPreferredWidth(80);
		tblPackages.getColumn(pkgTblModel.getColumnName(6)).setPreferredWidth(80);
	}

	protected void clearForm()
	{
	}

	// get packages ResultSet
	private ResultSet get_RS_Packages()
	{
		ResultSet rs_prackages = null;
		try
		{
			String sql1 = "SELECT PACKAGEID ID,"
					+ "PKGNAME Name, "
					+ "PKGSTARTDATE StartDate, "
					+ "PKGENDDATE EndDate, "
					+ "PKGDESC Description, "
					+ "PKGBASEPRICE Price,"
					+ "PKGAGENCYCOMMISSION Commission FROM packages ORDER BY ID";
			rs_prackages = sqlConn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE).executeQuery(sql1);

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return rs_prackages;
	}

	// get packages table columnNames vector and show all packages in the table.
	private Vector<String> initTable(PackagesTableModel ptm, int rowNum)
	{
		Vector<String> culomnNames = new Vector<String>();

		return culomnNames;
	}

	// get all records from table Products_Suppliers
	private void getAllProdList(String filter)
	{
		dlmAvi.clear();
		String sql1 = "SELECT ps.ProductSupplierId, pro.ProductId, ProdName, sup.SupplierId, SupName "
				+ "FROM Products_Suppliers ps, Products pro, Suppliers sup "
				+ "WHERE ps.ProductId = pro.ProductId "
				+ " AND ProdName || SupName LIKE '%"
				+ filter
				+ "%' "
				+ " AND ps.SupplierId = sup.SupplierId "
				+ "ORDER BY ProdName, SupName";
		System.out.println(sql1);
		try
		{
			stmt1 = sqlConn.createStatement();
			rs1 = stmt1.executeQuery(sql1);
			while (rs1.next())
			{
				Products_Suppliers ps = new Products_Suppliers();
				ps.setProductSupplierID(rs1.getInt(1));

				ps.setProductID(rs1.getInt(2));
				ps.setPsProdName(rs1.getString(3));
				ps.setSupplierID(rs1.getInt(4));
				ps.setPsSuppName(rs1.getString(5));
				dlmAvi.addElement(ps);
				// exclude products in lstProdInc
				for (int i = 0; i < dlmInc.size(); i++)
				{
					if (((Products_Suppliers) dlmInc.elementAt(i))
							.getProductSupplierID() == rs1.getInt(1))
					{
						dlmAvi.removeElement(ps);
					}
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	// show table detail in list lstProdInc
	private void tblPackagesMousePressed(MouseEvent evt)
	{
		System.out.println("tblPackages.mousePressed, event=" + evt);
//		dlmInc.clear();
//		Point pt = evt.getPoint();
//		if (tblPackages.rowAtPoint(pt) == -1)
//		{
//			tblPackages.clearSelection();
//			return;
//		}
//		String sql1 = "SELECT ps.ProductSupplierId, pro.ProductId, ProdName, sup.SupplierId, SupName "
//				+ " FROM Packages pkg, Packages_Products_Suppliers pps, Products_Suppliers ps, Products pro, Suppliers sup "
//				+ " WHERE pkg.PackageId = pps.PackageId "
//				+ " AND pps.ProductSupplierId = ps.ProductSupplierId "
//				+ " AND ps.ProductId = pro.ProductId "
//				+ " AND ps.SupplierId = sup.SupplierId "
//				+ " AND pkg.PackageId = "
//				+ tblPackages.getValueAt(tblPackages.rowAtPoint(pt),
//						PackagesTableModel.PACKAGE_ID)
//				+ " ORDER BY ProdName, SupName";
//		System.out.println(sql1);
//		try
//		{
//			rs1 = stmt1.executeQuery(sql1);
//			while (rs1.next())
//			{
//				Products_Suppliers ps = new Products_Suppliers();
//				ps.setProductSupplierID(rs1.getInt(1));
//				ps.setProductID(rs1.getInt(2));
//				ps.setPsProdName(rs1.getString(3));
//				ps.setSupplierID(rs1.getInt(4));
//				ps.setPsSuppName(rs1.getString(5));
//				dlmInc.addElement(ps);
//			}
//			getAllProdList(cmbProdFilter.getSelectedItem().toString());
//		}
//		catch (SQLException e)
//		{
//			e.printStackTrace();
//		}
	}

	// fill combobox CboProdFilter with product and supplier names
	private void initCboProdFilter()
	{
		cmbProdFilterModel.removeAllElements();
		cmbProdFilterModel.addElement("");
		String sql1 = "Select ProdName cboProd,'1' FROM Products " + " UNION "
				+ "Select SupName cboProd,'2' FROM Suppliers " + "ORDER BY 2,1";
		System.out.println(sql1);
		try
		{
			rs1 = stmt1.executeQuery(sql1);
			while (rs1.next())
			{
				cmbProdFilterModel.addElement(rs1.getString(1));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		cmbProdFilter.setSelectedIndex(0);
	}

	// filter the lstProdAvi when keyword changed
	private void cmbProdFilterItemStateChanged(ItemEvent evt)
	{
		if (evt.getStateChange() != ItemEvent.SELECTED)
		{
			return;
		}
		String key = cmbProdFilter.getSelectedItem().toString();
		getAllProdList(key);
	}

	// reverse the selected items between to listboxes
	private void flipLists(JList lstFrom, DefaultListModel modelFrom,
			JList lstTo, DefaultListModel modelTo)
	{
		if (lstFrom.getSelectedIndex() == -1)
		{
			return;
		}
		// for each i in lstFrom selected indexies
		int[] selectedIdx = lstFrom.getSelectedIndices();
		Vector<Products_Suppliers> v_ps = new Vector<Products_Suppliers>();
		for (int i : selectedIdx)
		{
			modelTo
					.addElement((Products_Suppliers) (modelFrom.getElementAt(i)));
			v_ps.addElement((Products_Suppliers) (modelFrom.getElementAt(i)));
		}
		for (Products_Suppliers ps : v_ps)
		{
			modelFrom.removeElement(ps);
		}
		// modelFrom.removeAllElements();
		lstFrom.clearSelection();
		lstTo.clearSelection();
		if (lstFrom == lstProdInc)
		{
			cmbProdFilter.setSelectedIndex(0);
		}

	}

	private void btnIncMouseClicked(MouseEvent evt)
	{
		flipLists(lstProdAvi, dlmAvi, lstProdInc, dlmInc);
	}

	private void btnExcMouseClicked(MouseEvent evt)
	{
		flipLists(lstProdInc, dlmInc, lstProdAvi, dlmAvi);
	}

	private void btnIncAllMouseClicked(MouseEvent evt)
	{
		System.out.println("btnIncAll.mouseClicked, event=" + evt);
		int[] indeices = new int[dlmAvi.size()];
		for (int i = 0; i < indeices.length; i++)
		{
			indeices[i] = i;
		}
		lstProdAvi.setSelectedIndices(indeices);
		btnIncMouseClicked(evt);

	}

	private void btnExcAllMouseClicked(MouseEvent evt)
	{
		System.out.println("btnExcAll.mouseClicked, event=" + evt);
		int[] indeices = new int[dlmInc.size()];
		for (int i = 0; i < indeices.length; i++)
		{
			indeices[i] = i;
		}
		lstProdInc.setSelectedIndices(indeices);
		btnExcMouseClicked(evt);
	}

	// update table Packages_Products_Suppliers
	private void btnSaveMouseClicked(MouseEvent evt)
	{
		System.out.println("btnSave.mouseClicked, event=" + evt);
		String sql1 = "DELETE from Packages_Products_Suppliers "
				+ "WHERE PackageId = "
				+ tblPackages.getValueAt(tblPackages.getSelectedRows()[0],
						PackagesTableModel.PACKAGE_ID);
		Vector<String> v_sql2 = new Vector<String>();
		for (int i = 0; i < dlmInc.getSize(); i++)
		{
			v_sql2
					.addElement("INSERT INTO Packages_Products_Suppliers VALUES ("
							+ tblPackages.getValueAt(tblPackages
									.getSelectedRows()[0],
									PackagesTableModel.PACKAGE_ID)
							+ ","
							+ ((Products_Suppliers) (dlmInc.getElementAt(i)))
									.getProductSupplierID() + ")");
		}
		if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(null,
				Messages.getConfirmMsg_Update("Package ID:"
						+ tblPackages.getValueAt(tblPackages.getSelectedRow(),
								PackagesTableModel.PACKAGE_ID).toString()),
				"Warning", JOptionPane.OK_CANCEL_OPTION))
		{
			return;
		}
		System.out.println(sql1);
		try
		{
			sqlConn.setAutoCommit(false);
			stmt1.execute(sql1);
			for (int i = 0; i < v_sql2.size(); i++)
			{
				System.out.println(v_sql2.elementAt(i));
				stmt1.executeUpdate(v_sql2.elementAt(i));
			}
			pkgTblModel.fireTableRowsUpdated(tblPackages.getSelectedRow(), tblPackages.getSelectedRow());
			sqlConn.commit();
			sqlConn.setAutoCommit(true);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			try
			{
				sqlConn.rollback();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	// table update
	private void pkgTblModelChanged(TableModelEvent e)
	{

	}

	// 
	private void executeSQL(String sql)
	{
		try
		{
			System.out.println(sql);
			int count = stmt1.executeUpdate(sql);
			if (count < 1)
			{
				JOptionPane.showMessageDialog(null, Messages.DB_EXCEPTION);
			}
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, Messages.DB_EXCEPTION);
			e.printStackTrace();
		}
		clearForm();
	}

	// add a new row to the end of jtable and insert into database
	private void addNewRow()
	{
		if ((tblPackages.getValueAt(tblPackages.getSelectedRow(), 1))==null)
		{
			return;
		}
		
	}

	// validate inputs in jtable
	private Boolean validateTable(int rowNum)
	{
		return true;
	}

	// delete button on mouse click event handler
	;

	private void btnDeleteMouseClicked(MouseEvent evt)
	{
		if (tblPackages.getSelectedRow()==-1)
		{
			return;
		}
		// popup confirm message box
		String msgID = "";
		int[] rows = tblPackages.getSelectedRows();
		for (int row : rows)
		{
			msgID += tblPackages.getValueAt(row, pkgTblModel.PACKAGE_ID) + " ";
		}
		if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(null,
				Messages.getConfirmMsg_Delete("Package ID:" + msgID),
				"Warning", JOptionPane.OK_CANCEL_OPTION))
		{
			return;
		}
		// delete all selected rows
		pkgTblModel.deleteRow(rows);
	}

	private void listSelectionChaged(ListSelectionEvent evt)
	{
		dlmInc.clear();
		if (tblPackages.getSelectedRow() == -1)
		{
			tblPackages.clearSelection();
			return;
		}
		String sql1 = "SELECT ps.ProductSupplierId, pro.ProductId, ProdName, sup.SupplierId, SupName "
				+ " FROM Packages pkg, Packages_Products_Suppliers pps, Products_Suppliers ps, Products pro, Suppliers sup "
				+ " WHERE pkg.PackageId = pps.PackageId "
				+ " AND pps.ProductSupplierId = ps.ProductSupplierId "
				+ " AND ps.ProductId = pro.ProductId "
				+ " AND ps.SupplierId = sup.SupplierId "
				+ " AND pkg.PackageId = "
				+ tblPackages.getValueAt(tblPackages.getSelectedRow(),
						PackagesTableModel.PACKAGE_ID)
				+ " ORDER BY ProdName, SupName";
		System.out.println(sql1);
		try
		{
			rs1 = stmt1.executeQuery(sql1);
			while (rs1.next())
			{
				Products_Suppliers ps = new Products_Suppliers();
				ps.setProductSupplierID(rs1.getInt(1));
				ps.setProductID(rs1.getInt(2));
				ps.setPsProdName(rs1.getString(3));
				ps.setSupplierID(rs1.getInt(4));
				ps.setPsSuppName(rs1.getString(5));
				dlmInc.addElement(ps);
			}
			getAllProdList(cmbProdFilter.getSelectedItem().toString());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private void btnEditMouseClicked(MouseEvent evt) {
		System.out.println("btnEdit.mouseClicked, event="+evt);
		tblPackages.editCellAt(tblPackages.getSelectedRow(),pkgTblModel.PACKAGE_NAME);
		//((JTextField)tblPackages.getCellEditor(tblPackages.getSelectedRow(), 1).getTableCellEditorComponent(tblPackages, null, true, tblPackages.getSelectedRow(),1)).grabFocus();
	}
}
