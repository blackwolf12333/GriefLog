package tk.blackwolf12333.grieflog.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.data.block.BucketData;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;
import tk.blackwolf12333.grieflog.utils.logging.GriefLogger;

public class BucketListener implements Listener {

	GriefLog plugin;

	public BucketListener(GriefLog plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		if(ConfigHandler.values.getAntilava()) {
			if((event.getBucket().equals(Material.LAVA_BUCKET)) && (!event.getPlayer().isOp())) {
				event.setCancelled(true);
				return;
			}
		}
		if(!event.isCancelled()) {
			if (ConfigHandler.values.getBucketWater()) {
				Material bucket = event.getBucket();
				if (bucket == Material.WATER_BUCKET) {
					BucketData data = new BucketData(event.getBlockClicked().getRelative(event.getBlockFace()), event.getPlayer().getName(), event.getPlayer().getGameMode().getValue(), event.getBucket());
					new GriefLogger(data);
				}
			}
			if (ConfigHandler.values.getBucketLava()) {
				Material bucket = event.getBucket();

				if (bucket == Material.LAVA_BUCKET) {
					BucketData data = new BucketData(event.getBlockClicked().getRelative(event.getBlockFace()), event.getPlayer().getName(), event.getPlayer().getGameMode().getValue(), event.getBucket());
					new GriefLogger(data);
				}
			}
		}
	}
}
