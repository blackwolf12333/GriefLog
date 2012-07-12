package tk.blackwolf12333.grieflog.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogger;
import tk.blackwolf12333.grieflog.search.GriefLogSearcher;
import tk.blackwolf12333.grieflog.search.Searcher;
import tk.blackwolf12333.grieflog.utils.Pages;
import tk.blackwolf12333.grieflog.utils.config.GLConfigHandler;

public class GLog implements CommandExecutor {

	public GriefLog plugin;
	GriefLogger logger;
	
	static ArrayList<String> toolUsers = new ArrayList<String>();
	public String[] helpTxt = { 
			ChatColor.RED + "+++++++++++++ [GriefLog] ++++++++++++++++", 
			"Commands:",
			ChatColor.GOLD + "/glog: " + ChatColor.DARK_GRAY + "This gets the current version of GriefLog.",
			ChatColor.GOLD + "/glog help: " + ChatColor.DARK_GRAY + "Shows the help text.",
			ChatColor.GOLD + "/glog get here: " + ChatColor.DARK_GRAY + "This gets the events from the blockChest you are currently standing in.",
			ChatColor.GOLD + "/glog get x y z: " + ChatColor.DARK_GRAY + "Here you have to fill in the x y and z coordinates yourself.",
			ChatColor.GOLD + "/glog rollback <options>: " + ChatColor.DARK_GRAY + "Rolls the actions from the given options(look at the BukkitDev page for help on that) " + ChatColor.RED + "(Warning, this can't be undone)",
			ChatColor.GOLD + "/glog pos:" + ChatColor.DARK_GRAY + " Gets your current position.",
			ChatColor.GOLD + "/glog report here: " + ChatColor.DARK_GRAY + "Creates a report for the admins so they can read who griefed \"Here\"",
			ChatColor.GOLD + "/glog report x y z: " + ChatColor.DARK_GRAY + "Fill in the x y and z coordinates yourself, this will report the blockChest you point to using the coordinates, it will  tell the admins you reported a griefer so they can look at it.",
			ChatColor.GOLD + "/glog tool: " + ChatColor.DARK_GRAY + "Gives you the grieflog tool with what you can check who griefed something.",
			ChatColor.GOLD + "/glog search <options>" + ChatColor.DARK_GRAY + "This lets you search for the specified arguments, these work the same as with the rollback arguments."};
	
	private String noPermsMsg = ChatColor.DARK_RED + "I am sorry, but i cannot let you do that! You don't have permission.";
	
