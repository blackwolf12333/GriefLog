package tk.blackwolf12333.grieflog;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import tk.blackwolf12333.grieflog.rollback.RollbackBase;
import tk.blackwolf12333.grieflog.search.GriefLogSearcher;
import tk.blackwolf12333.grieflog.search.Searcher;
import tk.blackwolf12333.grieflog.utils.Events;

public class GLPlayer {
	
	World world;
	Player player;
	GriefLog plugin;
	CommandSender sender;
	Boolean worldedit;
	public Integer rollbackTaskID;
	
	public boolean isDoingRollback = false;
	public boolean isSearching = false;
	public HashMap<Location, String> playersIgnitedTNT = new HashMap<Location, String>();
	public ArrayList<String> result = new ArrayList<String>();
	public ArrayList<String> filteredResult = new ArrayList<String>();
	
	public GLPlayer(GriefLog plugin, Player player) {
		this.player = player;
		this.plugin = plugin;
	}
	
	public GLPlayer(GriefLog plugin, CommandSender sender) {
		this.sender = sender;
		this.plugin = plugin;
	}
	
	public void search(boolean we, ArrayList<String> args) {
		isSearching = true;
		worldedit = we;
		Searcher searcher = new GriefLogSearcher(this, plugin);
		
		if(args.size() == 1) {
			result = searcher.searchText(args.get(0));
		} else if(args.size() == 2) {
			result = searcher.searchText(args.get(0), args.get(1));
		} else if(args.size() == 3) {
			result = searcher.searchText(args.get(0), args.get(1), args.get(2));
		} else {
			result = null;
		}
		
		if(we)
			filterForWorldEditRollback();
		
		isSearching = false;
	}
	
	@SuppressWarnings("unused")
	public void rollback() {
		isDoingRollback = true;
		
		if(worldedit) {
			player.sendMessage(ChatColor.YELLOW + "[GriefLog] Now going to rollback " + filteredResult.size() + " events!");
			RollbackBase rb = new RollbackBase(plugin, this, filteredResult);
		} else {
			player.sendMessage(ChatColor.YELLOW + "[GriefLog] Now going to rollback " + result.size() + " events!");
			RollbackBase rb = new RollbackBase(plugin, this, result);
		}
		result = null;
		
		isDoingRollback = false;
	}
	
