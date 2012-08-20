package tk.blackwolf12333.grieflog.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
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
import tk.blackwolf12333.grieflog.callback.BlockProtectionCallback;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.data.PlayerChangedGamemodeData;
import tk.blackwolf12333.grieflog.data.PlayerChangedWorldData;
import tk.blackwolf12333.grieflog.data.PlayerCommandData;
import tk.blackwolf12333.grieflog.data.PlayerJoinData;
import tk.blackwolf12333.grieflog.data.PlayerQuitData;
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
			PlayerChangedGamemodeData data = new PlayerChangedGamemodeData(event.getPlayer().getName(), event.getPlayer().getGameMode().getValue(), event.getPlayer().getWorld().getName(), event.getNewGameMode().getValue());
			
			GriefLogger logger = new GriefLogger(data.toString());
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		if (ConfigHandler.values.getWorldChange()) {
			PlayerChangedWorldData data = new PlayerChangedWorldData(event.getPlayer().getName(), event.getPlayer().getGameMode().getValue(), event.getPlayer().getWorld().getName(), event.getFrom().getName());
			
			GriefLogger logger = new GriefLogger(data.toString());
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (ConfigHandler.values.getCommand()) {
			PlayerCommandData data = new PlayerCommandData(event.getPlayer().getName(), event.getPlayer().getGameMode().getValue(), event.getPlayer().getWorld().getName(), event.getMessage());
			
			GriefLogger logger = new GriefLogger(data.toString());
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
			int x = p.getLocation().getBlockX();
			int y = p.getLocation().getBlockY();
			int z = p.getLocation().getBlockZ();

			PlayerJoinData data = new PlayerJoinData(event.getPlayer().getName(), event.getPlayer().getGameMode().getValue(), event.getPlayer().getWorld().getName(), address, x, y, z);
			
			GriefLogger logger = new GriefLogger(data.toString());
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDisconnect(PlayerQuitEvent event) {
		GriefLog.players.remove(event.getPlayer().getName());
		
		if(ConfigHandler.values.getPlayerQuit()) {
			int x = event.getPlayer().getLocation().getBlockX();
			int y = event.getPlayer().getLocation().getBlockY();
			int z = event.getPlayer().getLocation().getBlockZ();
			
			PlayerQuitData data = new PlayerQuitData(event.getPlayer().getName(), event.getPlayer().getGameMode().getValue(), event.getPlayer().getWorld().getName(), x, y, z);
			
			GriefLogger logger = new GriefLogger(data.toString());
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action a = event.getAction();
		Player p = event.getPlayer();
		Block b = event.getClickedBlock();
		
		// check which action occured and handle appropriate
		if (a == Action.LEFT_CLICK_BLOCK) {
			// check if the item in hand of the player == the selection tool
			// specified in the config file
			if (p.getItemInHand().getTypeId() == ConfigHandler.config.getInt("SelectionTool")) {
				if(p.isSneaking()) {
					GLPlayer player = GLPlayer.getGLPlayer(event.getPlayer());
					b = b.getRelative(event.getBlockFace());

					Integer x = b.getX();
					Integer y = b.getY();
					Integer z = b.getZ();
					String world = b.getWorld().getName();

					event.setCancelled(true);
					
					ArrayList<String> args = new ArrayList<String>();
					args.add(x + ", " + y + ", " + z + " in: " + world);
					new SearchTask(player, new SearchCallback(player), args);
				}
				GLPlayer player = GLPlayer.getGLPlayer(p);
				
				Integer x = b.getX();
				Integer y = b.getY();
				Integer z = b.getZ();
				String world = b.getWorld().getName();

				event.setCancelled(true);
				
				ArrayList<String> args = new ArrayList<String>();
				args.add(x + ", " + y + ", " + z + " in: " + world);
				new SearchTask(player, new SearchCallback(player), args);
			}
		} else if(a == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getType() == Material.FLINT_AND_STEEL) {
				if(event.getClickedBlock().getType() == Material.TNT) {
					playerFAS.put(event.getClickedBlock(), p.getName());
				}
			}
			
			if((b.getType() == Material.LEVER) || (b.getType() == Material.STONE_BUTTON)) {
				if(ConfigHandler.values.getBlockprotection()) {
					GLPlayer player = GriefLog.players.get(p.getName());
					
					int x = b.getX();
					int y = b.getY();
					int z = b.getZ();
					String world = b.getWorld().getName();
					String loc = x + ", " + y + ", " + z + " in: " + world;
					String evt = "[BLOCK_PLACE]";
					
					new SearchTask(player, new BlockProtectionCallback(player, null, event), loc, evt);
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
