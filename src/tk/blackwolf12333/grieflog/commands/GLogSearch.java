package tk.blackwolf12333.grieflog.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.SearchTask;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.utils.ArgumentParser;
import tk.blackwolf12333.grieflog.utils.Events;

public class GLogSearch {

	GriefLog plugin;
	
	public GLogSearch(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("glog")) {
			if(GriefLog.permission.has(sender, "grieflog.search")) {
				GLPlayer player = GLPlayer.getGLPlayer(sender);
				
				ArgumentParser parser = new ArgumentParser(args);
				
				new SearchTask(player, new SearchCallback(player), parser.getResult());
				return true;
			}
		}
		return false;
	}
	
	public String getEventFromAlias(String alias) {
		for(Events event : Events.values()) {
			for(String a : event.getAlias()) {
				if(alias.equalsIgnoreCase(a)) {
					return event.getEventName();
				}
			}
		}
		return null;
	}

}
