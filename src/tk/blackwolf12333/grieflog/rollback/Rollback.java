package tk.blackwolf12333.grieflog.rollback;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.utils.Events;

public class Rollback implements Runnable {

	GLPlayer player;
	World world;
	ArrayList<String> result;
	int lineCount;
	
	public Rollback(GLPlayer player) {
		this.player = player;
		this.result = player.getSearchResult();
		this.lineCount = 0;
		
		player.print(ChatColor.YELLOW + "[GriefLog] Now going to rollback " + result.size() + " events!");
		player.print(ChatColor.YELLOW + "[GriefLog] Predicted time this will take: " + result.size() / 20 + " seconds.");
		
		player.rollbackTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(player.getGriefLog(), this, 1L, 1L);
	}
	
	@Override
	public void run() {
		try {
			rollback(result.get(lineCount));
			lineCount++;
		} catch(IndexOutOfBoundsException e) {
			finishRollback();
			return;
		}
	}
	
	public boolean rollback(String line) {
		if(line == null) {
			return false;
		} else if(line.contains(Events.JOIN.getEvent())) {
			return true;
		} else if(line.contains(Events.QUIT.getEvent())) {
			return true;
		} else if(line.contains(Events.COMMAND.getEvent())) {
			return true;
		} else if(line.contains(Events.BREAK.getEvent())) {
			BreakRollback breakRB = new BreakRollback();
			return breakRB.rollback(line);
		} else if(line.contains(Events.EXPLODE.getEvent())) {
			ExplodeRollback explodeRB = new ExplodeRollback();
			return explodeRB.rollback(line);
		} else if(line.contains(Events.PLACE.getEvent())) {
			PlaceRollback placeRB = new PlaceRollback();
			return placeRB.rollback(line);
		} else if(line.contains(Events.LAVA.getEvent())) {
			LavaRollback lavaRB = new LavaRollback();
			return lavaRB.rollback(line);
		} else if(line.contains(Events.WATER.getEvent())) {
			WaterRollback waterRB = new WaterRollback();
			return waterRB.rollback(line);
		} else {
			return false;
		}
	}
	
	public void finishRollback() {
		Bukkit.getScheduler().cancelTask(player.rollbackTaskID);
		player.setDoingRollback(false);
		player.print(ChatColor.YELLOW + "[GriefLog] Finished rollback.");
	}

}
