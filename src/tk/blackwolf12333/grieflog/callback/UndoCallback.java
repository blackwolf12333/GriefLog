package tk.blackwolf12333.grieflog.callback;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.rollback.Undo;

public class UndoCallback implements BaseCallback {

	PlayerSession player;
	
	public UndoCallback(PlayerSession player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setDoingRollback(true);
		new Undo(player);
	}
}
