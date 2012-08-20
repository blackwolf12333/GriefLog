package tk.blackwolf12333.grieflog.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import tk.blackwolf12333.grieflog.utils.Events;

public class BucketData extends BasePlayerData {

	Integer blockX;
	Integer blockY;
	Integer blockZ;
	String blockType;
	byte blockData;
	Material bucket;
	
	public BucketData(Block block, String playerName, Integer gamemode, Material bucket) {
		putBlock(block);
		this.gamemode = gamemode;
		this.playerName = playerName;
		this.bucket = bucket;
	}
	
	public BucketData(Integer x, Integer y, Integer z, String world, String playerName, Integer gamemode, Material bucket) {
		this.gamemode = gamemode;
		this.playerName = playerName;
		this.bucket = bucket;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.worldName = world;
	}
	
	private void putBlock(Block b) {
		this.blockX = b.getX();
		this.blockY = b.getY();
		this.blockZ = b.getZ();
		this.worldName = b.getWorld().getName();
	}
	
	@Override
	public void rollback() {
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		Block[] fluids = getFluidStream(Bukkit.getWorld(worldName).getBlockAt(loc));
		for(Block b : fluids) {
			if(b != null) {
				Bukkit.getWorld(worldName).getBlockAt(b.getLocation()).setType(Material.AIR);
			}
		}
		Bukkit.getWorld(worldName).getBlockAt(loc).setType(Material.AIR);
	}
	
	@Override
	public String toString() {
		if(bucket == Material.WATER_BUCKET) {
			return " [BUCKET_WATER_EMPTY] Who: " + playerName + " GM: " + gamemode + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName + System.getProperty("line.separator");
		} else {
			return " [BUCKET_LAVA_EMPTY] Who: " + playerName + " GM: " + gamemode + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName + System.getProperty("line.separator");
		}
	}
	
	public static BucketData loadFromString(String line) {
		String[] data = line.split(" ");
		Material bucket = (line.contains(Events.WATER.getEventName()) ? Material.WATER_BUCKET : Material.LAVA_BUCKET);
		Integer x = Integer.parseInt(data[8].replace(",", ""));
		Integer y = Integer.parseInt(data[9].replace(",", ""));
		Integer z = Integer.parseInt(data[10].replace(",", ""));
		String world = data[data.length - 1].trim();
		String playerName = data[4];
		Integer gamemode = Integer.parseInt(data[6]);
		return new BucketData(x, y, z, world, playerName, gamemode, bucket);
	}
	
	private Block[] getFluidStream(Block source) {
		BlockFace[] faces = new BlockFace[] {BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST, BlockFace.DOWN, BlockFace.UP};
		Block[] fluids = new Block[16];
		int count = 0;
		
		for(BlockFace face : faces) {
			Block next = source.getRelative(face);
			if(source.getType() == Material.STATIONARY_WATER) {
				if((next.getType() == Material.WATER)) {
					fluids[count] = next;
					count++;
				}
			} else {
				if((next.getType() == Material.LAVA)) {
					fluids[count] = next;
					count++;
				}
			}
		}
		
		return fluids;
	}
}
