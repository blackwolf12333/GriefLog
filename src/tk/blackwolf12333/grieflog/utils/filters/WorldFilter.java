package tk.blackwolf12333.grieflog.utils.filters;

import tk.blackwolf12333.grieflog.data.BaseData;

public class WorldFilter implements Filter {

	String world;
	
	public WorldFilter(String world) {
		this.world = world;
	}
	
	@Override
	public boolean doFilter(BaseData data) {
		return data.getWorldName().equals(world);
	}

}
