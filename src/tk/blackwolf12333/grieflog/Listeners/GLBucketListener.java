package tk.blackwolf12333.grieflog.Listeners;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogger;
import tk.blackwolf12333.grieflog.utils.Time;

public class GLBucketListener implements Listener {

	Logger log = Logger.getLogger("Minecraft");
	GriefLog gl;
	Time t = new Time();
	GriefLogger logger;

	public GLBucketListener(GriefLog plugin) {
		gl = plugin;
		logger = new GriefLogger(plugin);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		if (gl.getConfig().getBoolean("BucketWaterEmpty")) {
			Player p = event.getPlayer();
			String name = p.getName();
			String world = event.getBlockClicked().getWorld().getName();
			Block b = event.getBlockClicked().getRelative(event.getBlockFace());
			int x = b.getX();
			int y = b.getY();
			int z = b.getZ();

			Material bucket = event.getBucket();
			String data = "";
			if (bucket == Material.WATER_BUCKET) {
				data = t.now() + " [BUCKET_WATER_EMPTY] Who: " + name + " Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");
				logger.Log(data);
			}
		}
		if (gl.getConfig().getBoolean("BucketLavaEmpty")) {
			Player p = event.getPlayer();
			String name = p.getName();
			String world = event.getBlockClicked().getWorld().getName();
			Block b = event.getBlockClicked().getRelative(event.getBlockFace());
			int x = b.getX();
			int y = b.getY();
			int z = b.getZ();

			Material bucket = event.getBucket();
			String data = "";

			if (bucket == Material.LAVA_BUCKET) {
				data = t.now() + " [BUCKET_LAVA_EMPTY] Who: " + name + " Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");

				logger.Log(data);
			}
		}
	}
}
