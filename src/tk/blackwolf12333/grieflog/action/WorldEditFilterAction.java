package tk.blackwolf12333.grieflog.action;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.utils.WorldEditFilter;

public class WorldEditFilterAction extends BaseAction {

	GLPlayer player;
	
	public WorldEditFilterAction(GLPlayer player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setSearchResult(result);
		new WorldEditFilter(player.getSearchResult(), new RegionRollbackAction(player), player);
	}
}
