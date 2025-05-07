package application;

import controller.MainController;

/* 
Programmer: Paul Xiong
	
Date: June 14, 2024

Title: Final Summative Project - PassLock

Description: This Java Swing application is a comprehensive password manager designed to securely store and manage multiple accounts and their associated passwords.
 Users can effortlessly create, edit, and delete passwords, ensuring their information is always up-to-date. The application features a powerful search function for 
 quick access and a security scanner to flag potential issues like duplicate or old passwords. With its user-friendly interface, this password manager makes it easy 
 for anyone to keep their credentials organized and secure.

Features: 
- Password hide option in login/sign up screen
- Functional search bar to filter keywords between account user, password and website. Placeholder text when clicking onto it will update accordingly
- Scan passwords which flag accounts with duplicate passwords or too old. displays info when hovering over warning icon
- External JDatePicker library used to allow for easy date picking user experience.
- Fetches website icons onto account cards (only applicable to some sites due to file mismatch)
- Number of account cards used to calculate size of scroll pane
- Multiple prompts to notify user to confirm their action or it went through (ex empty text fields, logout confirmation etc)
- Minor visual enhancements like highlighted and un-highlighted account cards when interacted, change of account viewer text, login screen tabs etc

Major Skills: Object Oriented Programming concepts, control statements, loops, Java Swing UI, UI design, data structures,
 external library use, inheritance using abstract classes, file reading & writing, composite objects, algorithms, MVC model


Areas of Concern:
- Filter section incomplete
- Lack of input validation. only checks for any character of input (mostly poses an issue with website). also username duplication
- JDatePicker feature 'x' button to remove date should not be a feature but cannot remove it 
 
 Notes:
 - Login details for pre-made account:
 	username: test
 	password: account
 
*/

// application class of entire project. starts the program
public class PasswordManagerApplication {

	public static void main(String[] args) {

		new MainController();

	}

}
