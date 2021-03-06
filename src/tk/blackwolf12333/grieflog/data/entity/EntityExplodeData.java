package tk.blackwolf12333.grieflog.data.entity;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.rollback.Undo;
import tk.blackwolf12333.grieflog.utils.logging.Events;


public class EntityExplodeData extends BaseEntityData {

	String activator;
	UUID activatorUUID;
	
	public EntityExplodeData(Integer x, Integer y, Integer z, String world, String blockType, byte blockData, String entityType, String activator) {
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.worldName = world;
		this.blockData = blockData;
		this.blockType = blockType;
		this.entityType = entityType;
		this.activator = activator;
		this.event = Events.EXPLODE.getEventName();
		this.xyz = blockX + ", " + blockY + ", " + blockZ;
	}
	
	public EntityExplodeData(Integer x, Integer y, Integer z, String world, String blockType, byte blockData, String entityType, String activator, UUID activatorUUID) {
		this(x, y, z, world, blockType, blockData, entityType, activator);
		this.activatorUUID = activatorUUID;
	}
	
	public EntityExplodeData(String time, Integer x, Integer y, Integer z, String world, String blockType, byte blockData, String entityType, String activator) {
		this(x, y, z, world, blockType, blockData, entityType, activator);
		this.time = time;
	}
	
	public EntityExplodeData(String time, Integer x, Integer y, Integer z, String world, String blockType, byte blockData, String entityType, String activator, UUID activatorUUID) {
		this(time, x, y, z, world, blockType, blockData, entityType, activator);
		this.activatorUUID = activatorUUID;
	}
	
	@Override
	public void rollback(Rollback rollback) {
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		setBlockFast(loc, Material.getMaterial(blockType).getId(), blockData);
		rollback.chunks.add(loc.getChunk());
	}
	
	@Override
	public void undo(Undo undo) {
		World w = Bukkit.getWorld(worldName);
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		w.getBlockAt(loc).setType(Material.AIR);
		undo.chunks.add(loc.getChunk());
	}
	
	@Override
	public String getMinimal() {
		return time + " " + activator + " let a " + entityType + " explode.";
	}
	
	@Override
	public String getPlayerName() {
		return activator;
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " By: " + activator + " EntityType: " + entityType + " Block: " + blockType + ":" + blockData + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName;
		}
		return " " + event + " By: " + activator + ":" + activatorUUID.toString() + " EntityType: " + entityType + " Block: " + blockType + ":" + blockData + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName;
	}
}
