package tk.blackwolf12333.grieflog.commands;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
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
		//if(GriefLog.enableRollback) {
			if (player.hasPermission("grieflog.rollback")) {
				if(player.isDoingRollback()) {
					player.getPlayer().sendMessage(ChatColor.YELLOW + "[GriefLog] You are already doing a rollback, you can't have multiple rollbacks at the time.");
					return true;
				}
				if(args.length == 1) {
					return useConversations(player);
				} else if((args[1].equalsIgnoreCase("we")) && (args.length == 2) && (player.getPlayer() != null)) {
					return useConversations(player);
				} else {
					ArgumentParser parser = new ArgumentParser(args);
					if(checkParserErrors(parser, player)) {
						addParserResultsToUndoConfig(parser);
						new SearchTask(player, new SearchCallback(player, SearchCallback.Type.ROLLBACK), parser);
					}
					
					return true;
				}
			} else {
				player.print(noPermsMsg);
				return true;
			}
		/*} else {
			player.print(ChatColor.DARK_RED + "Rollbacks are disabled because your CraftBukkit is not compatible!");
			return true;
		}*/
	}
	
	private boolean useConversations(PlayerSession player) {
		new RollbackConversation(plugin, player);
		return true;
	}
	
	private boolean checkParserErrors(ArgumentParser parser, PlayerSession player) {
		return true;
	}
	
	private void addParserResultsToUndoConfig(ArgumentParser parser) {
		GriefLog.undoSerializer.getArguments().add(parser);
	}
}