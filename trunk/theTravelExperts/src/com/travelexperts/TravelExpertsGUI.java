package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import javax.jnlp.UnavailableServiceException;
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

	// The MDI "parent container"
    private JDesktopPane desktopPane = new JDesktopPane();

    // Menu bar
    private MainMenuBar mainMenuBar = new MainMenuBar(this);

    // MDI Internal frames (all extend a respective JInternalFrame)
    private PackagesFrame packagesFrame = new PackagesFrame();
    private SuppliersFrame suppliersFrame = new SuppliersFrame();
    private ProductsFrame productsFrame = new ProductsFrame();
    private AgentsFrame agentsFrame = new AgentsFrame();
    private CustomersFrame customersFrame = new CustomersFrame();
    private UACustomersFrame uaCustomersFrame = new UACustomersFrame();
    private SupportServerFrame supportServerFrame = new SupportServerFrame();
    
    /**
     * Main constructor
     * @throws SQLException 
     * @throws InterruptedException 
     * @throws IOException 
     */
    public TravelExpertsGUI() throws SQLException, IOException, InterruptedException {

    	super("Travel Experts Management System");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	desktopPane.add(new LoginSystem(this));
    	
    	// Maximize and show the main form
    	add(desktopPane, BorderLayout.CENTER);    	// Attach MDI parent to Main Frame    	
    	pack();
    	setExtendedState(JFrame.MAXIMIZED_BOTH);
    	setVisible(true);
    	
    }
    
    // Called from the Login System if login is successful
    public void loadAllForms() {
    	// Attach children frames to parent frame, all not visible by default
    	setJMenuBar(mainMenuBar);
    	desktopPane.add(packagesFrame);
    	desktopPane.add(suppliersFrame);
    	desktopPane.add(productsFrame);
    	desktopPane.add(agentsFrame);
    	desktopPane.add(customersFrame);
    	desktopPane.add(uaCustomersFrame);
    	desktopPane.add(supportServerFrame);
    }
    
    @Action public void showPackages() {
    	packagesFrame.setVisible(true);
    }

    @Action public void showSuppliers() {
    	suppliersFrame.setVisible(true);
    }
    
    @Action public void showProducts() {
    	productsFrame.setVisible(true);
    }

    @Action public void showAgents() {
    	agentsFrame.setVisible(true);
    }

    @Action public void showCustomers() {
    	customersFrame.setVisible(true);
    }
    
    @Action public void showUnassigned() {
    	uaCustomersFrame.setVisible(true);
    }
    
    @Action public void showSupport() {
    	supportServerFrame.setVisible(true);
    }
    
    public ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
    
    public static void main(String[] args) {
    	EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
		        try {
		        	new TravelExpertsGUI();
				} catch (MalformedURLException e) {
					e.printStackTrace();
					TXLogger.logError(e.getMessage());
				} catch (SQLException e) {
					e.printStackTrace();
					TXLogger.logError(e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
					TXLogger.logError(e.getMessage());
				} catch (InterruptedException e) {
					e.printStackTrace();
					TXLogger.logError(e.getMessage());
				}
		        finally {
		        	TXLogger.logInfo("Application launched");
		        }
			}
		});
    }
}
