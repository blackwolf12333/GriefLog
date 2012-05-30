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
import tk.blackwolf12333.grieflog.utils.FileUtils;
import tk.blackwolf12333.grieflog.utils.Time;

public class GLEntityListener implements Listener {

	GriefLog plugin;
	Time t = new Time();
	FileUtils fu = new FileUtils();
	GriefLogger logger;
	
	public GLEntityListener(GriefLog instance) {
		plugin = instance;
		logger = new GriefLogger(plugin);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityExplode(EntityExplodeEvent event)
	{
		if(plugin.getConfig().getBoolean("CreeperExplode"))
		{
			List<Block> blocks = event.blockList();
			for(int i = 0; i < blocks.size(); i++)
			{
				blocks.get(i);
				Block b = blocks.get(i);
				int x = b.getX();
				int y = b.getY();
				int z = b.getZ();
				String entityName = event.getEntityType().getName();
				String world = event.getLocation().getWorld().getName();
				
				String data = t.now() + " [ENTITY_EXPLODE] EntityType: " + entityName + " Block: " + b.getType().toString() + " Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");
				
				logger.Log(data);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityBreakDoor(EntityBreakDoorEvent event)
	{
		if(plugin.getConfig().getBoolean("ZombieBreakDoor"))
		{
			Block b = event.getBlock();
			int x = b.getX();
			int y = b.getY();
			int z = b.getZ();
			String world = event.getBlock().getWorld().getName();
			
			String data = t.now() + " [ENTITY_BREAK_DOOR] Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");
			
			logger.Log(data);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityChangeBlock(EntityChangeBlockEvent event)
	{
		if(plugin.getConfig().getBoolean("EnderManPlaceAndPickup"))
		{
			if(event.getEntity() instanceof Enderman)
			{
				String data = "";
				Block b = event.getBlock();
				int x = b.getX();
				int y = b.getY();
				int z = b.getZ();
				String world = b.getWorld().getName();
				
				if(event.getTo() == Material.AIR)
				{
					data = t.now() + " [ENDERMAN_PICKUP] Where: " + x + ", " + y + ", " + z + " Ín: " + world + System.getProperty("line.separator");
				}
				else
				{
					data = t.now() + " [ENDERMAN_PLACE] Where: " + x + ", " + y + ", " + z + " Ín: " + world + System.getProperty("line.separator");
				}
							
				logger.Log(data);
			}
			else
			{
				return;
			}
		}
	}	
}
