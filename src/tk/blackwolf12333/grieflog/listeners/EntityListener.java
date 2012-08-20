package tk.blackwolf12333.grieflog.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogger;
import tk.blackwolf12333.grieflog.data.EntityBreakDoorData;
import tk.blackwolf12333.grieflog.data.EntityEndermanData;
import tk.blackwolf12333.grieflog.data.EntityExplodeData;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class EntityListener implements Listener {

	GriefLog plugin;
	
	HashMap<UUID, String> playerCI = new HashMap<UUID, String>();

	public EntityListener(GriefLog instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityExplode(EntityExplodeEvent event) {
		try {
			// check if the event is not canceled
			if(!event.isCancelled()) {
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
				
				if (ConfigHandler.values.getExplosions()) {
					List<Block> blocks = event.blockList();
					
					// get the player who ingited this tnt
					String player = BlockListener.playerTorch.get(event.getLocation().getBlock());
					if(player == null) {
						player = BlockListener.playerTNT.get(event.getLocation().getBlock());
						if(player == null) {
							player = PlayerListener.playerFAS.get(event.getLocation().getBlock());
							if(player == null) {
								player = playerCI.get(event.getEntity().getUniqueId());
							}
						}
					}
					
					for (int i = 0; i < blocks.size(); i++) {
						int x = blocks.get(i).getX();
						int y = blocks.get(i).getY();
						int z = blocks.get(i).getZ();

						EntityExplodeData data = new EntityExplodeData(x, y, z, event.getEntity().getWorld().getName(), event.blockList().get(i).getType().toString(), event.blockList().get(i).getData(), event.getEntityType().toString(), player);

						GriefLogger logger = new GriefLogger(data.toString());
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
					}
				}
			}
		} catch(NullPointerException e) {
			// just let this happen, i have no other way to prevent this
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityBreakDoor(EntityBreakDoorEvent event) {
		if (ConfigHandler.values.getZombie()) {
			int x = event.getBlock().getX();
			int y = event.getBlock().getY();
			int z = event.getBlock().getZ();
			
			EntityBreakDoorData data = new EntityBreakDoorData(x, y, z, event.getBlock().getWorld().getName(), event.getEntityType().toString());

			GriefLogger logger = new GriefLogger(data.toString());
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
					int x = event.getBlock().getX();
					int y = event.getBlock().getY();
					int z = event.getBlock().getZ();
					
					boolean pickup = (event.getTo() == Material.AIR);
					EntityEndermanData data = new EntityEndermanData(x, y, z, event.getBlock().getWorld().getName(), event.getBlock().getType().toString(), event.getBlock().getData(), pickup);					
					
					GriefLogger logger = new GriefLogger(data.toString());
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, logger);
				} else {
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteractWithCreeper(EntityDamageByEntityEvent event) {
		if(event.getDamager().getType() == EntityType.PLAYER) {
			if(event.getEntity().getType() == EntityType.CREEPER) {
				UUID entity = event.getEntity().getUniqueId();
				Player player = (Player) event.getDamager();
				String pName = player.getName();
				playerCI.put(entity, pName);
			}
		}
	}
}
