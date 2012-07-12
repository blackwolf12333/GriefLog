package tk.blackwolf12333.grieflog.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
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
import org.bukkit.event.player.PlayerQuitEvent;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogger;
import tk.blackwolf12333.grieflog.search.GriefLogSearcher;
import tk.blackwolf12333.grieflog.search.Searcher;
import tk.blackwolf12333.grieflog.utils.config.GLConfigHandler;

public class GLPlayerListener implements Listener {

	GriefLog plugin;
	
	List<File> files = new ArrayList<File>();
	Searcher searcher;

	public GLPlayerListener(GriefLog plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
		if (GLConfigHandler.values.getGmChange()) {
			Player player = event.getPlayer();
			String p = player.getName();
			GameMode gm = event.getNewGameMode();
			int gameM = gm.getValue();
			World world = player.getWorld();
			String playerWorld = world.getName();

			String data = " [GAMEMODE_CHANGE] " + p + " New Gamemode: " + gameM + " Where: " + playerWorld + System.getProperty("line.separator");
			GriefLogger logger = new GriefLogger(data);
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		if (GLConfigHandler.values.getWorldChange()) {
			Player player = event.getPlayer();
			String playerName = player.getName();
			World where = event.getFrom();
			String from = where.getName();

			String data = " [WORLD_CHANGE] Who: " + playerName + " From: " + from + System.getProperty("line.separator");
			GriefLogger logger = new GriefLogger(data);
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (GLConfigHandler.values.getCommand()) {
			String cmd = event.getMessage();
			String namePlayer = event.getPlayer().getName();

			String data = " [PLAYER_COMMAND] Who: " + namePlayer + " Command: " + cmd + System.getProperty("line.separator");
			GriefLogger logger = new GriefLogger(data);
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		
		GriefLog.players.put(p.getName(), new GLPlayer(plugin, p));
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

		if (GLConfigHandler.values.getPlayerJoin()) {
			String address = event.getPlayer().getAddress().getAddress().getHostAddress();
			int gm = event.getPlayer().getGameMode().getValue();
			String name = event.getPlayer().getName();
			String worldName = event.getPlayer().getWorld().getName();

			String data = " [PLAYER_LOGIN] " + name + " On: " + address + " With GameMode: " + gm + " In: " + worldName + System.getProperty("line.separator");
			GriefLogger logger = new GriefLogger(data);
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDisconnect(PlayerQuitEvent event) {
		GLPlayer p = GriefLog.players.get(event.getPlayer().getName());
		GriefLog.players.remove(p);
		p = null;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action a = event.getAction();
		Player p = event.getPlayer();
		
		if (a == Action.LEFT_CLICK_BLOCK) {
			// check if the item in hand of the player == the selection tool
			// specified in the config file
			if (p.getItemInHand().getTypeId() == GLConfigHandler.values.getTool()) {
				Block b = event.getClickedBlock();
				Player player = event.getPlayer();

				Integer x = b.getX();
				Integer y = b.getY();
				Integer z = b.getZ();

				event.setCancelled(true);
				
				searcher = new GriefLogSearcher(new GLPlayer(plugin, player), plugin);
				ArrayList<String> result = searcher.searchPos(x, y, z);
				if(result != null) {
					player.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");					
					for(int i = 0; i < result.size(); i++) {
						player.sendMessage(result.get(i));
					}
					player.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");
				} else {
					player.sendMessage(ChatColor.BLUE + "[GriefLog] Nothing Found Here.");
				}
			}
		}
		
		if(a == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getTypeId() == GLConfigHandler.values.getTool()) {
				BlockFace face = event.getBlockFace();
				Block b = event.getClickedBlock().getRelative(face);
				Player player = event.getPlayer();

				Integer x = b.getX();
				Integer y = b.getY();
				Integer z = b.getZ();

				event.setCancelled(true);
				
				searcher = new GriefLogSearcher(new GLPlayer(plugin, player), plugin);
				ArrayList<String> result = searcher.searchPos(x, y, z);
				if(result != null) {
					player.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");
					for(int i = 0; i < result.size(); i++) {
						player.sendMessage(result.get(i));
					}
					player.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");
				} else {
					player.sendMessage(ChatColor.BLUE + "[GriefLog] Nothing Found Here.");
				}
			}
			
			Block clicked = event.getClickedBlock();
			
			if((clicked.getType() == Material.LEVER) || (clicked.getType() == Material.STONE_BUTTON)) {
				if(GLConfigHandler.values.getBlockprotection()) {
					searcher = new GriefLogSearcher(new GLPlayer(plugin, event.getPlayer()), plugin);
					
					int x = clicked.getX();
					int y = clicked.getY();
					int z = clicked.getZ();
					String world = clicked.getWorld().getName();
					String loc = x + ", " + y + ", " + z + " in: " + world;
					String evt = "[BLOCK_PLACE]";
					
					ArrayList<String> result = searcher.searchText(evt, loc);
					
					if(result != null) {
						String[] split1 = new String[result.size()];
						for(int i = 0; i < split1.length; i++) {
							split1[i] = result.get(i);
						}
						String[] split2 = split1[split1.length - 1].split(" ");
						String owner = split2[4];
						if(!GLConfigHandler.isOnFriendsList(owner, event.getPlayer())) {
							event.setCancelled(true);
							event.getPlayer().sendMessage(ChatColor.DARK_GRAY + "Sorry this block is protected by " + owner + ".");
						}
					} else {
						// if the search result is null ignore it because than nothing happened on that location
					}
				}
			}
		}
	}
	
	/*int count = 0;
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMove(PlayerMoveEvent event) {
		Location to = event.getTo();
		Player player = event.getPlayer();
		
		Block blockTo = player.getWorld().getBlockAt(to);
		int x = blockTo.getX();
		int y = blockTo.getY();
		int z = blockTo.getZ();
		Block blockToOneDown = player.getWorld().getBlockAt(x, y-1, z);
		
		if(!(player.getGameMode() == GameMode.CREATIVE)) {
			if(!player.isInsideVehicle()) {
				if((blockToOneDown.getType() == Material.WATER) || (blockToOneDown.getType() == Material.STATIONARY_WATER)) {
					count++;
				}
			}
		}
		
		if(count == 20) {
			System.out.print("Player " + player.getName() + " being jesus!");
			count = 0;
			player.teleport(event.getFrom());
		}
	}*/
}
