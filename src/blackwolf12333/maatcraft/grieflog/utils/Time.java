package blackwolf12333.maatcraft.grieflog.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Time {

	public static final String DATE_FORMAT_NOW = "HH:mm:ss";
	
	public String now() {	    
	    DateFormat df = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return day() + "-" + month() + "-" + year() + " " + df.format(new Date());
	}
	
	public String Date()
	{
	    return day() + "-" + month() + "-" + year();
	}
	
	public long getTime()
	{
		Date date = new Date();
		return date.getTime();
	}
	
	public int day() {
		GregorianCalendar calendar = new GregorianCalendar();
	    int day = calendar.get(Calendar.DATE);
	    return day;
	}
	
	public int month() {
		GregorianCalendar calendar = new GregorianCalendar();
		int month = calendar.get(Calendar.MONTH) + 1;
		return month;
	}
	
	public int year() {
		GregorianCalendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		return year;
	}
}
