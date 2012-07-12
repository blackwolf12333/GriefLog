package tk.blackwolf12333.grieflog.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.GriefLog;

public abstract class ReportSearcher extends Searcher {

	public ReportSearcher(GLPlayer player, GriefLog plugin) {
		super(plugin, player);
		files.add(GriefLog.reportFile);
	}
	
	@Override
	public abstract ArrayList<String> searchText(String arg0);

	@Override
	public abstract ArrayList<String> searchText(String arg0, String arg1);

	@Override
	public abstract ArrayList<String> searchText(String arg0, String arg1, String arg2);

	@Override
	public abstract ArrayList<String> searchPos(int x, int y, int z);
	
	@Override
	public void readReportFile(File file, CommandSender sender) {
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String line = "";

			sender.sendMessage(ChatColor.RED + "+++++++++ReportStart+++++++++");
			while ((line = br.readLine()) != null) {
				sender.sendMessage(line);
			}
			sender.sendMessage(ChatColor.RED + "++++++++++ReportEnd+++++++++");

			br.close();
			fileReader.close();

		} catch (Exception e) {
			sender.sendMessage(ChatColor.DARK_RED + "No Reports have been found!");
		}
	}
}
