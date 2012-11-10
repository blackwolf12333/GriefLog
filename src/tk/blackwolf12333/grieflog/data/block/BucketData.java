package tk.blackwolf12333.grieflog.data.block;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.utils.BlockUtils;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class BucketData extends BaseBlockData {

	String blockType;
	byte blockData;
	Material bucket;
	
	public BucketData(Block block, String playerName, Integer gamemode, Material bucket) {
		putBlock(block);
		this.gamemode = gamemode;
		this.playerName = playerName;
		this.bucket = bucket;
		if(bucket == Material.WATER_BUCKET) {
			this.event = Events.WATER.getEventName();
		} else {
			this.event = Events.LAVA.getEventName();
		}
	}
	
	public BucketData(Integer x, Integer y, Integer z, String world, String playerName, Integer gamemode, Material bucket) {
		this.gamemode = gamemode;
		this.playerName = playerName;
		this.bucket = bucket;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.worldName = world;
		if(bucket == Material.WATER_BUCKET) {
			this.event = Events.WATER.getEventName();
		} else {
			this.event = Events.LAVA.getEventName();
		}
	}
	
	public BucketData(String time, Integer x, Integer y, Integer z, String world, String playerName, Integer gamemode, Material bucket) {
		this.gamemode = gamemode;
		this.time = time;
		this.playerName = playerName;
		this.bucket = bucket;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.worldName = world;
		if(bucket == Material.WATER_BUCKET) {
			this.event = Events.WATER.getEventName();
		} else {
			this.event = Events.LAVA.getEventName();
		}
	}
	
	@Override
	public void rollback(Rollback rollback) {
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		
		for(Block b : getFluidStream(loc.getBlock())) {
			if(b != null) {
				setBlockFast(b.getLocation(), Material.AIR.getId(), blockData);
				rollback.chunks.add(loc.getChunk());
			}
		}
		setBlockFast(loc, Material.AIR.getId(), blockData);
		rollback.chunks.add(loc.getChunk());
	}
	
	@Override
	public void undo() {
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		if(bucket == Material.WATER_BUCKET) {
			Bukkit.getWorld(worldName).getBlockAt(loc).setType(Material.WATER);
		} else {
			Bukkit.getWorld(worldName).getBlockAt(loc).setType(Material.LAVA);
		}
	}
	
	@Override
	public String getMinimal() {
		if(this.bucket == Material.LAVA_BUCKET) {
			return time + " " + playerName + " placed lava (GM: " + gamemode + ").";
		}
		return time + " " + playerName + " placed water (GM: " + gamemode + ").";
	}
	
	public void tpto(PlayerSession who) {
		World world = Bukkit.getServer().getWorld(worldName);
		Location loc = new Location(world, blockX, blockY, blockZ);
		
		who.print(ChatColor.YELLOW + "[GriefLog] Teleporting you.");
		who.teleport(loc);
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " Who: " + playerName + " GM: " + gamemode + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName;
		}
		return " " + event + " Who: " + playerName + " GM: " + gamemode + " Where: " + blockX + ", " + blockY + ", " + blockZ + " In: " + worldName;
	}
	
	public static BucketData loadFromString(String line) {
		String[] data = line.split(" ");
		String time = data[0] + " " + data[1];
		Material bucket = (line.contains(Events.WATER.getEventName()) ? Material.WATER_BUCKET : Material.LAVA_BUCKET);
		Integer x = Integer.parseInt(data[8].replace(",", ""));
		Integer y = Integer.parseInt(data[9].replace(",", ""));
		Integer z = Integer.parseInt(data[10].replace(",", ""));
		String world = data[data.length - 1].trim();
		String playerName = data[4];
		Integer gamemode = Integer.parseInt(data[6]);
		return new BucketData(time, x, y, z, world, playerName, gamemode, bucket);
	}
	
	private HashSet<Block> getFluidStream(Block source) {
		HashSet<Block> fluids = new HashSet<Block>();
		if(this.bucket == Material.WATER_BUCKET) {
			new BlockUtils().getWaterStream(source, fluids);
		} else {
			new BlockUtils().getLavaStream(source, fluids);
		}
		
		return fluids;
	}
}
