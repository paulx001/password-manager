package model;

import java.io.*;
import java.util.*;

// this class handles all the file reading. retrieves data on created accounts and their created passwords
public class FileReader {

	// this method gets every created account on the application
	public HashMap<String, User> getUserList() {

		// initialize data structure
		HashMap<String, User> userList = new HashMap<>();

		// reading in file
		try {

			// create new scanner
			Scanner inputFile = new Scanner(new File("user_passwords/user_list.txt"));

			// loop through data
			while (inputFile.hasNext()) {

				// split line into components
				String[] parts = inputFile.nextLine().split(",");

				// fields
				String userID = parts[0];
				String username = parts[1];
				String password = parts[2];
				
				// create new spill while adding to array list
				userList.put(userID, new User(userID, username, password));

			}

			inputFile.close();

		} catch (FileNotFoundException e) {
			// display error message
			System.out.println("File Error");

		}

		return userList;

	}
	
	// this method gets the user's created passwords given their userID
	public HashMap<String, Account> getUserAccountList(String userID) {

		// initialize data structure
		HashMap<String, Account> accountList = new HashMap<>();

		// reading in file
		try {

			// create new scanner
			Scanner inputFile = new Scanner(new File("user_passwords/user" + userID + "_passwords.txt"));


			// loop through data
			while (inputFile.hasNext()) {

				// split line into components
				String[] parts = inputFile.nextLine().split(",");

				// fields with ternary operators
				String username = parts[0];
				String password = parts[1];
				String website = parts.length > 2 ? parts[2] : "";
				String date = parts.length > 3 ? parts[3] : "";
				
				// create new spill while adding to array list
				accountList.put(username, new Account(username, password, website, date));

			}

			inputFile.close();

		} catch (FileNotFoundException e) {
			// display error message
			System.out.println("File Error");

		}

		return accountList;

	}

}
