package tk.blackwolf12333.grieflog.listeners;

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
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPlaceEvent;

import tk.blackwolf12333.grieflog.utils.searching.SearchTask;
import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.data.block.BlockBreakData;
import tk.blackwolf12333.grieflog.data.block.BlockIgniteData;
import tk.blackwolf12333.grieflog.data.block.BlockPlaceData;
import tk.blackwolf12333.grieflog.utils.InventoryStringDeSerializer;
import tk.blackwolf12333.grieflog.utils.config.ChestConfig;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;
import tk.blackwolf12333.grieflog.utils.filters.LocationFilter;
import tk.blackwolf12333.grieflog.utils.logging.GriefLogger;

public class BlockListener implements Listener {

	GriefLog plugin;
	
	public BlockListener(GriefLog plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		if(!event.isCancelled()) {
			if(event.getBlock().getType() == Material.AIR) {
				handleBlockBreakAir(event);
				return;
			} else if(event.getBlock().getType() == Material.CHEST) {
				handleBreakChest(event);
				return;
			} else if(event.getBlock().getType() == Material.WOOD_DOOR || (event.getBlock().getType() == Material.IRON_DOOR)) {
				handleBreakDoor(event);
				return;
			}
			
			String namePlayer = event.getPlayer().getName();
			Integer gm = event.getPlayer().getGameMode().getValue();
			BlockBreakData data = new BlockBreakData(event.getBlock(), namePlayer, gm);
			
			new GriefLogger(data);
		}
	}

	private void handleBreakDoor(BlockBreakEvent event) {
		BlockFace[] faces = new BlockFace[] {BlockFace.DOWN, BlockFace.UP};
		for(BlockFace face : faces) {
			if((event.getBlock().getRelative(face).getType() == Material.WOOD_DOOR) || (event.getBlock().getRelative(face).getType() == Material.IRON_DOOR)) {
				int gm = event.getPlayer().getGameMode().getValue();
				BlockBreakData data = new BlockBreakData(event.getBlock().getRelative(face), event.getPlayer().getName(), gm);
				new GriefLogger(data);
			}
		}
	}

	private void handleBlockBreakAir(BlockBreakEvent event) {
		if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			for(Player p : plugin.getServer().getOnlinePlayers()) {
				if(GriefLog.sessions.get(p).hasPermission("grieflog.getwarning.onbreakair")) {
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
		Block b = event.getBlock();
		if(b.getTypeId() == ConfigHandler.values.getTool()) {
			PlayerSession session = PlayerSession.getGLPlayer(event.getPlayer());
			if(session.isUsingTool()) {
				Integer x = b.getX();
				Integer y = b.getY();
				Integer z = b.getZ();
				String world = b.getWorld().getName();

				event.setCancelled(true);
			
				new SearchTask(session, new SearchCallback(session, SearchCallback.Type.SEARCH), new LocationFilter(x, y, z, world));
			}
		}
		
		if((!event.isCancelled())) {
			handleRedstoneOrTnt(event);
			if(event.getBlock().getType() == Material.FIRE) {
				handlePlacedFire(event);
				return;
			}
			
			String namePlayer = event.getPlayer().getName();
			Integer gm = event.getPlayer().getGameMode().getValue();
			
			BlockPlaceData data = new BlockPlaceData(event.getBlock(), namePlayer, gm);
			new GriefLogger(data);
		}
	}

	private void handlePlacedFire(BlockPlaceEvent event) {
		Block b = event.getBlockAgainst();
		Tracker.playerIgnite.put(b, event.getPlayer().getName());
		
		BlockIgniteData data = new BlockIgniteData(b, IgniteCause.FLINT_AND_STEEL.toString(), event.getPlayer().getName(), event.getPlayer().getGameMode().getValue());
		new GriefLogger(data);
	}

	private void handleRedstoneOrTnt(BlockPlaceEvent event) {
		if(event.getBlock().getType() == Material.TNT) {
			for(BlockFace face : BlockFace.values()) {
				if(event.getBlock().getRelative(face).getType() == Material.REDSTONE_TORCH_ON) {
					Tracker.playerTNT.put(event.getBlock(), event.getPlayer().getName());
				}
			}
		} else if(event.getBlock().getType() == Material.REDSTONE_TORCH_ON) {
			for(BlockFace face : BlockFace.values()) {
				if(event.getBlock().getRelative(face).getType() == Material.TNT) {
					Tracker.playerTorch.put(event.getBlock().getRelative(face), event.getPlayer().getName());
				}
			}
		}
	}
}
