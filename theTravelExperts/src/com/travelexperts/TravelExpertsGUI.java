package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.ActionMap;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

/**
 * 	The main frame
 */
public class TravelExpertsGUI extends JFrame {
	
	private static final long serialVersionUID = -7769502223714046401L;
	
	// The connection string
	public static Connection connection = null;
	static {
		connection = new TXConnection().getInstance();
	}

	// The MDI "parent container"
    private JDesktopPane desktopPane = new JDesktopPane();
    
    // Menu bar
    private MainMenuBar mainMenuBar = new MainMenuBar(this);
   
    // Status bar
    private StatusBar statusBar = new StatusBar();
    
    // MDI Internal frames (all extend a respective JInternalFrame)
    private PackagesFrame packagesFrame = new PackagesFrame();
    private SuppliersFrame suppliersFrame = new SuppliersFrame();
    private ProductsFrame productsFrame = new ProductsFrame();
    private AgentsFrame agentsFrame = new AgentsFrame();
    private CustomersFrame customersFrame = new CustomersFrame();
    private SupportServerFrame supportServerFrame;
    
    // Used by LookAndFeel 
	String className = null;

    /**
     * Main constructor
     * @throws SQLException 
     * @throws InterruptedException 
     * @throws IOException 
     */
    public TravelExpertsGUI() throws SQLException, IOException, InterruptedException {

    	super("Travel Experts Management System");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	supportServerFrame = new SupportServerFrame();
    	
    	// Attach children forms to MDI parent, all not visible by default
    	desktopPane.add(packagesFrame);
    	desktopPane.add(suppliersFrame);
    	desktopPane.add(productsFrame);
    	desktopPane.add(agentsFrame);
    	desktopPane.add(customersFrame);
    	desktopPane.add(supportServerFrame);
    	desktopPane.setBackground(Color.BLACK);
    	    	
    	// Add content to main JPanel
    	setJMenuBar(mainMenuBar);
		// add(statusBar, BorderLayout.NORTH);
    	add(desktopPane, BorderLayout.CENTER);    	// Attach MDI parent to Main Frame
    	
    	// Maximize and show the main form
    	pack();
    	setExtendedState(JFrame.MAXIMIZED_BOTH);
    	setVisible(true);
    	
    	// Log the user on
    	//	// while(LoginSystem.showForm() == false) {};

    }
    	
    @Action
    public void showPackages() {
    	packagesFrame.setVisible(true);
    }

    @Action
    public void showSuppliers() {
    	suppliersFrame.setVisible(true);
    }
    
    @Action
    public void showProducts() {
    	productsFrame.setVisible(true);
    }

    @Action
    public void showAgents() {
    	agentsFrame.setVisible(true);
    }

    @Action
    public void showCustomers() {
    	customersFrame.setVisible(true);
    }
    
    @Action
    public void showSupport() {
    	supportServerFrame.setVisible(true);
    }
    
    @Action
    public void printInvoice() {
    	// Do stuff here
    	// Write invoice to xml file then use xslt to generate html or printable output
    }

    public ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
    
    public static void main(String[] args) {
        try {
        	new TravelExpertsGUI();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        finally {}
    }

}
