package tk.blackwolf12333.grieflog.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockPlaceData extends BaseBlockData {

	public BlockPlaceData(Block block, String playerName, Integer gamemode) {
		putBlock(block);
		this.playerName = playerName;
		this.gamemode = gamemode;
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
	}
	
	@Override
	public void rollback() {
		World w = Bukkit.getWorld(worldName);
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		w.getBlockAt(loc).setType(Material.AIR);
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " [BLOCK_PLACE] By: " + playerName + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + System.getProperty("line.separator");
		}
		return " [BLOCK_PLACE] By: " + playerName + " GM: " + gamemode + " What: " + blockType + ":" + blockData + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + System.getProperty("line.separator");
	}
}
