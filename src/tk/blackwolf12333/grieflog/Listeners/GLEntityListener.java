package tk.blackwolf12333.grieflog.Listeners;

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
import tk.blackwolf12333.grieflog.utils.FileUtils;
import tk.blackwolf12333.grieflog.utils.Time;

public class GLEntityListener implements Listener {

	GriefLog plugin;
	Time t = new Time();
	FileUtils fu = new FileUtils();
	
	public GLEntityListener(GriefLog instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityExplode(EntityExplodeEvent event)
	{
		Block b = event.getEntity().getLocation().getBlock();
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		String entityName = event.getEntityType().getName();
		String world = event.getLocation().getWorld().getName();
		
		String data = t.now() + " [ENTITY_EXPLODE] Type: " + entityName + " Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");
		
		plugin.Log(data);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityBreakDoor(EntityBreakDoorEvent event)
	{
		Block b = event.getBlock();
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		String world = event.getBlock().getWorld().getName();
		
		String data = t.now() + " [ENTITY_BREAK_DOOR] Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");
		
		plugin.Log(data);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityChangeBlock(EntityChangeBlockEvent event)
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
						
			plugin.Log(data);
		}
		else
		{
			return;
		}
	}	
}
