package tk.blackwolf12333.grieflog.Listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
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
import tk.blackwolf12333.grieflog.GriefLogSearcher;
import tk.blackwolf12333.grieflog.GriefLogger;
import tk.blackwolf12333.grieflog.utils.Time;

public class GLBlockListener implements Listener {

	GriefLog gl;
	Time t = new Time();
	public String data; // dunno anymore why i added this, but i use it, so i
						// don't delete it
	GriefLogger logger;
	List<File> files = new ArrayList<File>();
	GriefLogSearcher searcher;

	public GLBlockListener(GriefLog plugin) {
		gl = plugin;
		logger = new GriefLogger(plugin);
		searcher = new GriefLogSearcher();
	}

	// dunno anymore why i added this, but i use it, so i don't delete it
	public String getData() {
		return data;
	}

	// dunno anymore why i added this, but i use it, so i don't delete it
	public void setData(String data) {
		this.data = data;
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

		String data = t.now() + " [BLOCK_BREAK] By: " + namePlayer + " What: " + type + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + System.getProperty("line.separator");

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
		if (type == "FIRE") {
			// this gets handled by the onBlockIgnite() function
			return;
		}

		// if the item in the players hand == the selection tool search the logs
		// with it
		if (player.getItemInHand().getTypeId() == gl.getConfig().getInt("SelectionTool")) {
			Block b = event.getBlock();

			Integer x = b.getX();
			Integer y = b.getY();
			Integer z = b.getZ();

			event.setCancelled(true);

			player.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");
			player.sendMessage(searcher.searchPos(x, y, z));
			player.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");
		} else // else log the event
		{
			String data = t.now() + " [BLOCK_PLACE] By: " + namePlayer + " What: " + type + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + System.getProperty("line.separator");
			setData(data);

			logger.Log(data);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockIgnite(BlockIgniteEvent event) throws EventException {
		if (gl.getConfig().getBoolean("BlockIgnite")) {
			String data = "";
			Player player = event.getPlayer();
			// check if it was the environment that ignited the block
			if (player == null) {
				if (!(gl.getConfig().getBoolean("IgnoreEnvironment"))) {
					IgniteCause ic = event.getCause();
					String worldName = event.getBlock().getWorld().getName();
					int x = event.getBlock().getX();
					int y = event.getBlock().getY();
					int z = event.getBlock().getZ();
					data = t.now() + " [BLOCK_IGNITE] By: Environment" + " How: " + ic.toString() + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
					setData(data);
				} else {
					return;
				}
			} else // it is a player
			{
				IgniteCause ic = event.getCause();
				String playerName = player.getName();
				String worldName = event.getBlock().getWorld().getName();
				int x = event.getBlock().getX();
				int y = event.getBlock().getY();
				int z = event.getBlock().getZ();

				data = t.now() + " [BLOCK_IGNITE] By: " + playerName + " How: " + ic.toString() + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
				setData(data);
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

			String data = t.now() + " [SIGN_CHANGE] By: " + playerName + " NewTxt: " + newTxt + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");

			logger.Log(data);
		}
	}

	// gonna implement this event later, not now
	/*
	 * @EventHandler(priority = EventPriority.NORMAL) public void
	 * onBlockPiston(BlockPistonEvent event) { int x = event.getBlock().getX();
	 * int y = event.getBlock().getY(); int z = event.getBlock().getZ(); String
	 * world = event.getBlock().getWorld().getName();
	 * 
	 * data = t.now() + " [BLOCK_PISTON] Where: " + x + ", " + y + ", " + z +
	 * " In: " + world;
	 * 
	 * try {
	 * 
	 * //if file doesnt exists, then create it if(!GriefLog.file.exists()){
	 * GriefLog.file.createNewFile(); }
	 * 
	 * if(fu.getFileSize(GriefLog.file) >= plugin.getConfig().getInt("mb")) {
	 * autoBackup(); }
	 * 
	 * fu.writeFile(GriefLog.file, data);
	 * 
	 * }catch(IOException e){ log.warning(e.toString()); } }
	 */
}
