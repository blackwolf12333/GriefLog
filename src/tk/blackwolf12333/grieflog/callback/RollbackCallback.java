package tk.blackwolf12333.grieflog.callback;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.rollback.Rollback;

public class RollbackCallback extends BaseCallback {

	PlayerSession player;
	
	public RollbackCallback(PlayerSession player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setDoingRollback(true);
		new Rollback(player).run();
	}
}
