package tk.blackwolf12333.grieflog.worldedit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.utils.Events;
import tk.blackwolf12333.grieflog.utils.Time;

public class WorldEditLogger implements Runnable {

	Player p;
	GriefLog plugin;
	
	Time t = new Time();
	HashMap<Block, Material> blocksChanged = new HashMap<Block, Material>();
	
	public WorldEditLogger(GriefLog plugin, Player p, HashMap<Block, Material> blocks) {
		this.p = p;
		this.blocksChanged = blocks;
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		for(Iterator<Block> it = blocksChanged.keySet().iterator(); it.hasNext();) {
			Block b = it.next();
			String type = blocksChanged.get(b).toString();
			int x = b.getX();
			int y = b.getY();
			int z = b.getZ();
			String world = b.getWorld().getName();
			String where = x + ", " + y + ", " + z + " in: " + world;
			String data = t.now() + " " + Events.WORLDEDIT.getEvent() + " By: " + p.getName() + " What: " + type + " Where: " + where + System.getProperty("line.separator");
			
			Log(data, GriefLog.weFile);
		}
	}

	public void Log(String data, File file)
	{
		try {
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			
			if(GriefLog.getFileSize(file) >= 5) {
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
				GriefLog.log.warning(e.getMessage());
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
		File backupdir = new File(plugin.getDataFolder() + File.separator +"WELogs" + File.separator);
		if (!backupdir.exists()) {
			backupdir.mkdir();
		}
		File backup = new File(plugin.getDataFolder() + File.separator +"WELogs" + File.separator + "WorldEditLog" + t.now() + ".txt");
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
