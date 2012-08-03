package tk.blackwolf12333.grieflog.action;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.utils.PageManager;

public class SearchAction extends BaseAction {

	GLPlayer player;
	
	public SearchAction(GLPlayer player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setSearchResult(result);
		player.setSearching(false);
		PageManager.printPage(player, 0);
	}
}
