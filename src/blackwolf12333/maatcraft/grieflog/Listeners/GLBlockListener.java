package blackwolf12333.maatcraft.grieflog.Listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import blackwolf12333.maatcraft.grieflog.GriefLog;
import blackwolf12333.maatcraft.grieflog.utils.FileUtils;
import blackwolf12333.maatcraft.grieflog.utils.Time;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class GLBlockListener implements Listener{

	Logger log = Logger.getLogger("Minecraft");
	GriefLog gl;
	FileUtils fu = new FileUtils();
	Time t = new Time();
	
	public GLBlockListener(GriefLog plugin) {
		gl = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreak(BlockBreakEvent event) {
		
		Integer blockX = event.getBlock().getLocation().getBlockX();
		Integer blockY = event.getBlock().getLocation().getBlockY();
		Integer blockZ = event.getBlock().getLocation().getBlockZ();
		Player player = event.getPlayer();
		String type = event.getBlock().getType().toString();
		String namePlayer = player.getName();
		String worldName = player.getWorld().getName();
		
		String data = t.now() + " [BLOCK_BREAK] " + " By: " + namePlayer + " What: " + type + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + "\n";

		try{
			//if file doesnt exists, then create it
			if(!GriefLog.file.exists()){
				GriefLog.file.createNewFile();
			}
 
			if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
			{
				// Destination directory
				File dir = new File("logs/");
				if(!dir.exists())
				{
					dir.mkdir();
				}

				// Move file to new directory
				boolean success = GriefLog.file.renameTo(new File(dir, (GriefLog.file.getName()+t.now())));
				if (!success) {
				    GriefLog.log.warning("[GriefLog] The old logfile could not be moved to logs/.");
				}
			}
			
			fu.writeFile(GriefLog.file, data);
			
		}catch(IOException e){
			log.warning(e.toString());
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(BlockPlaceEvent event) {
		
		Integer blockX = event.getBlock().getLocation().getBlockX();
		Integer blockY = event.getBlock().getLocation().getBlockY();
		Integer blockZ = event.getBlock().getLocation().getBlockZ();
		Player player = event.getPlayer();
		String type = event.getBlockPlaced().getType().toString();
		String namePlayer = player.getName();
		String worldName = player.getWorld().getName();
		
		String data = t.now() + " [BLOCK_PLACE] " + " Who: " + namePlayer + " What: " + type +  " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + "\n";
		
		try{ 
			//if file doesnt exists, then create it
			if(!GriefLog.file.exists()){
				GriefLog.file.createNewFile();
			}
			
			if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
			{
				// Destination directory
				File dir = new File("logs/");
				if(!dir.exists())
				{
					dir.mkdir();
				}

				// Move file to new directory
				boolean success = GriefLog.file.renameTo(new File(dir, (GriefLog.file.getName()+t.now())));
				if (!success) {
				    GriefLog.log.warning("[GriefLog] The old logfile could not be moved to logs/.");
				}
			}
			
			fu.writeFile(GriefLog.file, data);
				
		}catch(IOException e){
			log.warning(e.toString());
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBurn(BlockBurnEvent event) {
		
		Integer blockX = event.getBlock().getLocation().getBlockX();
		Integer blockY = event.getBlock().getLocation().getBlockY();
		Integer blockZ = event.getBlock().getLocation().getBlockZ();
		String worldName = event.getBlock().getWorld().getName();
		
		String data = t.now() + " [BLOCK_BURN] " + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + "\n";
		
		try	{
			
			//if file doesnt exists, then create it
			if(!GriefLog.file.exists()){
				GriefLog.file.createNewFile();
			}
			
			if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
			{
				// Destination directory
				File dir = new File("logs/");
				if(!dir.exists())
				{
					dir.mkdir();
				}

				// Move file to new directory
				boolean success = GriefLog.file.renameTo(new File(dir, (GriefLog.file.getName()+t.now())));
				if (!success) {
				    GriefLog.log.warning("[GriefLog] The old logfile could not be moved to logs/.");
				}
			}
			
			fu.writeFile(GriefLog.file, data);
				
		}catch(IOException e){
			log.warning(e.toString());
		}
	}
	
	/*@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		int x = event.getBlock().getX();
		int y = event.getBlock().getY();
		int z = event.getBlock().getZ();
		String playerName = event.getPlayer().getName();
		String worldName = event.getBlock().getWorld().getName();
		
		IgniteCause ic = event.getCause();
		
		ic.toString();
		
		String data = t.now() + " [BLOCK_IGNITE] By: " + playerName + " How: " + ic.toString() + " Where: " + x + ", " + y + ", " + z + "In: " + worldName + "\n";
		
		try	{
			
			//if file doesnt exists, then create it
			if(!GriefLog.file.exists()){
				GriefLog.file.createNewFile();
			}
			
			if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
			{
				// Destination directory
				File dir = new File("logs/");
				if(!dir.exists())
				{
					dir.mkdir();
				}

				// Move file to new directory
				boolean success = GriefLog.file.renameTo(new File(dir, (GriefLog.file.getName()+t.now())));
				if (!success) {
				    GriefLog.log.warning("[GriefLog] The old logfile could not be moved to logs/.");
				}
			}
			
			fu.writeFile(GriefLog.file, data);
				
		}catch(IOException e){
			log.warning(e.toString());
		}
	}*/
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockFade(BlockFadeEvent event)
	{
		int x = event.getBlock().getX();
		int y = event.getBlock().getY();
		int z = event.getBlock().getZ();
		String worldName = event.getBlock().getWorld().getName();
		
		event.getNewState();
		
		String data = t.now() + "[BLOCK_FADE] Where: " + x + ", " + y + ", " + z + " To What: " + event.getNewState().toString() + " In: " + worldName + "\n";
		
		try	{
			
			//if file doesnt exists, then create it
			if(!GriefLog.file.exists()){
				GriefLog.file.createNewFile();
			}
			
			if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
			{
				// Destination directory
				File dir = new File("logs/");
				if(!dir.exists())
				{
					dir.mkdir();
				}

				// Move file to new directory
				boolean success = GriefLog.file.renameTo(new File(dir, (GriefLog.file.getName()+t.now())));
				if (!success) {
				    GriefLog.log.warning("[GriefLog] The old logfile could not be moved to logs/.");
				}
			}
			
			fu.writeFile(GriefLog.file, data);
				
		}catch(IOException e){
			log.warning(e.toString());
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onSignChange(SignChangeEvent event)
	{
		Block b = event.getBlock();
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		String playerName = event.getPlayer().getName();
		String worldName = event.getBlock().getWorld().getName();
		String newTxt = event.getLine(0);
		
		String data = t.now() + " [SIGN_CHANGE] By: " + playerName + " NewTxt: " + newTxt + " Where: " + x + ", " +  y + ", " + z + " In: " + worldName + "\n";
	
		try	{
			
			//if file doesnt exists, then create it
			if(!GriefLog.file.exists()){
				GriefLog.file.createNewFile();
			}
			
			if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
			{
				// Destination directory
				File dir = new File("logs/");
				if(!dir.exists())
				{
					dir.mkdir();
				}

				// Move file to new directory
				boolean success = GriefLog.file.renameTo(new File(dir, (GriefLog.file.getName()+t.now())));
				if (!success) {
				    GriefLog.log.warning("[GriefLog] The old logfile could not be moved to logs/.");
				}
			}
			
			fu.writeFile(GriefLog.file, data);
				
		}catch(IOException e){
			log.warning(e.toString());
		}
	}
}
