package tk.blackwolf12333.grieflog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import tk.blackwolf12333.grieflog.api.IGriefLogger;
import tk.blackwolf12333.grieflog.utils.Time;

public class GriefLogger implements IGriefLogger {

	Time t = new Time();
	GriefLog plugin;

	public GriefLogger(GriefLog plugin) {
		this.plugin = plugin;
	}

	@Override
	public void Log(String data) {
		try {
			// if file doesnt exists, then create it
			if (!GriefLog.file.exists()) {
				GriefLog.file.createNewFile();
			}

			// if the file has reached the max size, set in the config back it
			// up
			if (plugin.getFileSize(GriefLog.file) >= plugin.getConfig().getInt("mb")) {
				autoBackup();
			}

			// log it
			Log(data, GriefLog.file);

		} catch (IOException e) {
			plugin.log.warning(e.toString());
		}
	}
	
	@Override
	public void Log(String data, File file)
	{
		
	}

	private void autoBackup() {
		File backupdir = new File("logs/");
		if (!backupdir.exists()) {
			backupdir.mkdir();
		}
		File backup = new File("logs" + File.separator + "GriefLog" + t.Date() + ".txt");
		if (!backup.exists()) {
			try {
				backup.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			copy(GriefLog.file, backup);
			plugin.log.info("[GriefLog] Log file moved to logs/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// pretty self explaining function
	public void copy(File from, File to) throws IOException {
		
		if (!from.exists())
			return;
		if (!to.exists()) {
			System.out.print("File \"" + to.getName() + "\" does not exist!");
			return;
		}
		
		FileInputStream in = new FileInputStream(from);
		FileOutputStream out = new FileOutputStream(to);
		
		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
		from.delete();
	}
}
