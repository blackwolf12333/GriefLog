package tk.blackwolf12333.grieflog.utils.searching.tasks;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.utils.filters.BlockFilter;
import tk.blackwolf12333.grieflog.utils.filters.EventFilter;
import tk.blackwolf12333.grieflog.utils.filters.Filter;
import tk.blackwolf12333.grieflog.utils.filters.PlayerFilter;
import tk.blackwolf12333.grieflog.utils.filters.WorldEditFilter;
import tk.blackwolf12333.grieflog.utils.filters.WorldFilter;
import tk.blackwolf12333.grieflog.utils.searching.ArgumentParser;

public class SearchTask extends GriefLogTask implements Runnable {

	public SearchTask(PlayerSession p, SearchCallback action, ArgumentParser parser) {
		this.p = p;
		this.action = action;
		this.world = parser.world;
		this.filters = getFilters(p, parser);
		
		addFilesToList();
		new Thread(this).start();
		p.print(ChatColor.YELLOW + "[GriefLog] Searching for matching results...");
	}
	
	public SearchTask(PlayerSession p, SearchCallback action, Filter... filters) {
		this.p = p;
		this.action = action;
		this.filters = Arrays.asList(filters);
		
		addFilesToList();
		new Thread(this).start();
		p.print(ChatColor.YELLOW + "[GriefLog] Searching for matching results...");
	}
	
	private ArrayList<Filter> getFilters(PlayerSession player, ArgumentParser parser) {
		ArrayList<Filter> filters = new ArrayList<Filter>();
		if(parser.worldedit == true) {
			filters.add(new WorldEditFilter(player));
		}
		if(parser.player != null) {
			filters.add(new PlayerFilter(parser.player));
		}
		if(parser.event != null) {
			filters.add(new EventFilter(parser.event));
		}
		if(parser.world != null) {
			filters.add(new WorldFilter(parser.world));
		}
		if(parser.blockFilter != null) {
			filters.add(new BlockFilter(player, parser.blockFilter));
		}
		return filters;
	}

	@Override
	protected void addToFoundDataIfComesThroughFilters(BaseData line) {
		if(doesComeThroughFilter(line)) {
			foundData.add(line);
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
