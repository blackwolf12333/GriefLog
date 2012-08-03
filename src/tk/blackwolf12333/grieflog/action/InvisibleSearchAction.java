package tk.blackwolf12333.grieflog.action;

import tk.blackwolf12333.grieflog.GLPlayer;

public class InvisibleSearchAction extends BaseAction {

	GLPlayer player;
	
	public InvisibleSearchAction(GLPlayer player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setSearchResult(result);
		player.setSearching(false);
	}
}
