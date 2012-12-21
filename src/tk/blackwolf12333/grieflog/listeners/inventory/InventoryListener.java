package tk.blackwolf12333.grieflog.listeners.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
//import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.data.player.InventoryTransactionData;
import tk.blackwolf12333.grieflog.utils.InventoryStringDeSerializer;

public class InventoryListener implements Listener {

	GriefLog plugin;
	HashMap<String, ItemStack[]> inventories = new HashMap<String, ItemStack[]>();
	
	public InventoryListener(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		inventories.put(event.getPlayer().getName(), event.getView().getTopInventory().getContents());
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
			String result = calcDiff(event.getView().getTopInventory().getContents(), inventories.get(event.getPlayer().getName()));
			GriefLog.debug("Transaction by: " + player + " with " + result);
			new InventoryTransactionData(player, chestX, chestY, chestZ, chestWorld, result);
		}
		inventories.remove(event.getPlayer().getName());
	}

	/*private String calculateInventoryDifference(ItemStack[] currentView, String name) {
		ArrayList<ItemStack> taken = new ArrayList<ItemStack>();
		ArrayList<ItemStack> put = new ArrayList<ItemStack>();
		ItemStack[] oldView = inventories.get(name);
		for(int i = 0; i < currentView.length; i++) {
			if(currentView[i] != null) {
				if(currentView[i].getAmount() != 0) {
					GriefLog.debug("Item CurrentView: " + currentView[i].getType() + "; Amount: " + currentView[i].getAmount());
				}
			}
			if(oldView[i] != null) {
				GriefLog.debug("Item OldView: " + oldView[i].getType() + "; Amount: " + oldView[i].getAmount());
				if((oldView[i].getTypeId() != currentView[i].getTypeId()) && (oldView[i].getAmount() != currentView[i].getAmount())) {
					GriefLog.debug("new item != old item\n");
					taken.add(oldView[i]);
				} else {
					GriefLog.debug("new item == old item\n");
					if((oldView[i].getType() == Material.AIR) && (currentView[i].getType() != Material.AIR)) {
						GriefLog.debug("new item is put\n");
						put.add(currentView[i]);
					}
				}
			} else {
				if((currentView[i] != null) && (currentView[i].getType() != Material.AIR)) {
					put.add(currentView[i]);
					continue;
				}
				continue;
			}
		}
		
		return getDifferenceResultInString(taken, put);
	}*/
	
	private String calcDiff(ItemStack[] after, ItemStack[] before) {
		ArrayList<ItemStack> taken = new ArrayList<ItemStack>();
		ArrayList<ItemStack> put = new ArrayList<ItemStack>();
		
		for(int i = 0; i < after.length; i++) {
			if((before[i] != null) || (before[i].getType() != Material.AIR) && (before[i].getType() != after[i].getType())) {
				taken.add(before[i]);
			}
			if((before[i] == null) || (before[i].getType() == Material.AIR) && (after[i].getType() != Material.AIR)) {
				put.add(after[i]);
			}
			if((before[i].getType() != after[i].getType())) {
				put.add(after[i]);
			}
			if((before[i] != null) || (before[i].getType() != Material.AIR) && (after[i] == null) || (after[i].getType() == Material.AIR)) {
				taken.add(before[i]);
			}
			if((after[i].getType() != Material.AIR) && (before[i] == null) || (before[i].getType() == Material.AIR)) {
				put.add(after[i]);
			}
		}
		
		return getDifferenceResultInString(taken, put);
	}

	private String getDifferenceResultInString(ArrayList<ItemStack> taken, ArrayList<ItemStack> put) {
		String takenStr = new String("Taken: ");
		String putStr = new String("Put: ");
		
		for(ItemStack i : taken) {
			takenStr += taken.indexOf(i) + "#" + InventoryStringDeSerializer.itemToString(i) + ";";
		}
		for(ItemStack i : put) {
			putStr += put.indexOf(i) + "#" + InventoryStringDeSerializer.itemToString(i) + ";";
		}
		
		return takenStr + " " + putStr;
	}
}
