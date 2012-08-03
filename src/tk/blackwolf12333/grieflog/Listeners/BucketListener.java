package tk.blackwolf12333.grieflog.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogger;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class BucketListener implements Listener {

	GriefLog plugin;

	public BucketListener(GriefLog plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		if(ConfigHandler.values.getAntilava()) {
			if((event.getBucket() == Material.LAVA_BUCKET) && (!event.getPlayer().isOp())) {
				event.setCancelled(true);
				return;
			}
		}
		
		if(!event.isCancelled()) {
			if (ConfigHandler.values.getBucketWater()) {
				Player p = event.getPlayer();
				String name = p.getName();
				String world = event.getBlockClicked().getWorld().getName();
				Block b = event.getBlockClicked().getRelative(event.getBlockFace());
				Integer gm = p.getGameMode().getValue();
				int x = b.getX();
				int y = b.getY();
				int z = b.getZ();

				Material bucket = event.getBucket();
				String data = "";
				if (bucket == Material.WATER_BUCKET) {
					data = " [BUCKET_WATER_EMPTY] Who: " + name + " GM: " + gm + " Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");
					GriefLogger logger = new GriefLogger(data);
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
				}
			}
			if (ConfigHandler.values.getBucketLava()) {
				Player p = event.getPlayer();
				String name = p.getName();
				String world = event.getBlockClicked().getWorld().getName();
				Block b = event.getBlockClicked().getRelative(event.getBlockFace());
				Integer gm = p.getGameMode().getValue();
				int x = b.getX();
				int y = b.getY();
				int z = b.getZ();

				Material bucket = event.getBucket();
				String data = "";

				if (bucket == Material.LAVA_BUCKET) {
					data = " [BUCKET_LAVA_EMPTY] Who: " + name + " GM: " + gm + " Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");

					GriefLogger logger = new GriefLogger(data);
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
				}
			}
		}
	}
}
