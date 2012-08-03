package tk.blackwolf12333.grieflog.action;

import java.util.ArrayList;

public abstract class BaseAction {

	/**
	 * 
	 */
	public ArrayList<String> result;
	
	/**
	 * Called if the Searcher is done.
	 */
	public abstract void start();
}
