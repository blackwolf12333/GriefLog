package tk.blackwolf12333.grieflog.callback;

public abstract class BaseCallback {

	/**
	 * Called when the sort is done.
	 */
	public abstract void start();
	
	public final void run() {
		sortResult();
	}
	
	protected void sortResult() {
		start();
	}
}
