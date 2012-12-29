package tk.blackwolf12333.grieflog.utils.filters;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class WorldEditFilter extends Filter {

	PlayerSession player;
	
	public WorldEditFilter(PlayerSession player) {
		this.player = player;
	}
	
	public boolean doFilter(BaseData data) {
		if(Events.getEvent(data.getEvent()).getCanRollback()) {
			return data.isInWorldEditSelectionOf(player);
		} else {
			return false;
		}
	}
}
