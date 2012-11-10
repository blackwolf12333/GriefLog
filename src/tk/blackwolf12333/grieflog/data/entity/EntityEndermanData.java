package tk.blackwolf12333.grieflog.data.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.utils.logging.Events;


public class EntityEndermanData extends BaseEntityData {

	boolean pickup;
	
	public EntityEndermanData(Integer x, Integer y, Integer z, String world, String blockType, byte blockData, boolean pickup) {
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.worldName = world;
		this.entityType = "Enderman";
		this.blockData = blockData;
		this.blockType = blockType;
		this.pickup = pickup;
		if(pickup) {
			this.event = Events.ENDERMAN_PICKUP.getEventName();
		}
		this.event = Events.ENDERMAN_PLACE.getEventName();
		this.xyz = blockX + ", " + blockY + ", " + blockZ;
	}
	
	public EntityEndermanData(String time, Integer x, Integer y, Integer z, String world, String blockType, byte blockData, boolean pickup) {
		this.time = time;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.worldName = world;
		this.entityType = "Enderman";
		this.blockData = blockData;
		this.blockType = blockType;
		this.pickup = pickup;
		if(pickup) {
			this.event = Events.ENDERMAN_PICKUP.getEventName();
		}
		this.event = Events.ENDERMAN_PLACE.getEventName();
		this.xyz = blockX + ", " + blockY + ", " + blockZ;
	}
	
	@Override
	public void rollback(Rollback rollback) {
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		if(pickup) {
			setBlockFast(loc, Material.getMaterial(blockType).getId(), blockData);
			rollback.chunks.add(loc.getChunk());
		} else {
			setBlockFast(loc, Material.AIR.getId(), blockData);
			rollback.chunks.add(loc.getChunk());
		}
	}
	
	@Override
	public void undo() {
		World w = Bukkit.getWorld(worldName);
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		if(pickup) {
			w.getBlockAt(loc).setType(Material.AIR);
		} else {
			w.getBlockAt(loc).setTypeIdAndData(Material.getMaterial(blockType).getId(), blockData, true);
		}
	}
	
	public String getMinimal() {
		if(pickup) {
			return time + "Enderman picked up a block.";
		}
		return time + "Enderman placed a block.";
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " What: " + blockType + ":" + blockData + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName;
		}
		return " " + event + " What: " + blockType + ":" + blockData + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName;
	}
}
