package model;

// this class represents login credentials where every user has a unique numbered userID
public class User {

	// fields
	private String userID;
	private String username;
	private String password;

	// constructor method
	public User(String userID, String username, String password) {

		this.userID = userID;
		this.username = username;
		this.password = password;

	}

	// getters & setters \\
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

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

	

}