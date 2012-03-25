package blackwolf12333.maatcraft.grieflog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

public class GLPlayerListener implements Listener{
	
	Logger log = Logger.getLogger("Minecraft");
	
	public static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";

	public static String now() {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(cal.getTime());

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
			String data = now() + " [GAMEMODE_CHANGE] " + p + " New Gamemode: " + gameM + " Where: " + playerWorld + "\n";
 
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
		Player player = event.getPlayer();
		String playerName = player.getName();
		World where = event.getFrom();
		String from = where.getName();
		
		try{
			String data = now() + " [WORLD_CHANGE] Who: " + playerName + " From: " + from + "\n";
 
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
			
			String data = now() + " [PLAYER_COMMAND] Who: " + namePlayer + " Command: " + cmd + "\n";

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
		String name = event.getPlayer().getName();
		if(name.equalsIgnoreCase("blackwolf12333") || name.equalsIgnoreCase("knightmareDM") || name.equalsIgnoreCase("Luuk_The_Buunk"))
		{
			if(GriefLog.reportFile.exists())
			{
				event.getPlayer().sendMessage("There Are New Player Reports!\n");
				event.getPlayer().sendMessage("Type /rdreport to read the reports");
			}
			else
			{
				return;
			}
		}
	}
}
