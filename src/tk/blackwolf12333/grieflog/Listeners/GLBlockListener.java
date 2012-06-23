package tk.blackwolf12333.grieflog.Listeners;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogger;

public class GLBlockListener implements Listener {

	GriefLog gl;
	GriefLogger logger;
	public static HashMap<String, Integer> tntIgnited = new HashMap<String, Integer>();
	
	public GLBlockListener(GriefLog plugin) {
		gl = plugin;
		logger = new GriefLogger(plugin);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {

		Integer blockX = event.getBlock().getLocation().getBlockX();
		Integer blockY = event.getBlock().getLocation().getBlockY();
		Integer blockZ = event.getBlock().getLocation().getBlockZ();
		Player player = event.getPlayer();
		String type = event.getBlock().getType().toString();
		String namePlayer = player.getName();
		String worldName = player.getWorld().getName();
		Integer gm = player.getGameMode().getValue();
		
		if(type.equalsIgnoreCase("AIR")) {
			return;
		}

		String data = " [BLOCK_BREAK] By: " + namePlayer + " GM: " + gm + " What: " + type + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + System.getProperty("line.separator");
		
		logger.Log(data);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent event) {

		Integer blockX = event.getBlock().getLocation().getBlockX();
		Integer blockY = event.getBlock().getLocation().getBlockY();
		Integer blockZ = event.getBlock().getLocation().getBlockZ();
		Player player = event.getPlayer();
		String type = event.getBlockPlaced().getType().toString();
		String namePlayer = player.getName();
		String worldName = player.getWorld().getName();
		Integer gm = player.getGameMode().getValue();
		
		if (type.equalsIgnoreCase("FIRE")) {
			// this gets handled by the onBlockIgnite() function
			return;
		}
		
		if(type.contains("redstone") || type.contains("REDSTONE")) {
			Block b = event.getBlock();
			for(BlockFace face : BlockFace.values()) {
				Block relative = b.getRelative(face);
				if(relative.getType() == Material.TNT) {
					if(tntIgnited.get(namePlayer) == null) {
						tntIgnited.put(namePlayer, 1);
					} else {
						tntIgnited.put(namePlayer, (tntIgnited.get(namePlayer)+1));
					}
				}
			}
		}
		
		// log it
		String data = " [BLOCK_PLACE] By: " + namePlayer + " GM: " + gm + " What: " + type + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + System.getProperty("line.separator");
		
		logger.Log(data);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockIgnite(BlockIgniteEvent event) throws EventException {
		if (gl.getConfig().getBoolean("BlockIgnite")) {
			String data = "";
			Player player = event.getPlayer();
			
			// check if it was the environment that ignited the blockChest
			if (player == null) {
				if (gl.getConfig().getBoolean("IgnoreEnvironment")) {
					return;
				} else {
					IgniteCause ic = event.getCause();
					String worldName = event.getBlock().getWorld().getName();
					int x = event.getBlock().getX();
					int y = event.getBlock().getY();
					int z = event.getBlock().getZ();
					
					data = " [BLOCK_IGNITE] By: Environment" + " How: " + ic.toString() + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
				}
			} else { // it is a player
				IgniteCause ic = event.getCause();
				String playerName = player.getName();
				String worldName = event.getBlock().getWorld().getName();
				Integer gm = player.getGameMode().getValue();
				int x = event.getBlock().getX();
				int y = event.getBlock().getY();
				int z = event.getBlock().getZ();

				data = " [BLOCK_IGNITE] By: " + playerName + " GM: " + gm + " How: " + ic.toString() + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
			}

			logger.Log(data);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onSignChange(SignChangeEvent event) {
		if (gl.getConfig().getBoolean("SignChangeText")) {
			Block b = event.getBlock();
			int x = b.getX();
			int y = b.getY();
			int z = b.getZ();
			String playerName = event.getPlayer().getName();
			String worldName = event.getBlock().getWorld().getName();
			String newTxt = event.getLine(0);

			String data = " [SIGN_CHANGE] By: " + playerName + " NewTxt: " + newTxt + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");

			logger.Log(data);
		}
	}
	
}
