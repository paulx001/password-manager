package controller;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.event.*;

import model.FileReader;
import model.MyDate;
import model.User;
import model.Account;
import view.AccountCard;
import view.AccountEditor;
import view.MainFrame;

// main class of application. connects all classes/controllers together while handling dashboard components like clicking onto account card, log out button
public class MainController implements ActionListener, FocusListener, MouseListener, DocumentListener {

	// controller \\
	private static LoginController loginController; // handles login screen
	private static AccountEditorController accountEditController; // handles account creation, editing, deletion

	// file reading \\
	private static FileReader fileReader = new FileReader(); // retrieve valid accounts and their inputed passwords

	// GUI \\
	private static MainFrame mainFrame;

	// data structures \\
	public static HashMap<String, User> userList; // list of every user who has account
	public static HashMap<String, Account> accountList; // list of the users passwords

	// configuration \\
	private static AccountCard lastSelected; // track which account card being highlighted
	public static String userID; // record the current user's ID

	// constructor method
	public MainController() {

		// collect every account made to search valid login credentials
		userList = fileReader.getUserList();

		// create the JFrame and holder of the entire application
		mainFrame = new MainFrame();

		// create other main controllers
		loginController = new LoginController(mainFrame.getLoginPanel(), userList);
		accountEditController = new AccountEditorController(mainFrame.getDashboardPanel().getAccountViewPanel());

		// add all necessary listener events
		mainFrame.addMouseListener(this);
		mainFrame.getDashboardPanel().getSearchBar().addFocusListener(this);
		mainFrame.getDashboardPanel().getSearchBar().getDocument().addDocumentListener(this);
		mainFrame.getDashboardPanel().getLogoutBtn().addActionListener(this);
		mainFrame.getDashboardPanel().getScanBtn().addActionListener(this);
		mainFrame.getDashboardPanel().getAddUserCard().getButton().addActionListener(this);

	}

