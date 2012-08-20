package tk.blackwolf12333.grieflog.callback;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.rollback.Rollback;

public class RollbackCallback extends BaseCallback {

	GLPlayer player;
	
	public RollbackCallback(GLPlayer player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setDoingRollback(true);
		player.setSearchResult(result);
		new Rollback(player);
	}
}
