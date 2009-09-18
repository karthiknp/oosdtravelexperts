package com.travelexperts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ActionMap;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

/**
 * 	Main Frame
 * 
 * 	Contains main JDesktopPane with all our JInternalFrame's
 * 
 *	- Will Dixon
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
    private SupportServerFrame supportServerFrame = new SupportServerFrame();
    
	// TODO: implement static get/set status method's and update from relevant components
	private static String status = "<html>Travel Experts Manager prototype  -  " +
			"Database connection: <font color='#00FF00'>OK</font>, " +
			"Server status: <font color='#00FF00'>Online</font></html>";
    private static final JLabel lblStatus = new JLabel(status, SwingConstants.CENTER);
    /*
    private static void setStatus(String status) {
		TravelExpertsGUI.status = status;
		lblStatus.setText(TravelExpertsGUI.status);
	}
	private static String getStatus() {
		return status;
	}
	*/

	/**
     * Main constructor
     */
    public TravelExpertsGUI() throws SQLException, IOException, InterruptedException {

    	super("Travel Experts Management System");
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	desktopPane.setBackground(Color.LIGHT_GRAY);

    	pack();
    	setExtendedState(JFrame.MAXIMIZED_BOTH);
    	setVisible(true);
    	
    	desktopPane.add(new LoginSystem(this), JDesktopPane.CENTER_ALIGNMENT);

    	//loadAllForms();
    	
    	// Maximize and show the main form
    	add(desktopPane, BorderLayout.CENTER);    	// Attach MDI parent to Main Frame
    	add(lblStatus, BorderLayout.SOUTH);
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
    	customersFrame.refreshTable();
    }
        
    @Action public void showSupport() {
    	supportServerFrame.setVisible(true);
    }
    
    public ActionMap getAppActionMap() {
        return Application.getInstance().getContext().getActionMap(this);
    }
    
    public static void main(String[] args) {
    	// Insert into AWT EventQueue
    	EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
		        try {
		        	new TravelExpertsGUI();
				} 
		        catch (SQLException e) {
					e.printStackTrace();
					TXLogger.logError(e.getMessage());
				} 
		        catch (IOException e) {
					e.printStackTrace();
					TXLogger.logError(e.getMessage());
				} 
		        catch (InterruptedException e) {
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
