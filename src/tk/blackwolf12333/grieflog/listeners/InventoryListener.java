package tk.blackwolf12333.grieflog.listeners;

import java.util.HashMap;

import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.data.player.InventoryTransactionData;
import tk.blackwolf12333.grieflog.utils.InventoryStringDeSerializer;
import tk.blackwolf12333.grieflog.utils.logging.GriefLogger;

public class InventoryListener implements Listener {

	GriefLog plugin;
	private static final int INDEX_NOT_FOUND = -1;
	private static final String EMPTY = null;
	HashMap<String, String> inventories = new HashMap<String, String>();
	
	public InventoryListener(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		inventories.put(event.getPlayer().getName(), InventoryStringDeSerializer.InventoryToString(event.getView().getTopInventory()));
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
			
			String before = inventories.get(player);
			String after = InventoryStringDeSerializer.InventoryToString(event.getView().getTopInventory());
			result = difference(before, after);
			GriefLog.debug("Transaction by: " + player + " with " + result);
			new GriefLogger(new InventoryTransactionData(player, chestX, chestY, chestZ, chestWorld, result));
		}
		inventories.remove(event.getPlayer().getName());
	}

	public String difference(String str1, String str2) {
	    if (str1 == null) {
	        return str2;
	    }
	    if (str2 == null) {
	        return str1;
	    }
	    int at = indexOfDifference(str1, str2);
	    if (at == INDEX_NOT_FOUND) {
	        return EMPTY;
	    }
	    return "taken: " + str1.substring(at) + " put: " + str2.substring(at);
	}

	public int indexOfDifference(CharSequence cs1, CharSequence cs2) {
	    if (cs1 == cs2) {
	        return INDEX_NOT_FOUND;
	    }
	    if (cs1 == null || cs2 == null) {
	        return 0;
	    }
	    int i;
	    for (i = 0; i < cs1.length() && i < cs2.length(); ++i) {
	        if (cs1.charAt(i) != cs2.charAt(i)) {
	            break;
	        }
	    }
	    if (i < cs2.length() || i < cs1.length()) {
	        return i;
	    }
	    return INDEX_NOT_FOUND;
	}
}
