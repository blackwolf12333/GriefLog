package tk.blackwolf12333.grieflog.utils.filters;

import java.util.UUID;

import tk.blackwolf12333.grieflog.data.BaseData;

public class PlayerFilter implements Filter {

	UUID player;
	
	public PlayerFilter(UUID player) {
		this.player = player;
	}
	
	@Override
	public boolean doFilter(BaseData data) {
		return (data.getPlayerUUID() != null) ? data.getPlayerUUID().equals(player) : false;
	}
}
