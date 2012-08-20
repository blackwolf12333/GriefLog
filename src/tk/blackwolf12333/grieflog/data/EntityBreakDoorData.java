package tk.blackwolf12333.grieflog.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class EntityBreakDoorData extends BaseEntityData {

	public EntityBreakDoorData(Integer x, Integer y, Integer z, String world, String entityType) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = world;
		this.entityType = entityType;
	}
	
	public EntityBreakDoorData(String time, Integer x, Integer y, Integer z, String world, String entityType) {
		this.time = time;
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = world;
		this.entityType = entityType;
	}
	
	@Override
	public void rollback() {
		World w = Bukkit.getWorld(worldName);
		Location loc = new Location(Bukkit.getWorld(worldName), x, y, z);
		w.getBlockAt(loc).setType(Material.WOOD_DOOR);
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " [ENTITY_BREAK_DOOR] Entity: " + entityType + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
		}
		return " [ENTITY_BREAK_DOOR] Entity: " + entityType + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
	}
}
