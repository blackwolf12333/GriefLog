package tk.blackwolf12333.grieflog.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Time {

	// time format
	public static final String DATE_FORMAT_NOW = "HH:mm:ss";
	
	// get the current time
	public String now() {	    
	    DateFormat df = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return day() + "-" + month() + "-" + year() + " " + df.format(new Date());
	}
	
	// get the date
	public String Date()
	{
	    return day() + "-" + month() + "-" + year();
	}
	
	// get the current time
	public long getTime()
	{
		Date date = new Date();
		return date.getTime();
	}
	
	// get today's day
	public int day() {
		GregorianCalendar calendar = new GregorianCalendar();
	    int day = calendar.get(Calendar.DATE);
	    return day;
	}
	
	// get this month
	public int month() {
		GregorianCalendar calendar = new GregorianCalendar();
		int month = calendar.get(Calendar.MONTH) + 1;
		return month;
	}
	
	// get the year we live in
	public int year() {
		GregorianCalendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		return year;
	}
	
	public long getTimeStamp(String strDate) {
		long time = 0;
		Date date = null;
        try {
        	DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        	date = (Date)format.parse(strDate);
        	time = date.getTime();
        } catch(Exception e) {
        	
        }
        return time;
    }
}
