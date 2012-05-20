package tk.blackwolf12333.grieflog.Listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
//import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.utils.FileUtils;
import tk.blackwolf12333.grieflog.utils.Time;


import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class GLBlockListener implements Listener {

	Logger log = Logger.getLogger("Minecraft");
	GriefLog gl;
	FileUtils fu = new FileUtils();
	Time t = new Time();
	public String data; // dunno anymore why i added this, but i use it, so i don't delete it
	
	public GLBlockListener(GriefLog plugin) {
		gl = plugin;
	}
	
	 // dunno anymore why i added this, but i use it, so i don't delete it
	public String getData() {
		return data;
	}
	
	 // dunno anymore why i added this, but i use it, so i don't delete it
	public void setData(String data) {
		this.data = data;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		
		Integer blockX = event.getBlock().getLocation().getBlockX();
		Integer blockY = event.getBlock().getLocation().getBlockY();
		Integer blockZ = event.getBlock().getLocation().getBlockZ();
		Player player = event.getPlayer();
		String type = event.getBlock().getType().toString();
		String namePlayer = player.getName();
		String worldName = player.getWorld().getName();
		
		String data = t.now() + " [BLOCK_BREAK] " + " By: " + namePlayer + " What: " + type + " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + System.getProperty("line.separator");
				
		try{
			//if file doesnt exists, then create it
			if(!GriefLog.file.exists()){
				GriefLog.file.createNewFile();
			}
			
			// if the file has reached the max size, set in the config back it up
			if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
			{
				autoBackup();
			}
			
			// log it
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
		if(type == "FIRE")
		{
			// this gets handled by the onBlockIgnite() function
			return;
		}
		
		// if the item in the players hand == the selection tool search the logs with it
		if(player.getItemInHand().getTypeId() == gl.getConfig().getInt("SelectionTool"))
		{
			Block b = event.getBlock();
		
			int x = b.getX();
			int y = b.getY();
			int z = b.getZ();
			
			event.setCancelled(true);
			
			File file = new File("logs/");
			String[] list = file.list();
			if(!list[0].isEmpty())
			{
				fu.searchText(x + ", " + y + ", " + z, GriefLog.file, player);
				event.setCancelled(true);
				return;
			}
			else
			{
				for(int i = 0; i < list.length; i++)
				{
					if(fu.searchText(x + ", " + y + ", " + z, new File("logs" + File.separator + list[i]), player))
					{
						return;
					}
				}
			}
		}
		else // else log the event
		{
			
			String data = t.now() + " [BLOCK_PLACE] " + "Who: " + namePlayer + " What: " + type +  " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + System.getProperty("line.separator");
			setData(data);
			
			try{ 
				//if file doesnt exists, then create it
				if(!GriefLog.file.exists()){
					GriefLog.file.createNewFile();
				}
				
				// if the file has reached the max size, set in the config back it up
				if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
				{
					autoBackup();
				}
				
				// log it
				fu.writeFile(GriefLog.file, data);
				
			}catch(IOException e){
				log.warning(e.toString());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockIgnite(BlockIgniteEvent event) throws EventException
	{
		String data = "";
		Player player = event.getPlayer();
		// check if it was the environment that ignited the block
		if(player == null)
		{
			IgniteCause ic = event.getCause();
			String worldName = event.getBlock().getWorld().getName();
			int x = event.getBlock().getX();
			int y = event.getBlock().getY();
			int z = event.getBlock().getZ();
			data = t.now() + " [BLOCK_IGNITE] By: Environment" + " How: " + ic.toString() + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
			setData(data);
		}
		else // it is a player
		{
			IgniteCause ic = event.getCause();
			String playerName = player.getName();
			String worldName = event.getBlock().getWorld().getName();
			int x = event.getBlock().getX();
			int y = event.getBlock().getY();
			int z = event.getBlock().getZ();
			
			data = t.now() + " [BLOCK_IGNITE] By: " + playerName + " How: " + ic.toString() + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
			setData(data);
		}
		try	{
			
			//if file doesnt exists, then create it
			if(!GriefLog.file.exists()){
				GriefLog.file.createNewFile();
			}
			
			// if the file has reached the max size, set in the config back it up
			if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
			{
				autoBackup();
			}
			
			// log it
			fu.writeFile(GriefLog.file, data);
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onSignChange(SignChangeEvent event)
	{
		Block b = event.getBlock();
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		String playerName = event.getPlayer().getName();
		String worldName = event.getBlock().getWorld().getName();
		String newTxt = event.getLine(0);
		
		String data = t.now() + " [SIGN_CHANGE] By: " + playerName + " NewTxt: " + newTxt + " Where: " + x + ", " +  y + ", " + z + " In: " + worldName + System.getProperty("line.separator");
	
		try	{
			
			//if file doesnt exists, then create it
			if(!GriefLog.file.exists()){
				GriefLog.file.createNewFile();
			}
			
			// if the file has reached the max size, set in the config back it up
			if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
			{
				autoBackup();
			}
			
			// log it
			fu.writeFile(GriefLog.file, data);
				
		}catch(IOException e){
			log.warning(e.toString());
		}
	}
	
	// gonna implement this event later, not now
	/*@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPiston(BlockPistonEvent event)
	{
		int x = event.getBlock().getX();
		int y = event.getBlock().getY();
		int z = event.getBlock().getZ();
		String world = event.getBlock().getWorld().getName();
		
		data = t.now() + " [BLOCK_PISTON] Where: " + x + ", " + y + ", " + z + " In: " + world;
		
		try	{
			
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
	}*/
	
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
