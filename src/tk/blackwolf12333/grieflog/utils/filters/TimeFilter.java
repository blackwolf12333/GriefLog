package tk.blackwolf12333.grieflog.utils.filters;

import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.utils.logging.Time;

public class TimeFilter implements Filter {

	String time;
	
	public TimeFilter(String time) {
		this.time = time;
	}
	
	@Override
	public boolean doFilter(BaseData data) {
		if(Time.getTimeStamp(time).after(Time.getTimeStamp(data.getTime())))
			return true;
		return false;
	}
	
}
