package tk.blackwolf12333.grieflog.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockIgniteData extends BaseBlockData {

	String cause;
	
	public BlockIgniteData(Block block, String cause, String playerName, Integer gamemode) {
		putBlock(block);
		this.cause = cause;
		this.playerName = playerName;
		this.gamemode = gamemode;
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
	}
	
	public BlockIgniteData(String time, Integer blockX, Integer blockY, Integer blockZ, String blockType, byte blockData, String world, String cause, String playerName, Integer gamemode) {
		this.time = time;
		this.blockX = blockX;
		this.blockY = blockY;
		this.blockZ = blockZ;
		this.blockType = blockType;
		this.blockData = blockData;
		this.worldName = world;
		this.cause = cause;
		this.playerName = playerName;
		this.gamemode = gamemode;
	}
	
	public String getCause() {
		return cause;
	}
	
	public void setCause(String cause) {
		this.cause = cause;
	}
	
	@Override
	public void rollback() {
		World w = Bukkit.getWorld(worldName);
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		w.getBlockAt(loc).setTypeIdAndData(Material.getMaterial(blockType).getId(), blockData, true);
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " [BLOCK_IGNITE] By: " + playerName + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " How: " + cause + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName + System.getProperty("line.separator");
		}
		return " [BLOCK_IGNITE] By: " + playerName + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " How: " + cause + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName + System.getProperty("line.separator");
	}
}
