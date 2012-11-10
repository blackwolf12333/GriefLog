package tk.blackwolf12333.grieflog.utils.searching;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.BaseCallback;
import tk.blackwolf12333.grieflog.data.BaseData;

public class SearchTask implements Runnable {

	public String world;
	ArrayList<BaseData> foundData = new ArrayList<BaseData>();
	ArrayList<File> filesToSearch = new ArrayList<File>();
	int filecount = 0;
	
	ArrayList<String> args = new ArrayList<String>();
	PlayerSession p;
	BaseCallback action;
	
	/**
	 * The search task that will search the files for the specified arugments.
	 * @param p The PlayerSession of the player that requested the search.
	 * @param action The callback that should happen after the search has been completed.
	 * @param args The arguments to search for.
	 */
	public SearchTask(PlayerSession p, BaseCallback action, ArrayList<String> args) {
		this.p = p;
		this.action = action;
		this.args = args;
		
		addFilesToList();
		new Thread(this).start();
		p.print(ChatColor.YELLOW + "[GriefLog] Searching for matching results...");
	}
	
	/**
	 * The search task that will search the files for the specified arugments.
	 * @param p The PlayerSession of the player that requested the search.
	 * @param action The callback that should happen after the search has been completed.
	 * @param args The arguments to search for.
	 * @param world The world where the PlayerSession wants to search in.
	 */
	public SearchTask(PlayerSession p, BaseCallback action, ArrayList<String> args, String world) {
		this.p = p;
		this.action = action;
		this.args = args;
		this.world = world;
		
		addFilesToList();
		new Thread(this).start();
		p.print(ChatColor.YELLOW + "[GriefLog] Searching for matching results...");
	}
	
	/**
	 * The search task that will search the files for the specified arugments.
	 * @param p The PlayerSession of the player that requested the search.
	 * @param action The callback that should happen after the search has been completed.
	 * @param parser The {@link ArgumentParser} that contains the arguments to search for.
	 */
	public SearchTask(PlayerSession p, BaseCallback action, ArgumentParser parser) {
		this.p = p;
		this.action = action;
		this.args = parser.getResult();
		this.world = parser.world;
		
		addFilesToList();
		new Thread(this).start();
		p.print(ChatColor.YELLOW + "[GriefLog] Searching for matching results...");
	}
	
	/**
	 * This function add's all the files we need to search through to {@code filesToSearch}
	 */
	public void addFilesToList() {
		if(GriefLog.logsDir.exists()) {
			if((world != null) && (!world.equalsIgnoreCase("null"))) {
				File f = new File(GriefLog.logsDir, world);
				addFilesInsideToFilesToSearch(f);
				addFilesInLogsDir();
			} else {
				File[] list = GriefLog.logsDir.listFiles();
				for (File f : list) {
					if(f.isFile()) {
						filesToSearch.add(f);
					} else if(f.isDirectory()) {
						addFilesInsideToFilesToSearch(f);
					}
				}
			}
		}
	}
	
	/**
	 * Add's all the files inside the directory file {@code f}.
	 * @param f The directory file.
	 */
	private void addFilesInsideToFilesToSearch(File f) {
		f.mkdir();
		File[] dircontents = f.listFiles();
		for(File file : dircontents) {
			if(file.isFile()) {
				filesToSearch.add(file);
			}
		}
	}
	
	/**
	 * Add's all the files in the directory {@code logs/} to keep compatibility with older versions.
	 */
	private void addFilesInLogsDir() {
		for(File f : GriefLog.logsDir.listFiles()) {
			if(f.isFile()) {
				filesToSearch.add(f);
			}
		}
	}

	public void run() {
		for(File searchFile : filesToSearch) {
			GriefLog.debug("Searching file: " + searchFile.getName() + " Size: " + GriefLog.fileIO.getFileSize(searchFile));
			long currentTime = System.currentTimeMillis();
			readAndSearchFile(searchFile);
			long nextTime = System.currentTimeMillis();
			GriefLog.debug("Took: " + (nextTime - currentTime) + "ms");
		}
		
		Collections.sort(foundData);
		p.setSearchResult(foundData);
		action.run();
	}
	
	/**
	 * Read's the {@code file} to a string and loops through the lines to check if these lines contain the search arugments.  
	 * @param file The file to search through.
	 */
	private void readAndSearchFile(File file) {
		try {
			String query = GriefLog.fileIO.read2String(file);
			String[] result = query.split(System.getProperty("line.separator"));
			for(String line : result) {
				addToFoundDataIfContainsText(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add's the parsed result of BaseData.loadFromString(line) to {@code foundData} if the line contained the search arguments.
	 * @param line The line to check.
	 */
	private void addToFoundDataIfContainsText(String line) {
		if (lineContainsText(line)) {
			foundData.add(BaseData.loadFromString(line));
		}
	}
	
	/**
	 * Check's if this line contains the arguments for the search.
	 * @param line The line to check.
	 * @return Returns true if the line contains all the arguments from {@code args}
	 */
	private boolean lineContainsText(String line) {
		String l = line.trim();
		for(String arg : args) {
			if(l.contains(arg)) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
}
