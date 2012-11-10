package tk.blackwolf12333.grieflog.callback;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.utils.searching.PageManager;

public class SearchCallback extends BaseCallback {

	PlayerSession player;
	
	public SearchCallback(PlayerSession player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setSearching(false);
		PageManager.printPageNormal(player, 0);
	}
}
