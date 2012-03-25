package blackwolf12333.maatcraft.grieflog.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import blackwolf12333.maatcraft.grieflog.GriefLog;

public class GPos implements CommandExecutor {

	public GriefLog gl;
	
	public GPos(GriefLog plugin) {
		gl = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("gpos"))
		{
			if (!(sender instanceof Player)) return true;
			
			Player p = (Player) sender;
			double x = p.getLocation().getBlockX();
			double y = p.getLocation().getBlockY();
			double z = p.getLocation().getBlockZ();
			
			p.sendMessage(x + ", " + y + ", " + z);
			return true;
		}
		
		return false;
	}

}
