package tk.blackwolf12333.grieflog.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

import tk.blackwolf12333.grieflog.data.block.BlockBreakData;
import tk.blackwolf12333.grieflog.data.block.BlockPlaceData;
import tk.blackwolf12333.grieflog.utils.logging.GriefLogger;

public class HangingListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHangingBreak(HangingBreakByEntityEvent event) {
		if(!event.isCancelled()) {
			int x = event.getEntity().getLocation().getBlockX();
			int y = event.getEntity().getLocation().getBlockY();
			int z = event.getEntity().getLocation().getBlockZ();
			String world = event.getEntity().getLocation().getWorld().getName();
			String playerName = (event.getRemover() instanceof Player) ? ((Player) event.getRemover()).getName() : "Entity";
			Integer gamemode = (event.getRemover() instanceof Player) ? ((Player) event.getRemover()).getGameMode().getValue() : 0;
			String blockType = "Painting";
			byte blockData = 0x0;
			BlockBreakData data = new BlockBreakData(x, y, z, world, blockType, blockData, playerName, gamemode);
			new GriefLogger(data);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHangingPlace(HangingPlaceEvent event) {
		if(!event.isCancelled()) {
			int blockX = event.getBlock().getX();
			int blockY = event.getBlock().getY();
			int blockZ = event.getBlock().getZ();
			String world = event.getBlock().getWorld().getName();
			String blockType = event.getBlock().getType().toString();
			byte blockData = event.getBlock().getData();
			String playerName = event.getPlayer().getName();
			Integer gamemode = event.getPlayer().getGameMode().getValue();
			BlockPlaceData data = new BlockPlaceData(blockX, blockY, blockZ, blockType, blockData, world, playerName, gamemode);
			new GriefLogger(data);
		}
	}
}
