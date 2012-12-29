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

public class Rollback implements Runnable {

	PlayerSession player;
	ArrayList<BaseData> result;
	long current;
	
	public HashSet<Chunk> chunks = new HashSet<Chunk>();
	
	public Rollback(PlayerSession player) {
		this.player = player;
		this.result = player.getSearchResult();
		player.setCurrentRollback(this);
		
		player.print(ChatColor.YELLOW + "[GriefLog] Now going to rollback " + result.size() + " events!");
		
		player.rollbackTaskID = Bukkit.getScheduler().scheduleSyncDelayedTask(player.getGriefLog(), this, 0);
		current = System.currentTimeMillis();
		GriefLog.debug("Starting rollback @: " + current);
	}
	
	public PlayerSession getSession() {
		return player;
	}
	
	@Override
	public synchronized void run() {
		GriefLog.debug("Going to rollback: " + result.size());
		for(int i = 0; i < result.size(); i++) {
			rollback(result.get(i));
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(player.getGriefLog(), new SendChangesTask(chunks, player), 0L);
		finishRollback();
	}
	
	/**
	 * Start's the rollback of {@code line}.
	 * @param line The child of BaseData to rollback.
	 */
	public void rollback(BaseData line) {
		Events event = Events.getEvent(line.getEvent());
		if(event != null) {
			if(event.getCanRollback()) { // TODO: don't rollback excluded types
				line.rollback(this);
			}
		}
	}
	
	private void finishRollback() {
		GriefLog.debug("Took: " + (System.currentTimeMillis() - current));
		player.setDoingRollback(false);
		player.setCurrentRollback(null);
		player.print(ChatColor.YELLOW + "[GriefLog] This rollback took: " + getNeededTime());
		player.print(ChatColor.YELLOW + "[GriefLog] Finished rollback.");
	}
	
	private String getNeededTime() {
		long totalmillis = (System.currentTimeMillis() - current);
		long totalseconds = totalmillis / 1000;
		long minutes = totalseconds / 60;
		long seconds = totalseconds % 60;
		long millis = totalmillis % 1000;
		return minutes + " min " + seconds + " sec " + millis + "ms.";
	}
}
