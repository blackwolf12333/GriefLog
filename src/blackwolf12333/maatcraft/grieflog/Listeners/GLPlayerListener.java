package blackwolf12333.maatcraft.grieflog.Listeners;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Logger;

import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import blackwolf12333.maatcraft.grieflog.GriefLog;
import blackwolf12333.maatcraft.grieflog.utils.FileUtils;
import blackwolf12333.maatcraft.grieflog.utils.Time;

public class GLPlayerListener implements Listener{
	
	Logger log = Logger.getLogger("Minecraft");
	GriefLog gl;
	Time t = new Time();
	FileUtils fu = new FileUtils();

	String playerName;
	int address;
	
	public GLPlayerListener(GriefLog plugin) {
		gl = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
		
		Player player = event.getPlayer();
		String p = player.getName();
		GameMode gm = event.getNewGameMode();
		int gameM = gm.getValue();
		World world = player.getWorld();
		String playerWorld = world.getName();		
		
		try{
			String data = t.now() + " [GAMEMODE_CHANGE] " + p + " New Gamemode: " + gameM + " Where: " + playerWorld + "\n";
 
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
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		
		Player player = event.getPlayer();
		String playerName = player.getName();
		World where = event.getFrom();
		String from = where.getName();
		
		try{
			String data = t.now() + " [WORLD_CHANGE] Who: " + playerName + " From: " + from + "\n";
 
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
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		
		String cmd = event.getMessage();
		String namePlayer = event.getPlayer().getName();
		
		try{
			
			String data = t.now() + " [PLAYER_COMMAND] Who: " + namePlayer + " Command: " + cmd + "\n";

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
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		Player p = event.getPlayer();
		if(p.isOp())
		{
			if(GriefLog.reportFile.exists())
			{
				event.getPlayer().sendMessage("There Are New Player Reports!\n");
				event.getPlayer().sendMessage("Type /rdreports to read the reports");
			}
			else
			{
				return;
			}
		}
		
		InetAddress address = event.getPlayer().getAddress().getAddress();
		int gm = event.getPlayer().getGameMode().getValue();
		String name = event.getPlayer().getName();
		String worldName = event.getPlayer().getWorld().getName();
		
		try {
			String data = t.now() + " " + name + " On: " + address.getHostAddress() + " With GameMode: " + gm + " In: " + worldName + "\n";
			
			//if file doesnt exists, then create it
    		if(!GriefLog.file.exists()){
    			GriefLog.file.createNewFile();
    		}
    		
    		if(fu.getFileSize(GriefLog.file) >= gl.getConfig().getInt("mb"))
			{
				autoBackup();
			}
 
    		fu.writeFile(GriefLog.file, data);
    		
		}catch(IOException e) {
			log.warning(e.toString());
    	}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Action a = event.getAction();
		Player p = event.getPlayer();		
		if(a == Action.LEFT_CLICK_BLOCK)
		{
			 if(p.getItemInHand().getTypeId() == gl.getConfig().getInt("SelectionTool"))
			 {
				 Block b = event.getClickedBlock();
				 
				 int x = b.getX();
				 int y = b.getY();
				 int z = b.getZ();
				 
				 fu.searchText(x + ", " + y + ", " + z, GriefLog.file, p);
				 event.setCancelled(true);
			 }
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		Player player = event.getPlayer();
		World world = player.getWorld();
		Chunk chunk = event.getTo().getChunk();
		boolean success = world.refreshChunk(chunk.getX(), chunk.getZ());
		while(!success)
		{
			world.refreshChunk(chunk.getX(), chunk.getZ());
			if(!world.isChunkLoaded(chunk))
				success = false;
			event.setCancelled(true);
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
