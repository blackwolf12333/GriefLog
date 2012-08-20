package tk.blackwolf12333.grieflog.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockBreakData extends BaseBlockData {
	
	public BlockBreakData(Block block, String playerName, Integer gamemode) {
		putBlock(block);
		this.playerName = playerName;
		this.gamemode = gamemode;
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
	}
	
	@Override
	public void rollback() {
		try {
			World w = Bukkit.getWorld(worldName);
			Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
			w.getBlockAt(loc).setTypeIdAndData(Material.getMaterial(blockType).getId(), blockData, true);
		} catch(Exception e) {
			
		}
		
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " [BLOCK_BREAK] By: " + playerName + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + System.getProperty("line.separator");
		}
		return " [BLOCK_BREAK] By: " + playerName + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + System.getProperty("line.separator");
	}
}
