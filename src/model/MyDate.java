package model;

// this class is custom date object with day, month, year
// source: classroom textbook
public class MyDate {
	
	// fields
	private int month;
	private int day;
	private int year;
	
	// constructor method
	public MyDate(int month, int day, int year) {
		this.month = checkMonth(month);
		this.year = year;
		this.day = checkDay(day);
		
		// System.out.printf("Date object constructor for date %s\n", this);
	}
	
	// getters & setters
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	// utility methods \\
	
	// method validates month
	private int checkMonth(int testMonth) {
		if (testMonth > 0 && testMonth <= 12)
			return testMonth;
		else {
			System.out.printf("Invalid month %d set to 1.", testMonth);
			return 1;
		}
	}
	
	// method validates day
	private int checkDay(int testDay) {
		int[] daysPerMonth = {0,31,28,31,30,31,30,31,31,30,31,30,31};
		
		// check if day in range for month
		if (testDay > 0 && testDay <= daysPerMonth[month])
			return testDay;
		
		// check for leap year
		if (month == 2 && testDay == 29 && (year % 400 == 0 || year % 4 == 0 && year % 100 != 0))
			return testDay;
		
		System.out.printf("Invalid day %d set to 1.", testDay);
		return 1;
	}
	
	public String toString() {
		return String.format("%d/%d/%d", month, day, year);
	}
}
