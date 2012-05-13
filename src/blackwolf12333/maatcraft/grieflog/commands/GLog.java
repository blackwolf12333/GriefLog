package blackwolf12333.maatcraft.grieflog.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import blackwolf12333.maatcraft.grieflog.GriefLog;
import blackwolf12333.maatcraft.grieflog.utils.*;

public class GLog implements CommandExecutor {

	public GriefLog gl;
	FileUtils fu = new FileUtils();
	Time t = new Time();
	
	public String[] helpTxt = {"+++++++++++++ [GriefLog] ++++++++++++++++",
							"Commands:",
							"/glog: This gets the current version of GriefLog.",
							"/glog get here: This gets the events from the block you are currently standing in.",
							"/glog get x y z: Here you have to fill in the x y and z coordinates yourself.",
							"/gpos: Gets your current position.",
							"/report here: Not functioning yet.",
							"/report x y z: Fill in the x y and z coordinates yourself, this will report the block you point to using the coordinates, it will tell the admins you reported a griefer so they can look at it.",
							"/gltool: Gives you the grieflog tool with what you can check who griefed something."};
	
	public GLog(GriefLog plugin) {
		gl = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel,
			String[] args) {
		
		// inside this if statement all the magic happens
		if(cmd.getName().equalsIgnoreCase("glog"))
		{
			try {
				
				if(args.length == 4) {
					if(args[0].equalsIgnoreCase("get"))
					{
						// checks if the sender is a player, if not, return false
						if(!(sender instanceof Player))
						{
							return false;
						}
						
						String x = args[1];
						String y = args[2];
						String z = args[3];
						
						File file = new File("logs/");
						String[] list = file.list();
						if(list == null)
						{
							fu.searchText(x+", "+y+", "+z, GriefLog.file, (Player)sender);
							return true;
						}
						for(int i = 0; i < list.length; i++)
						{
							if(fu.searchText(x + ", " + y + ", " + z, new File("logs" + File.separator + list[i]), (Player)sender))
							{
								break;
							}
						}
						
						return true;
					}
				}
			
				if(args.length == 2)
				{
					// get command
					if(args[0].equalsIgnoreCase("get"))
					{
						// check the second argument
						if(args[1].equalsIgnoreCase("here"))
						{
							// checks if the sender is a player, if not, return false
							if(!(sender instanceof Player))
							{
								return false;
							}
							Player p = (Player)sender;
							
							String pos = p.getLocation().getBlockX() + ", " + p.getLocation().getBlockY() + ", " + p.getLocation().getBlockZ();
							
							File file = new File("logs/");
							String[] list = file.list();
							if(list == null)
							{
								fu.searchText(pos, GriefLog.file, p);
								return true;
							}
							for(int i = 0; i < list.length; i++)
							{
								if(fu.searchText(pos, new File("logs" + File.separator + list[i]), p))
								{
									break;
								}
							}
							
							return true;
						}
					}
				}
				
				if(args.length == 0)
				{
					sender.sendMessage("[GriefLog] " + gl.version);
					return true;
				}
				
				if(args.length == 1)
				{
					if(args[0].equalsIgnoreCase("help"))
					{
						
						sender.sendMessage(helpTxt);
						return true;
					}
				}
			} catch(CommandException e) {
				GriefLog.log.warning(e.getMessage());
			}
		}
		return false;
	}
}