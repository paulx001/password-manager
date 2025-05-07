package controller;

import javax.swing.*;

import model.Account;

import java.io.*;
import java.util.*;
import java.awt.Color;
import java.awt.event.*;

import view.AccountCard;
import view.AccountEditor;

// this is a main controller of the application. this implements the visual components of account viewer panel and writes to account data file
public class AccountEditorController implements ActionListener {

	// fields
	private JPanel parentPanel;
	private AccountEditor currentEditor;
	private AccountCard accountCard;

	// constructor method
	public AccountEditorController(JPanel parentPanel) {

		this.parentPanel = parentPanel;

	}

	
	// getter & setter \\
	
	public AccountEditor getCurrentEditor() {
		return currentEditor;
	}

	// add action listeners when new panel is created
	public void setCurrentEditor(AccountEditor currentEditor, AccountCard accountCard) {

//		if (this.currentEditor != null) {
//			this.currentEditor.getCancelBtn().removeActionListener(this);
//			this.currentEditor.getActionBtn().removeActionListener(this);
//		}

		this.currentEditor = currentEditor;
		this.accountCard = accountCard;

		currentEditor.getCancelBtn().addActionListener(this);
		currentEditor.getActionBtn().addActionListener(this);

		if (currentEditor.getDeleteBtn() != null)
			currentEditor.getDeleteBtn().addActionListener(this);

	}

