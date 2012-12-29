package tk.blackwolf12333.grieflog.utils.filters;

import tk.blackwolf12333.grieflog.callback.BaseCallback;
import tk.blackwolf12333.grieflog.data.BaseData;

public abstract class Filter {

	BaseCallback callback;
	
	public abstract boolean doFilter(BaseData data);
	
	public void setCallback(BaseCallback callback) {
		this.callback = callback;
	}
}
