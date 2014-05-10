package tk.blackwolf12333.grieflog.data.block;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.rollback.Undo;
import tk.blackwolf12333.grieflog.utils.logging.Events;


public class BlockIgniteData extends BaseBlockData {

	String cause;
	
	public BlockIgniteData(Block block, String cause, String playerName, Integer gamemode) {
		putBlock(block);
		this.cause = cause;
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.event = Events.IGNITE.getEventName();
	}

	public BlockIgniteData(Block block, String cause, String playerName, UUID playerUUID, Integer gamemode) {
		this(block, cause, playerName, gamemode);
		this.playerUUID = playerUUID;
	}
	
	public BlockIgniteData(Integer blockX, Integer blockY, Integer blockZ, String blockType, byte blockData, String world, String cause, String playerName, Integer gamemode) {
		this.blockX = blockX;
		this.blockY = blockY;
		this.blockZ = blockZ;
		this.blockType = blockType;
		this.blockData = blockData;
		this.worldName = world;
		this.cause = cause;
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.event = Events.IGNITE.getEventName();
		xyz = blockX + ", " + blockY + ", " + blockZ;
	}
	
	public BlockIgniteData(String time, Integer blockX, Integer blockY, Integer blockZ, String blockType, byte blockData, String world, String cause, String playerName, Integer gamemode) {
		this(blockX, blockY, blockZ, blockType, blockData, world, cause, playerName, gamemode);
		this.time = time;
	}
	
	public BlockIgniteData(String time, Integer blockX, Integer blockY, Integer blockZ, String blockType, byte blockData, String world, String cause, String playerName, UUID playerUUID, Integer gamemode) {
		this(time, blockX, blockY, blockZ, blockType, blockData, world, cause, playerName, gamemode);
		this.playerUUID = playerUUID;
	}
	
	public String getCause() {
		return cause;
	}
	
	public void setCause(String cause) {
		this.cause = cause;
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
		return time + " " + playerName + " ignited " + blockType.toLowerCase() + ".";
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " By: " + playerName + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " How: " + cause + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName;
		}
		return " " + event + " By: " + playerName + ":" + playerUUID.toString() + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " How: " + cause + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName;
	}
}
