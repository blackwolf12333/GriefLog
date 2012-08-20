package tk.blackwolf12333.grieflog.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class EntityEndermanData extends BaseEntityData {

	boolean pickup;
	
	public EntityEndermanData(Integer x, Integer y, Integer z, String world, String blockType, byte blockData, boolean pickup) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = world;
		this.entityType = "Enderman";
		this.blockData = blockData;
		this.blockType = blockType;
		this.pickup = pickup;
	}
	
	@Override
	public void rollback() {
		World w = Bukkit.getWorld(worldName);
		Location loc = new Location(Bukkit.getWorld(worldName), x, y, z);
		if(pickup) {
			w.getBlockAt(loc).setTypeIdAndData(Material.getMaterial(blockType).getId(), blockData, true);
		} else {
			w.getBlockAt(loc).setType(Material.AIR);
		}
	}
	
	@Override
	public String toString() {
		if(pickup) {
			return " [ENDERMAN_PICKUP] What: " + blockType + ":" + blockData + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
		} else {
			return " [ENDERMAN_PLACE] What: " + blockType + ":" + blockData + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
		}
	}
}
