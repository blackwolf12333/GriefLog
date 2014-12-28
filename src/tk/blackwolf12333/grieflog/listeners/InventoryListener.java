package tk.blackwolf12333.grieflog.listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.data.player.ChestAccessData;
import tk.blackwolf12333.grieflog.utils.UUIDApi;
import tk.blackwolf12333.grieflog.utils.InventoryStringDeSerializer;
import tk.blackwolf12333.grieflog.utils.logging.GriefLogger;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class InventoryListener implements Listener {

	GriefLog plugin;
	private static final int INDEX_NOT_FOUND = -1;
	HashMap<UUID, String> inventories = new HashMap<UUID, String>();
	
	public InventoryListener(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		try {
	        if(ConfigHandler.values.getInventoryLogging()) {
			    inventories.put(UUIDApi.getUUIDOf(event.getPlayer().getName()), InventoryStringDeSerializer.InventoryToString(event.getView().getTopInventory()));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		try {
			if(ConfigHandler.values.getInventoryLogging()) {
			    if(event.getInventory().getType() == InventoryType.CHEST) {
				    InventoryHolder holder = event.getInventory().getHolder();
				    Integer chestX;
				    Integer chestY;
				    Integer chestZ;
				    String chestWorld;
				    if(holder instanceof DoubleChest) {
					    DoubleChest chest = (DoubleChest) holder;
					    chestX = chest.getLocation().getBlockX();
					    chestY = chest.getLocation().getBlockY();
					    chestZ = chest.getLocation().getBlockZ();
					    chestWorld = chest.getWorld().getName();
				    } else if(holder instanceof BlockState) {
					    BlockState chest = (BlockState) holder;
					    Location loc = chest.getLocation();
					    chestX = loc.getBlockX();
					    chestY = loc.getBlockY();
					    chestZ = loc.getBlockZ();
					    chestWorld = loc.getWorld().getName();
				    } else if(holder instanceof Entity) {
					    Entity chest = (Entity) holder;
					    Location loc = chest.getLocation();
					    chestX = loc.getBlockX();
					    chestY = loc.getBlockY();
					    chestZ = loc.getBlockZ();
					    chestWorld = loc.getWorld().getName();
				    } else {
					    GriefLog.log.info("Something went wrong logging an Inventory event");
					    return;
				    }
				    UUID playerUUID = UUIDApi.getUUIDOf(event.getPlayer().getName());
				    String player = event.getPlayer().getName();
				    String[] diff = new String[2]; // diff[0] is taken, diff[1] is put
				
				    String before = inventories.get(playerUUID);
				    String after = InventoryStringDeSerializer.InventoryToString(event.getView().getTopInventory());
				    diff = difference(before, after);
				    if(diff == null) {
				    	inventories.remove(UUIDApi.getUUIDOf(event.getPlayer().getName()));
				    	return;
				    }
				    GriefLog.debug("Transaction by: " + player + ":" + playerUUID + " with taken: " + diff[0] + " put: " + diff[1]);
					new GriefLogger(new ChestAccessData(player, playerUUID, chestX, chestY, chestZ, chestWorld, diff[0], diff[1]));
			    }
			}
			inventories.remove(UUIDApi.getUUIDOf(event.getPlayer().getName()));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String[] difference(String str1, String str2) {
		String[] ret = new String[2];
		if (str1 == null) {
	        ret[0] = str2;
			return ret;
	    }
	    if (str2 == null) {
	    	ret[0] = str1;
	        return ret;
	    }
	    int at = indexOfDifference(str1, str2);
	    if (at == INDEX_NOT_FOUND) {
	        return null;
	    }
	    ret[0] = str1.substring(at);
	    if(ret[0] == null) {
	    	ret[0] = "";
	    }
	    ret[1] = str2.substring(at);
	    if(ret[1] == null) {
	    	ret[1] = "";
	    }
	    return ret;
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
