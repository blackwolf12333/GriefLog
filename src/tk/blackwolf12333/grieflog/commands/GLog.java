package tk.blackwolf12333.grieflog.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogSearcher;
import tk.blackwolf12333.grieflog.GriefLogger;
import tk.blackwolf12333.grieflog.utils.Rollback;
import tk.blackwolf12333.grieflog.utils.Time;

public class GLog implements CommandExecutor {

	public GriefLog plugin;
	Time t = new Time();
	GriefLogSearcher searcher;
	GriefLogger logger;
	List<File> files = new ArrayList<File>();

	public String[] helpTxt = { 
			ChatColor.RED + "+++++++++++++ [GriefLog] ++++++++++++++++", 
			"Commands:",
			ChatColor.GOLD + "/glog: " + ChatColor.DARK_GRAY + "This gets the current version of GriefLog.",
			ChatColor.GOLD + "/glog help " + ChatColor.DARK_GRAY + "Shows the help text.",
			ChatColor.GOLD + "/glog get here: " + ChatColor.DARK_GRAY + "This gets the events from the block you are currently standing in.",
			ChatColor.GOLD + "/glog get x y z: " + ChatColor.DARK_GRAY + "Here you have to fill in the x y and z coordinates yourself.",
			ChatColor.GOLD + "/glog rollback <playername> " + ChatColor.DARK_GRAY + "Rolls the actions of the specified player back " + ChatColor.RED + "(Warning, this can't be undone)",
			ChatColor.GOLD + "/glog pos:" + ChatColor.DARK_GRAY + " Gets your current position.",
			ChatColor.GOLD + "/glog report here: " + ChatColor.DARK_GRAY + "Creates a report for the admins so they can read who griefed \"Here\"",
			ChatColor.GOLD + "/glog report x y z: " + ChatColor.DARK_GRAY + "Fill in the x y and z coordinates yourself, this will report the block you point to using the coordinates, it will  tell the admins you reported a griefer so they can look at it.",
			ChatColor.GOLD + "/glog tool: " + ChatColor.DARK_GRAY + "Gives you the grieflog tool with what you can check who griefed something." };

