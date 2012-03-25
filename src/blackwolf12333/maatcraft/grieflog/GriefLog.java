package blackwolf12333.maatcraft.grieflog;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import blackwolf12333.maatcraft.grieflog.commands.GLog;
import blackwolf12333.maatcraft.grieflog.commands.GPos;
import blackwolf12333.maatcraft.grieflog.commands.GReport;

public class GriefLog extends JavaPlugin {

	GLBlockListener bListener = new GLBlockListener();
	GLPlayerListener pListener = new GLPlayerListener();
	public static Logger log = Logger.getLogger("Minecraft");
	public static File file = new File("GriefLog.txt");
	public static File reportFile = new File("Report.txt");

	@Override
	public void onDisable() {
		log.info("GriefLog Disabled!!!");
	}

	@Override
	public void onEnable() {

		// register events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(bListener, this);
		pm.registerEvents(bListener, this);
		pm.registerEvents(bListener, this);
		pm.registerEvents(pListener, this);
		pm.registerEvents(pListener, this);
		pm.registerEvents(pListener, this);
		
		// register commands
		getCommand("glog").setExecutor(new GLog(this));
		getCommand("gpos").setExecutor(new GPos(this));
		getCommand("report").setExecutor(new GReport(this));
		
		log.info("BlockLog Enabled");
		
		
	}	
}
