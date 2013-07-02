package tk.blackwolf12333.grieflog.utils.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tk.blackwolf12333.grieflog.GriefLog;

public class ConfigHandler {
	public static GriefLog plugin;
	public static File configFile;
	public static FileConfiguration config;
	
	public static ConfigValues values;
	
	public ConfigHandler(GriefLog plugin) {
		ConfigHandler.plugin = plugin;
	}
	
	public static void setupGriefLogConfig() {
		configFile = new File(plugin.getDataFolder(), "config.yml");
	    config = new YamlConfiguration();
	    if(!configFile.exists()) {
	    	configFile.getParentFile().mkdirs();
	    	try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        copy(plugin.getResource("config.yml"), configFile);
	        loadConfig();
	    } else {
	    	mergeConfig();
	    }
	}
	
	private static void copy(InputStream in, File file) {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void saveConfig() {
	    try {
	    	GriefLog.log.info("Saving configuration file.");
	        config.save(configFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void loadConfig() {
	    try {
	        config.load(configFile);
	        ConfigHandler.values = new ConfigValues();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (InvalidConfigurationException e) {
	    	
	    }
	}
	
	public static void reloadConfig() {
		loadConfig();
	}
	
	private static void mergeConfig() {
		try {
			loadConfig();
			FileConfiguration newconfig = new YamlConfiguration();
			newconfig.load(plugin.getResource("config.yml"));
			ArrayList<String> currentEntries = getAllEntries(config);
			ArrayList<String> newEntries = getAllEntries(newconfig);
			
			for(String s : newEntries) {
				if(!currentEntries.contains(s)) {
					config.set(s, newconfig.get(s));
				}
			}
			
			for(String s : currentEntries) {
				if(!newEntries.contains(s)) {
					config.set(s, null);
				}
			}
			config.save(configFile);
			reloadConfig();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static ArrayList<String> getAllEntries(FileConfiguration config) {
		ArrayList<String> entries = new ArrayList<String>();
		Map<String, Object> contents = config.getValues(true);
		
		for(Iterator<String> it = contents.keySet().iterator(); it.hasNext();) {
			String s = it.next();
			if(s != null) entries.add(s);
		}
		
		return entries;
	}
}
