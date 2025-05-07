package controller;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.*;

import model.User;
import view.LoginPanel;

// this class is the controller for the login panel that validates login credentials and processes to open dashboard
public class LoginController implements ActionListener {

	// fields \\
	
	// login panel
	private LoginPanel loginPanel;

	// get user list of all accounts
	private HashMap<String, User> userList;

	// configurations
	private boolean isLoggingIn = true;
	private boolean passwordHidden = true;

	// constructor method
	public LoginController(LoginPanel loginPanel, HashMap<String, User> userList) {

		// set up fields
		this.userList = userList;
		this.loginPanel = loginPanel;

		// calls the action listener method
		loginPanel.getLoginSecBtn().addActionListener(this);
		loginPanel.getSignupSecBtn().addActionListener(this);
		loginPanel.getConfirmBtn().addActionListener(this);
		loginPanel.getTogglePassword().addActionListener(this);

	}

	// handles button clicks
	@Override
	public void actionPerformed(ActionEvent event) {

		// if user attempts to login or create account
		if (event.getSource() == loginPanel.getConfirmBtn()) {

			// get text fields
			String username = loginPanel.getInfoFields().get("Username").getText();
			String password = loginPanel.getInfoFields().get("Password").getText();

			// make sure neither of them are empty
			if (username.equals("") || password.equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter valid username and password");
				return;
			}

			// confirm button to log in
			if (isLoggingIn) {

				// declare variables
				String userID = "";
				User user = null;
				boolean userFound = false;
				boolean passMatched = false;
				
				// loop through user list to find matching login credentials
				for (Map.Entry<String, User> entry : userList.entrySet()) {

					// get user data
					userID = entry.getKey();
					User tempUser = entry.getValue();

					// user found
					if (tempUser.getUsername().equalsIgnoreCase(username))
						userFound = true;
					
					// username found that matches password
					if (tempUser.getPassword().equals(password)) {
						passMatched = true;
						user = tempUser;
						break;
					}

				}
				
				// user credentials found. logging in
				if (userFound && passMatched)
					MainController.loadUserDashboard(userID, user.getUsername());
				
				// found username but password does not match
				else if (userFound && !passMatched)
					JOptionPane.showMessageDialog(null, "Password does not match.");
				
				// username does not exist
				else 
					JOptionPane.showMessageDialog(null, "Account not found. Please check your credentials or create a new account.");

			}

			// if confirm button to create account
			else if (!isLoggingIn) {

				// give user a unique userID
				int userIDint = userList.size() + 1;
				String userID = String.valueOf(userIDint);

				// create new user object
				User newUser = new User(userID, username, password);
				userList.put(userID, newUser);

				// writing new data into user list file
				try {

					// create file writer object
					FileWriter myWriter = new FileWriter("user_passwords/user_list.txt", true);

					// put into their data
					myWriter.write(String.format("%s,%s,%s\n", userID, username, password));

					// finish writing and close file. notify user
					myWriter.close();
					JOptionPane.showMessageDialog(null, "Successfully created account.");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		// user wants to toggle the visibility of password field
		// source: https://stackoverflow.com/questions/20812857/how-to-display-characters-in-jpasswordfield-rather-than-sign-in-java
		else if (event.getSource() == loginPanel.getTogglePassword()) {

			// user wants to hide password (visible now)
			if (!passwordHidden) {
				passwordHidden = true;
				loginPanel.getInfoFields().get("Password").setEchoChar('•');
				// loginPanel.getInfoFields().get("Password").setEchoChar('*'); // if special character doesn't work

			} 
			
			// user wants to show password (hidden now)
			else {
				passwordHidden = false;
				loginPanel.getInfoFields().get("Password").setEchoChar((char) 0);
			}

		}

		// swapping between logging in and creating account
		else {

			// get panels of both
			JPanel oldPanel = null;
			JPanel currentPanel = null;

			// decide which frame to switch between based on which button is clicked and adjust their buttons
			
			// switching into log in frame
			if (event.getSource() == loginPanel.getLoginSecBtn()) {
				
				// verify not already in this frame
				if (!isLoggingIn) {
					isLoggingIn = true;
					loginPanel.getLoginSecBtn().setForeground(Color.gray);
					loginPanel.getSignupSecBtn().setForeground(Color.black);
					oldPanel = loginPanel.getSignupPanel();
					currentPanel = loginPanel.getLoginPanel();
				}
				
				else return;
			}

			// switching into sign up frame
			else if (event.getSource() == loginPanel.getSignupSecBtn()) {
				
				// verify not already in this frame
				if (isLoggingIn) {
					isLoggingIn = false;
					loginPanel.getLoginSecBtn().setForeground(Color.black);
					loginPanel.getSignupSecBtn().setForeground(Color.gray);
					oldPanel = loginPanel.getLoginPanel();
					currentPanel = loginPanel.getSignupPanel();
				}
				
				else return;
			}
			
			// reset text fields when switching tabs
			loginPanel.getInfoFields().get("Username").setText("");
			loginPanel.getInfoFields().get("Password").setText("");

			// start inserting components into the other panel
			
			// add info type label and fields
			for (String infoType : loginPanel.getInfoTypes()) {

				currentPanel.add(loginPanel.getInfoLabels().get(infoType));
				currentPanel.add(loginPanel.getInfoFields().get(infoType));

			}
			
			// add toggle password button
			currentPanel.add(loginPanel.getTogglePassword());

			// add padding too
			JLabel padding = new JLabel();
			padding.setPreferredSize(new Dimension(800, 30));
			currentPanel.add(padding);

			// adding confirm button
			currentPanel.add(loginPanel.getConfirmBtn());
			
			// adjust visibility accordingly
			oldPanel.removeAll(); // remove old padding on this basically
			oldPanel.setVisible(false);
			currentPanel.setVisible(true);
			
		}

	}

}