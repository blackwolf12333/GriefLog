package tk.blackwolf12333.grieflog.callback;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.utils.searching.PageManager;

public class ToolCallback implements BaseCallback {

	PlayerSession session;
	
	public ToolCallback(PlayerSession session) {
		this.session = session;
	}
	
	@Override
	public void start() {
		session.setSearching(false);
		PageManager.printPage(session, 0);
	}
}
