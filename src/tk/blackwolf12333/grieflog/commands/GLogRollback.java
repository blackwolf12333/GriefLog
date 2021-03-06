package tk.blackwolf12333.grieflog.commands;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.utils.searching.SearchTask;
import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.conversations.SearchConversation;
import tk.blackwolf12333.grieflog.utils.searching.ArgumentParser;
import tk.blackwolf12333.grieflog.utils.GriefLogException;

public class GLogRollback {
	
	GriefLog plugin;
	private String noPermsMsg = ChatColor.DARK_RED + "I am sorry, You do not have permission to run this command.";
	
	public GLogRollback(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(PlayerSession player, String[] args) {
		if(GriefLog.enableRollback) {
			if (player.hasPermission("grieflog.rollback")) {
				if(player.isDoingRollback()) {
					player.getPlayer().sendMessage(ChatColor.YELLOW + "[GriefLog] You are already doing a rollback, you can't have multiple rollbacks at the time.");
					return true;
				}
				if(args.length == 1) {
					return useConversations(player, false);
				} else if((args[1].equalsIgnoreCase("we")) && (args.length == 2) && (player.getPlayer() != null)) {
					return useConversations(player, true);
				} else {
					try {
						ArgumentParser parser = new ArgumentParser(args);
						addParserResultsToUndoConfig(parser);
						new SearchTask(player, new SearchCallback(player, SearchCallback.Type.ROLLBACK), parser);
					} catch(GriefLogException e) {
						player.print(ChatColor.RED + "[GriefLog] An error occured parsing your command, please check it for any mistakes.");
						return true;
					}
					
					return true;
				}
			} else {
				player.print(noPermsMsg);
				return true;
			}
		} else {
			player.print(ChatColor.DARK_RED + "Rollbacks are disabled because your CraftBukkit is not compatible!");
			return true;
		}
	}
	
	private boolean useConversations(PlayerSession player, boolean worldedit) {
		new SearchConversation(plugin, player, true, worldedit);
		return true;
	}
	
	private void addParserResultsToUndoConfig(ArgumentParser parser) {
		GriefLog.undoSerializer.getArguments().add(parser);
	}
}