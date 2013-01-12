package tk.blackwolf12333.grieflog.data.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.utils.InventoryStringDeSerializer;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class InventoryTransactionData extends BasePlayerData {

	int chestX;
	int chestY;
	int chestZ;
	String xyz;
	String result;
	
	public InventoryTransactionData(String player, Integer chestX, Integer chestY, Integer chestZ, String chestWorld, String result) {
		this.playerName = player;
		this.chestX = chestX;
		this.chestY = chestY;
		this.chestZ = chestZ;
		this.xyz = chestX + ", " + chestY + ", " + chestZ;
		this.worldName = chestWorld;
		this.result = result;
		this.event = Events.CHESTACCESS.getEventName();
	}
	
	public InventoryTransactionData(String time, String player, Integer chestX, Integer chestY, Integer chestZ, String chestWorld, String result) {
		this.time = time;
		this.playerName = player;
		this.chestX = chestX;
		this.chestY = chestY;
		this.chestZ = chestZ;
		this.xyz = chestX + ", " + chestY + ", " + chestZ;
		this.worldName = chestWorld;
		this.result = result;
		this.event = Events.CHESTACCESS.getEventName();
	}

	@Override
	public void tpto(PlayerSession who) {
		who.teleport(new Location(Bukkit.getWorld(worldName), chestX, chestY, chestZ));
	}

	@Override
	public String getMinimal() {
		return time + " " + playerName + " accessed chest and " + result;
	}
	
	@Override
	public void rollback(Rollback rollback) {
		Location loc = new Location(Bukkit.getWorld(worldName), chestX, chestY, chestZ);
		if(Bukkit.getWorld(worldName).getBlockAt(loc).getType() == Material.CHEST) {
			Chest chest = (Chest) Bukkit.getWorld(worldName).getBlockAt(loc).getState();
			chest.getInventory().addItem(getItems(result.split(" ")[1]));
			chest.getInventory().removeItem(getItems(result.split(" ")[0]));
		}
	}

	private ItemStack[] getItems(String putOrTaken) {
		String[] itemsStr = putOrTaken.split("|");
		ItemStack[] items = new ItemStack[itemsStr.length - 1];
		
		for(int i = 0; i < itemsStr.length; i++) {
			ItemStack item = InventoryStringDeSerializer.stringToItem(itemsStr[i]);
			items[i] = item;
		}
		
		return items;
	}

	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " By: " + playerName + " " + result + " where: " + xyz + " in: " + worldName + System.getProperty("line.separator");
		}
		return " " + event + " By: " + playerName + " " + result + " where: " + xyz + " in: " + worldName + System.getProperty("line.separator");
	}
}
