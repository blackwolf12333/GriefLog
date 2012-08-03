package tk.blackwolf12333.grieflog.action;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.rollback.Rollback;

public class RollbackAction extends BaseAction {

	GLPlayer player;
	
	public RollbackAction(GLPlayer player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setDoingRollback(true);
		player.setSearchResult(result);
		new Rollback(player);
	}
}
