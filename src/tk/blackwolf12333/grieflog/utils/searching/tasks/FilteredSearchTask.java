package tk.blackwolf12333.grieflog.utils.searching.tasks;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.utils.filters.Filter;
import tk.blackwolf12333.grieflog.utils.searching.ArgumentParser;

public class FilteredSearchTask extends GriefLogTask implements Runnable {

	Filter[] filters;
	
	/**
	 * The search task that will search the files for the specified arugments.
	 * @param p The PlayerSession of the player that requested the search.
	 * @param action The callback that should happen after the search has been completed.
	 * @param parser The {@link ArgumentParser} that contains the arguments to search for.
	 * @param filter The filter to apply after the search is done.
	 */
	public FilteredSearchTask(PlayerSession p, SearchCallback action, ArgumentParser parser, Filter... filter) {
		this.p = p;
		this.action = action;
		this.args = getParserResult(parser);
		this.world = parser.world;
		this.filters = filter;
		
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
	public FilteredSearchTask(PlayerSession p, SearchCallback action, ArrayList<String> args, String world, Filter... filter) {
		this.p = p;
		this.action = action;
		this.args = args;
		this.world = world;
		this.filters = filter;
		
		if(!(this.args.size() > 0)) {
			p.print(ChatColor.YELLOW + "[GriefLog] No valid arguments given, nothing to search for.");
			return;
		}
		
		addFilesToList();
		(this.searchThread = new Thread(this)).start();
		p.print(ChatColor.YELLOW + "[GriefLog] Searching for matching results...");
	}
	
	@Override
	protected void addToFoundDataIfContainsArguments(String line) {
		if (lineContainsArguments(line)) {
			BaseData data;
			if(doesComeThroughFilter((data = BaseData.loadFromString(line)))) {
				foundData.add(data);
			}
		}
	}
	
	private boolean doesComeThroughFilter(BaseData data) {
		if(this.filters != null) {
			for(Filter filter : filters) {
				if(filter.doFilter(data)) {
					continue;
				} else {
					return false;
				}
			}
		}
		return data != null;
	}
}
