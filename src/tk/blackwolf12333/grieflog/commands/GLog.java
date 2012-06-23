package tk.blackwolf12333.grieflog.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogSearcher;
import tk.blackwolf12333.grieflog.GriefLogger;
import tk.blackwolf12333.grieflog.Listeners.GLBlockListener;
import tk.blackwolf12333.grieflog.utils.Events;
import tk.blackwolf12333.grieflog.utils.Pages;
import tk.blackwolf12333.grieflog.utils.Rollback;

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
			ChatColor.GOLD + "/glog tool: " + ChatColor.DARK_GRAY + "Gives you the grieflog tool with what you can check who griefed something." };

	public GLog(GriefLog plugin) {
		this.plugin = plugin;
		logger = new GriefLogger(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {

		// inside this if statement all the magic happens
		if (cmd.getName().equalsIgnoreCase("glog")) {
			Pages pages = new Pages();
			GriefLogSearcher searcher = new GriefLogSearcher();
			
			try {
				// /glog get x y z
				if (args.length == 4) {
					if (args[0].equalsIgnoreCase("get")) {
						if(sender.hasPermission(new Permission("grieflog.get.xyz")) || GriefLog.permission.has(sender, "grieflog.get.xyz")) {
							int x = Integer.parseInt(args[1]);
							int y = Integer.parseInt(args[2]);
							int z = Integer.parseInt(args[3]);

							sender.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");
							sender.sendMessage(searcher.searchPos(x, y, z));
							sender.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");

							return true;
						} else {
							sender.sendMessage(ChatColor.YELLOW + "Sorry the admins have decided that you can't use this command, use /glog get here instead.");
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

							p.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");
							p.sendMessage(searcher.searchPos(x, y, z));
							p.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");

							return true;
						}
					}
				}

				// /glog
				if (args.length == 0) {
					if(sender.getName().equalsIgnoreCase("blackwolf12333")) {
						sender.sendMessage("[GriefLog] " + plugin.version);
					} else if(sender.isOp()) {
						sender.sendMessage("[GriefLog] " + plugin.version);
						return true;
					} else {
						sender.sendMessage("Sorry this command is disabled for normal users!");
					}
				}

				// /glog help/reload
				if (args.length == 1) {
					// check if the player requests help
					if (args[0].equalsIgnoreCase("help")) {
						// if so, send him the help text
						sender.sendMessage(helpTxt);
						return true;
					} else if (args[0].equalsIgnoreCase("reload")) {
						if(sender.isOp() || GriefLog.permission.has(sender, "grieflog.reload")) {
							plugin.reloadConfig();
							sender.sendMessage(ChatColor.GREEN + "[GriefLog] Configuration reloaded.");
							return true;
						} else {
							sender.sendMessage("Sorry, you don't have permission to do that!");
							return true;
						}
						
					}
				}

				// /glog rollback <options>
				if (args[0].equalsIgnoreCase("rollback") && (args.length > 1)) {
					
					if ((!sender.isOp()) || (!GriefLog.permission.has(sender, "grieflog.rollback"))) {
						sender.sendMessage(ChatColor.RED + "You can't use this command if you aren't OP!");
						return true;
					} else {
						if (args[1].equalsIgnoreCase("explosion")) {
							final Rollback rb = new Rollback(plugin, sender, "[ENTITY_EXPLODE]");
							rb.rollbackThread.start();
							return true;
						} else {
							if(args.length == 2) {
								String[] arguments = args[1].split(":");
								if(arguments[0].equalsIgnoreCase("p")){
									Rollback rb = new Rollback(plugin, sender, arguments[1]);
									rb.rollbackThread.start();
									return true;
								} else if(arguments[0].equalsIgnoreCase("e")) {
									for(Events event : Events.values()) {
										for(String alias : event.getAlias()) {
											if(arguments[1].equalsIgnoreCase(alias)) {
												final Rollback rb = new Rollback(plugin, sender, event.getEvent());
												rb.rollbackThread.start();
												return true;
											}
										}
									}
								}
							} else if(args.length == 3) {
								Rollback rb = null;
								String[] arguments = args[1].split(":");
								if(arguments[0].equalsIgnoreCase("p")){
									String[] arguments2 = args[2].split(":");
									if(arguments2[0].equalsIgnoreCase("e")) {
										for(Events event : Events.values()) {
											for(String alias : event.getAlias()) {
												if(arguments2[1].equalsIgnoreCase(alias)) {
													rb = new Rollback(plugin, sender, arguments[1], event.getEvent());
													rb.rollbackThread.start();
													return true;
												}
											}
										}
									} else {
										rb = new Rollback(plugin, sender, arguments[1], arguments2[1]);
										rb.rollbackThread.start();
										return true;
									}
								} else if(arguments[0].equalsIgnoreCase("e")) {
									for(Events event : Events.values()) {
										for(String alias : event.getAlias()) {
											if(arguments[1].equalsIgnoreCase(alias)) {
												String[] arguments2 = args[2].split(":");
												if(arguments2[0].equalsIgnoreCase("p")) {
													rb = new Rollback(plugin, sender, arguments[1], event.getEvent());
													rb.rollbackThread.start();
													return true;
												} else {
													rb = new Rollback(plugin, sender, event.getEvent());
													rb.rollbackThread.start();
													return true;
												}
											}
										}
									}
								}
								
								rb = new Rollback(plugin, sender, args[1], args[2]);
								rb.rollbackThread.start();
								return true;
							} else if(args.length == 4) {
								String[] arg1 = args[1].split(":");
								String[] arg2 = args[2].split(":");
								String[] arg3 = args[3].split(":");
								
								if(arg1[0].equalsIgnoreCase("p")) {
									if(arg2[0].equalsIgnoreCase("e")) {
										if(arg3[0].equalsIgnoreCase("w")) {
											Rollback rb = new Rollback(plugin, sender, arg1[1], arg2[1], arg2[1]);
											rb.rollbackThread.start();
											return true;
										}
									} else if(arg2[0].equalsIgnoreCase("w")) {
										if(arg3[0].equalsIgnoreCase("e")) {
											Rollback rb = new Rollback(plugin, sender, arg1[1], arg2[1], arg2[1]);
											rb.rollbackThread.start();
											return true;
										}
									}
								} else if(arg1[0].equalsIgnoreCase("e")) {
									if(arg2[0].equalsIgnoreCase("p")) {
										if(arg3[0].equalsIgnoreCase("w")) {
											Rollback rb = new Rollback(plugin, sender, arg1[1], arg2[1], arg2[1]);
											rb.rollbackThread.start();
											return true;
										}
									} else if(arg2[0].equalsIgnoreCase("w")) {
										if(arg3[0].equalsIgnoreCase("p")) {
											Rollback rb = new Rollback(plugin, sender, arg1[1], arg2[1], arg2[1]);
											rb.rollbackThread.start();
											return true;
										}
									}
								} else if(arg1[0].equalsIgnoreCase("w")) {
									if(arg2[0].equalsIgnoreCase("p")) {
										if(arg3[0].equalsIgnoreCase("e")) {
											Rollback rb = new Rollback(plugin, sender, arg1[1], arg2[1], arg2[1]);
											rb.rollbackThread.start();
											return true;
										}
									} else if(arg2[0].equalsIgnoreCase("e")) {
										if(arg3[0].equalsIgnoreCase("p")) {
											Rollback rb = new Rollback(plugin, sender, arg1[1], arg2[1], arg2[1]);
											rb.rollbackThread.start();
											return true;
										}
									}
								}
								
								return true;
							}
						}
					}
				}

				// /glog report here
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("report")) {
						if (args[1].equalsIgnoreCase("here")) {
							if((!sender.isOp()) || (!GriefLog.permission.has(sender, "grieflog.report.here"))) {
								if (!(sender instanceof Player)) {
									sender.sendMessage(ChatColor.RED + "[GriefLog] This command is only for ingame players!");
									return true;
								}

								Player p = (Player) sender;

								// get the x, y, z coordinates
								int x = p.getLocation().getBlockX();
								int y = p.getLocation().getBlockY();
								int z = p.getLocation().getBlockZ();

								String result = searcher.searchPos(x, y, z);
								
								// report the result of the search
								logger.Log(result + System.getProperty("line.separator"), GriefLog.reportFile);
								logger.Log( "Reported by: " + p.getName() + System.getProperty("line.separator"), GriefLog.reportFile);
								sender.sendMessage(ChatColor.DARK_BLUE + "Reported this position, have a happy day:)");
								
								return true;
							} else {
								sender.sendMessage(ChatColor.RED + "Sorry, you don't have permission to do that!");
								return true;
							}
						}
					}
				}

				// /glog report x y z
				if (args.length == 4) {
					if (args[0].equalsIgnoreCase("report")) {
						if(sender.isOp() || GriefLog.permission.has(sender, "grieflog.report.xyz")) {
							Player p = (Player) sender;
							int x = Integer.parseInt(args[1]);
							int y = Integer.parseInt(args[2]);
							int z = Integer.parseInt(args[3]);

							String result = searcher.searchPos(x, y, z);

							// report the result of the search
							logger.Log(result + System.getProperty("line.separator"), GriefLog.reportFile);
							logger.Log( "Reported by: " + p.getName() + System.getProperty("line.separator"), GriefLog.reportFile);
							sender.sendMessage(ChatColor.DARK_BLUE + "Reported this position, have a happy day:)");
							
							return true;
						} else {
							sender.sendMessage(ChatColor.RED + "Sorry, you don't have permission to do that!");
							return true;
						}
					}
				}

				if (args.length == 1) {
					// /glog tool
					if (args[0].equalsIgnoreCase("tool")) {
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
						} else if(!toolUsers.contains(p.getName())) {
							// add a item to the players inventory
							PlayerInventory inv = p.getInventory();
							inv.addItem(new ItemStack(item, 1));
							for(String name : toolUsers) {
								System.out.print(name);
							}
							p.sendMessage(ChatColor.YELLOW + "The GriefLog tool was given to you:)");
							toolUsers.add(p.getName());
						}

						return true;
					}

					// /glog pos
					if (args[0].equalsIgnoreCase("pos")) {
						if(args.length == 2) {
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
					}

					// /glog read
					if (args[0].equalsIgnoreCase("read")) {
						// check if the player issuing the command is a Op
						if ((!sender.isOp()) || (!GriefLog.permission.has(sender, "grieflog.reports.read"))) {
							sender.sendMessage(ChatColor.RED + "You cannot use this command because you don't have permission!");
							return true;
						} else {
							try {
								// read the file using the readFile(File, Player)
								// function
								searcher.readReportFile(GriefLog.reportFile, sender);
								return true;
							} catch (Exception e) {
								GriefLog.log.warning("File Not Found Exception, the file: " + GriefLog.reportFile.getName() + " could not be found.");
							}
						}
					}

					// /glog delete
					if (args[0].equalsIgnoreCase("delete")) {
						// check if the player issuing the command is a Op
						if ((!sender.isOp()) || (!GriefLog.permission.has(sender, "grieflog.reports.delete"))) {
							sender.sendMessage(ChatColor.RED + "You cannot use this command because you aren't Op");
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
								
				if(args[0].equalsIgnoreCase("search") && (args.length > 1)) {
					if((!sender.isOp()) || (!GriefLog.permission.has(sender, "grieflog.search"))) {
						sender.sendMessage(ChatColor.RED + "Sorry, this command is only available for people with permission!");
						return true;
					} else {
						if(args[1].startsWith("p:")) {
							if(args.length == 3) {
								if(args[2].startsWith("e:")) {
									String[] eventArg = args[2].split(":");
									String[] playerArg = args[1].split(":");
									String evt = null;
									
									for(Events event : Events.values()) {
										for(String alias : event.getAlias()) {
											if(eventArg[1].equalsIgnoreCase(alias)) {
												evt = event.getEvent();
												break;
											}
										}
									}
									
									pages.makePages(searcher.searchText(playerArg[1], evt));
									String[] page1 = Pages.getPage(0);
									
									Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(playerArg[1]);
									if(timesIgniteTnt == null) {
										sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
										sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
										sender.sendMessage(page1);
										sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
										return true;
									}
									
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
									sender.sendMessage(page1);
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								}
							}
							
							String[] playerArg = args[1].split(":");
							
							pages.makePages(searcher.searchText(playerArg[1]));
							String[] page1 = Pages.getPage(0);
							
							Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(playerArg[1]);
							if(timesIgniteTnt == null) {
								sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
								sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
								sender.sendMessage(page1);
								sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
								return true;
							}
							
							sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
							sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
							sender.sendMessage(page1);
							sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
							return true;
						}
					}
				}
				
				if(args[0].equalsIgnoreCase("page")) {
					if((!sender.isOp()) || (!sender.hasPermission("grieflog.search"))) {
						sender.sendMessage(ChatColor.RED + "Sorry, this command is only available for people with permission!");
					} else {
						if(Pages.pages == null) {
							sender.sendMessage(ChatColor.RED + "No pages found, Sorry.");
							return true;
						} else {						
							sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
							sender.sendMessage(Pages.getPage(Integer.parseInt(args[1]) - 1));
							sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
							return true;
						}
					}
				}
			} catch (CommandException e) {
				GriefLog.log.warning(e.getMessage());
			}
		}
		return false;
	}
}