	public GLog(GriefLog plugin) {
		this.plugin = plugin;
		logger = new GriefLogger();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {

		// inside this if statement all the magic happens
		if (cmd.getName().equalsIgnoreCase("glog")) {
			Searcher searcher = new GriefLogSearcher(GriefLog.players.get(sender.getName()), plugin);
			
			try {
				// /glog get x y z
				if (args.length == 4) {
					if (args[0].equalsIgnoreCase("get")) {
						if(GriefLog.permission.has(sender, "grieflog.get.xyz")) {
							int x = Integer.parseInt(args[1]);
							int y = Integer.parseInt(args[2]);
							int z = Integer.parseInt(args[3]);
							
							ArrayList<String> result = searcher.searchPos(x, y, z);
							if(result != null) {
								sender.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");
								for(int i = 0; i < result.size(); i++) {
									sender.sendMessage(result.get(i));
								}
								sender.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");
							} else {
								sender.sendMessage(ChatColor.BLUE + "[GriefLog] Nothing Found Here.");
							}

							return true;
						} else {
							sender.sendMessage(ChatColor.YELLOW + "Sorry the admins have decided that you can't use this command, use /glog get here instead.");
							return true;
						}
					} else if (args[0].equalsIgnoreCase("report")) {
						if(sender.isOp() || GriefLog.permission.has(sender, "grieflog.report.xyz")) {
							Player p = (Player) sender;
							int x = Integer.parseInt(args[1]);
							int y = Integer.parseInt(args[2]);
							int z = Integer.parseInt(args[3]);

							ArrayList<String> result = searcher.searchPos(x, y, z);

							// report the result of the search
							for(int i = 0; i < result.size(); i++) {
								logger.Log(result.get(i) + System.getProperty("line.separator"), GriefLog.reportFile);
							}
							logger.Log( "Reported by: " + p.getName() + System.getProperty("line.separator"), GriefLog.reportFile);
							sender.sendMessage(ChatColor.DARK_BLUE + "Reported this position, have a happy day:)");
							
							return true;
						} else {
							sender.sendMessage(noPermsMsg);
							return true;
						}
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
								sender.sendMessage(ChatColor.RED + "[GriefLog] This command is only for ingame players!");
								return true;
							}

							// declare variables
							Player p = (Player) sender;
							int x = p.getLocation().getBlockX();
							int y = p.getLocation().getBlockY();
							int z = p.getLocation().getBlockZ();

							ArrayList<String> result = searcher.searchPos(x, y, z);
							if(result != null) {
								p.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");
								for(int i = 0; i < result.size(); i++) {
									sender.sendMessage(result.get(i));
								}
								p.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");
							} else {
								p.sendMessage(ChatColor.BLUE + "[GriefLog] Nothing Found Here.");
							}
							return true;
						}
					}
				}

				// /glog
				if (args.length == 0) {
					if(sender.getName().equalsIgnoreCase("blackwolf12333")) {
						sender.sendMessage(ChatColor.GREEN + "[GriefLog] " + plugin.version);
						return true;
					} else if(sender.isOp()) {
						sender.sendMessage(ChatColor.GREEN + "[GriefLog] " + plugin.version);
						return true;
					} else {
						sender.sendMessage(ChatColor.YELLOW + "Sorry this command is disabled for normal users!");
						return true;
					}
				}

				// /glog rollback <options>
				if (args[0].equalsIgnoreCase("rollback") || args[0].equalsIgnoreCase("rb")) {
					GLogRollback rb = new GLogRollback(plugin);
					return rb.onCommand(sender, cmd, cmdLabel, args);
				}

				// /glog report here
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("report")) {
						if (args[1].equalsIgnoreCase("here")) {
							if(sender.isOp() || GriefLog.permission.has(sender, "grieflog.report.here")) {
								if (!(sender instanceof Player)) {
									sender.sendMessage(ChatColor.RED + "[GriefLog] This command is only for ingame players!");
									return true;
								}

								Player p = (Player) sender;

								// get the x, y, z coordinates
								int x = p.getLocation().getBlockX();
								int y = p.getLocation().getBlockY();
								int z = p.getLocation().getBlockZ();

								ArrayList<String> result = searcher.searchPos(x, y, z);
								
								// report the result of the search
								for(int i = 0; i < result.size(); i++) {
									logger.Log(result.get(i) + System.getProperty("line.separator"), GriefLog.reportFile);
								}
								logger.Log( "Reported by: " + p.getName() + System.getProperty("line.separator"), GriefLog.reportFile);
								sender.sendMessage(ChatColor.DARK_BLUE + "Reported this position, have a happy day:)");
								
								return true;
							} else {
								sender.sendMessage(noPermsMsg);
								return true;
							}
						}
					}
				}

