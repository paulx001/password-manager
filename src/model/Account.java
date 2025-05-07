package model;

// template object of password 'accounts'. this includes the info of what is displayed on the account cards
public class Account {

	// fields
    private String username;
    private String password;
    private String website;
    private String dateString;
    private MyDate date;
    
    // constructor method
	public Account(String username, String password, String website, String date) {
		this.username = username;
		this.password = password;
		setWebsite(website);
		dateString = date;
		setDate(date);
	}
	
	// getters & setters \\
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public MyDate getDate() {
		return date;
	}

	// set up date based on string conversion
	public void setDate(String dateString) {
		
		if (dateString.equals(""))
			date = null;
			
		else {
			
			String[] yearMonthDay = dateString.split("-");

			int year = Integer.valueOf(yearMonthDay[0]);
			int month = Integer.valueOf(yearMonthDay[1]);
			int day = Integer.valueOf(yearMonthDay[2]);

			this.date = new MyDate(month, day, year);

		}
		
	}
	
	
}
