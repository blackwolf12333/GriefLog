package tk.blackwolf12333.grieflog.rollback;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.search.GriefLogSearcher;
import tk.blackwolf12333.grieflog.search.Searcher;
import tk.blackwolf12333.grieflog.search.WorldEditSearcher;
import tk.blackwolf12333.grieflog.utils.Events;

public class RollbackWE implements Runnable {

	GriefLog plugin;
	CommandSender sender;
	World world;
	int count;
	String result;
	
	ArrayList<String> allLines = new ArrayList<String>();
	
	
	public RollbackWE(GriefLog plugin, CommandSender sender, ArrayList<String> args) {
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
			Searcher searcher = new WorldEditSearcher();
			if(args.size() < 1)
				return;
			if(args.size() == 1) {
				result = searcher.searchText(args.get(0));
				if(result != null) {
					String[] lines = result.split(System.getProperty("line.separator"));
					for(String line : lines) {
						allLines.add(line);
					}
				} else {
					sender.sendMessage(ChatColor.YELLOW + "[GriefLog] Nothing found to rollback.");
				}
			}
			if(args.size() == 2) {
				result = searcher.searchText(args.get(0), args.get(1));
				if(result != null) {
					String[] lines = result.split(System.getProperty("line.separator"));
					for(String line : lines) {
						allLines.add(line);
					}
				} else {
					sender.sendMessage(ChatColor.YELLOW + "[GriefLog] Nothing found to rollback.");
				}
			}
			if(args.size() == 3) {
				result = searcher.searchText(args.get(0), args.get(1), args.get(2));
				if(result != null) {
					String[] lines = result.split(System.getProperty("line.separator"));
					for(String line : lines) {
						allLines.add(line);
					}
				} else {
					sender.sendMessage(ChatColor.YELLOW + "[GriefLog] Nothing found to rollback.");
				}
			}
		} else {
			Searcher searcher = new GriefLogSearcher();
			if(args.size() < 1)
				return;
			if(args.size() == 1) {
				result = searcher.searchText(args.get(0));
				if(result != null) {
					String[] lines = result.split(System.getProperty("line.separator"));
					for(String line : lines) {
						allLines.add(line);
					}
				} else {
					sender.sendMessage(ChatColor.YELLOW + "[GriefLog] Nothing found to rollback.");
				}
			}
			if(args.size() == 2) {
				result = searcher.searchText(args.get(0), args.get(1));
				if(result != null) {
					String[] lines = result.split(System.getProperty("line.separator"));
					for(String line : lines) {
						allLines.add(line);
					}
				} else {
					sender.sendMessage(ChatColor.YELLOW + "[GriefLog] Nothing found to rollback.");
				}
			}
			if(args.size() == 3) {
				result = searcher.searchText(args.get(0), args.get(1), args.get(2));
				if(result != null) {
					String[] lines = result.split(System.getProperty("line.separator"));
					for(String line : lines) {
						allLines.add(line);
					}
				} else {
					sender.sendMessage(ChatColor.YELLOW + "[GriefLog] Nothing found to rollback.");
				}
			}
		}
	}
	
	@Override
	public void run() {
		if(allLines.size() == 0) {
			return;
		}
		while(count < allLines.size()) {
			for(String line : allLines) {
				rollbackWESelection(line);
				count++;
			}
		}
	}
	
	public boolean rollbackWESelection(String line) {
		if(line == null) {
			return false;
		} else if(line.contains("[PLAYER_LOGIN]")) {
			return false;
		} else if(line.contains("[PLAYER_COMMAND]")) {
			return false;
		} else if(line.contains(Events.BREAK.getEvent())) {
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
				if(isInWorldEditSelection((Player) sender, loc)) {
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
				if(isInWorldEditSelection((Player) sender, loc)) {
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
				if(isInWorldEditSelection((Player) sender, loc)) {
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
				if(isInWorldEditSelection((Player) sender, loc)) {
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
			} else {
				return false;
			}
		} else if(line.contains(Events.EXPLODE.getEvent())) {
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
			if(isInWorldEditSelection((Player) sender, loc)) {
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
		} else if(line.contains(Events.PLACE.getEvent())) {
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
				if(isInWorldEditSelection((Player) sender, loc)) {
					world.getBlockAt(loc).setType(Material.AIR);
					return true;
				} else {
					return false;
				}
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
				
				if(isInWorldEditSelection((Player) sender, loc)) {
					world.getBlockAt(loc).setType(Material.AIR);
					return true;
				} else {
					return false;
				}
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
				
				if(isInWorldEditSelection((Player) sender, loc)) {
					world.getBlockAt(loc).setType(Material.AIR);
					return true;
				} else {
					return false;
				}
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
				
				if(isInWorldEditSelection((Player) sender, loc)) {
					world.getBlockAt(loc).setType(Material.AIR);
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else if(line.contains(Events.LAVA.getEvent())) {
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
			if(isInWorldEditSelection((Player) sender, loc)) {
				world.getBlockAt(loc).setType(Material.AIR);
				return true;
			} else {
				return false;
			}
		} else if(line.contains(Events.WATER.getEvent())) {
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
			if(isInWorldEditSelection((Player) sender, loc)) {
				world.getBlockAt(loc).setType(Material.AIR);
				return true;
			} else {
				return false;
			}
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
			if(isInWorldEditSelection((Player) sender, loc)) {
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
		} else {
			return false;
		}
	}
	
	public boolean isInWorldEditSelection(Player player, Location block) {
		WorldEditPlugin we = (WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit");
		
		Selection sel = we.getSelection(player);
			
		if(sel.contains(block)) {
			return true;
		} else {
			return false;
		}
	}
}
