package tk.blackwolf12333.grieflog.utils;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.data.BaseBlockData;
import tk.blackwolf12333.grieflog.data.BlockBreakData;
import tk.blackwolf12333.grieflog.data.BlockIgniteData;
import tk.blackwolf12333.grieflog.data.BlockPlaceData;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

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
			return editToMatchShowOptions(pages[page]);
		}
	}
	
	private static String[] editToMatchShowOptions(String[] page) {
		String options = ConfigHandler.values.getWhatToShow();
		if(options.contains("default")) {
			return page;
		} else if(options.contains("minimal")) {
			return getMinimal(page);
		}
		
		return page;
	}
	
	private static String[] getMinimal(String[] page) {
		String[] returnPage = new String[9];
		
		for(int i = 0; i < page.length; i++) {
			if(page[i] == null) {
				continue;
			} else if(page[i].contains(Events.BREAK.getEventName())) {
				BlockBreakData data = (BlockBreakData) BaseBlockData.loadFromString(page[i]);
				returnPage[i] = data.getTime() + " " + data.getPlayerName() + " broke " + data.getBlockType().toLowerCase() + " (GM: " + data.getGamemode() + ")";
			} else if(page[i].contains(Events.PLACE.getEventName())) {
				BlockPlaceData data = (BlockPlaceData) BaseBlockData.loadFromString(page[i]);
				returnPage[i] = data.getTime() + " " + data.getPlayerName() + " placed " + data.getBlockType().toLowerCase() + " (GM: " + data.getGamemode() + ")";
			} else if(page[i].contains(Events.IGNITE.getEventName())) {
				BlockIgniteData data = (BlockIgniteData) BaseBlockData.loadFromString(page[i]);
				returnPage[i] = data.getTime() + " " + data.getPlayerName() + " ignited " + data.getBlockType().toLowerCase() + " (GM: " + data.getGamemode() + ")";
			}
		}
		
		return returnPage;
	}
	
	/*private String editForBlockPlace(String[] line, String option) {
		if(option.equalsIgnoreCase("gamemode")) {
			return line[0] + " " + line[1] + " " + line[2] + " " + line[3] + " " + line[4] + " " + line[7] + " " + line[8] + " " + line[9] + " " + line[10] + " " + line[11] + " " + line[12];
		} else if(option.equalsIgnoreCase("location")) {
			return line[0] + " " + line[1] + " " + line[2] + " " + line[3] + " " + line[4] + " " + line[5] + line[6] + line[11] + " " + line[12];
		} else if(option.equalsIgnoreCase("worldName")) {
			return line[0] + " " + line[1] + " " + line[2] + " " + line[3] + " " + line[4] + " " + line[7] + " " + line[8] + " " + line[9] + " " + line[10] + " " + line[11] + " " + line[12];
		}
	}*/
}