				if (args.length == 1) {
					// check if the player requests help
					if (args[0].equalsIgnoreCase("help")) {
						// if so, send him the help text
						sender.sendMessage(helpTxt);
						return true;
					} else if (args[0].equalsIgnoreCase("reload")) {
						if(sender.isOp()) {
							GLConfigHandler.reloadConfig();
							sender.sendMessage(ChatColor.GREEN + "[GriefLog] Configuration reloaded.");
							return true;
						} else if(GriefLog.permission.has(sender, "grieflog.reload")) {
							plugin.reloadConfig();
							sender.sendMessage(ChatColor.GREEN + "[GriefLog] Configuration reloaded.");
							return true;
						} else {
							sender.sendMessage(noPermsMsg);
							return true;
						}
					} else if (args[0].equalsIgnoreCase("tool")) {
						if(sender.isOp() || GriefLog.permission.has(sender, "grieflog.tool")) {
							if (!(sender instanceof Player)) {
								sender.sendMessage(ChatColor.RED + "[GriefLog] This command is only for ingame players!");
								return true;
							}

							// setup some variables
							Player p = (Player) sender;
							int item = plugin.getConfig().getInt("SelectionTool");
							
							if(toolUsers.contains(p.getName())) {
								PlayerInventory inv = p.getInventory();
								inv.remove(new ItemStack(item, 1));
								
								p.sendMessage(ChatColor.YELLOW + "The GriefLog tool was taken from to you.");
								toolUsers.remove(p.getName());
								
								return true;
							} else {
								// add a item to the players inventory
								PlayerInventory inv = p.getInventory();
								inv.addItem(new ItemStack(item, 1));
								for(String name : toolUsers) {
									System.out.print(name);
								}
								p.sendMessage(ChatColor.YELLOW + "The GriefLog tool was given to you:)");
								toolUsers.add(p.getName());
								return true;
							}
						} else {
							sender.sendMessage(noPermsMsg);
							return true;
						}
					} else if (args[0].equalsIgnoreCase("pos")) {
						if(args.length == 2) {
							if(sender.isOp()) {
								for(Player p : plugin.getServer().getOnlinePlayers()) {
									if(p.getName().contains(args[1])) {
										Block b = p.getLocation().getBlock();
										int x = b.getX();
										int y = b.getY();
										int z = b.getZ();
										
										sender.sendMessage(x +  ", " + y + ", " + z);
										return true;
									}
								}
							} else {
								sender.sendMessage(noPermsMsg);
								return true;
							}
						} else {
							if (!(sender instanceof Player)) {
								sender.sendMessage(ChatColor.RED + "[GriefLog] This command is only for ingame players!");
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
					} else if (args[0].equalsIgnoreCase("read")) {
						// check if the player issuing the command is a Op
						if (sender.isOp() || GriefLog.permission.has(sender, "grieflog.reports.read")) {
							try {
								// read the file using the readFile(File, Player)
								// function
								searcher.readReportFile(GriefLog.reportFile, sender);
								return true;
							} catch (Exception e) {
								GriefLog.log.warning("File Not Found Exception, the file: " + GriefLog.reportFile.getName() + " could not be found.");
								sender.sendMessage(ChatColor.YELLOW + "No report file has been found.");
								return true;
							}
						} else {
							sender.sendMessage(noPermsMsg);
							return true;
						}
					} else if (args[0].equalsIgnoreCase("delete")) {
						// check if the player issuing the command is a Op
						if (GriefLog.permission.has(sender, "grieflog.reports.delete")) {
							// check if the file could be deleted, if not, tell
							// the sender of the command
							if(!GriefLog.reportFile.exists()) {
								sender.sendMessage("Report file doesn't exist");
								return true;
							} else if (!GriefLog.reportFile.delete()) {
								sender.sendMessage("Report file could not be deleted!");
								return true;
							}
							
							sender.sendMessage("Report file is deleted");
							return true;
						} else {
							sender.sendMessage(noPermsMsg);
							return true;
						}
					} else if(args[0].equalsIgnoreCase("cancel")) {
						if(GriefLog.permission.has(sender, "grieflog.rollback")) {
							GLPlayer player = GriefLog.players.get(sender.getName());
							Bukkit.getScheduler().cancelTask(player.getRollbackTaskID()); // cancel the rollback at any point
						}
					}
				}
								
				if(args[0].equalsIgnoreCase("search")) {
					GLogSearch search = new GLogSearch(plugin);
					return search.onCommand(sender, cmd, cmdLabel, args);
				}
				
				if(args[0].equalsIgnoreCase("page")) {
					if(sender.isOp() || sender.hasPermission("grieflog.search")) {
						if(Pages.pages == null) {
							sender.sendMessage(ChatColor.RED + "No pages found, Sorry.");
							return true;
						} else {						
							sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
							sender.sendMessage(Pages.getPage(Integer.parseInt(args[1]) - 1));
							sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
							return true;
						}
					} else {
						sender.sendMessage(noPermsMsg);
						return true;
					}
				}
				
				if(args.length >= 2) {
					if(args[0].equalsIgnoreCase("bp")) {
						GLogBp blockProtection = new GLogBp();
						return blockProtection.onCommand(sender, cmdLabel, args);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
