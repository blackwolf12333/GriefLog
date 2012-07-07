package tk.blackwolf12333.grieflog.utils.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tk.blackwolf12333.grieflog.GriefLog;

public class GLConfigHandler {

	static GriefLog plugin;
	public static File configFile;
	public static File friendsFile;
	public static FileConfiguration config;
	public static FileConfiguration friendsConfig;
	
	public static ConfigValues values = new ConfigValues();
	
	public GLConfigHandler(GriefLog plugin) {
		GLConfigHandler.plugin = plugin;
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
	    } else {
	    	loadConfig();
	    }
	}
	
	public static void setupFriendsConfig() {
		friendsFile = new File(plugin.getDataFolder(), "friends.yml");
	    friendsConfig = new YamlConfiguration();

	    if(!friendsFile.exists()) {
	    	friendsFile.getParentFile().mkdirs();
	        copy(plugin.getResource("friends.yml"), friendsFile);
	    } else {
	    	loadFriendsConfig();
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
	
	public static void saveConfig() { //Save configuration file
	    try {
	        config.save(configFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void saveFriendsConfig() { //Save configuration file
	    try {
	        friendsConfig.save(friendsFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void loadConfig() { //Load configuration file
	    try {
	        config.load(configFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void loadFriendsConfig() { //Load configuration file
	    try {
	        friendsConfig.load(friendsFile);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public static void reloadConfig() { //Save configuration file
	    try {
	        config.load(configFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static void reloadFriendsConfig() { //Save configuration file
	    try {
	        friendsConfig.load(friendsFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> getFriends(String player) {
		if(friendsConfig.getStringList(player) == null) {
			return new ArrayList<String>();
		}
		return friendsConfig.getStringList(player);
	}
	
	public static boolean isOnFriendsList(String player, String friend) {
		return GLConfigHandler.getFriends(player).contains(friend);
	}
}
