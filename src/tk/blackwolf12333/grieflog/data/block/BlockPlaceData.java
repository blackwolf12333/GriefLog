package tk.blackwolf12333.grieflog.data.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.rollback.Undo;
import tk.blackwolf12333.grieflog.utils.logging.Events;


public class BlockPlaceData extends BaseBlockData {

	public BlockPlaceData(Block block, String playerName, Integer gamemode) {
		putBlock(block);
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.event = Events.PLACE.getEventName();
	}
	
	public BlockPlaceData(Integer blockX, Integer blockY, Integer blockZ, String blockType, byte blockData, String world, String playerName, Integer gamemode) {
		this.blockX = blockX;
		this.blockY = blockY;
		this.blockZ = blockZ;
		this.blockType = blockType;
		this.blockData = blockData;
		this.worldName = world;
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.xyz = blockX + ", " + blockY + ", " + blockZ;
		this.event = Events.PLACE.getEventName();
	}
	
	public BlockPlaceData(String time, Integer blockX, Integer blockY, Integer blockZ, String blockType, byte blockData, String world, String playerName, Integer gamemode) {
		this.time = time;
		this.blockX = blockX;
		this.blockY = blockY;
		this.blockZ = blockZ;
		this.blockType = blockType;
		this.blockData = blockData;
		this.worldName = world;
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.xyz = blockX + ", " + blockY + ", " + blockZ;
		this.event = Events.PLACE.getEventName();
	}
	
	@Override
	public void rollback(Rollback rollback) {
		try {
			Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
			setBlockFast(loc, Material.AIR.getId(), blockData);
			rollback.chunks.add(loc.getChunk());
		} catch(NullPointerException e) {
			GriefLog.log.warning("Non existing world!");
		}
		
	}
	
	@Override
	public void undo(Undo undo) {
		World w = Bukkit.getWorld(worldName);
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		w.getBlockAt(loc).setTypeIdAndData(Material.getMaterial(blockType).getId(), blockData, true);
		if(blockType.contains("DOOR")) {
			w.getBlockAt(getOtherDoorBlock(w.getBlockAt(loc))).setTypeIdAndData(Material.getMaterial(blockType).getId(), blockData, true);
		}
		undo.chunks.add(loc.getChunk());
	}
	
	@Override
	public String getMinimal() {
		return time + " " + playerName + " placed " + blockType.toLowerCase() + ".";
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " By: " + playerName + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName;
		}
		return " " + event + " By: " + playerName + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName;
	}
}
