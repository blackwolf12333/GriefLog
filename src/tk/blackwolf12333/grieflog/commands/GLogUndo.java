package tk.blackwolf12333.grieflog.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.utils.searching.SearchTask;
import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.rollback.Undo;
import tk.blackwolf12333.grieflog.utils.searching.ArgumentParser;

public class GLogUndo {

	private String noPermsMsg = ChatColor.DARK_RED + "I am sorry, You do not have permission to run this command.";
	
	public boolean onCommand(PlayerSession player, String[] args) {
                if(player.isDoingRollback()) {
                        player.print(ChatColor.DARK_RED + "You are still rolling back, you can't undo what isn't rolled back yet!");
                        return true;
                }
		if (player.hasPermission("grieflog.rollback")) {
			if(args.length == 2) {
				if((args.length == 2) && (args[1].equalsIgnoreCase("list"))) {
					printArguments(player);
					return true;
				} else {
					ArrayList<ArgumentParser> arguments = GriefLog.undoSerializer.getArguments();
					int id = Integer.parseInt(args[1]);
					if(arguments == null) {
						player.print(ChatColor.DARK_RED + "You have no rollback's I can undo!");
						return true;
					} else {
						new SearchTask(player, new SearchCallback(player, SearchCallback.Type.UNDO), arguments.get(id));
						return true;
					}
				}
			} else if(args.length == 1) {
				new Undo(player);
				return true;
			} else if(args.length > 2) {
				player.print(ChatColor.DARK_RED + "To many arguments!");
				player.print(ChatColor.DARK_RED + "/glog undo [id]");
				return true;
			} else {
				return false;
			}
		} else {
			player.print(noPermsMsg);
			return true;
		}
	}

	private void printArguments(PlayerSession player) {
		ArrayList<ArgumentParser> arguments = GriefLog.undoSerializer.getArguments();
		for(int id = 0; id < arguments.size(); id++) {
			player.print(id + ": " + getArgumentString(arguments.get(id)));
		}
	}

	private String getArgumentString(ArgumentParser argumentParser) {
		String argument = new String();
		if(argumentParser.worldedit) {
			argument += "we ";
		}
		argument += argumentParser.event + " ";
		argument += argumentParser.player + " ";
		argument += argumentParser.world + " ";
		argument += argumentParser.blockFilter;
		return argument;
	}
}
