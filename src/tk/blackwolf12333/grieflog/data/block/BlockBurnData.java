package tk.blackwolf12333.grieflog.data.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.rollback.Undo;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class BlockBurnData extends BaseBlockData {
	public BlockBurnData(Block b) {
		putBlock(b);
		this.event = Events.BURN.getEventName();
	}

	public BlockBurnData(String time, String blockType, byte blockData, int x, int y, int z, String world) {
		this.time = time;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.blockType = blockType;
		this.blockData = blockData;
		this.worldName = world;
		this.event = Events.BURN.getEventName();
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
	public String toString() {
		if(time != null) {
			return time + " " + event + " What: " + blockType + ":" + blockData + " on Pos: " + xyz + " in: " + worldName;
		}
		return " " + event + " What: " + blockType + ":" + blockData + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName;
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
		return time + " A block was burned.";
	}
}
