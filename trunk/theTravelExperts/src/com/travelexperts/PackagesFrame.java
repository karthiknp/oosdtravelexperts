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
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
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
import javax.swing.event.TableModelListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.StyledEditorKit.BoldAction;

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
	private Connection sqlConn;
	private Statement stmt1;
	private ResultSet rs1;
	private PackagesTableModel pkgTblModel;
	private String sql1;
	private ResultSetMetaData rsm;
	static Logger logger = Logger.getLogger(TXLogger.class.getName());
	private DefaultListModel dlmInc= new DefaultListModel();
	private DefaultListModel dlmAvi;

	private DefaultComboBoxModel cmbProdFilterModel;

	private Vector<Vector> v_initTblData = new Vector<Vector>();

	private Vector<Vector> v_initTblModelData;

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
		btnNew.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent evt)
			{
				clearForm();
			}
		});

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
					addNewRow();
				}
			});
			btnEdit.setBounds(350, 415, 32, 21);
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
					// TODO add your code for btnDelete.mouseClicked
					// popup confirm message box
					String msgID = "";
					for (int row : tblPackages.getSelectedRows())
					{
						msgID += (row + 1) + " ";
					}

					if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(
							null, Messages.getConfirmMsg_Delete("Package ID:"
									+ msgID), "Warning",
							JOptionPane.OK_CANCEL_OPTION))
					{
						return;
					}
					// delete all selected rows
					for (int row : tblPackages.getSelectedRows())
					{
						String sql1 = "DELETE FROM Packages_Products_Suppliers "
								+ " WHERE PackageId = "
								+ tblPackages.getValueAt(row, 0);
						String sql2 = "DELETE FROM  Packages"
								+ " WHERE PackageId = "
								+ tblPackages.getValueAt(row, 0);
						System.out.println(sql1);
						System.out.println(sql2);
						try
						{
							sqlConn.setAutoCommit(false);
							stmt1.executeQuery(sql1);
							stmt1.executeQuery(sql2);
							sqlConn.commit();
							sqlConn.setAutoCommit(true);
						}
						catch (SQLException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
							try
							{
								sqlConn.rollback();
							}
							catch (SQLException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					initTable(pkgTblModel,0);
				}
			});
			{
				tblPackages = new JTable();
				jScrollPane1.setViewportView(tblPackages);
				pkgTblModel = new PackagesTableModel();
				pkgTblModel.addTableModelListener(new TableModelListener()
				{
					@Override
					public void tableChanged(TableModelEvent e)
					{
						// TODO Auto-generated method stub
						pkgTblModelChanged(e);
					}

				});
				tblPackages.setModel(pkgTblModel);
				initTable(pkgTblModel,-1);
				final TableRowSorter sorter = new TableRowSorter(pkgTblModel);
				tblPackages.setRowSorter(sorter);
				tblPackages.getSelectionModel().addListSelectionListener(
						new ListSelectionListener()
						{

							private int indexToChange;

							@Override
							public void valueChanged(ListSelectionEvent e)
							{
								if (tblPackages.getSelectedRow()==-1 && dlmInc.size()>0)
								{
									dlmInc.removeRange(0, dlmInc.size()-1);
									return;
								}
								if(indexToChange  == tblPackages.getSelectedRow() )
								{
									return;
								}
								
								// TODO Auto-generated method stub 
								 indexToChange = tblPackages.getSelectedRow()==e.getLastIndex()?e.getFirstIndex():e.getLastIndex();
								
								System.out.println("ListSelection 1st Listener"
										+ e.getFirstIndex());
								System.out.println("ListSelection 2nd Listener"
										+ e.getLastIndex());
								System.out.println("Selected row num"
										+ tblPackages.getSelectedRow()); 
								Boolean rowChanged = false;
								String rowInitData = new String();
								String rowData = new String();
								for (int i = 0; i < v_initTblData.get(
										indexToChange).size(); i++)
								{
									if (v_initTblData.get(indexToChange)
											.get(i) == null
											&& v_initTblModelData.get(
													indexToChange).get(i) != null)
									{

										rowChanged = true;
									}
									else if(v_initTblData.get(indexToChange)
											.get(i) != null
											&& v_initTblModelData.get(
													indexToChange).get(i) == null)
									{
										rowChanged = true;
									}
									else if(v_initTblData.get(indexToChange)
											.get(i) != null
											&& v_initTblModelData.get(
													indexToChange).get(i) != null)
									{
										rowInitData = v_initTblData.get(
												indexToChange).get(i)
												.toString();
										rowData = v_initTblModelData.get(
												indexToChange).get(i)
												.toString();
										System.out.println(rowInitData);
										System.out.println(rowData);
										if (!rowInitData.equals(rowData))
										{
											rowChanged = true;
										}
									}
								}
								// no changes to the row
								if (!rowChanged)
								{
									return;
								}
								// validate Table inputs
								if (!validateTable(indexToChange))
								{
									tblPackages.setRowSelectionInterval(indexToChange, indexToChange);
									return;
								}
								String startDate = null;
								String endDate = null;
								if (tblPackages
										.getValueAt(indexToChange, 2) != null)
								{
									startDate = tblPackages.getValueAt(
											indexToChange, 2).toString();
									startDate = "to_date('"
											+ startDate.split(" ")[0]
											+ "','YYYY-MM-DD') ";
								}
								if (tblPackages
										.getValueAt(indexToChange, 3) != null)
								{
									endDate = tblPackages.getValueAt(
											indexToChange, 3).toString();
									endDate = "to_date('"
											+ endDate.split(" ")[0]
											+ "','YYYY-MM-DD') ";
								}
								String sql1 = "UPDATE packages SET "
										+ "PKGNAME = '"
										+ tblPackages.getValueAt(indexToChange, 1)
										+ "', "
										+ " PKGSTARTDATE ="
										+ startDate
										+ ", "
										+ " PKGENDDATE ="
										+ endDate
										+ ", "
										+ " PKGDESC = '"
										+ tblPackages.getValueAt(indexToChange, 4)
										+ "', "
										+ "PKGBASEPRICE = "
										+ tblPackages.getValueAt(indexToChange, 5)
										+ ", "
										+ "PKGAGENCYCOMMISSION = "
										+ tblPackages.getValueAt(indexToChange, 6)
										+ " WHERE PACKAGEID = "
										+ tblPackages.getValueAt(indexToChange, 0);

								if (JOptionPane.OK_OPTION == JOptionPane
										.showConfirmDialog(
												null,
												Messages
														.getConfirmMsg_Update("Package ID:"
																+ tblPackages
																		.getValueAt(
																				indexToChange,
																				0)
																		.toString()),
												"Warning",
												JOptionPane.OK_CANCEL_OPTION))
								{
									updatePackages(sql1);
									initTable(pkgTblModel,indexToChange);
								}
							}

						});
				tblPackages.setDefaultRenderer(Object.class,
						new EvenOddRenderer());
				JTextField cellNumeric = new JTextField();
				tblPackages.getColumnModel().getColumn(5).setCellEditor(
						new DefaultCellEditor(cellNumeric));
				tblPackages.getColumnModel().getColumn(6).setCellEditor(
						new DefaultCellEditor(cellNumeric));
				// Column 5 and 6 only accept numeric input
				cellNumeric.addKeyListener(new KeyAdapter()
				{
					public void keyTyped(KeyEvent e)
					{
						System.out.println(e.getKeyChar());
						String txtOld = ((JTextField) e.getComponent())
								.getText();

						if (e.getKeyChar() >= KeyEvent.VK_0
								&& e.getKeyChar() <= KeyEvent.VK_9)
						{
							if (txtOld.indexOf('.') == -1
									&& txtOld.length() == 15)
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
						// TODO Auto-generated method stub
						// String txtOld = ((JTextField)
						// e.getComponent()).getText();
						// BigDecimal amount = new BigDecimal(txtOld);
						// amount = amount.setScale(4,
						// BigDecimal.ROUND_HALF_UP);
						// System.out.println("focusLost"+amount.toString());
						// String txtNew = amount.toString();
						// ((JTextField) e.getComponent()).setText(txtNew);

					}

				});
				tblPackages.addMouseListener(new MouseAdapter()
				{
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
							if (j == 2 || j == 3)
							{
								// show calendar
								JOptionPane.showConfirmDialog(null,
										calStartDate);
								pkgTblModel.setValueAt(calStartDate
										.getCalendar().get(Calendar.YEAR)
										+ "-"
										+ calStartDate.getCalendar().get(
												Calendar.MONTH)
										+ "-"
										+ (calStartDate.getCalendar()
												.get(Calendar.DAY_OF_MONTH)), i, j);
							}
						}
					}
				});
				tblPackages.setBounds(32, 12, 700, 203);
				tblPackages.setPreferredSize(new java.awt.Dimension(682, 199));
				tblPackages.addKeyListener(new KeyAdapter()
				{
					public void keyPressed(KeyEvent evt)
					{
						// TODO add your code for tblPackages.keyPressed
						if (evt.getKeyChar() != KeyEvent.VK_ENTER)
						{
							evt.consume();
						}
						System.out.println("tblPackages.keyPressed, event="
								+ evt);
						int rowNum = tblPackages.getSelectedRow();
						if (rowNum == pkgTblModel.getRowCount() - 1)
						{
							addNewRow();
						}
					}
				});

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
			btnInc = new JButton();
			getContentPane().add(btnInc);
			btnInc.setBounds(351, 327, 52, 21);
			btnInc.setName("btnInc");
			btnInc.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent evt)
				{
					System.out.println("btnInc.mouseClicked, event=" + evt);
					// TODO add your code for btnInc.mouseClicked
					btnIncMouseClicked(evt);
				}
			});
		}
		{
			btnIncAll = new JButton();
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
			btnExc = new JButton();
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
			btnExcAll = new JButton();
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
	}

	protected void clearForm()
	{
	}

	// get packages table columnNames vector and show all packages in the table.
	private Vector<String> initTable(PackagesTableModel ptm, int rowNum)
	{
		Vector<String> culomnNames = new Vector<String>();
		try
		{
			sqlConn = new TXConnection().getInstance();
			sql1 = "SELECT PACKAGEID ID,"
					+ "PKGNAME Name, "
					+ "to_char(PKGSTARTDATE,'YYYY-MM-DD') StartDate, "
					+ "PKGENDDATE EndDate, "
					+ "PKGDESC Description, "
					+ "PKGBASEPRICE Price,"
					+ "PKGAGENCYCOMMISSION Commission FROM packages WHERE packageid = '0'";
			stmt1 = sqlConn.createStatement();
			rs1 = stmt1.executeQuery(sql1);
			rsm = rs1.getMetaData();
			for (int i = 1; i <= rsm.getColumnCount(); i++)
			{
				ptm.addColumn(rsm.getColumnName(i));
				culomnNames.addElement(rsm.getColumnName(i));
			}
			stmt1 = sqlConn.createStatement();
			sql1 = "SELECT PACKAGEID ID,"
					+ "PKGNAME Name, "
					+ "to_char(PKGSTARTDATE,'YYYY-MM-DD') StartDate, "
					+ "to_char(PKGENDDATE,'YYYY-MM-DD') EndDate, "
					+ "PKGDESC Description, "
					+ "PKGBASEPRICE Price,"
					+ "PKGAGENCYCOMMISSION Commission FROM packages ORDER BY ID";
			rs1 = stmt1.executeQuery(sql1);
			rsm = rs1.getMetaData();
			v_initTblData.clear();
			v_initTblModelData = new Vector<Vector>();
			Boolean hasRows = false;
			while (rs1.next())
			{
				hasRows = true;
				Vector<Object> v = new Vector<Object>();
				Vector<Object> v_init = new Vector<Object>();
				for (int i = 1; i <= rsm.getColumnCount(); i++)
				{
					v.addElement((rs1.getObject(i)));
					v_init.addElement((rs1.getObject(i)));
				}
				v_initTblData.addElement(v_init);
				v_initTblModelData.addElement(v);
				// pkgTblModel.addRow(v);
			}
			if (!hasRows)
			{
				JOptionPane.showMessageDialog(null, Messages
						.getWornMsg_NoRecord("package"));
				return null;
			}
			ptm.setDataVector(v_initTblModelData, culomnNames);
			
		}
		catch (SQLException e)
		{
			logger.error(e.getMessage());
		}
		if(rowNum>-1)
		tblPackages.setRowSelectionInterval(rowNum, rowNum);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// show table detail in list lstProdInc
	private void tblPackagesMousePressed(MouseEvent evt)
	{
		System.out.println("tblPackages.mousePressed, event=" + evt);
		// TODO add your code for tblPackages.mousePressed
		dlmInc.clear();
		Point pt = evt.getPoint();
		String sql1 = "SELECT ps.ProductSupplierId, pro.ProductId, ProdName, sup.SupplierId, SupName "
				+ " FROM Packages pkg, Packages_Products_Suppliers pps, Products_Suppliers ps, Products pro, Suppliers sup "
				+ " WHERE pkg.PackageId = pps.PackageId "
				+ " AND pps.ProductSupplierId = ps.ProductSupplierId "
				+ " AND ps.ProductId = pro.ProductId "
				+ " AND ps.SupplierId = sup.SupplierId "
				+ " AND pkg.PackageId = "
				+ tblPackages.getValueAt(tblPackages.rowAtPoint(pt), 0)
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			// TODO Auto-generated catch block
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
		// TODO add your code for btnIncAll.mouseClicked
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
		// TODO add your code for btnExcAll.mouseClicked
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
		// TODO add your code for btnSave.mouseClicked
		String sql1 = "DELETE from Packages_Products_Suppliers "
				+ "WHERE PackageId = "
				+ tblPackages.getValueAt(tblPackages.getSelectedRows()[0], 0);
		Vector<String> v_sql2 = new Vector<String>();
		for (int i = 0; i < dlmInc.getSize(); i++)
		{
			v_sql2
					.addElement("INSERT INTO Packages_Products_Suppliers VALUES ("
							+ tblPackages.getValueAt(tblPackages
									.getSelectedRows()[0], 0)
							+ ","
							+ ((Products_Suppliers) (dlmInc.getElementAt(i)))
									.getProductSupplierID() + ")");
		}
		if (JOptionPane.OK_OPTION != JOptionPane
				.showConfirmDialog(
						null,
						Messages
								.getConfirmMsg_Update("Package ID:"
										+ tblPackages
												.getValueAt(
														tblPackages.getSelectedRow(),
														0)
												.toString()),
						"Warning",
						JOptionPane.OK_CANCEL_OPTION))
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
			sqlConn.commit();
			sqlConn.setAutoCommit(true);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			try
			{
				sqlConn.rollback();
			}
			catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// table update
	private void pkgTblModelChanged(TableModelEvent e)
	{
		// if (e.getType() == TableModelEvent.UPDATE && e.getFirstRow() > -1)
		// {
		// System.out.println("table UPDATE");
		// System.out.println("<" + e.getFirstRow() + "," + e.getColumn()
		// + ">");
		// String startDate = null;
		// String endDate = null;
		// if (tblPackages.getValueAt(e.getFirstRow(), 2)!=null)
		// {
		// startDate = tblPackages.getValueAt(e.getFirstRow(), 2).toString();
		// startDate = "to_date('"+ startDate.split(" ")[0]+"','YYYY-MM-DD') ";
		// }
		// if (tblPackages.getValueAt(e.getFirstRow(), 3)!=null)
		// {
		// endDate = tblPackages.getValueAt(e.getFirstRow(), 3).toString();
		// endDate = "to_date('"+ endDate.split(" ")[0]+"','YYYY-MM-DD') ";
		// }
		// String sql1 = "UPDATE packages SET " + "PKGNAME = '"
		// + tblPackages.getValueAt(e.getFirstRow(), 1) + "', "
		// + " PKGSTARTDATE =" + startDate+ ", "
		// + " PKGENDDATE =" + endDate + ", "
		// + " PKGDESC = '"
		// + tblPackages.getValueAt(e.getFirstRow(), 4) + "', "
		// + "PKGBASEPRICE = '"
		// + tblPackages.getValueAt(e.getFirstRow(), 5) + "', "
		// + "PKGAGENCYCOMMISSION = '"
		// + tblPackages.getValueAt(e.getFirstRow(), 6) + "'"
		// + " WHERE PACKAGEID = "
		// + tblPackages.getValueAt(e.getFirstRow(), 0);
		//
		// if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null,
		// Messages.getConfirmMsg_Update("Package ID:"
		// + tblPackages.getValueAt(e.getFirstRow(), 0)
		// .toString()), "Warning",
		// JOptionPane.OK_CANCEL_OPTION))
		// {
		// updatePackages(sql1);
		// }
		// }
	}

	private void updatePackages(String sql)
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
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, Messages.DB_EXCEPTION);
			e.printStackTrace();
		}
		clearForm();
	}

	private void insertPackages(String sql)
	{
		try
		{
			System.out.println(sql);
			stmt1.execute(sql);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, Messages.DB_EXCEPTION);
			e.printStackTrace();
		}
		initTable(pkgTblModel,tblPackages.getRowCount()-1);
	}

	private void addNewRow()
	{
		if (pkgTblModel.getValueAt(pkgTblModel.getRowCount() - 1, 1) == null)
			return;
		// pkgTblModel.addEmptyRow();
		Vector v_pkg = new Vector();
		Vector v_pkgTbl = new Vector();
		int pkg_id = Integer.parseInt((pkgTblModel.getValueAt(pkgTblModel.getRowCount()-1, 0)).toString()) + 1;
		v_pkg.addElement(pkg_id);
		v_pkgTbl.addElement(pkg_id);
		
		pkgTblModel.addRow(v_pkg);
		v_initTblData.addElement(v_pkgTbl);
		String sql1 = "INSERT INTO packages (PACKAGEID, PKGNAME,PKGBASEPRICE) "
				+ "VALUES ( " + pkg_id + ", " + "'value required', 0)";
		insertPackages(sql1);
	}

	private Boolean validateTable(int rowNum)
	{
		System.out.println("validateTable");
		if(tblPackages.getValueAt(rowNum, 1) == null || (tblPackages.getValueAt(rowNum, 1)).toString().trim() == "")
		{
			JOptionPane.showMessageDialog(null, "Package Name " + Messages.VALUE_REQUIRED);
			return false;
		}
		if(tblPackages.getValueAt(rowNum, 4) == null || (tblPackages.getValueAt(rowNum, 4)).toString().trim().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Package Description " + Messages.VALUE_REQUIRED);
			return false;
		}
		if (pkgTblModel.getValueAt(rowNum, 2)!=null && pkgTblModel.getValueAt(rowNum, 3)!=null)
		{
		Calendar startDate =  Calendar.getInstance();
		startDate.set(Calendar.YEAR, Integer.parseInt(pkgTblModel.getValueAt(rowNum, 2).toString().split("-")[0]));
		startDate.set(Calendar.MONTH, Integer.parseInt(pkgTblModel.getValueAt(rowNum, 2).toString().split("-")[1]));
		startDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(pkgTblModel.getValueAt(rowNum, 2).toString().split("-")[2]));
		Calendar endDate =  Calendar.getInstance();
		endDate.set(Calendar.YEAR, Integer.parseInt(pkgTblModel.getValueAt(rowNum, 3).toString().split("-")[0]));
		endDate.set(Calendar.MONTH, Integer.parseInt(pkgTblModel.getValueAt(rowNum, 3).toString().split("-")[1]));
		endDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(pkgTblModel.getValueAt(rowNum, 3).toString().split("-")[2]));
		
		if(startDate.compareTo(endDate) > 0)
		{
			JOptionPane.showMessageDialog(null, Messages.getValidateMsg_A_CANT_GREATERTHAN_B("Start Date","End Date"));
			return false;
		}
		}
		BigDecimal price = new BigDecimal(pkgTblModel.getValueAt(rowNum, 5).toString());
		BigDecimal commission = new BigDecimal(pkgTblModel.getValueAt(rowNum, 6).toString());
		if(commission.compareTo(price) > 0)
		{
			JOptionPane.showMessageDialog(null, Messages.getValidateMsg_A_CANT_GREATERTHAN_B("Commission","Package Price"));
			return false;
		}

		return true;

	}
}
