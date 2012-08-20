package tk.blackwolf12333.grieflog.callback;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class BlockProtectionCallback extends BaseCallback {

	GLPlayer player;
	BlockBreakEvent event;
	PlayerInteractEvent iEvent;
	
	public BlockProtectionCallback(GLPlayer player, BlockBreakEvent bEvent, PlayerInteractEvent iEvent) {
		this.player = player;
		this.event = bEvent;
		this.iEvent = iEvent;
	}
	
	@Override
	public void start() {
		player.setSearching(false);
		player.setSearchResult(result);
		
		if(event != null) {
			if(player.result != null) {
				String[] split1 = new String[player.result.size()];
				for(int i = 0; i < split1.length; i++) {
					split1[i] = player.result.get(i);
				}
				
				String[] split2 = null;
				if(split1.length == 0) {
					
				} else if(split1.length == 1) {
					split2 = split1[0].split(" ");
				} else if(split1.length - 1 == -1) {
					split2 = split1[0].split(" ");
				} else {
					split2 = split1[split1.length - 1].split(" ");
				}
				if(!(split2 == null)) {
					String owner = split2[4];
					boolean isOnFriendsList = ConfigHandler.isOnFriendsList(owner, event.getPlayer());
					if(!isOnFriendsList) {
						cancelEvent();
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.DARK_GRAY + "Sorry this block is protected by " + owner + ".");
					}
				}
			} else {
				return;
			}
		} else if(iEvent != null) {
			if(player.result != null) {
				String[] split1 = new String[player.result.size()];
				for(int i = 0; i < split1.length; i++) {
					split1[i] = player.result.get(i);
				}
				String[] split2 = split1[split1.length - 1].split(" ");
				String owner = split2[4];
				if(!ConfigHandler.isOnFriendsList(owner, iEvent.getPlayer())) {
					iEvent.setCancelled(true);
					iEvent.getPlayer().sendMessage(ChatColor.DARK_GRAY + "Sorry this block is protected by " + owner + ".");
				}
			} else {
				return;
			}
		}
	}
	
	private void cancelEvent() {
		Inventory inv = event.getPlayer().getInventory();
		removeItemFromInventory(inv, event.getBlock().getType(), 1);
		resetBlock(event.getBlock().getLocation(), event.getBlock().getType(), event.getBlock().getData());
	}
	
	private void removeItemFromInventory(Inventory inv, Material type, int amount) {
		for(ItemStack is : inv.getContents()) {
			if(is != null && is.getType() == type) {
				int newamount = is.getAmount() - amount;
				if(newamount > 0) {
					is.setAmount(newamount);
					break;
				} else {
					inv.remove(is);
					amount = -newamount;
					if(amount == 0) break;
				}
			}
		}
	}
	
	private void resetBlock(Location loc, Material type, byte data) {
		loc.getBlock().setTypeIdAndData(type.getId(), data, true);
	}
}
