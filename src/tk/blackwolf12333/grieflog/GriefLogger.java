package tk.blackwolf12333.grieflog;

import java.io.File;
import java.io.IOException;

import tk.blackwolf12333.grieflog.api.IGriefLogger;
import tk.blackwolf12333.grieflog.utils.FileUtils;
import tk.blackwolf12333.grieflog.utils.Time;

public class GriefLogger implements IGriefLogger {

	FileUtils fu = new FileUtils();
	Time t = new Time();
	GriefLog plugin;
	
	public GriefLogger(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	public void Log(String data)
	{
		try	{
			//if file doesnt exists, then create it
			if(!GriefLog.file.exists()){
				GriefLog.file.createNewFile();
			}
			
			// if the file has reached the max size, set in the config back it up
			if(fu.getFileSize(GriefLog.file) >= plugin.getConfig().getInt("mb"))
			{
				autoBackup();
			}
			
			// log it
			fu.writeFile(GriefLog.file, data);
				
		}catch(IOException e){
			GriefLog.log.warning(e.toString());
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
			GriefLog.log.info("[GriefLog] Log file moved to logs/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
