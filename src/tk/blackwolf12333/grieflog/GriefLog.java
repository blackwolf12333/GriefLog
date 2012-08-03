package tk.blackwolf12333.grieflog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import tk.blackwolf12333.grieflog.api.IGriefLogger;
import tk.blackwolf12333.grieflog.commands.GLog;
import tk.blackwolf12333.grieflog.listeners.BlockListener;
import tk.blackwolf12333.grieflog.listeners.BucketListener;
import tk.blackwolf12333.grieflog.listeners.EntityListener;
import tk.blackwolf12333.grieflog.listeners.PlayerListener;
import tk.blackwolf12333.grieflog.utils.PermsHandler;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class GriefLog extends JavaPlugin {
	
	File temp;
	public String version;
	public static Logger log;
	public static GriefLog instance;
	public static PermsHandler permission;
	public static File dataFolder;
		
	// the listeners
	public BlockListener bListener = new BlockListener(this);
	public PlayerListener pListener = new PlayerListener(this);
	public EntityListener eListener = new EntityListener(this);
	public BucketListener bucketListener = new BucketListener(this);
	
	
	public static File file = new File("GriefLog.txt");
	public static File reportFile = new File("Report.txt");
	public static HashMap<String, GLPlayer> players = new HashMap<String, GLPlayer>();
	public boolean usingPerms = false;
	
	@Override
	public void onLoad() {
		log = this.getLogger();
		instance = this;
	}

	@Override
	public void onDisable() {
		// delete all instances of GLPlayer so that will not cause a memory leak
		players.clear();
		
		temp = new File(getDataFolder(), "temp.glog");
		
		// save the tntIgnited variable
		if(!temp.exists()) {
			try {
				temp.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		saveHashMapTo(BlockListener.tntIgnited, temp);
		
		// tell the console that grieflog is hereby disabled
		log.info("GriefLog Disabled!!!");
	}

	@Override
	public void onEnable() {
		temp = new File(getDataFolder(), "temp.glog");
		dataFolder = getDataFolder();
		
		if (!file.exists()) {
			try {
				file.createNewFile();
				log.info(ChatColor.BLUE + "No GriefLog file yet, creating one for you.");
			} catch (IOException e) {
				log.warning(e.getMessage());
			}
		}
		
		if(temp.exists()) {
			BlockListener.tntIgnited = loadHashMapFrom(temp);
		}
		
		// register events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(bListener, this);
		pm.registerEvents(pListener, this);
		pm.registerEvents(eListener, this);
		pm.registerEvents(bucketListener, this);
		
		// config stuff
		new ConfigHandler(this);
		ConfigHandler.setupGriefLogConfig();
		if(ConfigHandler.values.getBlockprotection()) {
			new ConfigHandler(this);
			ConfigHandler.setupFriendsConfig();
		}
		
		// check for CreeperHeal if it is in the server don't log explosions, 
		// this will result in logging air instead of the exploded block.
		if(pm.getPlugin("CreeperHeal") != null) {
			log.info("CreeperHeal was detected, not logging Creeper and TNT explosions.");
			ConfigHandler.config.set("Explosions", false);
		}
		
		// setup the permissions
		permission = new PermsHandler(this);
		
		// register command
		getCommand("glog").setExecutor(new GLog(this));
		
		// add all online players to the players hashmap
		for(Player p : getServer().getOnlinePlayers()) {
			// add the online players
			players.put(p.getName(), new GLPlayer(this, p));
		}
		// add the console as GLPlayer
		ConsoleCommandSender console = this.getServer().getConsoleSender();
		players.put(console.getName(), new GLPlayer(this, console));
		
		// enable the api, i think this is useless, so i will delete it soon
		IGriefLogger logger = new GriefLogger();
		Bukkit.getServicesManager().register(IGriefLogger.class, logger, this, ServicePriority.Normal);
		
		// tell the console that grieflog is hereby enabled
		version = this.getDescription().getVersion();
		log.info("GriefLog " + version + " Enabled");
	}
	
	public static double getFileSize(File file) {
		double bytes = file.length();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		
		return megabytes;
	}
	
	/**
	 * Saves a HashMap<K, V> to a file.
	 * @param hashmap : The HashMap to be saved.
	 * @param file : The file to which the HashMap is saved to.
	 */
	public <K, V> void saveHashMapTo(HashMap<K, V> hashmap, File file) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(hashmap);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Loads a HashMap<K, V> from a file.
	 * @param file : The file from which the HashMap will be loaded.
	 * @return Returns a HashMap that was saved in the file.
	 */
	@SuppressWarnings("unchecked")
	public <K, V> HashMap<K, V> loadHashMapFrom(File file) {
		HashMap<K, V> result = null;
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			result = (HashMap<K, V>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
