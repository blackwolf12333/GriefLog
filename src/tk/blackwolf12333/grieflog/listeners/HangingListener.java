package tk.blackwolf12333.grieflog.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;

import tk.blackwolf12333.grieflog.data.hanging.HangingBreakData;
import tk.blackwolf12333.grieflog.data.hanging.HangingPlaceData;
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
			String hangingType = event.getEntity().getType().toString();
			HangingBreakData data = new HangingBreakData(x, y, z, world, playerName, gamemode, hangingType);
			new GriefLogger(data);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHangingPlace(HangingPlaceEvent event) {
		if(!event.isCancelled()) {
			int blockX = event.getEntity().getLocation().getBlockX();
			int blockY = event.getEntity().getLocation().getBlockY();
			int blockZ = event.getEntity().getLocation().getBlockZ();
			String world = event.getEntity().getWorld().getName();
			String playerName = event.getPlayer().getName();
			String hangingType = event.getEntity().getType().toString();
			Integer gamemode = event.getPlayer().getGameMode().getValue();
			HangingPlaceData data = new HangingPlaceData(blockX, blockY, blockZ, world, playerName, gamemode, hangingType);
			new GriefLogger(data);
		}
	}
}
