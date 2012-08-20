package tk.blackwolf12333.grieflog.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Time {

	// time format
	public static final String DATE_FORMAT_NOW = "HH-mm-ss";
	GregorianCalendar calendar = new GregorianCalendar();

	// get the current time
	public String now() {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT_NOW);
		return day() + "-" + month() + "-" + year() + " " + df.format(new Date());
	}

	// get today's day
	public int day() {
		return calendar.get(Calendar.DATE);
	}

	// get this month
	public int month() {
		return calendar.get(Calendar.MONTH) + 1;
	}

	// get the year we live in
	public int year() {
		return calendar.get(Calendar.YEAR);
	}

	public static long getTimeStamp(String strDate) {
		long time = 0;
		Date date = null;
		try {
			DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
			date = format.parse(strDate);
			time = date.getTime();
		} catch (Exception e) {

		}
		return time;
	}
}
