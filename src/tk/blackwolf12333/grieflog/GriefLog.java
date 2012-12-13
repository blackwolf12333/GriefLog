package tk.blackwolf12333.grieflog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
import tk.blackwolf12333.grieflog.utils.Debug;
import tk.blackwolf12333.grieflog.utils.FileIO;
import tk.blackwolf12333.grieflog.utils.config.ChestConfig;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;
import tk.blackwolf12333.grieflog.utils.config.ConfigValues;
import tk.blackwolf12333.grieflog.utils.config.UndoConfig;
import tk.blackwolf12333.grieflog.utils.logging.Time;

public class GriefLog extends JavaPlugin {
	
	public static Debug log;
	public static File dataFolder;
		
	public static File logsDir = new File(ConfigHandler.values.getPathToLogs());
	public static Time t = new Time();
	public static FileIO fileIO = new FileIO();
	public static UndoConfig undoConfig = new UndoConfig();
	public static HashMap<String, PlayerSession> sessions = new HashMap<String, PlayerSession>();
	
	
	private BlockListener bListener = new BlockListener(this);
	private PlayerListener pListener = new PlayerListener(this);
	private EntityListener eListener = new EntityListener(this);
	private BucketListener bucketListener = new BucketListener(this);
	private WorldListener wListener = new WorldListener();
	
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
		System.gc(); // just hoping it will collect all my stuff that is not loaded anymore
	}

	private void garbageListeners() {
		bListener = null;
		pListener = null;
		eListener = null;
		bucketListener = null;
	}

	private void garbageStatics() {
		sessions = null;
		undoConfig = null;
		sessions = null;
		t = null;
		fileIO = null;
		glogCommand = null;
	}

	@Override
	public void onEnable() {
		dataFolder = new File("plugins" + File.separator + "GriefLog" + File.separator);
		setupConfig();
		registerListeners();
		getCommand("glog").setExecutor(glogCommand);
		onReloadLoadPlayerSessions();
		setupMetrics();
		
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
			sessions.put(p.getName(), new PlayerSession(this, p));
		}
		ConsoleCommandSender console = this.getServer().getConsoleSender();
		sessions.put(console.getName(), new PlayerSession(this, console));
	}

	private void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(bListener, this);
		pm.registerEvents(pListener, this);
		pm.registerEvents(eListener, this);
		pm.registerEvents(bucketListener, this);
		pm.registerEvents(wListener, this);
	}

	private void setupConfig() {
		new ConfigHandler(this);
		ConfigHandler.setupGriefLogConfig();
		ConfigHandler.values = new ConfigValues();
		if(ConfigHandler.values.getPutItemsBackOnRollback()) {
			ChestConfig.setup();
		}
	}

	public static void debug(Object msg) {
		log.log(msg, true);
	}
}
