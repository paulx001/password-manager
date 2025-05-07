package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField.AbstractFormatter;

import java.util.Calendar;

// class used along with JDatePicker to select date format
// source: https://www.youtube.com/watch?v=LpCxpnhVxbw
public class CustomFormat extends AbstractFormatter {

	@Override
	public Object stringToValue(String text) throws ParseException {
		return "";
	}

	@Override
	// convert date into specified date format
	public String valueToString(Object value) throws ParseException {

		if (value != null) {
			Calendar calendar = (Calendar) value;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = format.format(calendar.getTime());
			return strDate;
		}

		return "";
	}

}
