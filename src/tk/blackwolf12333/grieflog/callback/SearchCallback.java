package tk.blackwolf12333.grieflog.callback;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.utils.PageManager;

public class SearchCallback extends BaseCallback {

	GLPlayer player;
	
	public SearchCallback(GLPlayer player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setSearchResult(result);
		player.setSearching(false);
		PageManager.printPage(player, 0);
	}
}
