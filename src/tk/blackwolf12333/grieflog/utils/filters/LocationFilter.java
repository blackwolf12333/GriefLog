package tk.blackwolf12333.grieflog.utils.filters;

import org.bukkit.Location;

import tk.blackwolf12333.grieflog.data.BaseData;

public class LocationFilter implements Filter {

	int x, y, z;
	String world;
	
	public LocationFilter(int x, int y, int z, String world) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}
	
	@Override
	public boolean doFilter(BaseData data) {
		Location loc = data.getLocation();
		if(loc != null) {
			return ((loc.getBlockX() == this.x) && (loc.getBlockY() == this.y) && (loc.getBlockZ() == this.z) && (loc.getWorld().getName().equalsIgnoreCase(world)));
		} else {
			return false;
		}
	}
}
