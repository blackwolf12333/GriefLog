package tk.blackwolf12333.grieflog.utils.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tk.blackwolf12333.grieflog.utils.logging.Events;

public class UndoConfig {

	FileConfiguration config = new YamlConfiguration();
	File configFile = new File("plugins" + File.separator + "GriefLog" + File.separator, "undo.yml");
	int count = 0;
	
	public UndoConfig() {
		setup();
		try {
			config.load(configFile);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setup() {
		if(!configFile.exists()) {
			try {
				configFile.getParentFile().mkdirs();
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void add(String value) {
		count = config.getValues(false).size();
		config.set(new Integer(count).toString(), value);
		count++;
		save();
	}
	
	public ArrayList<String> get(String key) {//TODO: don't throw a nullpointer when config.getString returns null;line 48
		try {
			ArrayList<String> list = new ArrayList<String>();
			String[] split = config.getString(key).split(":");
			for(String i : split) {
				if(!i.equals("null")) {
					if(Events.isEvent(i)) {
						list.add(Events.getEvent(i).getEventName());
					} else {
						list.add(i);
					}
				}
			}
			return list;
		} catch(NullPointerException e) {
			return null;
		}
	}
	
	public ArrayList<String> getAll() {
		ArrayList<String> allEntries = new ArrayList<String>();
		for(Entry<String, Object> e : config.getValues(false).entrySet()) {
			allEntries.add(e.getKey() + ": " + e.getValue());
		}
		return allEntries;
	}
	
	public void save() {
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
