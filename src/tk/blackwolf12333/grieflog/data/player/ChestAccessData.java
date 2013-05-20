package tk.blackwolf12333.grieflog.data.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.inventory.Inventory;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.utils.InventoryStringDeSerializer;
import tk.blackwolf12333.grieflog.utils.SerializedItem;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class ChestAccessData extends BasePlayerData {

	int chestX;
	int chestY;
	int chestZ;
	String xyz;
	String taken;
	String put;
	
	public ChestAccessData(String player, Integer chestX, Integer chestY, Integer chestZ, String chestWorld, String taken, String put) {
		this.playerName = player;
		this.chestX = chestX;
		this.chestY = chestY;
		this.chestZ = chestZ;
		this.xyz = chestX + ", " + chestY + ", " + chestZ;
		this.worldName = chestWorld;
		this.taken = taken;
		this.put = put;
		this.event = Events.CHESTACCESS.getEventName();
	}
	
	public ChestAccessData(String time, String player, Integer chestX, Integer chestY, Integer chestZ, String chestWorld, String taken, String put) {
		this.time = time;
		this.playerName = player;
		this.chestX = chestX;
		this.chestY = chestY;
		this.chestZ = chestZ;
		this.xyz = chestX + ", " + chestY + ", " + chestZ;
		this.worldName = chestWorld;
		this.taken = taken;
		this.put = put;
		this.event = Events.CHESTACCESS.getEventName();
	}

	@Override
	public void tpto(PlayerSession who) {
		who.teleport(new Location(Bukkit.getWorld(worldName), chestX, chestY, chestZ));
	}

	@Override
	public String getMinimal() {
		return time + " " + playerName + " accessed chest and took: " + taken + " and put: " + put;
	}
	
	@Override
	public void rollback(Rollback rollback) {
		Location loc = new Location(Bukkit.getWorld(worldName), chestX, chestY, chestZ);
		if(Bukkit.getWorld(worldName).getBlockAt(loc).getType() == Material.CHEST) {
			Inventory inventory = null;
			if(Bukkit.getWorld(worldName).getBlockAt(loc).getState() instanceof DoubleChest) {
				inventory = ((DoubleChest) Bukkit.getWorld(worldName).getBlockAt(loc).getState()).getInventory();
			} else {
				inventory = ((Chest) Bukkit.getWorld(worldName).getBlockAt(loc).getState()).getInventory();
			}
			
			if(inventory == null) {
				return;
			} else {
				SerializedItem[] items = getItems(taken);
				if(items != null) {
					for(SerializedItem item : items) {
						inventory.setItem(item.getSlot(), item.getItemStack());
					}
				}
				items = getItems(put);
				if(items != null) {
					for(SerializedItem item : items) {
						inventory.setItem(item.getSlot(), item.getItemStack());
					}
				}
			}
		}
	}
	
	private SerializedItem[] getItems(String putOrTaken) {
		if(putOrTaken == null) {
			return null;
		} else if(putOrTaken.equalsIgnoreCase("")) {
			return null;
		}
		String[] itemsStr = putOrTaken.split(";");
		SerializedItem[] items = new SerializedItem[itemsStr.length];
		
		for(int i = 0; i < itemsStr.length; i++) {
			SerializedItem item = InventoryStringDeSerializer.stringToItem(itemsStr[i]);
			if(item == null) {
				continue;
			}
			items[i] = item;
		}
		return items;
	}

	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " By: " + playerName + " taken: " + taken.toString() + " put: " + put.toString() +" where: " + xyz + " in: " + worldName + System.getProperty("line.separator");
		}
		return " " + event + " By: " + playerName + " taken: " + taken.toString() + " put: " + put.toString() + " where: " + xyz + " in: " + worldName + System.getProperty("line.separator");
	}
}