	public GLog(GriefLog plugin) {
		this.plugin = plugin;
		searcher = new GriefLogSearcher();
		logger = new GriefLogger(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {

		// inside this if statement all the magic happens
		if (cmd.getName().equalsIgnoreCase("glog")) {
			try {
				// /glog get x y z
				if (args.length == 4) {
					if (args[0].equalsIgnoreCase("get")) {
						int x = Integer.parseInt(args[1]);
						int y = Integer.parseInt(args[2]);
						int z = Integer.parseInt(args[3]);

						sender.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");
						sender.sendMessage(searcher.searchPos(x, y, z));
						sender.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");

						return true;
					}
				}

				// /glog get here
				if (args.length == 2) {
					// get command
					if (args[0].equalsIgnoreCase("get")) {
						// check the second argument
						if (args[1].equalsIgnoreCase("here")) {
							// checks if the sender is a player, if not, return
							// false
							if (!(sender instanceof Player)) {
								sender.sendMessage("[GriefLog] This command is only for ingame players!");
								return true;
							}

							// declare variables
							Player p = (Player) sender;
							int x = p.getLocation().getBlockX();
							int y = p.getLocation().getBlockY();
							int z = p.getLocation().getBlockZ();

							p.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");
							p.sendMessage(searcher.searchPos(x, y, z));
							p.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");

							return true;
						}
					}
				}

				// /glog
				if (args.length == 0) {
					sender.sendMessage("[GriefLog] " + plugin.version);
					return true;
				}

				// /glog help/reload
				if (args.length == 1) {
					// check if the player requests help
					if (args[0].equalsIgnoreCase("help")) {
						// if so, send him the help text
						sender.sendMessage(helpTxt);
						return true;
					} else if (args[0].equalsIgnoreCase("reload")) {
						plugin.reloadConfig();
						sender.sendMessage("[GriefLog] Configuration reloaded.");
						return true;
					}
				}

				// /glog rollback <playername>
				if (args[0].equalsIgnoreCase("rollback") && (args.length > 1)) {
					
					final Player p = (Player) sender;
					if (!p.isOp()) {
						p.sendMessage("You can't use this command if you aren't OP!");
						return true;
					} else {
						if (args[1].equalsIgnoreCase("explosion")) {
							final Rollback rb = new Rollback(plugin);
							String explosion = "[ENTITY_EXPLODE]";
							rb.rollback(sender, explosion);
							return true;
						} else {
							
							if(args.length == 2) {
								final Rollback rb = new Rollback(plugin);
								rb.rollback(sender, args[1]);
								return true;
							} else if(args.length == 3) {
								final Rollback rb = new Rollback(plugin);
								rb.rollback(sender, args[1], args[2]);
								return true;
							} else if(args.length == 4) {
								final Rollback rb = new Rollback(plugin);
								rb.rollback(sender, args[1], args[2], args[3]);
								
								return true;
							}
						}
					}
				}

				if (args.length == 4) {
					if (args[0].equalsIgnoreCase("rollback")) {
						Player p = (Player) sender;
						if (!p.isOp()) {
							p.sendMessage("You can't use this command if you aren't OP!");
							return true;
						} else {
							/*
							 * long from = t.getTimeStamp(args[2] + " " +
							 * args[3]); Rollback rb = new Rollback(plugin);
							 * rb.rollback(args[1], p, GriefLog.file, from);
							 * return true;
							 */
						}
					}
				}

				// /glog report here
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("report")) {
						if (args[1].equalsIgnoreCase("here")) {
							if (!(sender instanceof Player)) {
								sender.sendMessage("[GriefLog] This command is only for ingame players!");
								return true;
							}

							Player p = (Player) sender;

							// get the x, y, z coordinates
							int x = p.getLocation().getBlockX();
							int y = p.getLocation().getBlockY();
							int z = p.getLocation().getBlockZ();

							String result = searcher.searchPos(x, y, z);
							
							// report the result of the search
							logger.Log(result, GriefLog.reportFile);
							logger.Log("", GriefLog.reportFile);
							logger.Log( "Reported by: " + p.getName(), GriefLog.reportFile);
							logger.Log("", GriefLog.reportFile);
							
							return true;
						}
					}
				}

				// /glog report x y z
				if (args.length == 4) {
					if (args[0].equalsIgnoreCase("report")) {
						Player p = (Player) sender;
						int x = Integer.parseInt(args[1]);
						int y = Integer.parseInt(args[2]);
						int z = Integer.parseInt(args[3]);

						String result = searcher.searchPos(x, y, z);

						// report the result of the search
						logger.Log(result, GriefLog.reportFile);
						logger.Log("", GriefLog.reportFile);
						logger.Log( "Reported by: " + p.getName(), GriefLog.reportFile);
						logger.Log("", GriefLog.reportFile);

						return true;
					}
				}

				if (args.length == 1) {
					// /glog tool
					if (args[0].equalsIgnoreCase("tool")) {
						if (!(sender instanceof Player)) {
							sender.sendMessage("[GriefLog] This command is only for ingame players!");
							return true;
						}

						// setup some variables
						Player p = (Player) sender;
						int item = plugin.getConfig().getInt("SelectionTool");

						// add a item to the players inventory
						PlayerInventory inv = p.getInventory();
						inv.addItem(new ItemStack(item, 1));

						p.sendMessage("The GriefLog tool was given to you:)");

						return true;
					}

					// /glog pos
					if (args[0].equalsIgnoreCase("pos")) {
						if (!(sender instanceof Player)) {
							sender.sendMessage("[GriefLog] This command is only for ingame players!");
							return true;
						}

						Player p = (Player) sender;
						Block b = p.getLocation().getBlock();
						int x = b.getX();
						int y = b.getY();
						int z = b.getZ();

						// send the player his current position
						p.sendMessage(x + ", " + y + ", " + z);
						return true;
					}

					// /glog read
					if (args[0].equalsIgnoreCase("read")) {
						// check if the player issuing the command is a Op
						if (!sender.isOp()) {
							sender.sendMessage("You cannot use this command because you aren't Op");
							return true;
						}

						try {
							// read the file using the readFile(File, Player)
							// function
							searcher.readReportFile(GriefLog.reportFile, sender);
							return true;
						} catch (Exception e) {
							sender.sendMessage("No report files have been found:)");
							plugin.log.warning("File Not Found Exception, the file: " + GriefLog.reportFile.getName() + " could not be found.");
						}
					}

					// /glog delete
					if (args[0].equalsIgnoreCase("delete")) {
						// check if the player issuing the command is a Op
						if (!sender.isOp()) {
							sender.sendMessage("You cannot use this command because you aren't Op");
							return true;
						} else {
							// check if the file could be deleted, if not, tell
							// the sender of the command
							if (!GriefLog.reportFile.delete())
								sender.sendMessage("Report file could not be deleted:(");
							sender.sendMessage("Report file is deleted");
							return true;
						}
					}
				}

			} catch (CommandException e) {
				plugin.log.warning(e.getMessage());
			}
		}
		return false;
	}
}
