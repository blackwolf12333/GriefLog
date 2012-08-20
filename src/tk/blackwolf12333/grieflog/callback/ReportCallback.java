package tk.blackwolf12333.grieflog.callback;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogger;

public class ReportCallback extends BaseCallback {

	GLPlayer player;
	GriefLogger logger = new GriefLogger();
	
	public ReportCallback(GLPlayer player) {
		this.player = player;
	}
	
	@Override
	public void start() {
		player.setSearchResult(result);
		player.setSearching(false);
		report();
	}
	
	private void report() {
		// report the result of the search
		for(int i = 0; i < player.getSearchResult().size(); i++) {
			logger.Log(player.getSearchResult().get(i) + System.getProperty("line.separator"), GriefLog.reportFile);
		}
		logger.Log( "Reported by: " + player.getPlayer().getName() + System.getProperty("line.separator"), GriefLog.reportFile);
		player.print(ChatColor.DARK_BLUE + "Reported this position, have a happy day:)");
	}
}
