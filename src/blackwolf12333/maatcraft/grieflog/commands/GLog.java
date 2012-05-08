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
	
	public String helpTxt = "+++++++++++++ [GriefLog] ++++++++++++++++\n" +
							"Commands: \n" +
							"/glog: This gets the current version of GriefLog.\n" +
							"/glog get here: This gets the events from the block you are currently standing in.\n" +
							"/glog get x y z: Here you have to fill in the x y and z coordinates yourself.\n" +
							"/gpos: Gets your current position.\n" +
							"/report here: Not functioning yet.\n" +
							"/report x y z: Fill in the x y and z coordinates yourself, this will report the block you point to using the coordinates, it will tell the admins you reported a griefer so they can look at it.\n" +
							"/gltool: This function isn't working well either.\n";
	
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
						String x = args[1];
						String y = args[2];
						String z = args[3];
						
						File file = new File("logs/");
						String[] list = file.list();
						for(int i = 0; i < list.length; i++)
						{
							fu.searchText(x+", "+y+", "+z, new File("logs" + File.separator + list[i]), (Player)sender);
							fu.searchText(x+", "+y+", "+z, GriefLog.file, (Player)sender);
						}
						
						return true;
					}
				}
			
				if(args.length == 2)
				{
					// get command
					if(args[0].equalsIgnoreCase("get"))
					{
						if(args[1].equalsIgnoreCase("here"))
						{
							Player p = (Player)sender;
							
							String pos = p.getLocation().getBlockX() + ", " + p.getLocation().getBlockY() + ", " + p.getLocation().getBlockZ();
							
							File file = new File("logs/");
							String[] list = file.list();
							for(int i = 0; i < list.length; i++)
							{
								fu.searchText(pos, new File("logs" + File.separator + list[i]), p);
								fu.searchText(pos, GriefLog.file, p);
							}
							
							return true;
						}
					}
				}
				
				if(args.length == 0)
				{
					sender.sendMessage("[GriefLog] " + gl.version);
				}
				
				if(args.length == 1)
				{
					if(args[0].equalsIgnoreCase("help"))
					{
						sender.sendMessage(helpTxt);
					}
				}
			} catch(CommandException e) {
				GriefLog.log.warning(e.getMessage());
			}
		}
		return false;
	}
}