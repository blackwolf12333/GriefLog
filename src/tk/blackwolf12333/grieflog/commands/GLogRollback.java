package tk.blackwolf12333.grieflog.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.GriefLog;
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
							GLPlayer player = GriefLog.players.get(sender.getName());
							
							if(player.isDoingRollback) {
								player.getPlayer().sendMessage(ChatColor.YELLOW + "[GriefLog] You are already doing a rollback, you can't have multiple rollbacks at the time.");
								return true;
							} else {
								for(int i = 0; i < args.length; i++) {
									if(i >= 2) {
										arguments.add(args[i]);
									}
								}
								ArgumentParser parser = new ArgumentParser(arguments);
								player.search(true, parser.getResult());
								
								player.rollback();
								
								return true;
							}
						} else {
							sender.sendMessage(ChatColor.DARK_RED + "You can't have a worldedit selection so you can't use this command!");
							return true;
						}
					} else {
						GLPlayer player = GriefLog.players.get(sender.getName());
						if(player.isDoingRollback) {
							player.getPlayer().sendMessage(ChatColor.YELLOW + "[GriefLog] You are already doing a rollback, you can't have multiple rollbacks at the time.");
							return true;
						} else {
							ArgumentParser parser = new ArgumentParser(args);
							player.search(false, parser.getResult());
							
							player.rollback();
							return true;
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