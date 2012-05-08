package blackwolf12333.maatcraft.grieflog.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import blackwolf12333.maatcraft.grieflog.GriefLog;
import blackwolf12333.maatcraft.grieflog.utils.FileUtils;

public class GLGetIp implements CommandExecutor {

	FileUtils fu = new FileUtils();
	GriefLog plugin;
	
	public GLGetIp(GriefLog instance) {
		plugin = instance;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel,	String[] args) {
		if(cmd.getName() == "getip")
		{
			String out = fu.searchText( "[PLAYER_LOGIN] " + args[0], GriefLog.file);
			GriefLog.log.info(out);
			return true;
		}
		return false;
	}

}
