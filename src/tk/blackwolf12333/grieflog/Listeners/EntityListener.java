package tk.blackwolf12333.grieflog.listeners;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogger;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class EntityListener implements Listener {

	GriefLog plugin;

	public EntityListener(GriefLog instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityExplode(EntityExplodeEvent event) {
		// check for anti grief options
		if(event.getEntityType() == EntityType.CREEPER) {
			if(ConfigHandler.values.getAnticreeper()) {
				event.setCancelled(true);
				return;
			}
		} else if(event.getEntityType() == EntityType.PRIMED_TNT) {
			if(ConfigHandler.values.getAntitnt()) {
				event.setCancelled(true);
				return;
			}
		}
		
		// check if the event is not canceled
		if(!event.isCancelled()) {
			if (ConfigHandler.values.getExplosions()) {
				List<Block> blocks = event.blockList();
				
				// get the player who ingited this tnt
				String player = BlockListener.playerTorch.get(event.getLocation().getBlock());
				if(player == null) {
					player = BlockListener.playerTNT.get(event.getLocation().getBlock());
					if(player == null) {
						player = PlayerListener.playerFAS.get(event.getLocation().getBlock());
					}
				}
				
				// loop through the blocks that have been deleted by the explosion
				for (int i = 0; i < blocks.size(); i++) {
					blocks.get(i);
					Block b = blocks.get(i);
					int x = b.getX();
					int y = b.getY();
					int z = b.getZ();
					Entity entity = event.getEntity();
					String entityName = entity.getType().getName();
					String world = event.getLocation().getWorld().getName();

					String data = " [ENTITY_EXPLODE] By: " + player + " EntityType: " + entityName + " Block: " + b.getType().toString() + " Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");

					GriefLogger logger = new GriefLogger(data);
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityBreakDoor(EntityBreakDoorEvent event) {
		if (ConfigHandler.values.getZombie()) {
			Block b = event.getBlock();
			int x = b.getX();
			int y = b.getY();
			int z = b.getZ();
			String world = event.getBlock().getWorld().getName();

			String data = " [ENTITY_BREAK_DOOR] Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");

			GriefLogger logger = new GriefLogger(data);
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		if(ConfigHandler.values.getAntiEnderMan()) {
			if(event.getEntity() instanceof Enderman) {
				event.setCancelled(true);
			}
		}
		
		if(!event.isCancelled()) {
			if (ConfigHandler.values.getEnderman()) {
				if (event.getEntity() instanceof Enderman) {
					String data = "";
					Block b = event.getBlock();
					int x = b.getX();
					int y = b.getY();
					int z = b.getZ();
					String world = b.getWorld().getName();
					
					if (event.getTo() == Material.AIR) {
						data = " [ENDERMAN_PICKUP] Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");
					} else {
						data = " [ENDERMAN_PLACE] Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");
					}
					
					GriefLogger logger = new GriefLogger(data);
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
				} else {
					return;
				}
			}
		}
	}
}
