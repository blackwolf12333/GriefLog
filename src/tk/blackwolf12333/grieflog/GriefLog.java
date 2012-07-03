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
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import tk.blackwolf12333.grieflog.Listeners.GLBlockListener;
import tk.blackwolf12333.grieflog.Listeners.GLBucketListener;
import tk.blackwolf12333.grieflog.Listeners.GLEntityListener;
import tk.blackwolf12333.grieflog.Listeners.GLPlayerListener;
import tk.blackwolf12333.grieflog.api.IGriefLogSearcher;
import tk.blackwolf12333.grieflog.api.IGriefLogger;
import tk.blackwolf12333.grieflog.api.IPages;
import tk.blackwolf12333.grieflog.commands.GLog;
import tk.blackwolf12333.grieflog.utils.Pages;
import tk.blackwolf12333.grieflog.utils.PermsHandler;

public class GriefLog extends JavaPlugin {

	public String version;
	public static Logger log;
	public static PermsHandler permission;
		
	// the listeners
	GLBlockListener bListener = new GLBlockListener(this);
	GLPlayerListener pListener = new GLPlayerListener(this);
	GLEntityListener eListener = new GLEntityListener(this);
	GLBucketListener bucketListener = new GLBucketListener(this);
	
	public static File file = new File("GriefLog.txt");
	public static File reportFile = new File("Report.txt");
	File temp = new File("plugins" + File.separator + "GriefLog" + File.separator + "temp.glog");
	public boolean usingPerms = false;

	@Override
	public void onLoad() {
		log = this.getLogger();
	}

	@Override
	public void onDisable() {
		// save the tntIgnited variable
		if(!temp.exists()) {
			try {
				temp.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch blockChest
				e.printStackTrace();
			}
		}
		saveHashMapTo(GLBlockListener.tntIgnited, temp);
		
		// tell the console that grieflog is hereby disabled
		log.info("GriefLog Disabled!!!");
	}

	@Override
	public void onEnable() {

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
		
		version = this.getDescription().getVersion();

		// config stuff		
		getConfig().options().copyHeader(true);
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		permission = new PermsHandler(this);
		
		// add the permissions
		pm.addPermission(new Permission("grieflog.get.xyz"));
		pm.addPermission(new Permission("grieflog.reports.read"));
		pm.addPermission(new Permission("grieflog.reports.delete"));
		pm.addPermission(new Permission("grieflog.search"));
		pm.addPermission(new Permission("grieflog.reload"));
		pm.addPermission(new Permission("grieflog.rollback"));
		pm.addPermission(new Permission("grieflog.report.here"));
		pm.addPermission(new Permission("grieflog.report.xyz"));
		
		// register command
		getCommand("glog").setExecutor(new GLog(this));
		
		// enable the api
		IGriefLogger logger = new GriefLogger(this);
		Bukkit.getServicesManager().register(IGriefLogger.class, logger, this, ServicePriority.Normal);
		
		IGriefLogSearcher searcher = new GriefLogSearcher();
		Bukkit.getServicesManager().register(IGriefLogSearcher.class, searcher, this, ServicePriority.Normal);
		
		IPages pages = new Pages();
		Bukkit.getServicesManager().register(IPages.class, pages, this, ServicePriority.Normal);

		// tell the console that grieflog is hereby enabled
		log.info("GriefLog " + version + " Enabled");
	}
	
	// pretty self explaining
	public double getFileSize(File file) {
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
			// TODO Auto-generated catch blockChest
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch blockChest
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
			// TODO Auto-generated catch blockChest
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch blockChest
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch blockChest
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
