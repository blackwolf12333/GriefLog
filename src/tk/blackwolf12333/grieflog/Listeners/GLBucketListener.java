package tk.blackwolf12333.grieflog.Listeners;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.utils.FileUtils;
import tk.blackwolf12333.grieflog.utils.Time;


public class GLBucketListener implements Listener {

	Logger log = Logger.getLogger("Minecraft");
	GriefLog gl;
	Time t = new Time();
	FileUtils fu = new FileUtils();
	int lava;
	
	public GLBucketListener(GriefLog plugin) {
		gl = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
	{
		Player p = event.getPlayer();
		String name = p.getName();
		String world = event.getBlockClicked().getWorld().getName();
		Block b = event.getBlockClicked().getRelative(event.getBlockFace());
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		
		if(lava >= gl.getConfig().getInt("warn-on-lava"))
		{
			Player[] players = gl.getServer().getOnlinePlayers();
			for(Player player : players)
			{
				if(player.isOp()) {
					player.sendMessage("Player: " + name + " placed " + gl.getConfig().getInt("warn-on-lava") + " or more times lava, possible grief.");
				}
			}
		}
		
		Material bucket = event.getBucket();
		String data = "";
		if(bucket == Material.WATER_BUCKET)
		{
			data = t.now() + " [BUCKET_WATER_EMPTY] Who: " + name + " Where: " + x + ", " + y + ", " + z + " In: " + world + System.getProperty("line.separator");
			
			try{
				//if file doesnt exists, then create it
				if(!GriefLog.file.exists()){
					GriefLog.file.createNewFile();
				}
	 
				if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
				{
					autoBackup();
				}
				
				fu.writeFile(GriefLog.file, data);
				
			}catch(IOException e){
				log.warning(e.toString());
			}
		}
		
		if(bucket == Material.LAVA_BUCKET)
		{
			data = t.now() + " [BUCKET_LAVA_EMPTY] Who: " + name + " Where: " + x + ", " + y + ", " + z + " In: " + world;
			
			try{
				//if file doesnt exists, then create it
				if(!GriefLog.file.exists()){
					GriefLog.file.createNewFile();
				}
	 
				if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
				{
					autoBackup();
				}
				
				fu.writeFile(GriefLog.file, data);
				
			}catch(IOException e){
				log.warning(e.toString());
			}
			
			lava++;
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
			e.printStackTrace();
		}
	}
}
