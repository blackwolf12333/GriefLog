package tk.blackwolf12333.grieflog.rollback;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.data.BaseData;

public class Undo implements Runnable {

	PlayerSession player;
	ArrayList<BaseData> result;
	int lineCount;
	
	public Undo(PlayerSession player) {
		this.player = player;
		this.result = player.getSearchResult();
		this.lineCount = 0;
		
		player.print(ChatColor.YELLOW + "[GriefLog] Now going to undo " + result.size() + " events!");
		player.print(ChatColor.YELLOW + "[GriefLog] Predicted time this will take: " + getNeededTime());
		
		player.rollbackTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(player.getGriefLog(), this, 1L, 1L);
	}
	
	@Override
	public void run() {
		try {
			undo(result.get(lineCount));
			lineCount++;
		} catch(IndexOutOfBoundsException e) {
			finishUndo();
			return;
		}
	}
	
	private void undo(BaseData baseData) {
		baseData.undo();
	}
	
	private void finishUndo() {
		Bukkit.getScheduler().cancelTask(player.rollbackTaskID);
		player.setDoingRollback(false);
		player.print(ChatColor.YELLOW + "[GriefLog] Finished undo.");
	}

	private String getNeededTime() {
		int totalseconds = result.size() / 20;
		int minutes = totalseconds / 60;
		int seconds = totalseconds % 60;
		return minutes + " min " + seconds + " sec.";
	}
}
