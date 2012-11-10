package tk.blackwolf12333.grieflog.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;

public class GLog implements CommandExecutor {

	public GriefLog plugin;
	
	public String[] helpTxt = { 
			ChatColor.RED + "+++++++++++++ [GriefLog] ++++++++++++++++", 
			"Commands:",
			ChatColor.GOLD + "/glog: " + ChatColor.DARK_GRAY + "This gets the current version of GriefLog.",
			ChatColor.GOLD + "/glog help: " + ChatColor.DARK_GRAY + "Shows this help text.",
			ChatColor.GOLD + "/glog get here: " + ChatColor.DARK_GRAY + "This gets the events from the block you are currently standing in.",
			ChatColor.GOLD + "/glog get blockX blockY blockZ: " + ChatColor.DARK_GRAY + "Here you have to fill in the x y and z coordinates yourself.",
			ChatColor.GOLD + "/glog rollback <options>: " + ChatColor.DARK_GRAY + "Rolls the actions from the given options.",
			ChatColor.GOLD + "/glog pos:" + ChatColor.DARK_GRAY + " Gets your current position.",
			ChatColor.GOLD + "/glog tool: " + ChatColor.DARK_GRAY + "Gives you the grieflog tool with what you can check who griefed something.",
			ChatColor.GOLD + "/glog search <options>: " + ChatColor.DARK_GRAY + "This lets you search for the specified arguments, these work the same as with the rollback arguments.",
			ChatColor.GOLD + "/glog tpto <player>: " + ChatColor.DARK_GRAY + "This teleports you to <player>, if he is offline you will teleport to his/her last location.",
			ChatColor.GOLD + "/glog undo <id>: " + ChatColor.DARK_GRAY + "This will undo the rollback specified by <id>. To get a list of id's use /glog undo list.",
			ChatColor.DARK_AQUA + "For more help, go to the BukkitDev page: http://dev.bukkit.org/server-mods/grieflog/"
	};
	
	public GLog(GriefLog plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("glog")) {
			PlayerSession player = null;
			if(sender instanceof Player) {
				player = PlayerSession.getGLPlayer((Player)sender);
			} else {
				player = PlayerSession.getGLPlayer((ConsoleCommandSender)sender);
			}
			CommandHandler handler = new CommandHandler(player);
			
			if (args.length == 0) {
				return handler.getVersion();
			} 
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(helpTxt);
					return true;
				} else if (args[0].equalsIgnoreCase("reload")) {
					return handler.reload();
				} else if (args[0].equalsIgnoreCase("tool")) {
					return handler.giveTool();
				} else if (args[0].equalsIgnoreCase("pos")) {
					return handler.getPosition();
				}
			} 
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("get")) {
					if(args[1].equalsIgnoreCase("here"))
						return handler.getHere();
				}  else if (args[0].equalsIgnoreCase("pos")) {
					return handler.getPositionOf(args[1]);
				} else if(args[0].equalsIgnoreCase("tpto")) {
					return handler.tpto(args[1]);
				}
			}
			if (args.length == 4) {
				if (args[0].equalsIgnoreCase("get")) {
					return handler.getXYZ(args[1], args[2], args[3]);
				}
			}
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("page")) {
					return handler.page(args[1]);
				} else if (args[0].equalsIgnoreCase("rollback") || args[0].equalsIgnoreCase("rb")) {
					return new GLogRollback(plugin).onCommand(player, args);
				} else if(args[0].equalsIgnoreCase("search")) {
					return new GLogSearch(plugin).onCommand(player, args);
				} else if(args[0].equalsIgnoreCase("undo")) {
					return new GLogUndo().onCommand(player, args);
				}
			}
		}
			
		sender.sendMessage(ChatColor.DARK_RED + "Somewhere in the process there was an error, check you command for any mistakes and try again.");
		return true;
	}
}
