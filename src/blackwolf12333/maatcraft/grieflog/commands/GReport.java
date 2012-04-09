package blackwolf12333.maatcraft.grieflog.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import blackwolf12333.maatcraft.grieflog.GriefLog;
import blackwolf12333.maatcraft.grieflog.utils.*;

public class GReport implements CommandExecutor {

	public GriefLog gl;
	FileUtils fu = new FileUtils();
	
	public GReport(GriefLog plugin) {
		gl = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		// inside this if statement all the magic happens
		if(cmd.getName().equalsIgnoreCase("report"))
		{
			BufferedWriter writer;
			
			if(args.length == 1)
			{
				if(args[0] == "here")
				{
					Player p = (Player) sender;					
					
					// get the x, y, z coordinates
					int x = p.getLocation().getBlockX();
					int y = p.getLocation().getBlockY();
					int z = p.getLocation().getBlockZ();
					
					String result = fu.searchText(x + ", " + y + ", " + z, GriefLog.file.getAbsolutePath());
					StringBuffer resultsb = new StringBuffer();
					resultsb.append(result);
					
					fu.writeFile(GriefLog.reportFile, resultsb.toString(), false);
					fu.writeFile(GriefLog.reportFile, "", true);
					fu.writeFile(GriefLog.reportFile, "Reported by: " + p.getName());
					fu.writeFile(GriefLog.reportFile, "", true);
					
					return false;
				}
			}
			
			if(args.length == 3)
			{
				Player p = (Player) sender;
				
				String x = args[0];
				String y = args[1];
				String z = args[2];
				
				String result = fu.searchText(x + ", " + y + ", " + z, GriefLog.file.getAbsolutePath());
				
				try {
					writer = new BufferedWriter(new FileWriter(GriefLog.reportFile.getAbsolutePath(),true));
					fu.writeFile(GriefLog.reportFile, result);
					writer.newLine();
					writer.write("Reported by: " + p.getName());
					writer.newLine();
					writer.close();
				} catch (IOException e) {
					GriefLog.log.warning(e.getMessage());
				}
				return false;
			}
		}
		
		return false;
	}
}
