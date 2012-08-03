package tk.blackwolf12333.grieflog.utils;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.GLPlayer;

public class PageManager extends Pages {

	public static void printPage(GLPlayer player, int page) {
		player.setPages(makePages(player.getSearchResult()));
		
		player.print(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
		player.print(getPlayerPage(player, page));
		player.print(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
	}
	
	public static String[] getPlayerPage(GLPlayer player, int page) {
		String[][] pages = player.getPages();
		if(page >= pages.length) {
			String[] ret = {"No Page Found Here"};
			return ret;
		} else {
			String[] ret = pages[page];
			return ret;
		}
	}
}
