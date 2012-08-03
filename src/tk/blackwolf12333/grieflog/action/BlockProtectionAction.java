package tk.blackwolf12333.grieflog.action;

import org.bukkit.ChatColor;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class BlockProtectionAction extends BaseAction {

	GLPlayer player;
	BlockBreakEvent event;
	PlayerInteractEvent iEvent;
	
	public BlockProtectionAction(GLPlayer player, BlockBreakEvent bEvent, PlayerInteractEvent iEvent) {
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
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.DARK_GRAY + "Sorry this block is protected by " + owner + ".");
					}
				}
			} else {
				// if the search result is null ignore it because than nothing happened on that location
			}
		} else if(iEvent != null) {
			// TODO: do the check for interact event
			if(player.result != null) {
				String[] split1 = new String[player.result.size()];
				for(int i = 0; i < split1.length; i++) {
					split1[i] = player.result.get(i);
				}
				String[] split2 = split1[split1.length - 1].split(" ");
				String owner = split2[4];
				if(!ConfigHandler.isOnFriendsList(owner, event.getPlayer())) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(ChatColor.DARK_GRAY + "Sorry this block is protected by " + owner + ".");
				}
			} else {
				// if the search result is null ignore it because than nothing happened on that location
			}
		}
	}
}
