package tk.blackwolf12333.grieflog.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.RollbackCallback;
import tk.blackwolf12333.grieflog.conversations.RollbackConversation;
import tk.blackwolf12333.grieflog.utils.filters.BlockFilter;
import tk.blackwolf12333.grieflog.utils.filters.WorldEditFilter;
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
			if(player.isDoingRollback()) {
				player.getPlayer().sendMessage(ChatColor.YELLOW + "[GriefLog] You are already doing a rollback, you can't have multiple rollbacks at the time.");
				return true;
			}
			if(args.length == 1) {
				return useConversations(player);
			} else if(args[1].equalsIgnoreCase("we")) {
				if(player.getPlayer() != null) {
					if(args.length == 2) {
						return useConversations(player);
					} else {
						ArgumentParser parser = parseWorldEditArguments(args);
						
						if(checkParserErrors(parser, player)) {
							if(parser.blockFilter != null) {
								addParserResultsToUndoConfig(parser, true);
								new SearchTask(player, new RollbackCallback(player), parser, new WorldEditFilter(player), new BlockFilter(player, parser.blockFilter));
							} else {
								addParserResultsToUndoConfig(parser, true);
								new SearchTask(player, new RollbackCallback(player), parser, new WorldEditFilter(player));
							}
						}
						return true;
					}
				} else {
					player.print(ChatColor.DARK_RED + "You can't have a worldedit selection so you can't use this command!");
					return true;
				}
			} else {
				ArgumentParser parser = new ArgumentParser(args);
				if(checkParserErrors(parser, player)) {
					if(parser.blockFilter != null) {
						addParserResultsToUndoConfig(parser, false);
						new SearchTask(player, new RollbackCallback(player), parser, new BlockFilter(player, parser.blockFilter));
					} else {
						addParserResultsToUndoConfig(parser, false);
						new SearchTask(player, new RollbackCallback(player), parser);
					}
				}
				
				return true;
			}
		} else {
			player.print(noPermsMsg);
			return true;
		}
	}
	
	private boolean useConversations(PlayerSession player) {
		new RollbackConversation(plugin, player);
		return true;
	}
	
	private ArgumentParser parseWorldEditArguments(String[] args) {
		ArrayList<String> arguments = new ArrayList<String>();
		
		for(int i = 0; i < args.length; i++) {
			if(i >= 2) {
				arguments.add(args[i]);
			}
		}
		
		return new ArgumentParser(arguments);
	}
	
	private boolean checkParserErrors(ArgumentParser parser, PlayerSession player) {
		if(parser.argsNullError) {
			player.print(ChatColor.DARK_RED + "Sorry, an error occured. Please check if you formatted the arguments right.");
			return false;
		} else if(parser.eventNullError) { 
			player.print(ChatColor.DARK_RED + "You misspelled the event, try again.");
			return false;
		} else {
			return true;
		}
	}
	
	private void addParserResultsToUndoConfig(ArgumentParser parser, boolean we) {
		String parsedArgs;
		if(we)
			parsedArgs = "we:" + parser.event + ":" + parser.player + ":" + parser.world;
		else
			parsedArgs = parser.event + ":" + parser.player + ":" + parser.world;
		GriefLog.undoConfig.add(parsedArgs);
	}
}