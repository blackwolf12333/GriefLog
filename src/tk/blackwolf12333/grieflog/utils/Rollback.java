package tk.blackwolf12333.grieflog.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogSearcher;

@SuppressWarnings("unused")
public class Rollback implements Runnable {

	GriefLog plugin;
	World world;
	String result;
	int count;
	CommandSender sender;
	
	int blockCount = 0;
	GriefLogSearcher searcher = new GriefLogSearcher();
	Time t = new Time();
	ArrayList<String> allLines = new ArrayList<String>();
	HashMap<Location, Material> blockToRollback = new HashMap<Location, Material>();
	public Thread rollbackThread = new Thread(this);

	public Rollback(GriefLog instance, CommandSender sender, String ...text) {
		plugin = instance;
		count = 0;
		this.sender = sender;
		
		if(text.length < 1)
			return;
		if(text.length == 1) {
			result = searcher.searchText(text[0]);
			String[] lines = result.split(System.getProperty("line.separator"));
			for(String line : lines) {
				allLines.add(line);
			}
		}
		if(text.length == 2) {
			result = searcher.searchText(text[0], text[1]);
			String[] lines = result.split(System.getProperty("line.separator"));
			for(String line : lines) {
				allLines.add(line);
			}
		}
		if(text.length == 3) {
			result = searcher.searchText(text[0], text[1], text[2]);
			String[] lines = result.split(System.getProperty("line.separator"));
			for(String line : lines) {
				allLines.add(line);
			}
		}
	}
	
	@Override
	public void run() {
		rollback(this.sender);
	}
	
