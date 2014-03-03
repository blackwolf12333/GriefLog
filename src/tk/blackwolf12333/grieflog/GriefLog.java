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
import tk.blackwolf12333.grieflog.listeners.InventoryListener;
import tk.blackwolf12333.grieflog.listeners.PlayerListener;
import tk.blackwolf12333.grieflog.listeners.WorldListener;
import tk.blackwolf12333.grieflog.utils.CompatibilityWrapper;
import tk.blackwolf12333.grieflog.utils.Debug;
import tk.blackwolf12333.grieflog.utils.FileIO;
import tk.blackwolf12333.grieflog.utils.UndoSerializer;
import tk.blackwolf12333.grieflog.utils.config.ChestConfig;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;
import tk.blackwolf12333.grieflog.utils.csv.CSVIO;
import tk.blackwolf12333.grieflog.utils.logging.Time;
import tk.blackwolf12333.grieflog.utils.logging.worldedit.GriefLogEditSessionFactory;
import tk.blackwolf12333.grieflog.utils.reports.Reporter;

public class GriefLog extends JavaPlugin {
	
	public static Debug log;
	public static File logsDir;
	public static Time time = new Time();
	public static FileIO fileIO = new FileIO();
	public static CSVIO csvIO;
	public static HashMap<String, PlayerSession> sessions = new HashMap<String, PlayerSession>();
	public static UndoSerializer undoSerializer;
	public static boolean enableRollback = true;
	public static CompatibilityWrapper compatibility = new CompatibilityWrapper();
	public static Reporter reporter = new Reporter();
	
	private BlockListener bListener = new BlockListener(this);
	private PlayerListener pListener = new PlayerListener(this);
	private EntityListener eListener = new EntityListener(this);
	private BucketListener bucketListener = new BucketListener(this);
	private WorldListener wListener = new WorldListener();
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
		reporter.saveReports();
		garbageStatics();
		garbageListeners();
		
		log.info("GriefLog Disabled!!!");
		log = null;
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
		compatibility = null;
		reporter = null;
	}

	@Override
	public void onEnable() {
		setupConfig();
		registerListeners();
		setupLogging();
		moveLogsIfNeeded();
		enableWorldEditLogging();
		getCommand("glog").setExecutor(glogCommand);
		onReloadLoadPlayerSessions();
		setupMetrics();
		loadUndo();
		reporter.loadReports();
		
		GriefLog.debug("Server is running " + this.getServer().getVersion());
		log.info("GriefLog " + this.getDescription().getVersion() + " Enabled");
	}

	private void moveLogsIfNeeded() {
		File oldDir = new File("logs/world/");
		if(oldDir.exists()) {
			oldDir = oldDir.getParentFile();
			for(File f : oldDir.listFiles()) {
				if(f.isDirectory()) {
					for(File file : f.listFiles()) {
						File newFile = new File(ConfigHandler.values.getLogsDir() + File.separatorChar + f.getName() + File.separatorChar + file.getName());
						if(!newFile.getParentFile().exists()) {
							newFile.getParentFile().mkdirs();
						}
						f.renameTo(newFile.getParentFile().getAbsoluteFile());
					}
				}
			}
		}
	}

	private void enableWorldEditLogging() {
		if(this.getServer().getPluginManager().isPluginEnabled("WorldEdit")) {
			new GriefLogEditSessionFactory(this).initialize();
		}
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
		pm.registerEvents(iListener, this);
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
