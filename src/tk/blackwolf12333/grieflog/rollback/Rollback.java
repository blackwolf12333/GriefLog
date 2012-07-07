package tk.blackwolf12333.grieflog.rollback;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.search.GriefLogSearcher;
import tk.blackwolf12333.grieflog.search.Searcher;
import tk.blackwolf12333.grieflog.search.WorldEditSearcher;
import tk.blackwolf12333.grieflog.utils.Events;

public class Rollback implements Runnable {

	World world;
	String result;
	int count;
	CommandSender sender;
	GriefLog plugin;
	
	int blockCount = 0;
	Searcher searcher;
	ArrayList<String> allLines = new ArrayList<String>();
	
	public Rollback(GriefLog plugin, CommandSender sender, ArrayList<String> args) {
		count = 0;
		this.plugin = plugin;
		this.sender = sender;
		
		boolean we = false;
		for(int i = 0; i < args.size(); i++) {
			if(args.get(i).contains(Events.WORLDEDIT.getEvent())) {
				we = true;
			}
		}
		
		if(we) {
			searcher = new WorldEditSearcher();
			if(args.size() < 1)
				return;
			if(args.size() == 1) {
				result = searcher.searchText(args.get(0));
				String[] lines = result.split(System.getProperty("line.separator"));
				for(String line : lines) {
					allLines.add(line);
				}
			}
			if(args.size() == 2) {
				result = searcher.searchText(args.get(0), args.get(1));
				String[] lines = result.split(System.getProperty("line.separator"));
				for(String line : lines) {
					allLines.add(line);
				}
			}
			if(args.size() == 3) {
				result = searcher.searchText(args.get(0), args.get(1), args.get(2));
				String[] lines = result.split(System.getProperty("line.separator"));
				for(String line : lines) {
					allLines.add(line);
				}
			}
		} else {
			searcher = new GriefLogSearcher();
			if(args.size() < 1)
				return;
			if(args.size() == 1) {
				result = searcher.searchText(args.get(0));
				String[] lines = result.split(System.getProperty("line.separator"));
				for(String line : lines) {
					allLines.add(line);
				}
			}
			if(args.size() == 2) {
				result = searcher.searchText(args.get(0), args.get(1));
				String[] lines = result.split(System.getProperty("line.separator"));
				for(String line : lines) {
					allLines.add(line);
				}
			}
			if(args.size() == 3) {
				result = searcher.searchText(args.get(0), args.get(1), args.get(2));
				String[] lines = result.split(System.getProperty("line.separator"));
				for(String line : lines) {
					allLines.add(line);
				}
			}
		}
	}
	
	@Override
	public void run() {
		while(count < allLines.size()) {
			for(String line : allLines) {
				rollback(line);
				count++;
			}
		}
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
			if(content.length == 13) {
				String strX = content[8].replace(",", "");
				String strY = content[9].replace(",", "");
				String strZ = content[10].replace(",", "");
				String type = content[5];
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
			} else if(content.length == 14) {
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
					return false;
				} else if(m == Material.LONG_GRASS) {
					world.getBlockAt(loc).setType(m);
					world.getBlockAt(loc).setData((byte) 1);
					return true;
				} else {
					world.getBlockAt(loc).setType(m);
					return true;
				}
			} else if(content.length == 15) {
				String strX = content[10].replace(",", "");
				String strY = content[11].replace(",", "");
				String strZ = content[12].replace(",", "");
				String type = content[7];
				String worldname = content[14];
				
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
			} else if(content.length == 16) {
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
			} else {
				System.out.print("debug");
				return false;
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
			
			if(content.length == 13) {
				String strX = content[8].replace(",", "");
				String strY = content[9].replace(",", "");
				String strZ = content[10].replace(",", "");
				String worldname = content[12];
				
				int x = Integer.parseInt(strX);
				int y = Integer.parseInt(strY);
				int z = Integer.parseInt(strZ);

				world = Bukkit.getWorld(worldname);
				Location loc = new Location(world, x, y, z);

				world.getBlockAt(loc).setType(Material.AIR);
				return true;
			} else if(content.length == 14) {
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
				return true;
			} else if(content.length == 15) {
				String strX = content[10].replace(",", "");
				String strY = content[11].replace(",", "");
				String strZ = content[12].replace(",", "");
				String worldname = content[14];
				
				int x = Integer.parseInt(strX);
				int y = Integer.parseInt(strY);
				int z = Integer.parseInt(strZ);

				world = Bukkit.getWorld(worldname);
				Location loc = new Location(world, x, y, z);
				
				world.getBlockAt(loc).setType(Material.AIR);
				return true;
			} else if(content.length == 16) {
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
			} else {
				return false;
			}
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
		} else if(line.contains(Events.WORLDEDIT.getEvent())) {
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
		} else {
			return false;
		}
	}
}
