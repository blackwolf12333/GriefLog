package tk.blackwolf12333.grieflog.utils.searching.tasks;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.utils.filters.Filter;
import tk.blackwolf12333.grieflog.utils.searching.ArgumentParser;

public abstract class GriefLogTask implements Runnable {
	
	public String world;
	ArrayList<File> filesToSearch = new ArrayList<File>();
	ArrayList<String> args = new ArrayList<String>();
	ArrayList<BaseData> foundData = new ArrayList<BaseData>();
	List<Filter> filters = new ArrayList<Filter>();
	PlayerSession p;
	SearchCallback action;
	Thread searchThread;
	
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
	 * Add's all the files in the logs directory to keep compatibility with older versions.
	 */
	private void addFilesInLogsDir() {
		for(File f : GriefLog.logsDir.listFiles()) {
			if(f.isFile()) {
				filesToSearch.add(f);
			}
		}
	}
	
	protected ArrayList<String> getParserResult(ArgumentParser parser) {
		ArrayList<String> result = new ArrayList<String>(4);
		addToListIfNotNull(parser.world, result);
		addToListIfNotNull(parser.player, result);
		addToListIfNotNull(parser.event, result);
		addToListIfNotNull(parser.blockFilter, result);
		return result;
	}
	
	/**
	 * Adds the string value to the list if value != null
	 * @param value
	 * @param list
	 */
	protected void addToListIfNotNull(String value, ArrayList<String> list) {
		 if(!(value == null)) {
			 list.add(value);
		 }
	 }
	
	/**
	 * Read's the {@code file} to a string and loops through the lines to check if these lines contain the search arugments.  
	 * @param file The file to search through.
	 */
	protected void searchFile(File file) {
		try {
			String query = GriefLog.fileIO.read2String(file);
			String[] lines = query.split(System.getProperty("line.separator"));
			for(String line : lines) {
				addToFoundDataIfContainsArguments(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Check's if this line contains the arguments for the search.
	 * @param line The line to check.
	 * @return Returns true if the line contains all the arguments from {@code args}
	 */
	protected boolean lineContainsArguments(String line) {
		String l = line.trim();
		for(String arg : args) {
			if(l.contains(arg)) {
				GriefLog.debug(arg);
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void run() {
		for(File searchFile : filesToSearch) {
			GriefLog.debug("Searching file: " + searchFile.getName() + " Size: " + GriefLog.fileIO.getFileSize(searchFile));
			long currentTime = System.currentTimeMillis();
			searchFile(searchFile);
			long nextTime = System.currentTimeMillis();
			GriefLog.debug("Took: " + (nextTime - currentTime) + "ms");
		}
		
		Collections.sort(foundData);
		p.setSearchResult(foundData);
		action.start();
	}
	
	protected abstract void addToFoundDataIfContainsArguments(String line);
}
