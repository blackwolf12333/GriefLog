package tk.blackwolf12333.grieflog.action;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.rollback.Rollback;

public class RegionRollbackAction extends BaseAction {

	GLPlayer player;
	
	public RegionRollbackAction(GLPlayer player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setDoingRollback(true);
		new Rollback(player);
	}
}
