package blackwolf12333.maatcraft.grieflog.commands;

import java.io.BufferedReader;
import java.io.FileReader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import blackwolf12333.maatcraft.grieflog.GriefLog;

public class GRDReport implements CommandExecutor {

	GriefLog gl;
	
	public GRDReport(GriefLog plugin) {
		gl = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		Player p = (Player) sender;
		
		// inside this if statement all the magic happens
		if(cmd.getName().equalsIgnoreCase("rdreports"))
		{
			// check if the player issuing the command is a Op
			if(!p.isOp())
			{
				p.sendMessage("You cannot use this command because you aren't Op");
				return false;
			}
			
			try {
				BufferedReader br = new BufferedReader(new FileReader(GriefLog.reportFile.getAbsoluteFile()));
				String line = null;
				
				while((line = br.readLine()) != null)
				{
					p.sendMessage(line);
				}
				return false;
			} catch (Exception e) {
				GriefLog.log.warning("File Not Found Exception, the file: " + GriefLog.reportFile.getName() + " could not be found.");			
			}
		}
		
		return false;
	}

}
