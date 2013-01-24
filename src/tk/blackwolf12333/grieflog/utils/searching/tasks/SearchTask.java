package tk.blackwolf12333.grieflog.utils.searching.tasks;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.utils.searching.ArgumentParser;

public class SearchTask extends GriefLogTask implements Runnable {

	/**
	 * The search task that will search the files for the specified arugments.
	 * @param p The PlayerSession of the player that requested the search.
	 * @param action The callback that should happen after the search has been completed.
	 * @param args The arguments to search for.
	 */
	public SearchTask(PlayerSession p, SearchCallback action, ArrayList<String> args) {
		this.p = p;
		this.action = action;
		this.args = args;
		
		if(!(this.args.size() > 0)) {
			p.print(ChatColor.YELLOW + "[GriefLog] No valid arguments given, nothing to search for.");
			return;
		}
		
		addFilesToList();
		(this.searchThread = new Thread(this)).start();
		p.print(ChatColor.YELLOW + "[GriefLog] Searching for matching results...");
	}
	
	/**
	 * The search task that will search the files for the specified arugments.
	 * @param p The PlayerSession of the player that requested the search.
	 * @param action The callback that should happen after the search has been completed.
	 * @param args The arguments to search for.
	 * @param world The world where the PlayerSession wants to search in.
	 */
	public SearchTask(PlayerSession p, SearchCallback action, ArrayList<String> args, String world) {
		this.p = p;
		this.action = action;
		this.args = args;
		this.world = world;
		
		if(!(this.args.size() > 0)) {
			p.print(ChatColor.YELLOW + "[GriefLog] No valid arguments given, nothing to search for.");
			return;
		}
		
		addFilesToList();
		(this.searchThread = new Thread(this)).start();
		p.print(ChatColor.YELLOW + "[GriefLog] Searching for matching results...");
	}
	
	/**
	 * The search task that will search the files for the specified arugments.
	 * @param p The PlayerSession of the player that requested the search.
	 * @param action The callback that should happen after the search has been completed.
	 * @param parser The {@link ArgumentParser} that contains the arguments to search for.
	 */
	public SearchTask(PlayerSession p, SearchCallback action, ArgumentParser parser) {
		this.p = p;
		this.action = action;
		this.args = getParserResult(parser);
		this.world = parser.world;
		
		if(!(this.args.size() > 0)) {
			p.print(ChatColor.YELLOW + "[GriefLog] No valid arguments given, nothing to search for.");
			return;
		}
		
		addFilesToList();
		(this.searchThread = new Thread(this)).start();
		p.print(ChatColor.YELLOW + "[GriefLog] Searching for matching results...");
	}
	
	/**
	 * Add's the parsed result of BaseData.loadFromString(line) to {@code foundData} if the line contained the search arguments.
	 * @param line The line to check.
	 */
	protected void addToFoundDataIfContainsArguments(String line) {
		if (lineContainsArguments(line)) {
			GriefLog.debug(line);
			foundData.add(BaseData.loadFromString(line));
		}
	}
}
