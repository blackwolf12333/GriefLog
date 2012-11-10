package tk.blackwolf12333.grieflog.data.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class BlockBreakData extends BaseBlockData {
	
	public BlockBreakData(Block block, String playerName, Integer gamemode) {
		putBlock(block);
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.event = Events.BREAK.getEventName();
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
		this.time = time;
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
	public void undo() {
		try {
			World w = Bukkit.getWorld(worldName);
			Location loc = new Location(w, blockX, blockY, blockZ);
			setBlockFast(loc, Material.AIR.getId(), (byte)0);
		} catch(Exception e) {
			
		}
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
		return " " + event + " By: " + playerName + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName;
	}
}