	private void filterForWorldEditRollback() {
		for(int i = 0; i < result.size(); i++) {
			String line = result.get(i);
			
			if(line.contains(Events.BREAK.getEvent())) {
				String[] content = line.split("\\ ");
				if(content.length == 13) {
					String strX = content[8].replace(",", "");
					String strY = content[9].replace(",", "");
					String strZ = content[10].replace(",", "");
					String worldname = content[12].trim();
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					if(isInWorldEditSelection(loc)) {
						filteredResult.add(line);
					}
				} else if(content.length == 14) {
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String worldname = content[13].trim();
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					if(isInWorldEditSelection(loc)) {
						filteredResult.add(line);
					}
				} else if(content.length == 15) {
					String strX = content[10].replace(",", "");
					String strY = content[11].replace(",", "");
					String strZ = content[12].replace(",", "");
					String worldname = content[14].trim();
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					if(isInWorldEditSelection(loc)) {
						filteredResult.add(line);
					}
				} else if(content.length == 16) {
					String strX = content[11].replace(",", "");
					String strY = content[12].replace(",", "");
					String strZ = content[13].replace(",", "");
					String worldname = content[15].trim();
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					if(isInWorldEditSelection(loc)) {
						filteredResult.add(line);
					}
				}
			} else if(line.contains(Events.EXPLODE.getEvent())) {
				String[] content = line.split("\\ ");
				
				String strX = content[8].replace(",", "");
				String strY = content[9].replace(",", "");
				String strZ = content[10].replace(",", "");
				String worldname = content[12].trim();
				
				int x = Integer.parseInt(strX);
				int y = Integer.parseInt(strY);
				int z = Integer.parseInt(strZ);

				world = Bukkit.getWorld(worldname);
				Location loc = new Location(world, x, y, z);
				if(isInWorldEditSelection(loc)) {
					filteredResult.add(line);
				}
			} else if(line.contains(Events.PLACE.getEvent())) {
				String[] content = line.split("\\ ");
				
				if(content.length == 13) {
					String strX = content[8].replace(",", "");
					String strY = content[9].replace(",", "");
					String strZ = content[10].replace(",", "");
					String worldname = content[12].trim();
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					if(isInWorldEditSelection(loc)) {
						filteredResult.add(line);
					}
				} else if(content.length == 14) {
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String worldname = content[13].trim();
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					if(isInWorldEditSelection(loc)) {
						filteredResult.add(line);
					}
				} else if(content.length == 15) {
					String strX = content[10].replace(",", "");
					String strY = content[11].replace(",", "");
					String strZ = content[12].replace(",", "");
					String worldname = content[14].trim();
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					if(isInWorldEditSelection(loc)) {
						filteredResult.add(line);
					}
				} else if(content.length == 16) {
					String strX = content[11].replace(",", "");
					String strY = content[12].replace(",", "");
					String strZ = content[13].replace(",", "");
					String worldname = content[15].trim();
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					if(isInWorldEditSelection(loc)) {
						filteredResult.add(line);
					}
				}
			} else if(line.contains(Events.LAVA.getEvent())) {
				String[] content = line.split("\\ ");
				
				String strX = content[6].replace(",", "");
				String strY = content[7].replace(",", "");
				String strZ = content[8].replace(",", "");
				String worldname = content[10].trim();
				
				int x = Integer.parseInt(strX);
				int y = Integer.parseInt(strY);
				int z = Integer.parseInt(strZ);

				world = Bukkit.getWorld(worldname);
				Location loc = new Location(world, x, y, z);
				if(isInWorldEditSelection(loc)) {
					filteredResult.add(line);
				}
			} else if(line.contains(Events.WATER.getEvent())) {
				String[] content = line.split("\\ ");
				
				String strX = content[6].replace(",", "");
				String strY = content[7].replace(",", "");
				String strZ = content[8].replace(",", "");
				String worldname = content[10].trim();
				
				int x = Integer.parseInt(strX);
				int y = Integer.parseInt(strY);
				int z = Integer.parseInt(strZ);

				world = Bukkit.getWorld(worldname);
				Location loc = new Location(world, x, y, z);
				if(isInWorldEditSelection(loc)) {
					filteredResult.add(line);
				}
			}
		}
	}
	
	public void print(String msg) {
		player.sendMessage(msg);
	}
	
	public void print(String[] msg) {
		player.sendMessage(msg);
	}
	
	private boolean isInWorldEditSelection(Location loc) {
		return this.getWorldEditSelection().contains(loc);
	}
	
	public World getWorld() {
		return player.getWorld();
	}
	
	public Server getServer() {
		return player.getServer();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ArrayList<String> getSearchResult() {
		return result;
	}
	
	public Selection getWorldEditSelection() {
		WorldEditPlugin we = (WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit");
		return we.getSelection(player);
	}
	
	public Integer getRollbackTaskID() {
		return rollbackTaskID;
	}
	
	@Override
	public String toString() {
		return "{GLPlayer} name: " + player.getName() + " rollback: " + isDoingRollback + " search: " + isSearching;
	}
	
	/**
	 * This equals if the object is an instance of GLPlayer,
	 * if the hashcode of this GLPlayer == hashcode of the object,
	 * if the toString()'s are equal 
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof GLPlayer)) {
			return false;
		} else {
			GLPlayer p = (GLPlayer) obj;
			if(this.hashCode() == p.hashCode()) {
				return true;
			} else if(this.toString().equals(p.toString())) {
				return true;
			}
			return super.equals(obj);
		}
	}
}
