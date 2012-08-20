package tk.blackwolf12333.grieflog.callback;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import tk.blackwolf12333.grieflog.GLPlayer;

public class TpToCallback extends BaseCallback {

	GLPlayer player;
	
	public TpToCallback(GLPlayer player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setSearching(false);
		player.setSearchResult(result);
		teleport();
	}
	
	private void teleport() {
		String lastLine = null;
		if(result.size() == 0) {
			player.print(ChatColor.DARK_RED + "This player has never disconnected, i can't teleport you.");
			return;
		} else {
			lastLine = result.get(result.size() - 1);
		}
		String[] split = lastLine.split(" ");
		
		// use the results to get the information needed
		if(split.length == 12) {
			String playername = split[3];
			double x = Integer.parseInt(split[7].replace(",", ""));
			double y = Integer.parseInt(split[8].replace(",", ""));
			double z = Integer.parseInt(split[9].replace(",", ""));
			World world = Bukkit.getServer().getWorld(split[11].trim());
			Location loc = new Location(world, x, y, z);
			
			// teleport the player and return true
			player.print(ChatColor.YELLOW + "[GriefLog] Teleporting you to " + playername + ".");
			player.teleport(loc);
		} else {
			String playername = split[3];
			double x = Integer.parseInt(split[6].replace(",", ""));
			double y = Integer.parseInt(split[7].replace(",", ""));
			double z = Integer.parseInt(split[8].replace(",", ""));
			World world = Bukkit.getServer().getWorld(split[11].trim());
			Location loc = new Location(world, x, y, z);
			
			// teleport the player and return true
			player.print(ChatColor.YELLOW + "[GriefLog] Teleporting you to " + playername + ".");
			player.teleport(loc);
		}
	}
}