	// method called from login controller. with specific user we can start setting up dashboard panel
	public static void loadUserDashboard(String newUserID, String user) {

		// track user id
		userID = newUserID;

		// re-adjust panel visibility
		mainFrame.getDashboardPanel().setVisible(true);
		mainFrame.getLoginPanel().setVisible(false);

		// set user's name as the header label
		mainFrame.getDashboardPanel().getUserLabel().setText(user);
		
		// get the user's passwords
		accountList = fileReader.getUserAccountList(userID);

		// loop through pre-existing accounts and insert them into scrolling pane
		for (Map.Entry<String, Account> entry : accountList.entrySet()) {

			Account account = entry.getValue();
			mainFrame.getDashboardPanel().createNewAccountCard(account);

		}

		// with account cards created, loop through all of them to set up action listeners
		for (Map.Entry<String, AccountCard> entry : mainFrame.getDashboardPanel().getAccountCardList().entrySet()) {

			// get component values
			String username = entry.getKey();
			AccountCard accountCard = entry.getValue();

			// find button on account card and set up action listener (set it up here because of static context)
			JButton button = accountCard.getButton();
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// adjust account view panel text
					mainFrame.getDashboardPanel().getAccountViewLabel().setText("View Account");

					// if selecting onto new account while in previous frame, delete previous
					if (accountEditController.getCurrentEditor() != null)
						mainFrame.getDashboardPanel().getAccountViewPanel()
								.remove(accountEditController.getCurrentEditor());

					// reset the highlight of account card for same reason above
					if (lastSelected != null)
						lastSelected.setBackground(new Color(170, 170, 170));

					// set highlight to show selected
					accountCard.setBackground(new Color(190, 190, 190));
					
					// set as new currently selected item
					lastSelected = accountCard;

					// set up account edit controller
					AccountEditor viewUser = new AccountEditor("view", accountList.get(accountCard.getUsername()));
					accountEditController.setCurrentEditor(viewUser, accountCard);
					mainFrame.getDashboardPanel().getAccountViewPanel().add(viewUser);
					mainFrame.getDashboardPanel().getAccountViewPanel().repaint();
					mainFrame.getDashboardPanel().getAccountViewPanel().revalidate();
				}

			});

		}

	}

	// this method used to update accounts being created/edited/deleted
	public static void updateAccount(String oldUsername, Account newAccount) {

		// if creating new account default index of 0 so it appears at the top
		int oldIndex = 1;

		// if old username is provided we want to delete this value
		if (oldUsername != null) {
			// get the index of where this component is in-case we are replacing this data
			oldIndex = mainFrame.getDashboardPanel().getListPanel()
					.getComponentZOrder(mainFrame.getDashboardPanel().getAccountCardList().get(oldUsername));
			
			// remove account card based on index
			mainFrame.getDashboardPanel().getListPanel().remove(oldIndex);
			mainFrame.getDashboardPanel().getListPanel().revalidate();

			// remove data form data structures
			accountList.remove(oldUsername);
			mainFrame.getDashboardPanel().getAccountCardList().remove(oldUsername);
		}

		
		// if new account is provided, we want to create new account or replace old one
		if (newAccount != null) {

			// put in new data into account list and corresponding account card
			accountList.put(newAccount.getUsername(), newAccount);
			mainFrame.getDashboardPanel().createNewAccountCard(newAccount);

			// get account card and set up its action events
			AccountCard accountCard = mainFrame.getDashboardPanel().getAccountCardList().get(newAccount.getUsername());
			
			// either set new card at the top (initial index 1) or at old location (oldIndex we found)
			mainFrame.getDashboardPanel().getListPanel().add(accountCard, oldIndex);

			// create action listener because of static context...
			accountCard.getButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// adjust account view panel text
					mainFrame.getDashboardPanel().getAccountViewLabel().setText("View Account");

					// if selecting onto new account while in previous frame, delete previous
					if (accountEditController.getCurrentEditor() != null)
						mainFrame.getDashboardPanel().getAccountViewPanel()
								.remove(accountEditController.getCurrentEditor());

					// reset the highlight of account card for same reason above
					if (lastSelected != null)
						lastSelected.setBackground(new Color(170, 170, 170));

					// set highlight to show selected
					accountCard.setBackground(new Color(190, 190, 190));
					
					// set as new currently selected item
					lastSelected = accountCard;

					// set up account edit controller
					AccountEditor viewUser = new AccountEditor("view", accountList.get(accountCard.getUsername()));
					accountEditController.setCurrentEditor(viewUser, accountCard);
					mainFrame.getDashboardPanel().getAccountViewPanel().add(viewUser);
					mainFrame.getDashboardPanel().getAccountViewPanel().repaint();
					mainFrame.getDashboardPanel().getAccountViewPanel().revalidate();
					
				}

			});

		}

	}

	// this method is part of the scanning process. displays warning label on specific account cards based on secuirty issues
	private static void setWarningLabel(String username, String toolTiptext) {
		JLabel warningLabel = mainFrame.getDashboardPanel().getAccountCardList().get(username).getWarningLabel();
		warningLabel.setVisible(true);

		String oldTooltip = warningLabel.getToolTipText();
		
		if (oldTooltip != null && oldTooltip.contains(toolTiptext))
			return;

		if (oldTooltip != null)
			warningLabel.setToolTipText(oldTooltip + ", " + toolTiptext);
		
		else
			warningLabel.setToolTipText(toolTiptext);
	}

	// handles button actions
	@Override
	public void actionPerformed(ActionEvent event) {

		// if trying to log out
		if (event.getSource() == mainFrame.getDashboardPanel().getLogoutBtn()) {
			
			// prompt user for verification
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Confirmation",
					dialogButton, JOptionPane.QUESTION_MESSAGE);

			// wants to log out and proceed back to log in screen
			if (dialogResult == 0) {

				// delete current frame
				mainFrame.dispose();

				// create new controller
				new MainController();

			}

		}

		// if user clicks on 'add account' button
		else if (event.getSource() == mainFrame.getDashboardPanel().getAddUserCard().getButton()) {

			// get rid of previous account viewer panel
			if (accountEditController.getCurrentEditor() != null)
				mainFrame.getDashboardPanel().getAccountViewPanel().remove(accountEditController.getCurrentEditor());

			// setup account viewer panel
			AccountEditor createUser = new AccountEditor("create", null);
			accountEditController.setCurrentEditor(createUser, null);

			// adjust panel
			mainFrame.getDashboardPanel().getAccountViewLabel().setText("Create Account");
			mainFrame.getDashboardPanel().getAccountViewPanel().add(createUser);
			mainFrame.getDashboardPanel().getAccountViewPanel().repaint();
			mainFrame.getDashboardPanel().getAccountViewPanel().revalidate();

		}

		// if user wants to scan their accounts for security issues
		else if (event.getSource() == mainFrame.getDashboardPanel().getScanBtn()) {

			// create a list to track duplicate passwords
			HashMap<String, String> usedPasswords = new HashMap<>();

			// loop through all passwords
			for (Map.Entry<String, Account> entry : accountList.entrySet()) {
				
				// get the username and account
				String username = entry.getKey();
				Account account = entry.getValue();

				// get the account password and date
				String password = account.getPassword();
				MyDate accountDate = account.getDate();

				// calculate the account age in years
				LocalDate currentDate = LocalDate.now();
				int accountAgeYears = currentDate.getYear() - accountDate.getYear();

				// if 5 years old this is considered old and flag this
				if (accountAgeYears > 5) {
					setWarningLabel(username, "Old Password");
				}

				// check if duplicate passwords exists at this point
				if (!usedPasswords.containsKey(password)) {
					
					// this account becomes the new reference point for duplication
					usedPasswords.put(password, username);

				}

				// this means there is duplicate password
				else {

					// flag first instance of password
					String storedUsername = usedPasswords.get(password);

					// set the warning label for both accounts
					setWarningLabel(username, "Duplicate Password");
					setWarningLabel(storedUsername, "Duplicate Password");

					// this becomes new reference for the password
					usedPasswords.put(password, username);

				}

			}

		}

		// ** old action for edit button but don't work here because of static context..
		// else {
		//
		// for (Map.Entry<String, AccountCard> entry :
		// mainFrame.getDashboardPanel().getAccountCardList().entrySet()) {
		//
		// //
		// AccountCard accountCard = entry.getValue();
		//
		// JButton button = accountCard.getButton();
		//
		// if (event.getSource() == button) {
		//
		// mainFrame.getDashboardPanel().getAccountViewLabel().setText("View
		// Account");
		//
		// if (accountEditController.getCurrentEditor() != null)
		// mainFrame.getDashboardPanel().getAccountViewPanel().remove(accountEditController.getCurrentEditor());
		//
		// if (lastSelected != null)
		// lastSelected.setBackground(new Color(170, 170, 170));
		//
		// accountCard.setBackground(new Color(190, 190, 190));
		// lastSelected = accountCard;
		//
		// AccountEditor viewUser = new AccountEditor("view",
		// accountList.get(accountCard.getUsername()));
		// accountEditController.setCurrentEditor(viewUser, accountCard);
		// mainFrame.getDashboardPanel().getAccountViewPanel().add(viewUser);
		// mainFrame.getDashboardPanel().getAccountViewPanel().repaint();
		// mainFrame.getDashboardPanel().getAccountViewPanel().revalidate();
		//
		// }
		//
		// }
		//
		// }

	}

	// when clicking on search bar with placeholder text, enable normal text
	@Override
	public void focusGained(FocusEvent event) {

		JTextField searchBar = (JTextField) event.getSource();

		if (searchBar.getText().equals("Search...")) {
			searchBar.setText("");
			searchBar.setForeground(Color.BLACK);
		}

	}

	// when clicking out on search bar with empty text, default to placeholder text
	@Override
	public void focusLost(FocusEvent event) {

		JTextField searchBar = (JTextField) event.getSource();

		if (searchBar.getText().isEmpty()) {
			searchBar.setForeground(Color.GRAY);
			searchBar.setText("Search...");
		}

	}

	// this method allows user to click off text fields and will reset its focus
	@Override
	public void mouseClicked(MouseEvent event) {

		if (mainFrame.getFocusOwner() != null && mainFrame.getFocusOwner().getClass() == JTextField.class) {
			mainFrame.requestFocus();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	// when text is added in search bar, filter through all accounts too see if there is a matching key word
	@Override
	public void insertUpdate(DocumentEvent e) {
		for (Map.Entry<String, Account> entry : accountList.entrySet()) {

			// get component values
			String username = entry.getKey();
			Account account = entry.getValue();

			// convert text to lower (should have used equalsIgnorecase)
			String lowerResult = mainFrame.getDashboardPanel().getSearchBar().getText().toLowerCase();

			// if there is not a match
			if (!account.getUsername().toLowerCase().contains(lowerResult)
					&& !account.getPassword().toLowerCase().contains(lowerResult)
					&& !account.getWebsite().toLowerCase().contains(lowerResult) && !lowerResult.equals("search...")) {

				// hide the account cards
				AccountCard accountCard = mainFrame.getDashboardPanel().getAccountCardList().get(username);

				if (accountCard.getParent() == mainFrame.getDashboardPanel().getListPanel()) {
					mainFrame.getDashboardPanel().getListPanel().remove(accountCard);
					mainFrame.getDashboardPanel().getListPanel().repaint();
					mainFrame.getDashboardPanel().getListPanel().revalidate();

				}

			}

		}
	}

	// when text is removed in search bar, filter through all accounts too see if there is a matching key word
	@Override
	public void removeUpdate(DocumentEvent e) {
		for (Map.Entry<String, Account> entry : accountList.entrySet()) {

			// get component values
			String username = entry.getKey();
			Account account = entry.getValue();

			// convert text to lower (should have used equalsIgnorecase)
			String lowerResult = mainFrame.getDashboardPanel().getSearchBar().getText().toLowerCase();

			
			// if there is a match
			if (account.getUsername().toLowerCase().contains(lowerResult)
					|| account.getPassword().toLowerCase().contains(lowerResult)
					|| account.getWebsite().toLowerCase().contains(lowerResult) || lowerResult.equals("")) {

				
				// 'bring back' hidden account cards
				AccountCard accountCard = mainFrame.getDashboardPanel().getAccountCardList().get(username);

				if (accountCard.getParent() != mainFrame.getDashboardPanel().getListPanel()) {
					mainFrame.getDashboardPanel().getListPanel().add(accountCard);
					mainFrame.getDashboardPanel().getListPanel().repaint();
					mainFrame.getDashboardPanel().getListPanel().revalidate();

				}

			}

		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// System.out.println("changed");
	}

}