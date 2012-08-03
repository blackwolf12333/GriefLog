package tk.blackwolf12333.grieflog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;

import tk.blackwolf12333.grieflog.action.BaseAction;

public class SearchTask implements Runnable {

	ArrayList<String> data = new ArrayList<String>();
	ArrayList<File> files = new ArrayList<File>();
	
	String[] text;
	GLPlayer p;
	BaseAction action;
	
	public SearchTask(GLPlayer p, BaseAction action, String ...text) {
		this.text = text;
		this.p = p;
		this.action = action;
		
		addFiles();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(p.getGriefLog(), this);
	}
	
	public SearchTask(GLPlayer p, BaseAction action, ArrayList<String> args) {
		this.p = p;
		this.action = action;
		this.text = new String[args.size()];
		
		for(int i = 0; i < args.size(); i++) {
			text[i] = args.get(i);
		}
		
		addFiles();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(p.getGriefLog(), this);
	}
	
	public void addFiles() {
		files.add(GriefLog.file);
		
		File file = new File("logs" + File.separator);
		String[] list = file.list();
		if(file.exists()) {
			for (String element : list) {
				files.add(new File("logs" + File.separator + element));
			}
		}
	}
	
	public void run() {
		if(text.length == 1)
		{
			File[] searchFiles = new File[files.size()];
			searchFiles = files.toArray(searchFiles);
			FileReader fr = null;
			BufferedReader br = null;
			
			for(File searchFile : searchFiles)
			{
				try {
					fr = new FileReader(searchFile);
					br = new BufferedReader(fr);
					String line = null;

					while ((line = br.readLine()) != null) {
						if (line.contains(text[0])) {
							data.add(line + System.getProperty("line.separator"));
						} else {
							continue;
						}
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
		}
		if(text.length == 2) {
			File[] searchFiles = new File[files.size()];
			searchFiles = files.toArray(searchFiles);
			FileReader fr = null;
			BufferedReader br = null;
			
			for(File searchFile : searchFiles)
			{
				try {
					fr = new FileReader(searchFile);
					br = new BufferedReader(fr);
					String line = null;

					while ((line = br.readLine()) != null) {
						if ((line.contains(text[0])) && (line.contains(text[1]))) {
							data.add(line + System.getProperty("line.separator"));
						} else {
							continue;
						}
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
		}
		if(text.length == 3)
		{
			File[] searchFiles = new File[files.size()];
			searchFiles = files.toArray(searchFiles);
			FileReader fr = null;
			BufferedReader br = null;
			
			for(File searchFile : searchFiles)
			{
				try {
					fr = new FileReader(searchFile);
					br = new BufferedReader(fr);
					String line = null;

					while ((line = br.readLine()) != null) {
						if ((line.contains(text[0])) && (line.contains(text[1])) && (line.contains(text[2]))) {
							data.add(line + System.getProperty("line.separator"));
						} else {
							continue;
						}
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
		}
		
		action.result = data;
		action.start();
	}
}
