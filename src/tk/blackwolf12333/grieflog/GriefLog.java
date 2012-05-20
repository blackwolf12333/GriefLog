package tk.blackwolf12333.grieflog;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import tk.blackwolf12333.grieflog.Listeners.GLBlockListener;
import tk.blackwolf12333.grieflog.Listeners.GLEntityListener;
import tk.blackwolf12333.grieflog.Listeners.GLPlayerListener;
import tk.blackwolf12333.grieflog.commands.GLog;
import tk.blackwolf12333.grieflog.utils.FileUtils;
import tk.blackwolf12333.grieflog.utils.Time;

import tk.blackwolf12333.grieflog.Listeners.GLBucketListener;

public class GriefLog extends JavaPlugin {

	GLBlockListener bListener = new GLBlockListener(this);
	GLPlayerListener pListener = new GLPlayerListener(this);
	GLEntityListener eListener = new GLEntityListener(this);
	GLBucketListener bucketListener = new GLBucketListener(this);
	public static Logger log = Logger.getLogger("Minecraft");
	public static File file = new File("GriefLog.txt");
	public static File reportFile = new File("Report.txt");
	FileUtils fu = new FileUtils();
	Time t = new Time();
	public String version;

	@Override
	public void onDisable() {
		// tell the console that grieflog is hereby disabled
		log.info("GriefLog Disabled!!!");
	}

	@Override
	public void onEnable() {
		
		// register events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(bListener, this);
		pm.registerEvents(pListener, this);
		pm.registerEvents(eListener, this);
		pm.registerEvents(bucketListener, this);
		
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
		
		// tell the console that grieflog is hereby enabled
		log.info("[GriefLog] GriefLog " + version + " Enabled");
	}
	
	public void Log(String data)
	{
		try	{
			//if file doesnt exists, then create it
			if(!GriefLog.file.exists()){
				GriefLog.file.createNewFile();
			}
			
			// if the file has reached the max size, set in the config back it up
			if(fu.getFileSize(GriefLog.file) >= getConfig().getInt("mb"))
			{
				autoBackup();
			}
			
			// log it
			fu.writeFile(GriefLog.file, data);
				
		}catch(IOException e){
			log.warning(e.toString());
		}
	}
	
	private void autoBackup()
	{
		File backupdir = new File("logs/");
		if(!backupdir.exists())
		{
			backupdir.mkdir();
		}
		File backup = new File("logs" + File.separator + "GriefLog" + t.Date() + ".txt");
		if(!backup.exists())
		{
			try {
				backup.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			fu.copy(GriefLog.file, backup);
			log.info("[GriefLog] Log file moved to logs/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
