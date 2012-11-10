package tk.blackwolf12333.grieflog.utils.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import tk.blackwolf12333.grieflog.GriefLog;

public class Time {

	public static final String DATE_FORMAT = "dd-MM-yyyy HH-mm-ss";

	public String now() {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		return df.format(new Date());
	}

	public static Date getTimeStamp(String strDate) {
		Date date = null;
		try {
			DateFormat format = new SimpleDateFormat(DATE_FORMAT);
			date = format.parse(strDate);
		} catch (Exception e) {
			GriefLog.debug("Something went wrong with the date format parsing, tell this to blackwolf12333 please.");
		}
		return date;
	}
}
