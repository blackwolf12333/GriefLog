package tk.blackwolf12333.grieflog.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.rollback.Undo;
import tk.blackwolf12333.grieflog.utils.filters.WorldEditFilter;
import tk.blackwolf12333.grieflog.utils.searching.SearchTask;

public class GLogUndo {

	private String noPermsMsg = ChatColor.DARK_RED + "I am sorry Dave, but i cannot let you do that! You don't have permission.";
	
	public boolean onCommand(PlayerSession player, String[] args) {
		if (player.hasPermission("grieflog.rollback")) {
			if(args.length == 2) {
				if((args.length == 2) && (args[1].equalsIgnoreCase("list"))) {
					player.print(GriefLog.undoConfig.getAll());
					return true;
				} else {
					ArrayList<String> arguments = GriefLog.undoConfig.get(args[1]);
					if(arguments == null) {
						player.print(ChatColor.DARK_RED + "You have no rollback's I can undo!");
						return true;
					} else if(arguments.get(0).equals("we")) {
						new SearchTask(player, new SearchCallback(player, SearchCallback.Type.UNDO), arguments, arguments.get(3), new WorldEditFilter(player));
						return true;
					} else {
						new SearchTask(player, new SearchCallback(player, SearchCallback.Type.UNDO), arguments, arguments.get(2));
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
}
