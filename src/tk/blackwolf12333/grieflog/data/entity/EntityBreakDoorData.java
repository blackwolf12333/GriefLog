package tk.blackwolf12333.grieflog.data.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.rollback.Undo;
import tk.blackwolf12333.grieflog.utils.logging.Events;


public class EntityBreakDoorData extends BaseEntityData {

	public EntityBreakDoorData(Integer x, Integer y, Integer z, String world, String entityType) {
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.worldName = world;
		this.entityType = entityType;
		this.event = Events.ZOMBIEBREAKDOOR.getEventName();
		this.xyz = blockX + ", " + blockY + ", " + blockZ;
	}
	
	public EntityBreakDoorData(String time, Integer x, Integer y, Integer z, String world, String entityType) {
		this(x, y, z, world, entityType);
		this.time = time;
	}
	
	@Override
	public void rollback(Rollback rollback) {
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		setBlockFast(loc, Material.WOOD_DOOR.getId(), blockData);
		setBlockFast(getOtherDoorBlock(loc), Material.WOOD_DOOR.getId(), blockData);
		rollback.chunks.add(loc.getChunk());
	}
	
	@Override
	public void undo(Undo undo) {
		World w = Bukkit.getWorld(worldName);
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		w.getBlockAt(loc).setType(Material.AIR);
		undo.chunks.add(loc.getChunk());
	}
	
	public String getMinimal() {
		return time + " Zombie broke a door here.";
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " Entity: " + entityType + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName;
		}
		return " " + event + " Entity: " + entityType + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName;
	}
	
	private Location getOtherDoorBlock(Location door) {
		BlockFace[] faces = new BlockFace[] {BlockFace.DOWN, BlockFace.UP};
		for(BlockFace face : faces) {
			if(door.getBlock().getRelative(face).getType().equals(Material.AIR)) {
				return door.getBlock().getRelative(face).getLocation();
			}
		}
		return null;
	}
}