	public void rollback(CommandSender sender)	{
		if(plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				while(count < allLines.size()) {
					for(String line : allLines) {
						rollback(line);
						count++;
					}
				}
			}
		}) >= 0) sender.sendMessage("Successfully rolled back the grief:)");
	}
	
	public boolean rollback(String line) {
		if(line == null) {
			return false;
		} else if(line.contains("[PLAYER_LOGIN]")) {
			return false;
		} else if(line.contains("[PLAYER_COMMAND]")) {
			return false;
		} else if(line.contains(Events.BREAK.toString())) {
			String[] content = line.split("\\ ");
			
			String strX = content[11].replace(",", "");
			String strY = content[12].replace(",", "");
			String strZ = content[13].replace(",", "");
			String type = content[8];
			String worldname = content[15];
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);

			world = Bukkit.getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			Material m = Material.getMaterial(type);
			if (m == null) {
				GriefLog.log.info("Could not get the right materials!");
				return false;
			} else if(m == Material.LONG_GRASS) {
				world.getBlockAt(loc).setType(m);
				world.getBlockAt(loc).setData((byte) 1);
				return true;
			} else {
				world.getBlockAt(loc).setType(m);
				return true;
			}
		} else if(line.contains("[ENTITY_EXPLODE]")) {
			String[] content = line.split("\\ ");
			
			String strX = content[8].replace(",", "");
			String strY = content[9].replace(",", "");
			String strZ = content[10].replace(",", "");
			String type = content[6];
			String worldname = content[12];
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);

			world = Bukkit.getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			Material m = Material.getMaterial(type);
			if (m == null) {
				GriefLog.log.info("Could not get the right materials!");
				return false;
			} else if(m == Material.LONG_GRASS) {
				world.getBlockAt(loc).setType(m);
				world.getBlockAt(loc).setData((byte) 1);
				return true;
			} else {
				world.getBlockAt(loc).setType(m);
				return true;
			}
		} else if(line.contains("[BLOCK_PLACE]")) {
			String[] content = line.split("\\ ");
			
			String strX = content[11].replace(",", "");
			String strY = content[12].replace(",", "");
			String strZ = content[13].replace(",", "");
			String worldname = content[15];
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);

			world = Bukkit.getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			world.getBlockAt(loc).setType(Material.AIR);
			return true;
		} else if(line.contains("[BUCKET_LAVA_EMPTY]")) {
			String[] content = line.split("\\ ");
			
			String strX = content[6].replace(",", "");
			String strY = content[7].replace(",", "");
			String strZ = content[8].replace(",", "");
			String worldname = content[10];
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);

			world = Bukkit.getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			world.getBlockAt(loc).setType(Material.AIR);
			return true;
		} else if(line.contains("[BUCKET_WATER_EMPTY]")) {
			String[] content = line.split("\\ ");
			
			String strX = content[6].replace(",", "");
			String strY = content[7].replace(",", "");
			String strZ = content[8].replace(",", "");
			String worldname = content[10];
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);

			world = Bukkit.getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			world.getBlockAt(loc).setType(Material.AIR);
			return true;
		} else {
			return false;
		}
	}
	
	public void rollback(CommandSender sender, String ...text)
	{
		if(text.length < 1) {
			return;
		}
		if(text.length == 1) {
			String result = searcher.searchText(text[0]);
			String[] lines = result.split(System.getProperty("line.separator"));
			for(String line : lines)
			{
				if(line == null) {
					continue;
				} else if(line.contains("[PLAYER_LOGIN]")) {
					continue;
				} else if(line.contains("[PLAYER_COMMAND]")) {
					continue;
				} else if(line.contains("[BLOCK_BREAK]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String type = content[6];
					String worldname = content[13];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
										
					if (m == null) {
						GriefLog.log.info("Could not get the right materials!");
						continue;
					} else if(m == Material.LONG_GRASS) {
						world.getBlockAt(loc).setType(m);
						world.getBlockAt(loc).setData((byte) 1);
					} else {
						world.getBlockAt(loc).setType(m);
					}
				} else if(line.contains("[ENTITY_EXPLODE]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[8].replace(",", "");
					String strY = content[9].replace(",", "");
					String strZ = content[10].replace(",", "");
					String type = content[6];
					String worldname = content[12];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
					
					if (m == null) {
						GriefLog.log.info("Could not get the right materials!");
						continue;
					} else if(m == Material.LONG_GRASS) {
						world.getBlockAt(loc).setType(m);
						world.getBlockAt(loc).setData((byte) 1);
					} else {
						world.getBlockAt(loc).setType(m);
					}
				} else if(line.contains("[BLOCK_PLACE]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String worldname = content[13];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					
					world.getBlockAt(loc).setType(Material.AIR);
				} else if(line.contains("[BUCKET_LAVA_EMPTY]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[6].replace(",", "");
					String strY = content[7].replace(",", "");
					String strZ = content[8].replace(",", "");
					String worldname = content[10];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					
					world.getBlockAt(loc).setType(Material.AIR);
				} else if(line.contains("[BUCKET_WATER_EMPTY]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[6].replace(",", "");
					String strY = content[7].replace(",", "");
					String strZ = content[8].replace(",", "");
					String worldname = content[10];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					
					world.getBlockAt(loc).setType(Material.AIR);
				}
			}
			sender.sendMessage(ChatColor.RED + "Rolled back da grief:D");
		} else if(text.length == 2) {
			String result = searcher.searchText(text[0], text[1]);
			String[] lines = result.split(System.getProperty("line.separator"));
			for(String line : lines)
			{
				if(line == null) {
					continue;
				} else if(line.contains("[PLAYER_LOGIN]")) {
					continue;
				} else if(line.contains("[PLAYER_COMMAND]")) {
					continue;
				} else if(line.contains("[BLOCK_BREAK]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String type = content[6];
					String worldname = content[13];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
					
					if (m == null) {
						GriefLog.log.info("Could not get the right materials!");
						continue;
					} else if(m == Material.LONG_GRASS) {
						world.getBlockAt(loc).setType(m);
						world.getBlockAt(loc).setData((byte) 1);
					} else {
						world.getBlockAt(loc).setType(m);
					}
				} else if(line.contains("[ENTITY_EXPLODE]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[8].replace(",", "");
					String strY = content[9].replace(",", "");
					String strZ = content[10].replace(",", "");
					String type = content[6];
					String worldname = content[12];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
					
					if (m == null) {
						GriefLog.log.info("Could not get the right materials!");
						continue;
					} else if(m == Material.LONG_GRASS) {
						world.getBlockAt(loc).setType(m);
						world.getBlockAt(loc).setData((byte) 1);
					} else {
						world.getBlockAt(loc).setType(m);
					}
				} else if(line.contains("[BLOCK_PLACE]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String worldname = content[13];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					
					world.getBlockAt(loc).setType(Material.AIR);
				} else if(line.contains("[BUCKET_LAVA_EMPTY]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[6].replace(",", "");
					String strY = content[7].replace(",", "");
					String strZ = content[8].replace(",", "");
					String worldname = content[10];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					
					world.getBlockAt(loc).setType(Material.AIR);
				} else if(line.contains("[BUCKET_WATER_EMPTY]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[6].replace(",", "");
					String strY = content[7].replace(",", "");
					String strZ = content[8].replace(",", "");
					String worldname = content[10];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					
					world.getBlockAt(loc).setType(Material.AIR);
				}
			}
			sender.sendMessage(ChatColor.RED + "Rolled back da grief:D");
		} else if(text.length == 3) {
			String result = searcher.searchText(text[0], text[1], text[2]);
			String[] lines = result.split(System.getProperty("line.separator"));
			for(String line : lines)
			{
				if(line == null) {
					continue;
				} else if(line.contains("[PLAYER_LOGIN]")) {
					continue;
				} else if(line.contains("[PLAYER_COMMAND]")) {
					continue;
				} else if(line.contains("[BLOCK_BREAK]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String type = content[6];
					String worldname = content[13];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
					
					if (m == null) {
						GriefLog.log.info("Could not get the right materials!");
						continue;
					} else if(m == Material.LONG_GRASS) {
						world.getBlockAt(loc).setType(m);
						world.getBlockAt(loc).setData((byte) 1);
					} else {
						world.getBlockAt(loc).setType(m);
					}
				} else if(line.contains("[ENTITY_EXPLODE]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[8].replace(",", "");
					String strY = content[9].replace(",", "");
					String strZ = content[10].replace(",", "");
					String type = content[6];
					String worldname = content[12];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
					
					if (m == null) {
						GriefLog.log.info("Could not get the right materials!");
						continue;
					} else if(m == Material.LONG_GRASS) {
						world.getBlockAt(loc).setType(m);
						world.getBlockAt(loc).setData((byte) 1);
					} else {
						world.getBlockAt(loc).setType(m);
					}
				} else if(line.contains("[BLOCK_PLACE]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String worldname = content[13];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					
					world.getBlockAt(loc).setType(Material.AIR);
				} else if(line.contains("[BUCKET_LAVA_EMPTY]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[6].replace(",", "");
					String strY = content[7].replace(",", "");
					String strZ = content[8].replace(",", "");
					String worldname = content[10];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					
					world.getBlockAt(loc).setType(Material.AIR);
				} else if(line.contains("[BUCKET_WATER_EMPTY]")) {
					
					String[] content = line.split("\\ ");
					
					String strX = content[6].replace(",", "");
					String strY = content[7].replace(",", "");
					String strZ = content[8].replace(",", "");
					String worldname = content[10];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					
					world.getBlockAt(loc).setType(Material.AIR);
				}
			}
			sender.sendMessage(ChatColor.RED + "Rolled back da grief:D");
		}
		if(text.length > 3) {
			return;
		}
	}
}
