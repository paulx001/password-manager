package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.HashMap;

// This class is the login panel that is used to display all the unimplemented visual components of the login panel.
public class LoginPanel extends JPanel {

	// main panel holders
	private JPanel loginPanel;
	private JPanel signupPanel;

	// set up title of application
	private JLabel titleLabel;
	private JLabel iconLabel;
	
	// corresponding username, password label and text field
	private String[] infoTypes = new String[] { "Username", "Password"};
	private HashMap<String, JLabel> infoLabels = new HashMap<>();
	private HashMap<String, JPasswordField> infoFields = new HashMap<>();

	// main buttons
	private JButton togglePassword;
	private JButton confirmBtn;
	private JButton loginSecBtn;
	private JButton signupSecBtn;


	// constructor method
	public LoginPanel() {

		// set properties of the main frame
		setSize(1280, 720);
		
		// create initial padding from the top of the screen
		JLabel padding = new JLabel();
		padding.setPreferredSize(new Dimension(1280, 50));
		add(padding);
		
		// create icon image of application logo
		iconLabel = new JLabel();
		Image logo = new ImageIcon("img/logo.png").getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		iconLabel.setIcon(new ImageIcon(logo));
		iconLabel.setPreferredSize(new Dimension(100, 100));
		add(iconLabel);
		
		// add slight gap between icon and title
		add(Box.createRigidArea(new Dimension(20, 0)));
		
		// edits the properties of the title label
		titleLabel = new JLabel("PassLock");
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 55));
		titleLabel.setPreferredSize(titleLabel.getPreferredSize());
		add(titleLabel);
		
		// create more padding between title and 'tab' buttons
		JLabel padding1 = new JLabel();
		padding1.setPreferredSize(new Dimension(1280, 30));
		add(padding1);
		
		// editing the properties of the login tab button
		loginSecBtn = new JButton("Log In ");
		loginSecBtn.setContentAreaFilled(false);
		loginSecBtn.setBorder(null);
		loginSecBtn.setFocusPainted(false);
		loginSecBtn.setFont(new Font("Verdana", Font.PLAIN, 30));
		loginSecBtn.setForeground(Color.gray);
		loginSecBtn.setHorizontalAlignment(JLabel.RIGHT);
		loginSecBtn.setVerticalAlignment(JLabel.CENTER);
		loginSecBtn.setPreferredSize(new Dimension(600, 100));
		add(loginSecBtn);
		
		// create line between 2 buttons
		JLabel line = new JLabel("|");
		line.setFont(new Font("Verdana", Font.PLAIN, 50));
		line.setPreferredSize(line.getPreferredSize());
		add(line);

		// editing the properties of the signup tab button
		signupSecBtn = new JButton(" Register");
		signupSecBtn.setContentAreaFilled(false);
		signupSecBtn.setBorder(null);
		signupSecBtn.setFocusPainted(false);
		signupSecBtn.setFont(new Font("Verdana", Font.PLAIN, 30));
		signupSecBtn.setForeground(Color.black);
		signupSecBtn.setHorizontalAlignment(JLabel.LEFT);
		signupSecBtn.setVerticalAlignment(JLabel.CENTER);
		signupSecBtn.setPreferredSize(new Dimension(600, 100));
		add(signupSecBtn);
		
		// create login panel (login mode)
		loginPanel = new JPanel();
		loginPanel.setBackground(new Color(211, 211, 211));
		loginPanel.setPreferredSize(new Dimension(650, 250));
		add(loginPanel);
		
		// create signup panel (signup mode)
		signupPanel = new JPanel();
		signupPanel.setBackground(new Color(211, 211, 211));
		signupPanel.setPreferredSize(new Dimension(650, 250));
		signupPanel.setVisible(false);
		add(signupPanel);

		

		// creating label and textfields for username and password
		for (String infoType : infoTypes) {

			// create info type label
			JLabel infoLabel = new JLabel(infoType + ":");
			infoLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
			infoLabel.setPreferredSize(new Dimension(500, 25));
			loginPanel.add(infoLabel);
			infoLabels.put(infoType, infoLabel);

			// create info type text field
			JPasswordField infoField = new JPasswordField();
			infoField.setPreferredSize(new Dimension(500, 25));
			loginPanel.add(infoField);
			infoFields.put(infoType, infoField);
			
			// both password fields but username should not be hidden
			if (infoType.equals("Username"))
				infoField.setEchoChar((char) 0);

		}

		
		// create button that toggles between dotted or normal characters to show/hide password
		togglePassword = new JButton("show/hide password");
		togglePassword.setHorizontalAlignment(JLabel.LEFT);
		togglePassword.setForeground(Color.blue);
		togglePassword.setBorder(null);
		togglePassword.setContentAreaFilled(false);
		togglePassword.setFocusPainted(false);
		togglePassword.setPreferredSize(new Dimension(500, 25));
		loginPanel.add(togglePassword);
		
		// padding between text field components and confirm button
		JLabel padding2 = new JLabel();
		padding2.setPreferredSize(new Dimension(800, 30));
		loginPanel.add(padding2);

		// editing properties of the submit button
		confirmBtn = new JButton("Confirm");
		confirmBtn.setPreferredSize(new Dimension(500, 30));
		loginPanel.add(confirmBtn);

	}



	// getters & setters \\
	
	public String[] getInfoTypes() {
		return infoTypes;
	}

	
	public void setInfoTypes(String[] infoTypes) {
		this.infoTypes = infoTypes;
	}
	
	public HashMap<String, JLabel> getInfoLabels() {
		return infoLabels;
	}

	public void setInfoLabels(HashMap<String, JLabel> infoLabels) {
		this.infoLabels = infoLabels;
	}
	

	public HashMap<String, JPasswordField> getInfoFields() {
		return infoFields;
	}


	public void setInfoFields(HashMap<String, JPasswordField> infoFields) {
		this.infoFields = infoFields;
	}


	public JButton getConfirmBtn() {
		return confirmBtn;
	}


	public void setConfirmBtn(JButton confirmBtn) {
		this.confirmBtn = confirmBtn;
	}


	public JButton getLoginSecBtn() {
		return loginSecBtn;
	}
	

	public void setLoginSecBtn(JButton loginSecBtn) {
		this.loginSecBtn = loginSecBtn;
	}


	public JButton getSignupSecBtn() {
		return signupSecBtn;
	}


	public void setSignupSecBtn(JButton signupSecBtn) {
		this.signupSecBtn = signupSecBtn;
	}


	public JPanel getLoginPanel() {
		return loginPanel;
	}



	public void setLoginPanel(JPanel loginPanel) {
		this.loginPanel = loginPanel;
	}


	public JPanel getSignupPanel() {
		return signupPanel;
	}



	public void setSignupPanel(JPanel signupPanel) {
		this.signupPanel = signupPanel;
	}



	public JButton getTogglePassword() {
		return togglePassword;
	}



	public void setTogglePassword(JButton togglePassword) {
		this.togglePassword = togglePassword;
	}
	
	

}