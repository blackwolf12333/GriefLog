package tk.blackwolf12333.grieflog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tk.blackwolf12333.grieflog.commands.GLog;
import tk.blackwolf12333.grieflog.listeners.BlockListener;
import tk.blackwolf12333.grieflog.listeners.BucketListener;
import tk.blackwolf12333.grieflog.listeners.EntityListener;
import tk.blackwolf12333.grieflog.listeners.HangingListener;
import tk.blackwolf12333.grieflog.listeners.PlayerListener;
import tk.blackwolf12333.grieflog.listeners.WorldListener;
import tk.blackwolf12333.grieflog.listeners.inventory.InventoryListener;
import tk.blackwolf12333.grieflog.utils.Debug;
import tk.blackwolf12333.grieflog.utils.FileIO;
import tk.blackwolf12333.grieflog.utils.config.ChestConfig;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;
import tk.blackwolf12333.grieflog.utils.config.UndoConfig;
import tk.blackwolf12333.grieflog.utils.logging.Time;

public class GriefLog extends JavaPlugin {
	
	public static Debug log;
	public static File logsDir;
	
	public static Time t = new Time();
	public static FileIO fileIO = new FileIO();
	public static UndoConfig undoConfig = new UndoConfig();
	public static HashMap<String, PlayerSession> sessions = new HashMap<String, PlayerSession>();
	
	
	private BlockListener bListener = new BlockListener(this);
	private PlayerListener pListener = new PlayerListener(this);
	private EntityListener eListener = new EntityListener(this);
	private BucketListener bucketListener = new BucketListener(this);
	private WorldListener wListener = new WorldListener();
	private HangingListener hListener = new HangingListener();
	@SuppressWarnings("unused")
	private InventoryListener iListener = new InventoryListener(this);
	
	private GLog glogCommand = new GLog(this);
	
	@Override
	public void onLoad() {
		log = new Debug(this.getLogger());
	}

	@Override
	public void onDisable() {
		undoConfig.save();
		sessions.clear();
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
		hListener = null;
		iListener = null;
	}

	private void garbageStatics() {
		sessions = null;
		undoConfig = null;
		sessions = null;
		t = null;
		fileIO = null;
		glogCommand = null;
		logsDir = null;
	}

	@Override
	public void onEnable() {
		setupConfig();
		registerListeners();
		getCommand("glog").setExecutor(glogCommand);
		onReloadLoadPlayerSessions();
		setupMetrics();
		
		GriefLog.debug("Server is running " + this.getServer().getVersion());
		log.info("GriefLog " + this.getDescription().getVersion() + " Enabled");
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
		pm.registerEvents(hListener, this);
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
		log.log(msg, true);
	}
	
	public static GriefLog getGriefLog() {
		return (GriefLog) Bukkit.getServer().getPluginManager().getPlugin("GriefLog");
	}
}
