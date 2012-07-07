package tk.blackwolf12333.grieflog.search;

import java.io.File;

import org.bukkit.command.CommandSender;

import tk.blackwolf12333.grieflog.GriefLog;

public class WorldEditSearcher extends Searcher {

	public WorldEditSearcher() {
		files.add(GriefLog.weFile);
		
		File file = new File(GriefLog.dataFolder + File.separator +"WELogs" + File.separator);
		String[] list = file.list();
		if(file.exists()) {
			for (String element : list) {
				files.add(new File(GriefLog.dataFolder + File.separator +"WELogs" + File.separator + element));
			}
		}
	}
	
	public String searchText(String arg0) {
		return searchFile(arg0);
	}

	public String searchText(String arg0, String arg1) {
		return searchFile(arg0, arg1);
	}

	public String searchText(String arg0, String arg1, String arg2) {
		return searchFile(arg0, arg1, arg2);
	}

	public String searchPos(int x, int y, int z) {
		String xyz = x + ", " + y + ", " + z;
		return searchFile(xyz);
	}

	@Override
	/** 
	 * not implemented here
	 */
	public void readReportFile(File file, CommandSender sender) {}
}
