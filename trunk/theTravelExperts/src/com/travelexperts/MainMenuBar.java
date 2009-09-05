package com.travelexperts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MainMenuBar extends JMenuBar {

	// Menus
	private TravelExpertsGUI parentFrame;
    private JMenu helpMenu = new JMenu("Help");
    public JMenu optionsMenu = new JMenu("Options"); 
    private JMenu customersMenu = new JMenu("Customers");
    private JMenu agentsMenu = new JMenu("Agents");
    private JMenu productsMenu = new JMenu("Products");
    private JMenu suppliersMenu = new JMenu("Suppliers");
    private JMenu packagesMenu = new JMenu("Packages");
    private JMenu fileMenu = new JMenu("File");

    // Menu items
    public JMenuItem fileMenuExit = new JMenuItem("Exit", 'X');
    public JMenuItem packagesMenuEdit = new JMenuItem("Manage Packages", 'P');
    public JMenuItem productsMenuEdit = new JMenuItem("Edit Products", 'R');
    public JMenuItem suppliersMenuEdit = new JMenuItem("Edit Suppliers", 'S');
    public JMenuItem agentsMenuEdit = new JMenuItem("Manage Agents", 'A');
    public JMenuItem customersMenuEdit = new JMenuItem("Manage Customers", 'C');
    public JMenuItem customersMenuReport = new JMenuItem("Print Invoices", 'C');
    public JMenuItem helpFAQ = new JMenuItem("FAQ");
    public JMenuItem helpAbout = new JMenuItem("About");
    public JMenuItem helpSupport = new JMenuItem("Real-time Support");
    
    public  JCheckBoxMenuItem optionsErrorLog;
	
	public MainMenuBar(TravelExpertsGUI newParentFrame) {
		super();
		
		this.parentFrame = newParentFrame;
		
		// Build the menu
    	// Add menus and menu items
    	add(fileMenu);
    	{
    		// File->Exit
    		fileMenu.add(fileMenuExit);
    		fileMenuExit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
    	}
    	add(packagesMenu);
    	{
    		// Packages->Manage
    		packagesMenu.add(packagesMenuEdit);
    		packagesMenuEdit.setAction(parentFrame.getAppActionMap().get("showPackages"));
    	}
    	add(suppliersMenu);
    	{
    		suppliersMenu.add(suppliersMenuEdit);
    		suppliersMenuEdit.setAction(parentFrame.getAppActionMap().get("showSuppliers"));
    		suppliersMenuEdit.setText("Supplier Management");
    		try {
				suppliersMenuEdit.setIcon(new ImageIcon(new URL("http://t1.gstatic.com/images?q=tbn:2VZ5Jdb3EApnjM:http://www.aditnorth.org.uk/uploadedImages/partners_icon.gif")));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
    	}
    	add(productsMenu);
    	{
    		productsMenu.add(productsMenuEdit);
    		productsMenuEdit.setAction(parentFrame.getAppActionMap().get("showProducts"));
    		productsMenuEdit.setText("Supplier Management");
    		try {
				productsMenuEdit.setIcon(new ImageIcon(new URL("http://t0.gstatic.com/images?q=tbn:Ez4gvTSY8ob0jM:http://www.istockphoto.com/file_thumbview_approve/6885224/2/istockphoto_6885224-palm-tree.jpg")));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
}
    	add(agentsMenu);
    	{
    		agentsMenu.add(agentsMenuEdit);
    		agentsMenuEdit.setAction(parentFrame.getAppActionMap().get("showAgents"));
    	}
    	add(customersMenu);
    	{
    		customersMenu.add(customersMenuEdit);
    		customersMenuEdit.setAction(parentFrame.getAppActionMap().get("showCustomers"));
    		customersMenuReport.setAction(parentFrame.getAppActionMap().get("printInvoice"));
    	}
    	add(optionsMenu);
    	{
	   		optionsMenu.add(new JMenuItem("Preferences"));
    		optionsMenu.add(new JCheckBoxMenuItem("Verbose Debug Logging"));
    		
    	}
    	add(helpMenu);
    	{
    		helpMenu.add(helpFAQ);
    		helpMenu.add(helpAbout);
    		helpMenu.add(helpSupport);
    		helpSupport.setAction(parentFrame.getAppActionMap().get("showSupport"));
    	}		
	}
}
