package tk.blackwolf12333.grieflog.utils.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import tk.blackwolf12333.grieflog.GriefLog;

public class Time {

	public static final String DATE_FORMAT = "dd-MM-yyyy HH-mm-ss";

	public String now() {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		return df.format(new Date());
	}

	public static Date getDate(String strDate) {
		Date date = null;
		try {
			DateFormat format = new SimpleDateFormat(DATE_FORMAT);
			date = format.parse(strDate);
		} catch (Exception e) {
			GriefLog.debug("Something went wrong with the date format parsing, tell this to blackwolf12333 please.");
		}
		return date;
	}
	
	public static String getTimeFrom(String time) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		int index = 0;
		int begin = 0;
		for(int i = 0; i < time.length(); i++) {
			if(isInt(time.charAt(index))) {
				index++;
			} else {
				switch(time.charAt(i)) {
				case 'd':
					day -= Integer.parseInt(time.substring(begin, index));
					begin+=index+1;
					break;
				case 'h':
					hour -= Integer.parseInt(time.substring(begin, index));
					begin+=index+1;
					break;
				case 'm':
					minute -= Integer.parseInt(time.substring(begin, index));
					begin+=index+1;
					break;
				case 's':
					second -= Integer.parseInt(time.substring(begin, index));
					begin+=index+1;
					break;
				}
				index+=1;
			}
		}
		
		calendar.set(year, month, day, hour, minute, second);
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		GriefLog.log.info("parsed time: " + df.format(calendar.getTime()));
		GriefLog.log.info("current time: " + df.format(new Date()));
		return df.format(calendar.getTime());
	}
	
	private static boolean isInt(char c) {
		try {
			Integer.parseInt(String.valueOf(c));
			return true;
		} catch(NumberFormatException e) {}
		return false;
	}
}
