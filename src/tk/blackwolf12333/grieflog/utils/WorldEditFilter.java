package tk.blackwolf12333.grieflog.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.action.BaseAction;

public class WorldEditFilter implements Runnable {

	public ArrayList<String> result = new ArrayList<String>();
	ArrayList<String> searchResult;
	World world;
	GLPlayer player;
	BaseAction action;
	
	public WorldEditFilter(ArrayList<String> searchResult, BaseAction action, GLPlayer player) {
		this.searchResult = searchResult;
		this.player = player;
		this.action = action;
		
		Bukkit.getScheduler().scheduleAsyncDelayedTask(player.getGriefLog(), this);
	}
	
	@Override
	public void run() {
		for(int i = 0; i < searchResult.size(); i++) {
			String line = searchResult.get(i);
			
			if(line == null) {
				continue;
			} else if(line.contains(Events.JOIN.getEvent())) {
				continue;
			} else if(line.contains(Events.QUIT.getEvent())) {
				continue;
			} else if(line.contains(Events.COMMAND.getEvent())) {
				continue;
			} else if(line.contains(Events.BREAK.getEvent())) {
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
						result.add(line);
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
						result.add(line);
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
						result.add(line);
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
						result.add(line);
					}
				}
			} else if(line.contains(Events.EXPLODE.getEvent())) {
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
						result.add(line);
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
						result.add(line);
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
						result.add(line);
					}
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
						result.add(line);
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
						result.add(line);
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
						result.add(line);
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
						result.add(line);
					}
				}
			} else if(line.contains(Events.LAVA.getEvent())) {
				String[] content = line.split("\\ ");
				
				if(content.length == 11) {
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
						result.add(line);
					}
				} else if(content.length == 12) {
					String strX = content[7].replace(",", "");
					String strY = content[8].replace(",", "");
					String strZ = content[9].replace(",", "");
					String worldname = content[11].trim();
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					if(isInWorldEditSelection(loc)) {
						result.add(line);
					}
				} else if(content.length == 13) {
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
						result.add(line);
					}
				}
			} else if(line.contains(Events.WATER.getEvent())) {
				String[] content = line.split("\\ ");
				
				if(content.length == 11) {
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
						result.add(line);
					}
				} else if(content.length == 12) {
					String strX = content[7].replace(",", "");
					String strY = content[8].replace(",", "");
					String strZ = content[9].replace(",", "");
					String worldname = content[11].trim();
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					if(isInWorldEditSelection(loc)) {
						result.add(line);
					}
				} else if(content.length == 13) {
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
						result.add(line);
					}
				}
			}
		}
		
		player.setSearchResult(result);
		action.result = result;
		action.start();
	}

	private boolean isInWorldEditSelection(Location loc) {
		return player.getWorldEditSelection().contains(loc);
	}
}
