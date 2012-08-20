package tk.blackwolf12333.grieflog.rollback;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.data.BaseData;
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
		player.print(ChatColor.YELLOW + "[GriefLog] Predicted time this will take: " + getNeededTime());
		
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
	
	public void rollback(String line) {
		/*if(line == null) {
			return;
		} else if(line.contains(Events.JOIN.getEventName())){
			return;
		} else if(line.contains(Events.QUIT.getEventName())){
			return;
		} else if(line.contains(Events.COMMAND.getEventName())){
			return;
		} else if(line.contains(Events.BREAK.getEventName())) {
			BreakRollback breakRB = new BreakRollback();
			breakRB.rollback(line);
		} else if(line.contains(Events.EXPLODE.getEventName())) {
			ExplodeRollback explodeRB = new ExplodeRollback();
			explodeRB.rollback(line);
		} else if(line.contains(Events.PLACE.getEventName())) {
			PlaceRollback placeRB = new PlaceRollback();
			placeRB.rollback(line);
		} else if(line.contains(Events.LAVA.getEventName())) {
			LavaRollback lavaRB = new LavaRollback();
			lavaRB.rollback(line);
		} else if(line.contains(Events.WATER.getEventName())) {
			WaterRollback waterRB = new WaterRollback();
			waterRB.rollback(line);
		} else {
			player.print(ChatColor.DARK_RED + "[GriefLog] This event is not yet supported by rollback.");
			return;
		}*/
		Events event = Events.getEvent(line.split(" ")[2]);
		if(event != null) {
			if(event.getCanRollback()) {
				BaseData.loadFromString(line).rollback();
			} else {
				player.print(ChatColor.DARK_RED + "[GriefLog] This event is not yet supported by rollback.");
			}
		} else {
			player.print("Unknown event!");
		}
	}
	
	private void finishRollback() {
		Bukkit.getScheduler().cancelTask(player.rollbackTaskID);
		player.setDoingRollback(false);
		player.print(ChatColor.YELLOW + "[GriefLog] Finished rollback.");
		player.rollbackTaskID = null;
	}
	
	private String getNeededTime() {
		int totalseconds = result.size() / 20;
		int minutes = totalseconds / 60;
		int seconds = totalseconds % 60;
		return minutes + " min " + seconds + " sec.";
	}
}
