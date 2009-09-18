package com.travelexperts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Custom menu bar for Travel Experts project
 * 
 * Keyboard hotkeys/shortcuts added by Ingrid Niu
 * 
 * @author will_ad
 * 
 */
@SuppressWarnings("serial")
public class MainMenuBar extends JMenuBar
{

	// Menus
	private TravelExpertsGUI parentFrame;
	private JMenu helpMenu = new JMenu("Help");
	private JMenu optionsMenu = new JMenu("Options");
	private JMenu customersMenu = new JMenu("Customers");
	private JMenu agentsMenu = new JMenu("Agents");
	private JMenu productsMenu = new JMenu("Products");
	private JMenu suppliersMenu = new JMenu("Suppliers");
	private JMenu packagesMenu = new JMenu("Packages");
	private JMenu fileMenu = new JMenu("File");
	private JMenu supportMenu = new JMenu("Support");

	// Submenus
	private JMenu skinsMenu = new JMenu("Look and Feel");

	// Menu items
	public JMenuItem fileMenuExit = new JMenuItem("Exit", 'X');
	public JMenuItem packagesMenuEdit = new JMenuItem("Manage", 'P');
	public JMenuItem productsMenuEdit = new JMenuItem("Edit Products", 'R');
	public JMenuItem suppliersMenuEdit = new JMenuItem("Edit Suppliers", 'S');
	public JMenuItem agentsMenuEdit = new JMenuItem("Manage Agents", 'A');
	public JMenuItem customersMenuEdit = new JMenuItem("Manage Customers", 'C');
	public JMenuItem customersMenuReport = new JMenuItem("Print Invoices", 'I');
	public JMenuItem helpFAQ = new JMenuItem("FAQ");
	public JMenuItem helpAbout = new JMenuItem("About");
	public JMenuItem helpSupport = new JMenuItem("Real-time Support");

	public JCheckBoxMenuItem optionsErrorLog;

