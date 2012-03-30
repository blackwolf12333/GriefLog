package blackwolf12333.maatcraft.grieflog.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import blackwolf12333.maatcraft.grieflog.GriefLog;

public class GDelReport implements CommandExecutor {

	GriefLog gl;
	public GDelReport(GriefLog plugin) {
		gl = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		// in this if statement all the magic happens
		if(cmd.getName().equalsIgnoreCase("delreports"))
		{
			GriefLog.reportFile.delete();
			return true;
		}
		
		return false;
	}

}
