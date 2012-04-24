package blackwolf12333.maatcraft.grieflog.Listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import blackwolf12333.maatcraft.grieflog.GriefLog;
import blackwolf12333.maatcraft.grieflog.utils.FileUtils;
import blackwolf12333.maatcraft.grieflog.utils.Time;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class GLBlockListener implements Listener {

	Logger log = Logger.getLogger("Minecraft");
	GriefLog gl;
	FileUtils fu = new FileUtils();
	Time t = new Time();
	public String data;
	
	public GLBlockListener(GriefLog plugin) {
		gl = plugin;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
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
		setData(data);
		
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
			return;
		}
		
		String data = t.now() + " [BLOCK_PLACE] " + "Who: " + namePlayer + " What: " + type +  " on Pos: " + blockX.toString() + ", " + blockY.toString() + ", " + blockZ.toString() + " in: " + worldName + "\n";
		setData(data);
		
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
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockIgnite(BlockIgniteEvent event) throws EventException
	{
		//Block block = event.getBlock();
		String data = null;
		Player player = event.getPlayer();
		if(player == null)
		{
			IgniteCause ic = event.getCause();
			String worldName = event.getBlock().getWorld().getName();
			int x = event.getBlock().getX();
			int y = event.getBlock().getY();
			int z = event.getBlock().getZ();
			data = t.now() + " [BLOCK_IGNITE] By: Environment" + " How: " + ic.toString() + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + "\n";
			setData(data);
		}
		else
		{
			IgniteCause ic = event.getCause();
			String playerName = player.getName();
			String worldName = event.getBlock().getWorld().getName();
			int x = event.getBlock().getX();
			int y = event.getBlock().getY();
			int z = event.getBlock().getZ();
			
			data = t.now() + " [BLOCK_IGNITE] By: " + playerName + " How: " + ic.toString() + " Where: " + x + ", " + y + ", " + z + " In: " + worldName + "\n";
			setData(data);
		}
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
				
		} catch (IOException e) {
			e.printStackTrace();
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
				autoBackup();
			}
			
			fu.writeFile(GriefLog.file, data);
				
		}catch(IOException e){
			log.warning(e.toString());
		}
	}
	
	private void autoBackup()
	{
		File backup = new File("logs\\GriefLog" + t.Date() + ".txt");
		if(!backup.exists())
		{
			try {
				backup.createNewFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
