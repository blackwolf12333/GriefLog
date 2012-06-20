package tk.blackwolf12333.grieflog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
			
			// put the time in front of the data
			String ret = t.now() + data;

			// log it
			Log(ret, GriefLog.file);

		} catch (IOException e) {
			GriefLog.log.warning(e.toString());
		}
	}
	
	@Override
	public void Log(String data, File file)
	{
		try {
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// if the file has reached the max size, set in the config back it
			// up
			if (plugin.getFileSize(file) >= plugin.getConfig().getInt("mb")) {
				autoBackup();
			}

			// log it
			FileWriter fw = null;
			BufferedWriter bw = null;
			try {
				fw = new FileWriter(file, true);
				bw = new BufferedWriter(fw);
				bw.write(data);
				bw.close();
				fw.close();
			} catch (Exception e) {
				GriefLog.log.warning("FileException! On GriefLog:FileUtils:writeFile(File,String)");
			} finally {
				if((fw != null) && (bw != null))
				{
					bw.close();
					fw.close();
				}
			}

		} catch (IOException e) {
			GriefLog.log.warning(e.toString());
		}
	}

	private void autoBackup() {
		File backupdir = new File("logs" + File.separator);
		if (!backupdir.exists()) {
			backupdir.mkdir();
		}
		File backup = new File("logs" + File.separator + "GriefLog" + t.now() + ".txt");
		if (!backup.exists()) {
			try {
				backup.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			copy(GriefLog.file, backup);
			GriefLog.log.info("[GriefLog] Log file moved to logs/");
		} catch (Exception e) {
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
