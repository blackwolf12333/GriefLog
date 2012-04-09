package blackwolf12333.maatcraft.grieflog.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import blackwolf12333.maatcraft.grieflog.GriefLog;
import blackwolf12333.maatcraft.grieflog.utils.FileUtils;
import blackwolf12333.maatcraft.grieflog.utils.Time;

import java.io.IOException;
import java.util.logging.Logger;

public class GLBlockListener implements Listener{

	Logger log = Logger.getLogger("Minecraft");
	FileUtils fu = new FileUtils();
	Time t = new Time();
	
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
			
			fu.writeFile(GriefLog.file, data);
				
		}catch(IOException e){
			log.warning(e.toString());
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		//TODO: Log it...
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockFade(BlockFadeEvent event)
	{
		//TODO: Log it...
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onSignChange(SignChangeEvent event)
	{
		//TODO: Log it...
	}
}
