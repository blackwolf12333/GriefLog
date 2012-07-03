package tk.blackwolf12333.grieflog.Listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogger;

public class GLEntityListener implements Listener {

	GriefLog plugin;
	GriefLogger logger;

	public GLEntityListener(GriefLog instance) {
		plugin = instance;
		logger = new GriefLogger(instance);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityExplode(EntityExplodeEvent event) {
		if (plugin.getConfig().getBoolean("Explosions")) {
			List<Block> blocks = event.blockList();
			for (int i = 0; i < blocks.size(); i++) {
				blocks.get(i);
				Block b = blocks.get(i);
				int x = b.getX();
				int y = b.getY();
				int z = b.getZ();
				String entityName = event.getEntityType().toString();
				String world = event.getLocation().getWorld().getName();

				String data = " [ENTITY_EXPLODE] EntityType: " + entityName + " Block: " + b.getType().toString() + " Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");

				logger.Log(data);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityBreakDoor(EntityBreakDoorEvent event) {
		if (plugin.getConfig().getBoolean("ZombieBreakDoor")) {
			Block b = event.getBlock();
			int x = b.getX();
			int y = b.getY();
			int z = b.getZ();
			String world = event.getBlock().getWorld().getName();

			String data = " [ENTITY_BREAK_DOOR] Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");

			logger.Log(data);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		if (plugin.getConfig().getBoolean("EnderManPlaceAndPickup")) {
			if (event.getEntity() instanceof Enderman) {
				String data = "";
				Block b = event.getBlock();
				int x = b.getX();
				int y = b.getY();
				int z = b.getZ();
				String world = b.getWorld().getName();

				if (event.getTo() == Material.AIR) {
					data = " [ENDERMAN_PICKUP] Where: " + x + ", " + y + ", " + z + " Ín: " + world + System.getProperty("line.separator");
				} else {
					data = " [ENDERMAN_PLACE] Where: " + x + ", " + y + ", " + z + " Ín: " + world + System.getProperty("line.separator");
				}

				logger.Log(data);
			} else {
				return;
			}
		}
	}
}
