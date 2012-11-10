package tk.blackwolf12333.grieflog.utils.searching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class PageManager {

	public static HashMap<Integer, ResultPage> pages;
	
	/**
	 * Prints a specific page for the specified player with the colors applied or minimal options.
	 * 
	 * @param player : Who?
	 * @param page : Which page?
	 */
	public static void printPage(PlayerSession player, int page) {
		player.setPages(makePages(player.getSearchResult()));
		String options = ConfigHandler.values.getWhatToShow();
		int maxPages = player.getPages().size() == 0 ? 1 : player.getPages().size();
		
		player.print(ChatColor.DARK_GREEN + "+++++++SearchResults " + (page + 1) + "/" + maxPages + "++++++++");
		
		if(player.getPages().get(page) != null) {
			if(options.contains("default")) {
				player.print(applyColors(player.getPages().get(page).getPage()));
			} else if(options.contains("minimal")) {
				player.printMinimal(player.getPages().get(page).getPage());
			} else {
				player.print(applyColors(player.getPages().get(page).getPage()));
			}
		} else {
			player.print(ChatColor.DARK_AQUA + "No page found here!");
		}
		
		player.print(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
	}
	
	/**
	 * Prints a specific page for the specified player in default.
	 * 
	 * @param player : Who?
	 * @param page : Which page?
	 */
	public static void printPageNormal(PlayerSession player, int page) {
		player.setPages(makePages(player.getSearchResult()));
		int maxPages = player.getPages().size() == 0 ? 1 : player.getPages().size();
		
		player.print(ChatColor.DARK_GREEN + "+++++++SearchResults " + (page + 1) + "/" + maxPages + "++++");
		if(player.getPages().get(page) != null) {
			player.print(applyColors(player.getPages().get(page).getPage()));
		} else {
			player.print(ChatColor.DARK_AQUA + "No page found here!");
		}
		player.print(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
	}
	
	/**
	 * Applies the colors from the config to the page
	 * 
	 * @param page : The page to apply the colors on.
	 * @return The page with the colors applied
	 */
	private static BaseData[] applyColors(BaseData[] page) {
		for(int i = 0; i < page.length; i++) {
			if(page[i] != null) {
				page[i] = page[i].applyColors(getColors());
			}
		}
		
		return page;
	}
	
	/**
	 * Get's the colors from the config and put's them in a HashMap.
	 * @return Returns the HashMap with the colors.
	 */
	private static HashMap<String, ChatColor> getColors() {
		HashMap<String, ChatColor> colors = new HashMap<String, ChatColor>();
		
		colors.put("time", ChatColor.valueOf(ConfigHandler.values.getTimecolor().toUpperCase()));
		colors.put("event", ChatColor.valueOf(ConfigHandler.values.getEventcolor().toUpperCase()));
		colors.put("player", ChatColor.valueOf(ConfigHandler.values.getPlayercolor().toUpperCase()));
		colors.put("blockinfo", ChatColor.valueOf(ConfigHandler.values.getBlockinfocolor().toUpperCase()));
		colors.put("location", ChatColor.valueOf(ConfigHandler.values.getLocationcolor().toUpperCase()));
		colors.put("world", ChatColor.valueOf(ConfigHandler.values.getWorldcolor().toUpperCase()));
		
		return colors;
	}
	
	/**
	 * Generates the ResultPages from the search result.
	 * @param result The result from the search.
	 * @return Returns a HashMap with the page numbers and the corresponding ResultPage
	 */
	private static HashMap<Integer, ResultPage> makePages(ArrayList<BaseData> result) {
		pages = new HashMap<Integer, ResultPage>();
		int maxLinesPerPage = 9;
		int maxPages = (int) Math.ceil((double)result.size() / 9);
		for(int page = 0; page < maxPages; page++) {
			List<BaseData> subList = new ArrayList<BaseData>();
			for(int i = page * maxLinesPerPage; i < (page * maxLinesPerPage) + maxLinesPerPage; i++) {
				if(i == result.size()) {
					break;
				} else {
					subList.add(result.get(i));
				}
			}
			pages.put(page, new ResultPage(subList, page));
		}
		
		return pages;
	}
}
