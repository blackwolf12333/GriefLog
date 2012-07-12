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
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import tk.blackwolf12333.grieflog.api.ISearcher;
import tk.blackwolf12333.grieflog.api.IGriefLogger;
import tk.blackwolf12333.grieflog.commands.GLog;
import tk.blackwolf12333.grieflog.listeners.GLBlockListener;
import tk.blackwolf12333.grieflog.listeners.GLBucketListener;
import tk.blackwolf12333.grieflog.listeners.GLEntityListener;
import tk.blackwolf12333.grieflog.listeners.GLPlayerListener;
import tk.blackwolf12333.grieflog.search.GriefLogSearcher;
import tk.blackwolf12333.grieflog.utils.PermsHandler;
import tk.blackwolf12333.grieflog.utils.config.GLConfigHandler;

public class GriefLog extends JavaPlugin {

	public String version;
	public static Logger log;
	public static GriefLog instance;
	public static PermsHandler permission;
	public static File dataFolder;
	File temp;
		
	// the listeners
	GLBlockListener bListener = new GLBlockListener(this);
	GLPlayerListener pListener = new GLPlayerListener(this);
	GLEntityListener eListener = new GLEntityListener(this);
	GLBucketListener bucketListener = new GLBucketListener(this);
	
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
		temp = new File(getDataFolder(), "temp.glog");
		
		// save the tntIgnited variable
		if(!temp.exists()) {
			try {
				temp.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		saveHashMapTo(GLBlockListener.tntIgnited, temp);
		
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
			GLBlockListener.tntIgnited = loadHashMapFrom(temp);
		}
		
		// register events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(bListener, this);
		pm.registerEvents(pListener, this);
		pm.registerEvents(eListener, this);
		pm.registerEvents(bucketListener, this);
		
		// config stuff
		new GLConfigHandler(this);
		GLConfigHandler.setupGriefLogConfig();
		if(GLConfigHandler.values.getBlockprotection()) {
			new GLConfigHandler(this);
			GLConfigHandler.setupFriendsConfig();
		}
		
		// setup the permissions
		permission = new PermsHandler(this);
		
		// register command
		getCommand("glog").setExecutor(new GLog(this));
		
		// enable the api
		IGriefLogger logger = new GriefLogger();
		Bukkit.getServicesManager().register(IGriefLogger.class, logger, this, ServicePriority.Normal);
		
		ISearcher searcher = new GriefLogSearcher();
		Bukkit.getServicesManager().register(ISearcher.class, searcher, this, ServicePriority.Normal);

		// tell the console that grieflog is hereby enabled
		version = this.getDescription().getVersion();
		log.info("GriefLog " + version + " Enabled");
	}
	
	// pretty self explaining
	public static double getFileSize(File file) {
		double bytes = file.length();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		
		return megabytes;
	}
	
	public void saveHashMapTo(HashMap<String,Integer> hashmap, File file) {
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
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> loadHashMapFrom(File file) {
		HashMap<String, Integer> result = null;
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			result = (HashMap<String, Integer>) ois.readObject();
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
