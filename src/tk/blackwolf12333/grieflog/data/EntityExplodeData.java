package tk.blackwolf12333.grieflog.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class EntityExplodeData extends BaseEntityData {

	String activator;
	
	public EntityExplodeData(Integer x, Integer y, Integer z, String world, String blockType, byte blockData, String entityType, String activator) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = world;
		this.blockData = blockData;
		this.blockType = blockType;
		this.entityType = entityType;
		this.activator = activator;
	}
	
	public EntityExplodeData(String time, Integer x, Integer y, Integer z, String world, String blockType, byte blockData, String entityType, String activator) {
		this.time = time;
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = world;
		this.blockData = blockData;
		this.blockType = blockType;
		this.entityType = entityType;
		this.activator = activator;
	}
	
	@Override
	public void rollback() {
		World w = Bukkit.getWorld(worldName);
		Location loc = new Location(Bukkit.getWorld(worldName), x, y, z);
		w.getBlockAt(loc).setTypeIdAndData(Material.getMaterial(blockType).getId(), blockData, true);
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " [ENTITY_EXPLODE] By: " + activator + " EntityType: " + entityType + " Block: " + blockType + ":" + blockData + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
		}
		return " [ENTITY_EXPLODE] By: " + activator + " EntityType: " + entityType + " Block: " + blockType + ":" + blockData + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
	}
}
