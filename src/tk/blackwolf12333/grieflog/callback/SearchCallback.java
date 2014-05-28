package tk.blackwolf12333.grieflog.callback;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.rollback.Undo;
import tk.blackwolf12333.grieflog.utils.searching.PageManager;
import tk.blackwolf12333.grieflog.utils.searching.ChestSearchResult;

public class SearchCallback {

	PlayerSession player;
	Type type;
	
	public SearchCallback(PlayerSession player, Type type) {
		this.player = player;
		this.type = type;
	}
	
	public SearchCallback(Type type) {
		this.type = type;
	}
	
	public void start(PlayerSession player) {
		player.setSearching(false);
		
		switch(this.type) {
		case SEARCH:
			PageManager.printPage(player, 0);
			break;
		case TPTO:
			teleport(player);
			break;
		case ROLLBACK:
			player.setDoingRollback(true);
			new Rollback(player);
			break;
		case UNDO:
			player.setDoingRollback(true);
			new Undo(player);
			break;
		case CHEST_SEARCH:
			new ChestSearchResult(player);
			break;
		}
	}
	
	private void teleport(PlayerSession player) {
		if(player.getSearchResult().size() == 0) {
			player.print(ChatColor.DARK_RED + "Nothing was found, sorry.");
			return;
		} else {
			player.getSearchResult().get(player.getSearchResult().size() - 1).tpto(player);
		}
	}
	
	public enum Type {
		SEARCH,
		TPTO,
		ROLLBACK,
		UNDO,
		CHEST_SEARCH;
	}
}
