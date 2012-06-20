package tk.blackwolf12333.grieflog.Listeners;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogSearcher;

public class GLPlayerListener implements Listener {

	GriefLog gl;
	List<File> files = new ArrayList<File>();
	GriefLogSearcher searcher = new GriefLogSearcher();

	public GLPlayerListener(GriefLog plugin) {
		gl = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
		if (gl.getConfig().getBoolean("ChangeGameMode")) {
			Player player = event.getPlayer();
			String p = player.getName();
			GameMode gm = event.getNewGameMode();
			int gameM = gm.getValue();
			World world = player.getWorld();
			String playerWorld = world.getName();

			String data = " [GAMEMODE_CHANGE] " + p + " New Gamemode: " + gameM + " Where: " + playerWorld + System.getProperty("line.separator");
			GriefLog.logger.Log(data);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {

		if (gl.getConfig().getBoolean("ChangeWorld")) {
			Player player = event.getPlayer();
			String playerName = player.getName();
			World where = event.getFrom();
			String from = where.getName();

			String data = " [WORLD_CHANGE] Who: " + playerName + " From: " + from + System.getProperty("line.separator");
			GriefLog.logger.Log(data);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		
		if (gl.getConfig().getBoolean("DoCommand")) {
			String cmd = event.getMessage();
			String namePlayer = event.getPlayer().getName();

			String data = " [PLAYER_COMMAND] Who: " + namePlayer + " Command: " + cmd + System.getProperty("line.separator");
			GriefLog.logger.Log(data);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		// check if the player is op, if so, tell him/her, if there are new
		// reports
		if (p.isOp()) {
			if (GriefLog.reportFile.exists()) {
				event.getPlayer().sendMessage("There Are New Player Reports!\n");
				event.getPlayer().sendMessage("Type /glog read to read the reports");
			} else {
				// if not do nothing
			}
		}

		if (gl.getConfig().getBoolean("PlayerJoin")) {
			InetAddress address = event.getPlayer().getAddress().getAddress();
			int gm = event.getPlayer().getGameMode().getValue();
			String name = event.getPlayer().getName();
			String worldName = event.getPlayer().getWorld().getName();

			String data = " [PLAYER_LOGIN] " + name + " On: " + address.getHostAddress() + " With GameMode: " + gm + " In: " + worldName + System.getProperty("line.separator");
			GriefLog.logger.Log(data);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action a = event.getAction();
		Player p = event.getPlayer();
		// check if the player left clicked a block
		if (a == Action.LEFT_CLICK_BLOCK) {
			// check if the item in hand of the player == the selection tool
			// specified in the config file
			if (p.getItemInHand().getTypeId() == gl.getConfig().getInt("SelectionTool")) {
				Block b = event.getClickedBlock();
				Player player = event.getPlayer();

				Integer x = b.getX();
				Integer y = b.getY();
				Integer z = b.getZ();

				event.setCancelled(true);

				player.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");
				player.sendMessage(searcher.searchPos(x, y, z));
				player.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");
			}
		}
		
		if(a == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getTypeId() == gl.getConfig().getInt("SelectionTool")) {
				BlockFace face = event.getBlockFace();
				Block b = event.getClickedBlock().getRelative(face);
				Player player = event.getPlayer();

				Integer x = b.getX();
				Integer y = b.getY();
				Integer z = b.getZ();

				event.setCancelled(true);

				player.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");
				player.sendMessage(searcher.searchPos(x, y, z));
				player.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");
			}
		}
	}
}