	// handles all button clicks
	@Override
	public void actionPerformed(ActionEvent event) {

		// if user wants to cancel out of viewer panel
		if (event.getSource() == currentEditor.getCancelBtn()) {

			// reset header into placeholder text
			((JLabel) parentPanel.getComponent(0)).setText("-------");

			// if account card found reset its highlight
			if (accountCard != null)
				accountCard.setBackground(new Color(170, 170, 170));

			// update viewer panel to be empty
			parentPanel.remove(currentEditor);
			parentPanel.repaint();

		}

		// if user wants to perform an action (create account or edit data)
		else if (event.getSource() == currentEditor.getActionBtn()) {

			// loop through text fields first to ensure data is inputed
			for (Map.Entry<String, JTextField> entry : currentEditor.getInfoFields().entrySet()) {

				// get component values
				String infoType = entry.getKey();
				JTextField infoField = entry.getValue();

				// if text field is empty prompt user to enter something (website ignored/optional)
				if (infoField.getText().equals("") && !infoType.equals("Website")) {
					JOptionPane.showMessageDialog(null, String.format("Please enter info into %s.", infoType));
					return;

				}

			}

			// set up file reading paths
			String filePath = String.format("user_passwords/user%s_passwords.txt", MainController.userID);
			String tempFilePath = String.format("user_passwords/temp_user%s_passwords.txt", MainController.userID);
			HashMap<String, JTextField> infoFields = currentEditor.getInfoFields();
			FileWriter myWriter; // initialize writer object

			// get file
			try {

				// when creating account we just append data to the bottom
				if (currentEditor.getType().equals("create")) {

					// create file writer object
					myWriter = new FileWriter(filePath, true);

					// create new account based on data
					Account newAccount = new Account(infoFields.get("Username").getText(),
							infoFields.get("Password").getText(), infoFields.get("Website").getText(),
							infoFields.get("Date").getText());

					// record that data into the user's passwords
					myWriter.write(String.format("%s,%s,%s,%s\n", newAccount.getUsername(), newAccount.getPassword(),
							newAccount.getWebsite(), newAccount.getDateString()));

					// finish writing and close file. notify user
					myWriter.close();
					JOptionPane.showMessageDialog(null, "Successfully created account.");

					// add this new account into scrolling pane
					MainController.updateAccount(null, newAccount);

				}

				// editing information
				else if (currentEditor.getType().equals("view")) {

					// ensure desired files exist
					File originalFile = new File(filePath);
					File tempFile = new File(tempFilePath);

					// create file writer object
					myWriter = new FileWriter(tempFilePath);

					// create new scanner
					Scanner inputFile = new Scanner(originalFile);

					// get info of edited values
					String newUsername = infoFields.get("Username").getText();
					String newPassword = infoFields.get("Password").getText();
					String newWebsite = infoFields.get("Website").getText();
					String newDate = infoFields.get("Date").getText();
					
					// get old username to update data structures
					String oldUsername = "";

					// loop through data
					while (inputFile.hasNext()) {
						
						// split line into components
						String[] parts = inputFile.nextLine().split(",");

						// fields with ternary operators
						String username = parts[0];
						String password = parts[1];
						String website = parts.length > 2 ? parts[2] : "";
						String date = parts.length > 3 ? parts[3] : "";

						// check if the username matches the one we want to edit and write updated info
						if (username.equals(currentEditor.getAccount().getUsername())) {
							myWriter.write(String.format("%s,%s,%s,%s\n", newUsername, newPassword, newWebsite, newDate));
							oldUsername = username;
						} 
						
						// write the original info
						else {
							myWriter.write(String.format("%s,%s,%s,%s\n", username, password, website, date));
						}
					}

					// close the scanner and writer
					inputFile.close();
					myWriter.close();

					// delete the original file and temp becomes new file
					if (originalFile.delete()) {
						
						// rename the temp file to the original file name
						if (tempFile.renameTo(originalFile)) {
							System.out.println("file updated successfully.");
						} 
						
						// track error
						else {
							System.out.println("failed to rename temp file.");
						}
					} 
					
					// track error
					else {
						System.out.println("failed to delete the original file.");
					}

					// update account into data structures and also editing data on account card
					MainController.updateAccount(oldUsername, new Account(newUsername, newPassword, newWebsite, newDate));

				}

				
				// reset account viewer panel
				((JLabel) parentPanel.getComponent(0)).setText("-------");
				parentPanel.remove(currentEditor);
				parentPanel.repaint();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// when trying to delete an account
		else if (event.getSource() == currentEditor.getDeleteBtn()) {

			// prompt user before going through with it
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete account?",
					"Confirmation", dialogButton, JOptionPane.QUESTION_MESSAGE);

			// if user proceeds
			if (dialogResult == 0) {

				// set up file reading paths
				String filePath = String.format("user_passwords/user%s_passwords.txt", MainController.userID);
				String tempFilePath = String.format("user_passwords/temp_user%s_passwords.txt", MainController.userID);
				FileWriter myWriter;

				// get file
				try {

					
					// make sure files exist
					File originalFile = new File(filePath);
					File tempFile = new File(tempFilePath);
					
					// create writer object
					myWriter = new FileWriter(tempFilePath);

					// create new scanner
					Scanner inputFile = new Scanner(originalFile);

					// loop through data
					while (inputFile.hasNext()) {

						// split line into components
						String[] parts = inputFile.nextLine().split(",");

						// fields with ternary operators
						String username = parts[0];
						String password = parts[1];
						String website = parts.length > 2 ? parts[2] : "";
						String date = parts.length > 3 ? parts[3] : "";

						// only one line that does not fulfill this condition which is the one we want
						// to delete. find the line the target user is on and ignore it when reproducing data
						if (!username.equals(currentEditor.getAccount().getUsername()))
							myWriter.write(String.format("%s,%s,%s,%s\n", username, password, website, date));

					}

					// finish writing and close file.
					inputFile.close();
					myWriter.close();

					// delete original file and temp file becomes new one
					if (originalFile.delete()) {
						
						// rename the temp file to the original file name
						if (tempFile.renameTo(originalFile)) {
							System.out.println("file updated successfully.");
						} 
						
						// error checking
						else {
							System.out.println("failed to rename temp file.");
						}
					} 
					
					// error checking
					else {
						System.out.println("failed to delete the original file.");
					}
					

					// delete account card from scrolling frame
					MainController.updateAccount(currentEditor.getAccount().getUsername(), null);
					
					
					// update account viewer panel
					((JLabel) parentPanel.getComponent(0)).setText("-------");
					parentPanel.remove(currentEditor);
					parentPanel.repaint();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

}
