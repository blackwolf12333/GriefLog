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
	FileUtils st = new FileUtils();
	
	public GReport(GriefLog plugin) {
		gl = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		// inside this if statement all the magic happens
		if(cmd.getName().equalsIgnoreCase("report"))
		{
			BufferedWriter writer;
			
			if(args[0] == "here")
			{
				Player p = (Player) sender;
				
				int x = p.getLocation().getBlockX();
				int y = p.getLocation().getBlockY();
				int z = p.getLocation().getBlockZ();
				
				st.searchText(x + ", " + y + ", " + z);
				
				try {
					writer = new BufferedWriter(new FileWriter(GriefLog.reportFile.getAbsolutePath(),true));
					writer.write(FileUtils.foundLine);
					writer.newLine();
					writer.write("Reported by: " + p.getName());
					writer.newLine();
					writer.close();
				} catch (IOException e) {
					GriefLog.log.warning(e.getMessage());
				}
			}
			
			if(args.length == 3)
			{
				Player p = (Player) sender;
				
				String x = args[0];
				String y = args[1];
				String z = args[2];
				
				try {
					writer = new BufferedWriter(new FileWriter(GriefLog.reportFile.getAbsolutePath(),true));
					writer.write(st.searchText(x + ", " + y + ", " + z));
					writer.newLine();
					writer.write("Reported by: " + p.getName());
					writer.newLine();
					writer.close();
				} catch (IOException e) {
					GriefLog.log.warning(e.getMessage());
				}
			}
		}
		
		return false;
	}
}
