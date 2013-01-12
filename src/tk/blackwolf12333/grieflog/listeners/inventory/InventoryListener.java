package tk.blackwolf12333.grieflog.listeners.inventory;

import static tk.blackwolf12333.grieflog.utils.BlockUtils.compareInventories;
import static tk.blackwolf12333.grieflog.utils.BlockUtils.compressInventory;

import java.util.HashMap;

import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.data.player.InventoryTransactionData;
import tk.blackwolf12333.grieflog.utils.InventoryStringDeSerializer;
import tk.blackwolf12333.grieflog.utils.logging.GriefLogger;

public class InventoryListener implements Listener {

	GriefLog plugin;
	HashMap<String, ItemStack[]> inventories = new HashMap<String, ItemStack[]>();
	
	public InventoryListener(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		inventories.put(event.getPlayer().getName(), compressInventory(event.getInventory().getContents()));
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if(event.getInventory().getType() == InventoryType.CHEST) {
			Chest chest = (Chest) event.getInventory().getHolder();
			Integer chestX = chest.getX();
			Integer chestY = chest.getY();
			Integer chestZ = chest.getZ();
			String chestWorld = chest.getWorld().getName();
			
			String player = event.getPlayer().getName();
			String result = null;
			final ItemStack[] after = compressInventory(event.getView().getTopInventory().getContents());
			final ItemStack[] before = inventories.get(player);
			if(before != null) {
				final ItemStack[] diff = compareInventories(before, after);
				result = getDifferenceResultInString(diff);
				if(result == null) {
					inventories.remove(event.getPlayer().getName());
					return;
				}
			}
			GriefLog.debug("Transaction by: " + player + " with " + result);
			new GriefLogger(new InventoryTransactionData(player, chestX, chestY, chestZ, chestWorld, result));
		}
		inventories.remove(event.getPlayer().getName());
	}

	private String getDifferenceResultInString(ItemStack[] diff) {
		String taken = new String("Taken: ");
		String put = new String("Put: ");
		
		if(diff.length == 0) {
			return null;
		}
		for(ItemStack is : diff) {
			if(is.getAmount() < 0) {
				is.setAmount(-is.getAmount());
				taken += InventoryStringDeSerializer.itemToString(is) + "|";
			} else {
				put += InventoryStringDeSerializer.itemToString(is) + "|";
			}
		}
		
		return taken + " " + put;
	}
}
