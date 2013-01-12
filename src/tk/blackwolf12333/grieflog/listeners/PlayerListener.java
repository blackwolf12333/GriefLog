package tk.blackwolf12333.grieflog.listeners;

import java.util.ArrayList;

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

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.data.player.PlayerChangedGamemodeData;
import tk.blackwolf12333.grieflog.data.player.PlayerChangedWorldData;
import tk.blackwolf12333.grieflog.data.player.PlayerCommandData;
import tk.blackwolf12333.grieflog.data.player.PlayerJoinData;
import tk.blackwolf12333.grieflog.data.player.PlayerQuitData;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;
import tk.blackwolf12333.grieflog.utils.logging.GriefLogger;
import tk.blackwolf12333.grieflog.utils.searching.tasks.SearchTask;

public class PlayerListener implements Listener {

	GriefLog plugin;
	
	public PlayerListener(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
		if (ConfigHandler.values.getGmChange()) {
			PlayerChangedGamemodeData data = new PlayerChangedGamemodeData(event.getPlayer().getName(), event.getPlayer().getGameMode().getValue(), event.getPlayer().getWorld().getName(), event.getNewGameMode().getValue());
			
			new GriefLogger(data);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		if (ConfigHandler.values.getWorldChange()) {
			PlayerChangedWorldData data = new PlayerChangedWorldData(event.getPlayer().getName(), event.getPlayer().getGameMode().getValue(), event.getPlayer().getWorld().getName(), event.getFrom().getName());
			
			new GriefLogger(data);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (ConfigHandler.values.getCommand()) {
			if(ConfigHandler.values.getIgnoredCommands().contains(event.getMessage().split(" ")[0].trim())) return;
			PlayerCommandData data = new PlayerCommandData(event.getPlayer().getName(), event.getPlayer().getGameMode().getValue(), event.getPlayer().getWorld().getName(), event.getMessage());
			
			new GriefLogger(data);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		if (ConfigHandler.values.getPlayerJoin()) {
			Player p = event.getPlayer();
			GriefLog.sessions.put(p.getName(), new PlayerSession(p));
			
			String address = event.getPlayer().getAddress().getAddress().getHostAddress();
			int x = p.getLocation().getBlockX();
			int y = p.getLocation().getBlockY();
			int z = p.getLocation().getBlockZ();

			PlayerJoinData data = new PlayerJoinData(event.getPlayer().getName(), event.getPlayer().getGameMode().getValue(), event.getPlayer().getWorld().getName(), address, x, y, z);
			
			new GriefLogger(data);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDisconnect(PlayerQuitEvent event) {
		GriefLog.sessions.remove(event.getPlayer().getName());
		
		if(ConfigHandler.values.getPlayerQuit()) {
			int x = event.getPlayer().getLocation().getBlockX();
			int y = event.getPlayer().getLocation().getBlockY();
			int z = event.getPlayer().getLocation().getBlockZ();
			
			PlayerQuitData data = new PlayerQuitData(event.getPlayer().getName(), event.getPlayer().getGameMode().getValue(), event.getPlayer().getWorld().getName(), x, y, z);
			new GriefLogger(data);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Action a = event.getAction();
		Player p = event.getPlayer();
		
		if (a == Action.LEFT_CLICK_BLOCK) {
			if (p.getItemInHand().getTypeId() == ConfigHandler.values.getTool()) {
				PlayerSession player = PlayerSession.getGLPlayer(p);
				Block b = event.getClickedBlock();
				
				Integer x = b.getX();
				Integer y = b.getY();
				Integer z = b.getZ();
				String world = b.getWorld().getName();

				event.setCancelled(true);
				p.getInventory().setMaxStackSize(64);
				
				ArrayList<String> args = new ArrayList<String>();
				args.add(x + ", " + y + ", " + z);
<<<<<<< HEAD
				new SearchTask(player, new SearchCallback(player, SearchCallback.Type.SEARCH), args, world);
=======
				new SearchTask(player, new SearchCallback(player), args, world);
>>>>>>> b72a6e8156a4ffe473266872bc48d4b20b0c9e10
			}
		} else if(a == Action.RIGHT_CLICK_BLOCK) {
			if(event.getPlayer().getInventory().getItemInHand().getTypeId() == ConfigHandler.values.getTool()) {
				
			}
			if(p.getItemInHand().getType() == Material.FLINT_AND_STEEL) {
				if(event.getClickedBlock().getType() == Material.TNT) {
					Tracker.playerFAS.put(event.getClickedBlock(), p.getName());
				}
			}
		}
	}
}