	public MainMenuBar(TravelExpertsGUI newParentFrame)
	{
		super();

		this.parentFrame = newParentFrame;

		// Build the menu
		// Add menus and menu items
		add(fileMenu);
		fileMenu.setMnemonic('F');
		{
			// File->Exit
			fileMenu.add(fileMenuExit);
			fileMenuExit.setAccelerator(KeyStroke.getKeyStroke("control X"));
			fileMenuExit.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					System.exit(0);
				}
			});
		}
		add(packagesMenu);
		packagesMenu.setMnemonic('P');
		{
			// Packages->Manage
			packagesMenu.add(packagesMenuEdit);
			packagesMenuEdit.setAction(parentFrame.getAppActionMap().get(
					"showPackages"));
			packagesMenuEdit.setText("Manage Packages");
			packagesMenuEdit.setAccelerator(KeyStroke.getKeyStroke("control P"));
		}
		add(suppliersMenu);
		suppliersMenu.setMnemonic('S');
		{
			suppliersMenu.add(suppliersMenuEdit);
			suppliersMenuEdit.setAction(parentFrame.getAppActionMap().get(
					"showSuppliers"));
			suppliersMenuEdit.setText("Manage Suppliers");
			suppliersMenuEdit.setAccelerator(KeyStroke.getKeyStroke("control S"));
//			try
//			{
//				suppliersMenuEdit
//						.setIcon(new ImageIcon(
//								new URL(
//										"http://t1.gstatic.com/images?q=tbn:2VZ5Jdb3EApnjM:http://www.aditnorth.org.uk/uploadedImages/partners_icon.gif")));
//			}
//			catch (MalformedURLException e)
//			{
//				e.printStackTrace();
//			}
		}
		add(productsMenu);
		productsMenu.setMnemonic('R');
		{
			productsMenu.add(productsMenuEdit);
			productsMenuEdit.setAction(parentFrame.getAppActionMap().get(
					"showProducts"));
			productsMenuEdit.setText("Manage Products");
			productsMenuEdit.setAccelerator(KeyStroke.getKeyStroke("control R"));
//			try
//			{
//				// Steal an icon off google lol
//				productsMenuEdit
//						.setIcon(new ImageIcon(
//								new URL(
//										"http://t0.gstatic.com/images?q=tbn:Ez4gvTSY8ob0jM:http://www.istockphoto.com/file_thumbview_approve/6885224/2/istockphoto_6885224-palm-tree.jpg")));
//			}
//			catch (MalformedURLException e)
//			{
//				e.printStackTrace();
//			}
		}
		add(agentsMenu);
		agentsMenu.setMnemonic('A');
		{
			agentsMenu.add(agentsMenuEdit);
			agentsMenuEdit.setAction(parentFrame.getAppActionMap().get(
					"showAgents"));
			agentsMenuEdit.setText("Manage Agents");
			agentsMenuEdit.setAccelerator(KeyStroke.getKeyStroke("control A"));
		}
		add(customersMenu);
		customersMenu.setMnemonic('C');
		{
			customersMenu.add(customersMenuEdit);
			customersMenuEdit.setAction(parentFrame.getAppActionMap().get(
					"showCustomers"));
			customersMenuEdit.setText("Manage Customers");
			customersMenuEdit.setAccelerator(KeyStroke.getKeyStroke("control C"));

		}
		add(optionsMenu);
		optionsMenu.setMnemonic('O');
		{
			{
				// Look and Feels
				// FINALLY FIXED!!!
				// Had to call SwingUtilities.refreshLookAndFeel
				// with reference to parent JFrame
				// - Will Dixon

				// Add already installed Looks and Feels
				LookAndFeelInfo[] skins = UIManager.getInstalledLookAndFeels();
				// Add menu items for each LookAndFeel
				int i = 0;
				for (LookAndFeelInfo skin : skins)
				{

					JMenuItem skinMenuItem = new JMenuItem(skin.getName());
					// Must declare final to reference within ActionListener
					final String skinName = skin.getClassName();

					// Add detected LNFs
					skinMenuItem.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							try
							{
								UIManager.setLookAndFeel(skinName);
								SwingUtilities
										.updateComponentTreeUI(parentFrame);
							}
							catch (ClassNotFoundException e1)
							{
								e1.printStackTrace();
							}
							catch (InstantiationException e1)
							{
								e1.printStackTrace();
							}
							catch (IllegalAccessException e1)
							{
								e1.printStackTrace();
							}
							catch (UnsupportedLookAndFeelException e1)
							{
								e1.printStackTrace();
							}

						}
					});
					
					skinsMenu.add(skinMenuItem);
					skinMenuItem.setAccelerator(KeyStroke.getKeyStroke("control "+i++));
				}

				// Add our LNFs attached as jars
				JMenuItem skinMenuItem = new JMenuItem("Mac");
				skinMenuItem.setAccelerator(KeyStroke.getKeyStroke("control "+i++));
				skinMenuItem
						.addActionListener(newSkinMenuAction("ch.randelshofer.quaqua.QuaquaLookAndFeel"));
				skinsMenu.add(skinMenuItem);

				skinMenuItem = new JMenuItem("NimROD");
				skinMenuItem.setAccelerator(KeyStroke.getKeyStroke("control "+i++));
			skinMenuItem.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							UIManager
									.setLookAndFeel(new com.nilo.plaf.nimrod.NimRODLookAndFeel());
							SwingUtilities.updateComponentTreeUI(parentFrame);
						}
						catch (UnsupportedLookAndFeelException e1)
						{
							// printStackTrace();
						}
					}
				});
				skinsMenu.add(skinMenuItem);
			}
			optionsMenu.add(skinsMenu);
			skinsMenu.setMnemonic('L');

		}
		add(supportMenu);
		supportMenu.setMnemonic('U');
		{
			supportMenu.add(helpSupport).setAccelerator(KeyStroke.getKeyStroke("control H"));
			helpSupport.setAction(parentFrame.getAppActionMap().get(
					"showSupport"));
			helpSupport.setText("Chat Interface");
			helpSupport.setAccelerator(KeyStroke.getKeyStroke("control I"));
		}
		add(helpMenu);
		helpMenu.setMnemonic('H');
		{
			helpMenu.add(helpFAQ).setAccelerator(KeyStroke.getKeyStroke("control F"));
			helpMenu.add(helpAbout).setAccelerator(KeyStroke.getKeyStroke("control A"));
		}
	}

	private ActionListener newSkinMenuAction(final String skinClassPath)
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					UIManager.setLookAndFeel(skinClassPath);
					SwingUtilities.updateComponentTreeUI(parentFrame);
				}
				catch (ClassNotFoundException e1)
				{
					e1.printStackTrace();
				}
				catch (InstantiationException e1)
				{
					e1.printStackTrace();
				}
				catch (IllegalAccessException e1)
				{
					e1.printStackTrace();
				}
				catch (UnsupportedLookAndFeelException e1)
				{
					e1.printStackTrace();
				}
			}
		};
	}

}
