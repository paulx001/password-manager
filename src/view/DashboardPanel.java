package view;

import javax.swing.*;

import model.Account;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// this class handles the visual components of the dashboard frame and mainly the account list scrolling frame
public class DashboardPanel extends JPanel {

	// main panel holders
	private JPanel headerPanel;
	private JPanel filterPanel;
	private JPanel accountListPanel;
	private JPanel accountViewPanel;

	// filter components
	private JLabel filterLabel;
	// private JCheckBox[] filter;

	// account list components
	private JTextField searchBar;
	private JButton scanBtn;
	private JLabel accountListLabel;
	private JPanel listPanel;
	private JScrollPane scrollPane;
	private HashMap<String, AccountCard> accountCardList = new HashMap<>();

	// account view components
	private JLabel accountViewLabel;

	// header components
	private JLabel userIcon;
	private JLabel userLabel;
	private JButton logoutBtn;
	private AccountCard addUserCard;

	// constructor method
	public DashboardPanel() {

		// set up panel
		setSize(1280, 720);
		setLayout(null);
		
		// initialize header panel
		headerPanel = new JPanel();
		headerPanel.setBackground(new Color(211, 211, 211));
		headerPanel.setBounds(0, 0, 1280, 100);
		setUpHeaderPanel();
		add(headerPanel);

		// initialize filter panel
		filterPanel = new JPanel();
		filterPanel.setBackground(new Color(211, 211, 211));
		filterPanel.setBounds(45, 150, 250, 500);
		setUpFilterPanel();
		add(filterPanel);

		// initialize account list panel
		accountListPanel = new JPanel();
		accountListPanel.setBackground(new Color(211, 211, 211));
		accountListPanel.setBounds(340, 150, 500, 500);
		setUpAccountListPanel();
		add(accountListPanel);

		// initialize account view panel
		accountViewPanel = new JPanel();
		accountViewPanel.setBackground(new Color(211, 211, 211));
		accountViewPanel.setBounds(885, 150, 350, 500);
		setUpAccountViewPanel();
		add(accountViewPanel);

	}

	// getters & setters \\
	
	public JPanel getAccountViewPanel() {
		return accountViewPanel;
	}

	public void setAccountViewPanel(JPanel accountViewPanel) {
		this.accountViewPanel = accountViewPanel;
	}

	public JLabel getAccountViewLabel() {
		return accountViewLabel;
	}

	public void setAccountViewLabel(JLabel accountViewLabel) {
		this.accountViewLabel = accountViewLabel;
	}
	
	public JLabel getUserLabel() {
		return userLabel;
	}

	public void setUserLabel(JLabel userLabel) {
		this.userLabel = userLabel;
	}

	public JPanel getListPanel() {
		return listPanel;
	}

	public void setListPanel(JPanel listPanel) {
		this.listPanel = listPanel;
	}

	public JTextField getSearchBar() {
		return searchBar;
	}

	public void setSearchBar(JTextField searchBar) {
		this.searchBar = searchBar;
	}

	public JButton getLogoutBtn() {
		return logoutBtn;
	}

	public void setLogoutBtn(JButton logoutBtn) {
		this.logoutBtn = logoutBtn;
	}
	
	public JButton getScanBtn() {
		return scanBtn;
	}

	public void setScanBtn(JButton scanBtn) {
		this.scanBtn = scanBtn;
	}

	public HashMap<String, AccountCard> getAccountCardList() {
		return accountCardList;
	}

	public void setAccountCardList(HashMap<String, AccountCard> accountCardList) {
		this.accountCardList = accountCardList;
	}

	public AccountCard getAddUserCard() {
		return addUserCard;
	}

	public void setAddPlayerCard(AccountCard addUserCard) {
		this.addUserCard = addUserCard;
	}

	
	// utility methods \\
	
	// this method creates key components of header panel
	private void setUpHeaderPanel() {

		// set panel layout
		headerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 25, 25));

		// create placeholder account user image
		userIcon = new JLabel();
		userIcon.setIcon(new ImageIcon("img/user_icon.png"));
		userIcon.setPreferredSize(new Dimension(50, 50));
		headerPanel.add(userIcon);

		// displays user's name
		userLabel = new JLabel("test account");
		userLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
		userLabel.setPreferredSize(new Dimension(960, 50));
		headerPanel.add(userLabel);

		// creates log out button
		logoutBtn = new JButton("Logout");
		logoutBtn.setFont(new Font("Verdana", Font.PLAIN, 16));
		logoutBtn.setForeground(Color.white);
		logoutBtn.setFocusPainted(false);
		logoutBtn.setBackground(new Color(230,0 , 0));
		logoutBtn.setPreferredSize(new Dimension(150, 50));
		headerPanel.add(logoutBtn);

	}

	// this method creates key components of filter panel
	private void setUpFilterPanel() {

		filterPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		filterLabel = new JLabel("Filter", JLabel.CENTER);
		filterLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		filterLabel.setPreferredSize(new Dimension(250, 50));
		filterPanel.add(filterLabel);

	}

	// this method creates key components of account list panel
	private void setUpAccountListPanel() {

		// set up panel layout
		accountListPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		// create text field for search bar. text starts gray to show place holder text
		searchBar = new JTextField("Search...");
		searchBar.setForeground(Color.GRAY);
		searchBar.setFont(new Font("Verdana", Font.PLAIN, 18));
		searchBar.setBounds(340, 125, 500, 25);
		add(searchBar);

		// create header of the panel
		accountListLabel = new JLabel("Account List", JLabel.CENTER);
		accountListLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		accountListLabel.setPreferredSize(new Dimension(500, 50));
		accountListPanel.add(accountListLabel);
		
		// create scan button to search for security issues
		scanBtn = new JButton("scan passwords");
		scanBtn.setFocusPainted(false);
		scanBtn.setFont(new Font("Verdana", Font.PLAIN, 12));
		scanBtn.setPreferredSize(new Dimension(150, 25));
		accountListPanel.add(scanBtn);

		// creates padding for more aesthetic
		JLabel padding = new JLabel();
		padding.setPreferredSize(new Dimension(500, 20));
		accountListPanel.add(padding);

		// create main panel where account cards are stored
		listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(400, 405));
		accountListPanel.add(listPanel);

		// create scroll pane based on list panel
		scrollPane = new JScrollPane(listPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10); // quicker scrolling
		scrollPane.setPreferredSize(new Dimension(500, 405));
		accountListPanel.add(scrollPane);

		// create "add account" button at the top of the scrolling frame
		addUserCard = new AccountCard(false, null, null, null);
		listPanel.add(addUserCard);

	}

	// this method creates key components of account view panel
	private void setUpAccountViewPanel() {

		// set up panel layout
		accountViewPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		// create placeholder header / label
		accountViewLabel = new JLabel("-------", JLabel.CENTER);
		accountViewLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
		accountViewLabel.setPreferredSize(new Dimension(350, 50));
		accountViewPanel.add(accountViewLabel);

	}

	// this method creates a new account card and displays onto scrolling frame. also adjust scrolling frame size accordinlgy
	public void createNewAccountCard(Account newAccount) {

		// get new account name
		String username = newAccount.getUsername();

		// create new account card and track into data structure
		AccountCard card = new AccountCard(true, username, newAccount.getDateString(), newAccount.getWebsite());
		accountCardList.put(username, card);
		listPanel.add(card);
		
		// set the size and update scrolling pane
		listPanel.setPreferredSize(new Dimension(400, accountCardList.size() * 58));
		listPanel.revalidate();

	}

}
