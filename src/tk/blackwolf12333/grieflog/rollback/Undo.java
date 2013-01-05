package tk.blackwolf12333.grieflog.rollback;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class Undo implements Runnable {

	PlayerSession player;
	ArrayList<BaseData> result;
	int lineCount;
	
	public HashSet<Chunk> chunks = new HashSet<Chunk>();
	
	public Undo(PlayerSession player) {
		this.player = player;
		this.result = player.getSearchResult();
		this.lineCount = 0;
		
		player.print(ChatColor.YELLOW + "[GriefLog] Now going to rollback " + result.size() + " events!");
		player.rollbackTaskID = Bukkit.getScheduler().scheduleSyncDelayedTask(GriefLog.getGriefLog(), this, 0);
	}
	
	@Override
	public void run() {
		for(int i = 0; i < result.size(); i++) {
			undo(result.get(i));
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(GriefLog.getGriefLog(), new SendChangesTask(chunks, player), 0L);
		finishUndo();
	}
	
	private void undo(BaseData baseData) {
		Events event = Events.getEvent(baseData.getEvent());
		if(event != null) {
			if(event.getCanRollback()) {
				baseData.undo(this);
			}
		}
	}
	
	private void finishUndo() {
		Bukkit.getScheduler().cancelTask(player.rollbackTaskID);
		player.setDoingRollback(false);
		player.print(ChatColor.YELLOW + "[GriefLog] Finished undo.");
	}
}
