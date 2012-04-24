package blackwolf12333.maatcraft.grieflog.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
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
		
		try {
		
			// check if the player issuing the command is a Op
			if(!sender.isOp())
				{
				sender.sendMessage("You cannot use this command because you aren't Op");
				return true;
			}
		
		
			// in this if statement all the magic happens
			if(cmd.getName().equalsIgnoreCase("delreports"))
			{
				GriefLog.reportFile.delete();
				sender.sendMessage("Report file is deleted");
				return true;
			}
		
		} catch (CommandException e) {
			GriefLog.log.warning(e.getMessage());
		}
		return false;
	}

}
