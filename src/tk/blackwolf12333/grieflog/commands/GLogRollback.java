package tk.blackwolf12333.grieflog.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.rollback.RollbackWE;
import tk.blackwolf12333.grieflog.utils.ArgumentParser;

public class GLogRollback {
	
	GriefLog plugin;

	private String noPermsMsg = ChatColor.DARK_RED + "I am sorry, but i cannot let you do that! You don't have permission.";
	
	public GLogRollback(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("glog")) {
			// /glog rollback <options>
			if (args[0].equalsIgnoreCase("rollback") || args[0].equalsIgnoreCase("rb")) {
				if (sender.isOp() || GriefLog.permission.has(sender, "grieflog.rollback")) {
					if(args[1].equalsIgnoreCase("we")) {
						if(sender instanceof Player) {
							ArrayList<String> arguments = new ArrayList<String>();
							
							for(int i = 0; i < args.length; i++) {
								if(i >= 2) {
									arguments.add(args[i]);
								}
							}
							
							ArgumentParser parser = new ArgumentParser(arguments);
							
							RollbackWE rb = new RollbackWE(plugin, sender, parser.getResult());
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, rb);
							return true;
						} else {
							sender.sendMessage(ChatColor.DARK_RED + "You can't have a worldedit selection so you can't use this command!");
							return true;
						}
					} else {
						if(args.length == 2) {
							ArgumentParser parser = new ArgumentParser(args);
							
							Rollback rb = new Rollback(plugin, sender, parser.getResult());
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, rb);
						} else if(args.length == 3) {
							ArgumentParser parser = new ArgumentParser(args);
							
							Rollback rb = new Rollback(plugin, sender, parser.getResult());
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, rb);
							
							
							sender.sendMessage("Tried to rollback the grief!");
							return true;
						} else if(args.length == 4) {
							ArgumentParser parser = new ArgumentParser(args);
							
							Rollback rb = new Rollback(plugin, sender, parser.getResult());
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, rb);
						}
					}
				} else {
					sender.sendMessage(noPermsMsg);
					return true;
				}
			}
		}
		
		return false;
	}
}