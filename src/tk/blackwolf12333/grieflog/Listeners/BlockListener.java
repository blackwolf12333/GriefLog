package tk.blackwolf12333.grieflog.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogger;
import tk.blackwolf12333.grieflog.SearchTask;
import tk.blackwolf12333.grieflog.callback.BlockProtectionCallback;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.data.BlockBreakData;
import tk.blackwolf12333.grieflog.data.BlockIgniteData;
import tk.blackwolf12333.grieflog.data.BlockPlaceData;
import tk.blackwolf12333.grieflog.utils.InventoryStringDeSerializer;
import tk.blackwolf12333.grieflog.utils.config.ChestConfig;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class BlockListener implements Listener {

	GriefLog plugin;
	
	public HashMap<Block, String> igniterOfBlocks = new HashMap<Block, String>();
	public static HashMap<Block, String> playerTNT = new HashMap<Block, String>();
	public static HashMap<Block, String> playerTorch = new HashMap<Block, String>();
	
	public BlockListener(GriefLog plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if(ConfigHandler.values.getBlockprotection()) {
			handleBlockBreakProtection(event);
		}
		
		if(!event.isCancelled()) {
			if(event.getBlock().getType().toString().equalsIgnoreCase("AIR")) {
				handleBlockBreakAir(event);
				return;
			} else if(event.getBlock().getType() == Material.CHEST) {
				handleBreakChest(event);
			}
			
			String namePlayer = event.getPlayer().getName();
			Integer gm = event.getPlayer().getGameMode().getValue();
			BlockBreakData data = new BlockBreakData(event.getBlock(), namePlayer, gm);
			
			GriefLogger logger = new GriefLogger(data.toString());
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, logger);
		}
	}
	
	private void handleBlockBreakProtection(BlockBreakEvent event) {
		if(!isBlockOnBlacklist(event.getBlock().getTypeId())) {
			if(!GriefLog.permission.has(event.getPlayer(), "grieflog.blockprotection.bypass")) {
				GLPlayer player = GriefLog.players.get(event.getPlayer().getName());
				
				int x = event.getBlock().getX();
				int y = event.getBlock().getY();
				int z = event.getBlock().getZ();
				String world = event.getBlock().getWorld().getName();
				if(ConfigHandler.values.getWorlds().contains(world)) {
					String loc = x + ", " + y + ", " + z + " in: " + world;
					String evt = "[BLOCK_PLACE]";
					
					new SearchTask(player, new BlockProtectionCallback(player, event, null), loc, evt);
				}
			}
		}
	}
	
	private void handleBlockBreakAir(BlockBreakEvent event) {
		if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			for(Player p : plugin.getServer().getOnlinePlayers()) {
				if(GriefLog.permission.has(p, "grieflog.getwarning.onbreakair")) {
					p.sendMessage(ChatColor.DARK_RED + "[GriefLog] Player " + event.getPlayer().getName() + " might be a hacker, he tried to break air!");
				} else {
					continue;
				}
			}
		}
		return;
	}
	
	private void handleBreakChest(BlockBreakEvent event) {
		Block b = event.getBlock();
		Chest chest = (Chest) event.getBlock().getState();
		String inv = InventoryStringDeSerializer.InventoryToString(chest.getBlockInventory());
		String strChest = b.getX() + "#" + b.getY() + "#" + b.getZ() + "#" + b.getWorld().getName();
		ChestConfig.addChest(strChest, inv);
		return;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent event) {
		// due to limitations of the minecraft server i had to move this from the PlayerInteract to here:(
		if(event.getPlayer().getItemInHand().getTypeId() == ConfigHandler.values.getTool()) {
			Block b = event.getBlock();
			GLPlayer player = GLPlayer.getGLPlayer(event.getPlayer());

			Integer x = b.getX();
			Integer y = b.getY();
			Integer z = b.getZ();
			String world = b.getWorld().getName();

			event.setCancelled(true);
			
			ArrayList<String> args = new ArrayList<String>();
			args.add(x + ", " + y + ", " + z + " in: " + world);
			new SearchTask(player, new SearchCallback(player), args);
		}
		
		if(event.getBlock().getType() == Material.TNT) {
			for(BlockFace face : BlockFace.values()) {
				if(event.getBlock().getRelative(face).getType() == Material.REDSTONE_TORCH_ON) {
					playerTNT.put(event.getBlock(), event.getPlayer().getName());
				}
			}
		} else if(event.getBlock().getType() == Material.REDSTONE_TORCH_ON) {
			for(BlockFace face : BlockFace.values()) {
				if(event.getBlock().getRelative(face).getType() == Material.TNT) {
					playerTorch.put(event.getBlock().getRelative(face), event.getPlayer().getName());
				}
			}
		}
		
		if((!event.isCancelled())) {
			String namePlayer = event.getPlayer().getName();
			Integer gm = event.getPlayer().getGameMode().getValue();
			
			if (event.getBlockPlaced().getType().toString().equalsIgnoreCase("FIRE")) {
				// this gets handled by the onBlockIgnite() function
				return;
			}
			
			BlockPlaceData data = new BlockPlaceData(event.getBlock(), namePlayer, gm);
			
			GriefLogger logger = new GriefLogger(data.toString());
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, logger);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockIgnite(BlockIgniteEvent event) {
		if(ConfigHandler.values.getAntifire()) {
			event.setCancelled(true);
			return;
		}
		
		if((ConfigHandler.values.getBlockIgnite()) && (!event.isCancelled())) {
			BlockIgniteData data = null;
			Player player = event.getPlayer();
			
			// check if it was the environment that ignited the block
			if (player == null) {
				if (ConfigHandler.values.getIgnoreEnvironment()) {
					return;
				} else {
					data = new BlockIgniteData(event.getBlock(), event.getCause().toString(), "Environment", 0);
				}
			} else {
				if(event.getBlock().getType() == Material.TNT) {
					GLPlayer p = new GLPlayer(plugin, player);
					p.playersIgnitedTNT.put(event.getBlock().getLocation(), player.getName());
				}
				
				if(!igniterOfBlocks.containsKey(event.getBlock())) {
					igniterOfBlocks.put(event.getBlock(), player.getName());
				}
				
				data = new BlockIgniteData(event.getBlock(), event.getCause().toString(), event.getPlayer().getName(), event.getPlayer().getGameMode().getValue());
			}
			
			GriefLogger logger = new GriefLogger(data.toString());
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}
	
	@EventHandler
	public void onFireSpread(BlockSpreadEvent event) {
		Block b = event.getBlock();
		Block source = event.getSource();
		if(igniterOfBlocks.containsKey(source)) {
			BlockIgniteData data = new BlockIgniteData(b, "Spread", igniterOfBlocks.get(source), 0);
			GriefLogger logger = new GriefLogger(data.toString());
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}
	
	public boolean isBlockOnBlacklist(int id) {
		List<Integer> blacklist = ConfigHandler.values.getItemBlacklist();
		if(blacklist == null) {
			return false;
		}
		
		for(int i = 0; i < blacklist.size(); i++) {
			if(id == blacklist.get(i)) {
				return true;
			}
		}
		
		return false;
	}
}
