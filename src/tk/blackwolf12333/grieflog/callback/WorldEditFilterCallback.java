package tk.blackwolf12333.grieflog.callback;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.utils.WorldEditFilter;

public class WorldEditFilterCallback extends BaseCallback {

	PlayerSession player;
	BaseCallback action;
	
	public WorldEditFilterCallback(PlayerSession player, BaseCallback action) {
		this.player = player;
		this.action = action;
	}
	
	@Override
	public void start() {
		new WorldEditFilter(player, action);
	}
}
