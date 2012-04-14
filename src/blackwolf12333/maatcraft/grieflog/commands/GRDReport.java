package blackwolf12333.maatcraft.grieflog.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import blackwolf12333.maatcraft.grieflog.GriefLog;
import blackwolf12333.maatcraft.grieflog.utils.FileUtils;

public class GRDReport implements CommandExecutor {

	GriefLog gl;
	FileUtils fu = new FileUtils();
	
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
				return true;
			}
			
			try {
				p.sendMessage(fu.readFile(GriefLog.reportFile));
				return true;
			} catch (Exception e) {
				GriefLog.log.warning("File Not Found Exception, the file: " + GriefLog.reportFile.getName() + " could not be found.");			
			}
		}
		
		return false;
	}

}
