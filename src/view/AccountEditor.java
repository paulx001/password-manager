package view;

import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import org.jdatepicker.impl.*;

import model.Account;
import model.CustomFormat;

// significant component of dashboard panel. handles the visual components when user wants to create, edit, or delete an 'account' password
public class AccountEditor extends JPanel {

	// fields \\
	
	// account information
	private Account account;
	private String type;

	// text field components with respective labels
	private String[] infoTypes = new String[] { "Username", "Password", "Website", "Date" };
	private HashMap<String, JLabel> infoLabels;
	private HashMap<String, JTextField> infoFields;

	// main buttons
	private JDatePickerImpl dateBtn;
	private JButton cancelBtn;
	private JButton actionBtn;
	private JButton deleteBtn;
	
	
	// constructor method
	public AccountEditor(String type, Account account) {

		// initialize fields
		this.type = type;
		this.account = account;

		// set up panel
		setBorder(new EmptyBorder(5, 50, 0, 0));
		setPreferredSize(new Dimension(300, 450));
		setBackground(new Color(211, 211, 211));
		setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		// initialize data structure components
		infoLabels = new HashMap<>();
		infoFields = new HashMap<>();

		// loop through info types to create labels and text fields for all
		for (String infoType : infoTypes) {

			// create label for info type and store it
			JLabel infoLabel = new JLabel(infoType + ":");
			infoLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
			infoLabel.setPreferredSize(new Dimension(250, 25));
			add(infoLabel);
			infoLabels.put(infoType, infoLabel);

			// create text field for info type (except date which is handled later)
			if (!infoType.equals("Date")) {
				JTextField infoField = new JTextField();
				infoField.setPreferredSize(new Dimension(250, 25));
				add(infoField);
				infoFields.put(infoType, infoField);
			}

		}

		// JDatePicker implementation source: https://www.youtube.com/watch?v=LpCxpnhVxbw
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		SqlDateModel model = new SqlDateModel();
		model.setSelected(true);		
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

		dateBtn = new JDatePickerImpl(datePanel, new CustomFormat());
		dateBtn.getJFormattedTextField().setBackground(Color.white);
		dateBtn.getJFormattedTextField().setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		infoFields.put("Date", dateBtn.getJFormattedTextField());
		dateBtn.setPreferredSize(new Dimension(250, 25));
		add(dateBtn);

		// create padding between text fields and buttons
		JLabel padding = new JLabel();
		padding.setPreferredSize(new Dimension(300, 15));
		add(padding);
		
		// create cancel button
		cancelBtn = new JButton("Cancel");
		cancelBtn.setFocusPainted(false);
		cancelBtn.setPreferredSize(new Dimension(100, 30));
		add(cancelBtn);

		// create gap between 2 main buttons
		add(Box.createRigidArea(new Dimension(50, 0)));

		// create action button to 'create' mode
		if (type.equals("create")) {

			// create action button
			actionBtn = new JButton("Create");
			actionBtn.setFocusPainted(false);
			actionBtn.setPreferredSize(new Dimension(100, 30));
			add(actionBtn);

		}

		// set action button and panel into 'view' mode that allows editing and deletion of accounts
		else if (type.equals("view")) {

			// create action button
			actionBtn = new JButton("Edit");
			actionBtn.setFocusPainted(false);
			actionBtn.setPreferredSize(new Dimension(100, 30));
			add(actionBtn);
			
			// create delete button. make sure to put it at the top
			deleteBtn = new JButton("Delete");
			deleteBtn.setFocusable(false);
			deleteBtn.setForeground(Color.white);
			deleteBtn.setBackground(new Color(230,0,0));
			deleteBtn.setPreferredSize(new Dimension(250, 30));
			add(deleteBtn, 0);
			
			// set text fields with pre-existing data
			infoFields.get("Username").setText(account.getUsername());
			infoFields.get("Password").setText(account.getPassword());
			infoFields.get("Website").setText(account.getWebsite());
			
			// if account has a date created
			if (!account.getDateString().equals("")) {
				model.setDate(account.getDate().getYear(), account.getDate().getMonth() - 1, account.getDate().getDay());
				
				dateBtn.getJFormattedTextField().setText(account.getDateString());
			}

		}

	}

	
	// getters & setters \\
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public HashMap<String, JTextField> getInfoFields() {
		return infoFields;
	}

	public void setInfoFields(HashMap<String, JTextField> infoFields) {
		this.infoFields = infoFields;
	}

	public JDatePickerImpl getDateBtn() {
		return dateBtn;
	}

	public void setDateBtn(JDatePickerImpl dateBtn) {
		this.dateBtn = dateBtn;
	}

	public JButton getActionBtn() {
		return actionBtn;
	}

	public void setActionBtn(JButton actionBtn) {
		this.actionBtn = actionBtn;
	}

	public JButton getCancelBtn() {
		return cancelBtn;
	}

	public void setCancelBtn(JButton cancelBtn) {
		this.cancelBtn = cancelBtn;
	}

	public JButton getDeleteBtn() {
		return deleteBtn;
	}

	public void setDeleteBtn(JButton deleteBtn) {
		this.deleteBtn = deleteBtn;
	}

}
