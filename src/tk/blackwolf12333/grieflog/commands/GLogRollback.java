package tk.blackwolf12333.grieflog.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.SearchTask;
import tk.blackwolf12333.grieflog.callback.RollbackCallback;
import tk.blackwolf12333.grieflog.callback.WorldEditFilterCallback;
import tk.blackwolf12333.grieflog.utils.ArgumentParser;

public class GLogRollback {
	
	GriefLog plugin;

	private String noPermsMsg = ChatColor.DARK_RED + "I am sorry, but i cannot let you do that! You don't have permission.";
	
	public GLogRollback(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("glog")) {
			if (args[0].equalsIgnoreCase("rollback") || args[0].equalsIgnoreCase("rb")) {
				if (sender.isOp() || GriefLog.permission.has(sender, "grieflog.rollback")) {
					if(args[1].equalsIgnoreCase("we")) {
						if(sender instanceof Player) {
							ArrayList<String> arguments = new ArrayList<String>();
							GLPlayer player = GLPlayer.getGLPlayer(sender);
							
							if(player.isDoingRollback()) {
								player.getPlayer().sendMessage(ChatColor.YELLOW + "[GriefLog] You are already doing a rollback, you can't have multiple rollbacks at the time.");
								return true;
							} else {
								for(int i = 0; i < args.length; i++) {
									if(i >= 2) {
										arguments.add(args[i]);
									}
								}
								
								ArgumentParser parser = new ArgumentParser(arguments);
								
								// check if the parser throwed any errors
								if(parser.error) {
									player.print(ChatColor.DARK_RED + "Sorry, an error occured. Please check if you formatted the arguments right.");
								} else {
									new SearchTask(player, new WorldEditFilterCallback(player), parser.getResult());
								}
								
								return true;
							}
						} else {
							sender.sendMessage(ChatColor.DARK_RED + "You can't have a worldedit selection so you can't use this command!");
							return true;
						}
					} else {
						GLPlayer player = GLPlayer.getGLPlayer(sender);
						if(player.isDoingRollback()) {
							player.print(ChatColor.YELLOW + "[GriefLog] You are already doing a rollback, you can't have multiple rollbacks at the time.");
							return true;
						} else {
							ArgumentParser parser = new ArgumentParser(args);
							// check if the parser throwed any errors
							if(parser.error) {
								player.print(ChatColor.DARK_RED + "Sorry, an error occured. Please check if you formatted the arguments right.");
							} else {
								new SearchTask(player, new RollbackCallback(player), parser.getResult());
							}
							
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