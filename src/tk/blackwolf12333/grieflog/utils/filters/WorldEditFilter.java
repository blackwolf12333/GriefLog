package tk.blackwolf12333.grieflog.utils.filters;

import java.util.ArrayList;

import org.bukkit.World;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.BaseCallback;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class WorldEditFilter implements Runnable {

	public ArrayList<BaseData> filterResult = new ArrayList<BaseData>();
	ArrayList<BaseData> searchResult;
	World world;
	PlayerSession player;
	BaseCallback action;
	
	public WorldEditFilter(PlayerSession player, BaseCallback action) {
		this.searchResult = player.getSearchResult();
		this.player = player;
		this.action = action;
		
		new Thread(this).start();
	}
	
	public void run() {
		for(int i = 0; i < searchResult.size(); i++) {
			BaseData data = searchResult.get(i);
			
			if(Events.getEvent(data.getEvent()).getCanRollback()) {
				if(data.isInWorldEditSelectionOf(player)) {
					filterResult.add(data);
				}
			}
		}
		
		player.setSearchResult(filterResult);
		action.start();
	}
}
