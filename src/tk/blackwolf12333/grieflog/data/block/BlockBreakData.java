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

public class BlockBreakData extends BaseBlockData {
	
	public BlockBreakData(Block block, String playerName, Integer gamemode) {
		putBlock(block);
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.event = Events.BREAK.getEventName();
	}
	
	public BlockBreakData(Block block, String playerName, UUID playerUUID, Integer gamemode) {
		this(block, playerName, gamemode);
		this.playerUUID = playerUUID;
	}
	
	public BlockBreakData(Integer x, Integer y, Integer z, String world, String blockType, byte blockData, String playerName, Integer gamemode) {
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.blockType = blockType;
		this.blockData = blockData;
		this.worldName = world;
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.event = Events.BREAK.getEventName();
		this.xyz = blockX + ", " + blockY + ", " + blockZ;
	}
	
	public BlockBreakData(String time, Integer x, Integer y, Integer z, String world, String blockType, byte blockData, String playerName, Integer gamemode) {
		this(x, y, z, world, blockType, blockData, playerName, gamemode);
		this.time = time;
	}
	
	public BlockBreakData(String time, Integer x, Integer y, Integer z, String world, String blockType, byte blockData, String playerName, UUID playerUUID, Integer gamemode) {
		this(time, x, y, z, world, blockType, blockData, playerName, gamemode);
		this.playerUUID = playerUUID;
	}
	
	@Override
	public void rollback(Rollback rollback) {
		try {
			Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
			setBlockFast(loc, Material.getMaterial(blockType).getId(), blockData);
			rollback.chunks.add(loc.getChunk());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void undo(Undo undo) {
		World w = Bukkit.getWorld(worldName);
		Location loc = new Location(w, blockX, blockY, blockZ);
		setBlockFast(loc, Material.AIR.getId(), (byte)0);
		undo.chunks.add(loc.getChunk());
	}
	
	@Override
	public String getMinimal() {
		return time + " " + playerName + " broke " + blockType.toLowerCase() + ".";
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " By: " + playerName + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " on Pos: " + xyz + " in: " + worldName;
		}
		return " " + event + " By: " + playerName + ":" + playerUUID.toString() + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName;
	}
}
