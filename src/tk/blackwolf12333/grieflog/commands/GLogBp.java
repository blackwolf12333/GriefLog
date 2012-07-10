package tk.blackwolf12333.grieflog.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import tk.blackwolf12333.grieflog.utils.config.GLConfigHandler;

public class GLogBp {

	String[] helpTxt = { 
			ChatColor.RED + "++++++++++ [GriefLog-BlockProtection] +++++++++++++", 
			"Commands:",
			ChatColor.GOLD + "/glog bp help" + ChatColor.DARK_GRAY + "Gives this help text.",
			ChatColor.GOLD + "/glog bp listfriends: " + ChatColor.DARK_GRAY + "This lists the friends on your friendslist.",
			ChatColor.GOLD + "/glog bp addfriend <friendsname>: " + ChatColor.DARK_GRAY + "This adds a friend to your friendslist so that he can break your blocks.",
			ChatColor.GOLD + "/glog bp removefriend <friendsname>: " + ChatColor.DARK_GRAY + "This removes a friend from your friendslist."};
	
	public boolean onCommand(CommandSender sender, String cmdLabel, String[] args) {
		
		if(args[1].equalsIgnoreCase("listfriends")) {
			List<String> friends = GLConfigHandler.getFriends(sender.getName());
			sender.sendMessage(ChatColor.AQUA + "This are your current friends: ");
			for(int i = 0; i < friends.size(); i++) {
				sender.sendMessage(ChatColor.AQUA + friends.get(i));
			}
			return true;
		}
		
		if(args[1].equalsIgnoreCase("addfriend")) {
			GLConfigHandler.loadFriendsConfig();
			if(GLConfigHandler.getFriends(sender.getName()).size() == 0) {
				String[] list = new String[] {args[2]};
				GLConfigHandler.friendsConfig.set(sender.getName(), Arrays.asList(list));
			} else {
				if(GLConfigHandler.getFriends(sender.getName()).contains(args[2])) {
					sender.sendMessage(ChatColor.AQUA + "[GriefLog] Player " + args[2] + " is allready on your friends list.");
				} else {
					List<String> friends = GLConfigHandler.friendsConfig.getStringList(sender.getName());
					friends.add(args[0]);
					GLConfigHandler.friendsConfig.set(sender.getName(), friends);
					sender.sendMessage(ChatColor.AQUA + "[GriefLog] Friend " + args[2] + " added to friends list.");
				}
			}
			GLConfigHandler.saveFriendsConfig();
			
			return true;
		}
		
		if(args[1].equalsIgnoreCase("removefriend")) {
			GLConfigHandler.loadFriendsConfig();
			
			if(GLConfigHandler.getFriends(sender.getName()) == null) {
				sender.sendMessage(ChatColor.AQUA + "[GriefLog] You don't have any friends added yet.");
				return true;
			} else {
				if(GLConfigHandler.getFriends(sender.getName()).contains(args[2])) {
					List<String> friends = GLConfigHandler.friendsConfig.getStringList(sender.getName());
					friends.remove(args[2]);
					GLConfigHandler.friendsConfig.set(sender.getName(), friends);
					GLConfigHandler.saveFriendsConfig();
					sender.sendMessage(ChatColor.AQUA + "[GriefLog] Player " + args[2] + " is removed from your friends list.");
					return true;
				} else {
					sender.sendMessage(ChatColor.AQUA + "[GriefLog] " + args[2] + " is not on your friends list.");
					return true;
				}
			}
		}
		
		if(args[1].equalsIgnoreCase("help")) {
			sender.sendMessage(helpTxt);
			return true;
		}
		
		return false;
	}
}
