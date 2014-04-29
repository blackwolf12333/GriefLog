package tk.blackwolf12333.grieflog.utils;

import java.io.File;
import java.util.Date;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class FilePurger implements Runnable {
	GriefLog plugin;
	
	public FilePurger(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		for(File worldDir : GriefLog.logsDir.listFiles()) {
			for(File f : worldDir.listFiles()) {
				long diff = new Date().getTime() - f.lastModified();
				int days = ConfigHandler.values.getPurgeAfter();
				if(diff > days * 24 * 60 * 60 * 1000) {
					System.out.println("Deleting old file: " + f.getName() + " after " + days + " days.");
					f.delete();
				}
			}
		}
	}
}
