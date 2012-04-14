package blackwolf12333.maatcraft.grieflog;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import blackwolf12333.maatcraft.grieflog.Listeners.GLBlockListener;
import blackwolf12333.maatcraft.grieflog.Listeners.GLPlayerListener;
import blackwolf12333.maatcraft.grieflog.commands.GDelReport;
import blackwolf12333.maatcraft.grieflog.commands.GLTool;
import blackwolf12333.maatcraft.grieflog.commands.GLog;
import blackwolf12333.maatcraft.grieflog.commands.GPos;
import blackwolf12333.maatcraft.grieflog.commands.GRDReport;
import blackwolf12333.maatcraft.grieflog.commands.GReport;

import blackwolf12333.maatcraft.grieflog.utils.*;

public class GriefLog extends JavaPlugin {

	GLBlockListener bListener = new GLBlockListener(this);
	GLPlayerListener pListener = new GLPlayerListener(this);
	public static Logger log = Logger.getLogger("Minecraft");
	public static File file = new File("GriefLog.txt");
	public static File reportFile = new File("Report.txt");
	FileUtils fu = new FileUtils();
	Time t = new Time();
	public String version;

	@Override
	public void onDisable() {
		log.info("GriefLog Disabled!!!");
	}

	@Override
	public void onEnable() {
		
		// register events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(bListener, this);
		pm.registerEvents(pListener, this);
		version = this.getDescription().getVersion();
		
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.warning(e.getMessage());
			}
		}
		
		// config stuff
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		// register commands
		getCommand("glog").setExecutor(new GLog(this));
		getCommand("gpos").setExecutor(new GPos(this));
		getCommand("report").setExecutor(new GReport(this));
		getCommand("rdreports").setExecutor(new GRDReport(this));
		getCommand("delreports").setExecutor(new GDelReport(this));
		getCommand("gltool").setExecutor(new GLTool(this));
		
		log.info("GriefLog Enabled");	
	}
}
