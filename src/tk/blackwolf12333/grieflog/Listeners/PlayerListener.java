package tk.blackwolf12333.grieflog.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import tk.blackwolf12333.grieflog.SearchTask;
import tk.blackwolf12333.grieflog.action.BlockProtectionAction;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class PlayerListener implements Listener {

	GriefLog plugin;
	
	List<File> files = new ArrayList<File>();
	public static HashMap<Block, String> playerFAS = new HashMap<Block, String>();

	public PlayerListener(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
		if (ConfigHandler.values.getGmChange()) {
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
		if (ConfigHandler.values.getWorldChange()) {
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
		if (ConfigHandler.values.getCommand()) {
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

		if (ConfigHandler.values.getPlayerJoin()) {
			String address = event.getPlayer().getAddress().getAddress().getHostAddress();
			int gm = event.getPlayer().getGameMode().getValue();
			String name = event.getPlayer().getName();
			int x = p.getLocation().getBlockX();
			int y = p.getLocation().getBlockY();
			int z = p.getLocation().getBlockZ();
			String worldName = event.getPlayer().getWorld().getName();
			String where = " " + x + ", " + y + ", " + z + " in: " + worldName;

			String data = " [PLAYER_LOGIN] " + name + " On: " + address + " With GameMode: " + gm + where + System.getProperty("line.separator");
			GriefLogger logger = new GriefLogger(data);
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDisconnect(PlayerQuitEvent event) {
		GLPlayer p = GriefLog.players.get(event.getPlayer().getName());
		GriefLog.players.remove(p);
		p = null;
		
		if(ConfigHandler.values.getPlayerQuit()) {
			Player player = event.getPlayer();
			String name = player.getName();
			int gm = player.getGameMode().getValue();
			int x = player.getLocation().getBlockX();
			int y = player.getLocation().getBlockY();
			int z = player.getLocation().getBlockZ();
			String world = player.getWorld().getName();
			String where = " " + x + ", " + y + ", " + z + " in: " + world;
			
			String data = " [PLAYER_QUIT] " + name + " GM: " + gm + where + System.getProperty("line.separator");
			GriefLogger logger = new GriefLogger(data);
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action a = event.getAction();
		Player p = event.getPlayer();
		Block b = event.getClickedBlock();
		
		if (a == Action.LEFT_CLICK_BLOCK) {
			// check if the item in hand of the player == the selection tool
			// specified in the config file
			if (p.getItemInHand().getTypeId() == ConfigHandler.config.getInt("SelectionTool")) {
				GLPlayer player = GLPlayer.getGLPlayer(p);
				
				Integer x = b.getX();
				Integer y = b.getY();
				Integer z = b.getZ();

				event.setCancelled(true);
				
				ArrayList<String> args = new ArrayList<String>();
				args.add(x + ", " + y + ", " + z);
				player.search(false, args);
			}
		}
		
		if(a == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getTypeId() == ConfigHandler.values.getTool()) {
				BlockFace face = event.getBlockFace();
				b = event.getClickedBlock().getRelative(face);
				final GLPlayer player = GLPlayer.getGLPlayer(p);

				Integer x = b.getX();
				Integer y = b.getY();
				Integer z = b.getZ();

				event.setCancelled(true);
				
				ArrayList<String> args = new ArrayList<String>();
				args.add(x + ", " + y + ", " + z);
				player.search(false, args);
			} else if(p.getItemInHand().getType() == Material.FLINT_AND_STEEL) {
				if(event.getClickedBlock().getType() == Material.TNT) {
					playerFAS.put(event.getClickedBlock(), p.getName());
				}
			}
			
			Block clicked = event.getClickedBlock();
			
			if((clicked.getType() == Material.LEVER) || (clicked.getType() == Material.STONE_BUTTON)) {
				if(ConfigHandler.values.getBlockprotection()) {
					GLPlayer player = GriefLog.players.get(p.getName());
					
					int x = clicked.getX();
					int y = clicked.getY();
					int z = clicked.getZ();
					String world = clicked.getWorld().getName();
					String loc = x + ", " + y + ", " + z + " in: " + world;
					String evt = "[BLOCK_PLACE]";
					
					new SearchTask(player, new BlockProtectionAction(player, null, event), loc, evt);
					
					/*if(player.result != null) {
						String[] split1 = new String[player.result.size()];
						for(int i = 0; i < split1.length; i++) {
							split1[i] = player.result.get(i);
						}
						String[] split2 = split1[split1.length - 1].split(" ");
						String owner = split2[4];
						if(!ConfigHandler.isOnFriendsList(owner, event.getPlayer())) {
							event.setCancelled(true);
							event.getPlayer().sendMessage(ChatColor.DARK_GRAY + "Sorry this block is protected by " + owner + ".");
						}
					} else {
						// if the search result is null ignore it because than nothing happened on that location
					}*/
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
