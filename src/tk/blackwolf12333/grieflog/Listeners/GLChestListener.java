package tk.blackwolf12333.grieflog.Listeners;

import java.util.HashMap;

import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import tk.blackwolf12333.grieflog.GriefLog;

public class GLChestListener implements Listener {

	GriefLog plugin;
	public static HashMap<String, Inventory> playerWhoAccessedAChest = new HashMap<String, Inventory>();
	
	public GLChestListener(GriefLog instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryOpen(InventoryOpenEvent event) {
		if((event.getInventory().getType() == InventoryType.CHEST) || (event.getInventory().getType() == InventoryType.FURNACE)) {
			Inventory inv = event.getInventory();
			playerWhoAccessedAChest.put(event.getPlayer().getName(), inv);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClose(InventoryCloseEvent event) {
		if(plugin.getConfig().getBoolean("ChestChange")) {
			if(event.getInventory().getType() == InventoryType.CHEST) {
				String itemsChanged = "";
				
				if(playerWhoAccessedAChest.containsKey(event.getPlayer().getName())) {
					Inventory i = playerWhoAccessedAChest.get(event.getPlayer().getName());
					ItemStack[] stack = i.getContents();
					for(ItemStack item : stack) {
						if(!event.getInventory().contains(item)) {
							System.out.print("ChestChange");
							itemsChanged += item.toString() + ",";
						}
					}
				} else {
					GriefLog.log.warning("Player: " + event.getPlayer().getName() + " might be a hacker, he illegaly closed a chest!");
				}
				
				String playerName = event.getPlayer().getName();
				Chest thisChest = (Chest) event.getInventory();
				Integer blockX = thisChest.getBlock().getLocation().getBlockX();
				Integer blockY = thisChest.getBlock().getLocation().getBlockY();
				Integer blockZ = thisChest.getBlock().getLocation().getBlockZ();
				String world = thisChest.getBlock().getWorld().getName();
				String where = blockX + ", " + blockY + ", " + blockZ + "in: " + world;
				
				String data = " [CHESTCONTENT_CHANGE] By: " + playerName + " What: " + itemsChanged + " Where: " + where + System.getProperty("line.separator");
				GriefLog.logger.Log(data);
			}
		}
		
		if(plugin.getConfig().getBoolean("FurnaceChange")) {
			if(event.getInventory().getType() == InventoryType.FURNACE) {
				String itemsChanged = "";
				
				if(playerWhoAccessedAChest.containsKey(event.getPlayer().getName())) {
					Inventory i = playerWhoAccessedAChest.get(event.getPlayer().getName());
					ItemStack[] stack = i.getContents();
					for(ItemStack item : stack) {
						if(!event.getInventory().contains(item)) {
							System.out.print("ChestChange");
							itemsChanged += item.toString() + ",";
						}
					}
				} else {
					GriefLog.log.warning("Player: " + event.getPlayer().getName() + " might be a hacker, he illegaly closed a chest!");
				}
				
				String playerName = event.getPlayer().getName();
				Furnace thisFurnace = (Furnace) event.getInventory();
				Integer blockX = thisFurnace.getBlock().getLocation().getBlockX();
				Integer blockY = thisFurnace.getBlock().getLocation().getBlockY();
				Integer blockZ = thisFurnace.getBlock().getLocation().getBlockZ();
				String world = thisFurnace.getBlock().getWorld().getName();
				String where = blockX + ", " + blockY + ", " + blockZ + "in: " + world;
				
				String data = " [FURNACECONTENT_CHANGE] By: " + playerName + " What: " + itemsChanged + " Where: " + where + System.getProperty("line.separator");
				GriefLog.logger.Log(data);
			}
		}
	}
}
