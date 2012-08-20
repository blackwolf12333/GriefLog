package tk.blackwolf12333.grieflog.callback;

import java.util.ArrayList;
import java.util.Collections;

public abstract class BaseCallback {

	/**
	 * The search result
	 */
	public ArrayList<String> result;
	
	/**
	 * Called if the sort is done.
	 */
	public abstract void start();
	
	public final void run() {
		sortResult();
	}
	
	protected void sortResult() {
		Collections.sort(result, Collections.reverseOrder());
		start();
	}
}
