package view;

import javax.swing.*;
import java.awt.*;

// JFrame of the entire application. all the visual components are contained here
public class MainFrame extends JFrame {

	// declare essential panels
	private LoginPanel loginPanel;
	private DashboardPanel dashboardPanel;
	
	// constructor method
	public MainFrame() {
		
		// set up JFrame
		setSize(1280, 720); // set up JFrame size (16:9 ratio)
		setTitle("PassLock"); // set the title of the frame
		setIconImage(new ImageIcon("img/logo.png").getImage()); // set the icon of the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // end program when application closed
		setLocationRelativeTo(null); // display the JFrame at the center of the screen
		setResizable(false); // disable the button that resizes application

		// initialize login panel
		loginPanel = new LoginPanel();
		loginPanel.setVisible(true); // visible at start
		add(loginPanel);
		
		// initialize dashboard panel
		dashboardPanel = new DashboardPanel();
		dashboardPanel.setVisible(false); // not visible at start
		add(dashboardPanel);
		
		// set JFrame visible
		setVisible(true);
		
	}
	
	// getters & setters \\

	public LoginPanel getLoginPanel() {
		return loginPanel;
	}

	public void setLoginPanel(LoginPanel loginPanel) {
		this.loginPanel = loginPanel;
	}

	public DashboardPanel getDashboardPanel() {
		return dashboardPanel;
	}

	public void setDashboardPanel(DashboardPanel dashboardPanel) {
		this.dashboardPanel = dashboardPanel;
	}
	
}
