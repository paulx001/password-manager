package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.io.*;
import javax.imageio.*;

// this class contains java swing components of password 'accounts' which are to be displayed in scrolling frame
public class AccountCard extends JPanel {

	// fields \\
	
	// account info
	private String username;
	
	// GUI components
	private JLabel icon;
	private JLabel userLabel;
	private JLabel infoLabel;
	private JLabel warningLabel;
	
	// edit button
	private JButton button;
	
	// constructor method
	public AccountCard(boolean isAccount, String username, String date, String website) {
		
		// set up fields
		this.username = username;
		
		// set up panel
        setPreferredSize(new Dimension(470, 50));
        setBackground(new Color(170, 170, 170));
        setLayout(null);

        // create labels for user account
        if (isAccount) {
        	
            // create website icon (if applicable)
            icon = new JLabel();
            icon.setIcon(getWebIcon(website));
            icon.setBounds(13, 0, 50, 50);
            add(icon);

            // username label
            userLabel = new JLabel(username);
            userLabel.setFont(new Font("Verdana", Font.BOLD, 17));
            userLabel.setLocation(50, 5);
            userLabel.setSize(userLabel.getPreferredSize());
            add(userLabel);

            // info label (text is concatenated with date and website)
            infoLabel = new JLabel();
            infoLabel.setText(getInfoLabelText(date, website));
            infoLabel.setFont(new Font("Verdana", Font.PLAIN, 13));
            infoLabel.setLocation(50, 25);
            infoLabel.setSize(infoLabel.getPreferredSize());
            add(infoLabel);
            
            // flagged icon when specific account poses security issues
            warningLabel = new JLabel();
            warningLabel.setIcon(new ImageIcon("img/warning.png"));
            warningLabel.setVisible(false); // initially hidden until user scans
            warningLabel.setBounds(380, 0, 50, 50);
            add(warningLabel);

            // edit button
            button = new JButton();
            button.setIcon(new ImageIcon("img/edit.png"));
            button.setContentAreaFilled(false);
			button.setBorderPainted(false); 
			button.setFocusable(false);
            button.setBounds(410, 0, 50, 50);
            add(button);
            
        } 
        
        // default card (where you add a new user) simply a button with text
        else {
           
        	// create main button
            button = new JButton("+ Add Account");
            button.setFont(new Font("Verdana", Font.BOLD, 20));
            button.setFocusable(false);
            button.setSize(470, 50);
            add(button);
            
        }

	}

	
	// getters & setters \\
	
	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}
	
    public JLabel getWarningLabel() {
		return warningLabel;
	}


	public void setWarningLabel(JLabel warningLabel) {
		this.warningLabel = warningLabel;
	}

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	// utility methods \\
	
	// this method retrieves the provided website's icon
	private ImageIcon getWebIcon(String website) {
    	
    	// no valid site just give blank image
    	if (website.equals(""))
    			return new ImageIcon("img/blank.png");
    	
    	// attempt to get site icon
        try {

        	// get rid of www.
        	website = website.replace("www.", "");

            // attempt to fetch the icon
            URL url = new URL("https://favicone.com/" + website + "?s=25");
            BufferedImage image = ImageIO.read(url);
            
            // if image is found, apply image, otherwise default
            if (image != null) {
                return new ImageIcon(image);
            } else {
                // Return a default icon if favicon is not found
                return new ImageIcon("img/blank.png");
            }
            
    
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
	// this method concatenates the info label string depending on available data of website and date
	private String getInfoLabelText(String date, String website) {
		
		if (!date.equals("") && !website.equals(""))
			 return String.format("%s - %s", date, website);
		
		else if (!date.equals(""))
			 return date;
		
		else if (!website.equals(""))
			 return website;
		
		else
			return "";
		
	}
	
	
}
