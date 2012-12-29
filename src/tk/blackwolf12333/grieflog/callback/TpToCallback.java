package tk.blackwolf12333.grieflog.callback;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.PlayerSession;

public class TpToCallback implements BaseCallback {

	PlayerSession player;
	
	public TpToCallback(PlayerSession player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setSearching(false);
		teleport();
	}
	
	private void teleport() {
		if(player.getSearchResult().size() == 0) {
			player.print(ChatColor.DARK_RED + "Nothing was found, sorry.");
			return;
		} else {
			player.getSearchResult().get(player.getSearchResult().size() - 1).tpto(player);
		}
	}
}
