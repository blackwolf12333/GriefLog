package tk.blackwolf12333.grieflog.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import tk.blackwolf12333.grieflog.GriefLog;

public class GLBucketListener implements Listener {

	GriefLog gl;

	public GLBucketListener(GriefLog plugin) {
		gl = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		if (gl.getConfig().getBoolean("BucketWaterEmpty")) {
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
				GriefLog.logger.Log(data);
			}
		}
		if (gl.getConfig().getBoolean("BucketLavaEmpty")) {
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

				GriefLog.logger.Log(data);
			}
		}
	}
}
