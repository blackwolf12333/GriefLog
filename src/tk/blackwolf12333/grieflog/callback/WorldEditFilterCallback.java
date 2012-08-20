package tk.blackwolf12333.grieflog.callback;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.utils.WorldEditFilter;

public class WorldEditFilterCallback extends BaseCallback {

	GLPlayer player;
	
	public WorldEditFilterCallback(GLPlayer player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setSearchResult(result);
		new WorldEditFilter(new RegionRollbackCallback(player), player);
	}
}
