package tk.blackwolf12333.grieflog.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.RegionRollbackCallback;
import tk.blackwolf12333.grieflog.callback.RollbackCallback;
import tk.blackwolf12333.grieflog.callback.WorldEditFilterCallback;
import tk.blackwolf12333.grieflog.conversations.RollbackConversation;
import tk.blackwolf12333.grieflog.utils.searching.ArgumentParser;
import tk.blackwolf12333.grieflog.utils.searching.SearchTask;

public class GLogRollback {
	
	GriefLog plugin;

	private String noPermsMsg = ChatColor.DARK_RED + "I am sorry Dave, but i cannot let you do that! You don't have permission.";
	
	public GLogRollback(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(PlayerSession player, String[] args) {
		if (player.hasPermission("grieflog.rollback")) {
			if(args.length == 1) {
				if(player.isDoingRollback()) {
					player.getPlayer().sendMessage(ChatColor.YELLOW + "[GriefLog] You are already doing a rollback, you can't have multiple rollbacks at the time.");
					return true;
				} else { 
					new RollbackConversation(plugin, player);
				}
				return true;
			} else if(args[1].equalsIgnoreCase("we")) {
				if(args.length == 2) {
					if(player.isDoingRollback()) {
						player.getPlayer().sendMessage(ChatColor.YELLOW + "[GriefLog] You are already doing a rollback, you can't have multiple rollbacks at the time.");
						return true;
					} else { 
						new RollbackConversation(plugin, player);
					}
					return true;
				}
				if(player.getPlayer() != null) {
					ArrayList<String> arguments = new ArrayList<String>();
					
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
						
						if(parser.argsNullError) {
							player.print(ChatColor.DARK_RED + "Sorry, an error occured. Please check if you formatted the arguments right.");
							return true;
						} else if(parser.eventNullError) { 
							player.print(ChatColor.DARK_RED + "You misspelled the event, try again.");
							return true;
						} else {
							String parsedArgs = "we:" + parser.event + ":" + parser.player + ":" + parser.world;
							GriefLog.undoConfig.add(parsedArgs);
							new SearchTask(player, new WorldEditFilterCallback(player, new RegionRollbackCallback(player)), parser);
						}
						
						return true;
					}
				} else {
					player.print(ChatColor.DARK_RED + "You can't have a worldedit selection so you can't use this events_command!");
					return true;
				}
			} else {
				if(player.isDoingRollback()) {
					player.print(ChatColor.YELLOW + "[GriefLog] You are already doing a rollback, you can't have multiple rollbacks at the time.");
					return true;
				} else {
					ArgumentParser parser = new ArgumentParser(args);
					if(parser.argsNullError) {
						player.print(ChatColor.DARK_RED + "Sorry, an error occured. Please check if you formatted the arguments right.");
					} else if(parser.eventNullError) { 
						player.print(ChatColor.DARK_RED + "You misspelled the event, try again.");
					} else {
						String parsedArgs = parser.event + ":" + parser.player + ":" + parser.world;
						GriefLog.undoConfig.add(parsedArgs);
						new SearchTask(player, new RollbackCallback(player), parser);
					}
					
					return true;
				}
			}
		} else {
			player.print(noPermsMsg);
			return true;
		}
	}
		
}