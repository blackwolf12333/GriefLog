package blackwolf12333.maatcraft.grieflog.Listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import blackwolf12333.maatcraft.grieflog.GriefLog;
import blackwolf12333.maatcraft.grieflog.utils.FileUtils;
import blackwolf12333.maatcraft.grieflog.utils.Time;

public class GLPlayerListener implements Listener{
	
	Logger log = Logger.getLogger("Minecraft");
	Time t = new Time();
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
		
		// check if the logfile is to large
		if(FileUtils.getFileSize(GriefLog.reportFile) > 10)
		{
			// Destination directory
			File dir = new File("logs/");
			
			dir.mkdir();
			// Move file to new directory
			boolean success = GriefLog.file.renameTo(new File(dir, GriefLog.file.getName()+t.Date()));
			if (!success) {
				log.warning("Failed to move the old logfile to the logs directory!");
			}
		}
		
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
 
    		//true = append file
    		FileWriter fileWritter = new FileWriter(GriefLog.file.getName(),true);
    		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    		bufferWritter.write(data);
    		bufferWritter.close();

		}catch(IOException e){
			log.warning(e.toString());
    	}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		
		// check if the logfile is to large
		if(FileUtils.getFileSize(GriefLog.reportFile) > 10)
		{
			// Destination directory
			File dir = new File("logs/");
			
			dir.mkdir();
			// Move file to new directory
			boolean success = GriefLog.file.renameTo(new File(dir, GriefLog.file.getName()+t.Date()));
			if (!success) {
				log.warning("Failed to move the old logfile to the logs directory!");
			}
		}
		
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
 
    		//true = append file
    		FileWriter fileWritter = new FileWriter(GriefLog.file.getName(),true);
    		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    		bufferWritter.write(data);
    		bufferWritter.close();

		}catch(IOException e){
			log.warning(e.toString());
    	}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		
		// check if the logfile is to large
		if(FileUtils.getFileSize(GriefLog.reportFile) > 10)
		{
			// Destination directory
			File dir = new File("logs/");
			
			dir.mkdir();
			// Move file to new directory
			boolean success = GriefLog.file.renameTo(new File(dir, GriefLog.file.getName()+t.Date()));
			if (!success) {
				log.warning("Failed to move the old logfile to the logs directory!");
			}
		}
		
		String cmd = event.getMessage();
		String namePlayer = event.getPlayer().getName();
		
		if(cmd.startsWith("/login"))
		{
			cmd = "LOGIN: not showed";
		}
		else if(cmd.startsWith("/register"))
		{
			cmd = "REGISTER: password not showed";
		}
		
		try{
			
			String data = t.now() + " [PLAYER_COMMAND] Who: " + namePlayer + " Command: " + cmd + "\n";

    		//if file doesnt exists, then create it
    		if(!GriefLog.file.exists()){
    			GriefLog.file.createNewFile();
    		}
 
    		//true = append file
    		FileWriter fileWritter = new FileWriter(GriefLog.file.getName(),true);
    		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    		bufferWritter.write(data);
    		bufferWritter.close();

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
	}
}
