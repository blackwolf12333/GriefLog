package tk.blackwolf12333.grieflog.utils.reports;

import java.io.Serializable;

public class Report implements Serializable {

	private static final long serialVersionUID = 3564703201532845810L;
	int x, y, z;
	String world;
	
	public Report(int x, int y, int z, String world) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}
	
	public String getWorld() {
		return world;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
}
