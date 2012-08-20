package tk.blackwolf12333.grieflog.callback;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.rollback.Rollback;

public class RegionRollbackCallback extends BaseCallback {

	GLPlayer player;
	
	public RegionRollbackCallback(GLPlayer player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setDoingRollback(true);
		new Rollback(player);
	}
}
