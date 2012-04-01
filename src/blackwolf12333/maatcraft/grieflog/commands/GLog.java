package blackwolf12333.maatcraft.grieflog.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import blackwolf12333.maatcraft.grieflog.GriefLog;
import blackwolf12333.maatcraft.grieflog.utils.*;

public class GLog implements CommandExecutor {

	public GriefLog gl;
	FileUtils st = new FileUtils();
	
	public GLog(GriefLog plugin) {
		gl = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel,
			String[] args) {
		
		Player p = (Player) sender;
		
		// inside this if statement all the magic happens
		if(cmd.getName().equalsIgnoreCase("glog"))
		{
			try {
				if (!(sender instanceof Player)) return true;
				
				if(args.length == 4) {
					if(args[0].equalsIgnoreCase("get"))
					{
						String x = args[1];
						String y = args[2];
						String z = args[3];
					
						sender.sendMessage(st.searchText(x+", "+y+", "+z, GriefLog.file.getAbsolutePath(), p));
						return true;
					}
				}
			
				if(args.length == 2)
				{
					if(args[0].equalsIgnoreCase("get"))
					{
						if(args[1].equalsIgnoreCase("here"))
						{
							String pos = p.getLocation().getBlockX() + ", " + p.getLocation().getBlockY() + ", " + p.getLocation().getBlockZ();
							
							sender.sendMessage(st.searchText(pos, GriefLog.file.getAbsolutePath(), p));
							return true;
						}
					}
				}
			} catch(CommandException e) {
				GriefLog.log.warning(e.getMessage());
			}
		}
		return false;
	}
}