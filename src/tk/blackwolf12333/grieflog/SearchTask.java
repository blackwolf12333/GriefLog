package tk.blackwolf12333.grieflog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.callback.BaseCallback;
import tk.blackwolf12333.grieflog.callback.BlockProtectionCallback;

public class SearchTask implements Runnable {

	ArrayList<String> foundData = new ArrayList<String>();
	TreeSet<File> filesToSearch = new TreeSet<File>();
	
	String[] text;
	GLPlayer p;
	BaseCallback action;
	
	public SearchTask(GLPlayer p, BaseCallback action, String ...text) {
		this.text = text;
		this.p = p;
		this.action = action;
		
		addFilesToList();
		Bukkit.getScheduler().scheduleSyncDelayedTask(p.getGriefLog(), this);
		if(!(action instanceof BlockProtectionCallback)) {
			p.print(ChatColor.YELLOW + "[GriefLog] Searching for matching results...");
		}
	}
	
	public SearchTask(GLPlayer p, BaseCallback action, ArrayList<String> args) {
		this.p = p;
		this.action = action;
		this.text = new String[args.size()];
		
		for(int i = 0; i < args.size(); i++) {
			text[i] = args.get(i);
		}
		
		addFilesToList();
		Bukkit.getScheduler().scheduleSyncDelayedTask(p.getGriefLog(), this);
		if(!(action instanceof BlockProtectionCallback)) {
			p.print(ChatColor.YELLOW + "[GriefLog] Searching for matching results...");
		}
	}
	
	public void addFilesToList() {
		filesToSearch.add(GriefLog.file);
		
		File file = new File("logs" + File.separator);
		String[] list = file.list();
		if(file.exists()) {
			for (String element : list) {
				filesToSearch.add(new File("logs" + File.separator + element));
			}
		}
	}
	
	public void run() {
		for(File searchFile : filesToSearch) {
			readAndSearchFile(searchFile);
		}

		action.result = foundData;
		action.run();
	}
	
	private void readAndSearchFile(File file) {
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = null;

			while ((line = br.readLine()) != null) {
				addToFoundDataIfContainsText(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if((fr != null) && (br != null)) {
				try {
					fr.close();
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private boolean lineContainsText(String line) {
		if(text.length == 1) {
			return line.contains(text[0]);
		} else if(text.length == 2) {
			return line.contains(text[0]) && line.contains(text[1]);
		} else if(text.length == 3) {
			return line.contains(text[0]) && line.contains(text[1]) && line.contains(text[2]);
		} else {
			return false;
		}
	}
	
	private void addToFoundDataIfContainsText(String line) {
		if (lineContainsText(line)) {
			foundData.add(line + System.getProperty("line.separator"));
		}
	}
}
