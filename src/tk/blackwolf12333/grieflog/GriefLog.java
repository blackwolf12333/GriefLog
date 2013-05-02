package tk.blackwolf12333.grieflog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tk.blackwolf12333.grieflog.commands.GLog;
import tk.blackwolf12333.grieflog.listeners.BlockListener;
import tk.blackwolf12333.grieflog.listeners.BucketListener;
import tk.blackwolf12333.grieflog.listeners.EntityListener;
import tk.blackwolf12333.grieflog.listeners.PlayerListener;
import tk.blackwolf12333.grieflog.listeners.WorldListener;
import tk.blackwolf12333.grieflog.listeners.inventory.InventoryListener;
import tk.blackwolf12333.grieflog.utils.Debug;
import tk.blackwolf12333.grieflog.utils.FileIO;
import tk.blackwolf12333.grieflog.utils.UndoSerializer;
import tk.blackwolf12333.grieflog.utils.config.ChestConfig;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;
import tk.blackwolf12333.grieflog.utils.csv.CSVIO;
import tk.blackwolf12333.grieflog.utils.logging.Time;

public class GriefLog extends JavaPlugin {
	
	public static Debug log;
	public static File logsDir;
	private static final String craftbukkitVersion = "1.5.1-R0.2";
	
	public static Time time = new Time();
	public static FileIO fileIO = new FileIO();
	public static CSVIO csvIO;
	public static HashMap<String, PlayerSession> sessions = new HashMap<String, PlayerSession>();
	public static UndoSerializer undoSerializer;
	public static boolean enableRollback = true;
	
	private BlockListener bListener = new BlockListener(this);
	private PlayerListener pListener = new PlayerListener(this);
	private EntityListener eListener = new EntityListener(this);
	private BucketListener bucketListener = new BucketListener(this);
	private WorldListener wListener = new WorldListener();
	@SuppressWarnings("unused")
	private InventoryListener iListener = new InventoryListener(this);
	
	private GLog glogCommand = new GLog(this);
	
	@Override
	public void onLoad() {
		log = new Debug(this.getLogger());
	}

	@Override
	public void onDisable() {
		sessions.clear();
		undoSerializer.save();
		garbageStatics();
		garbageListeners();
		
		log.info("GriefLog Disabled!!!");
		log = null; // has to be done after the message above, otherwise you get an npe
		System.gc();
	}

	private void garbageListeners() {
		bListener = null;
		pListener = null;
		eListener = null;
		bucketListener = null;
		wListener = null;
		iListener = null;
	}

	private void garbageStatics() {
		sessions = null;
		sessions = null;
		time = null;
		fileIO = null;
		csvIO = null;
		glogCommand = null;
		logsDir = null;
		undoSerializer = null;
	}

	@Override
	public void onEnable() {
		setupConfig();
		registerListeners();
		setupLogging();
		getCommand("glog").setExecutor(glogCommand);
		onReloadLoadPlayerSessions();
		setupMetrics();
		loadUndo();
		checkVersionCompatibility();
		
		GriefLog.debug("Server is running " + this.getServer().getVersion());
		log.info("GriefLog " + this.getDescription().getVersion() + " Enabled");
	}

	private void setupLogging() {
		if(ConfigHandler.values.getLoggingMethod().equalsIgnoreCase("csv")) {
			loadJCSV();
			csvIO = new CSVIO();
		}
	}

	private void loadJCSV() {
		File libFolder = new File(this.getDataFolder(), "libs");
		File jCSVlib = new File(libFolder, "jcsv-1.4.0.jar");
		try {
			JCSVLoader.extractFromJar(jCSVlib, this.getDataFolder().getName());
			URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
	        Class<URLClassLoader> sysclass = URLClassLoader.class;
	        try {
	            Method method = sysclass.getDeclaredMethod("addURL", new Class[] { URL.class });
	            method.setAccessible(true);
	            method.invoke(sysloader, new Object[] { JCSVLoader.getJarUrl(jCSVlib) });
	        } catch (final Throwable t) {
	            t.printStackTrace();
	            throw new IOException("Error adding " + JCSVLoader.getJarUrl(jCSVlib) + " to system classloader");
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void checkVersionCompatibility() {
		String version = this.getServer().getVersion();
		if(!version.contains(craftbukkitVersion)) {
			log.warning("Rollback will not be possible with your version of CraftBukkit!");
			enableRollback = false;
		}
	}

	private void loadUndo() {
		undoSerializer = new UndoSerializer();
		undoSerializer.load();
	}

	private void setupMetrics() {
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
	}

	private void onReloadLoadPlayerSessions() {
		for(Player p : getServer().getOnlinePlayers()) {
			sessions.put(p.getName(), new PlayerSession(p));
		}
		ConsoleCommandSender console = this.getServer().getConsoleSender();
		sessions.put(console.getName(), new PlayerSession(console));
	}

	private void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(bListener, this);
		pm.registerEvents(pListener, this);
		pm.registerEvents(eListener, this);
		pm.registerEvents(bucketListener, this);
		pm.registerEvents(wListener, this);
//		pm.registerEvents(iListener, this);
	}

	private void setupConfig() {
		new ConfigHandler(this);
		ConfigHandler.setupGriefLogConfig();
		logsDir = new File(ConfigHandler.values.getLogsDir());
		if(ConfigHandler.values.getPutItemsBackOnRollback()) {
			ChestConfig.setup();
		}
	}

	public static void debug(Object msg) {
		if(msg instanceof List<?>) {
			List<?> list = (List<?>) msg;
			for(int i = 0; i < list.size(); i++) {
				log.log(list.get(i), true);
			}
		} else {
			log.log(msg, true);
		}
	}
	
	public static void debug(String args[]) {
		for(String s : args) {
			log.log(s, true);
		}
	}
	
	public static GriefLog getGriefLog() {
		return (GriefLog) Bukkit.getServer().getPluginManager().getPlugin("GriefLog");
	}
}
