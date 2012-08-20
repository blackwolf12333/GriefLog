package tk.blackwolf12333.grieflog.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogger;
import tk.blackwolf12333.grieflog.SearchTask;
import tk.blackwolf12333.grieflog.callback.ReportCallback;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.callback.TpToCallback;
import tk.blackwolf12333.grieflog.utils.Events;
import tk.blackwolf12333.grieflog.utils.PageManager;
import tk.blackwolf12333.grieflog.utils.Pages;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class GLog implements CommandExecutor {

	public GriefLog plugin;
	GriefLogger logger;
	
	static ArrayList<String> toolUsers = new ArrayList<String>();
	public String[] helpTxt = { 
			ChatColor.RED + "+++++++++++++ [GriefLog] ++++++++++++++++", 
			"Commands:",
			ChatColor.GOLD + "/glog: " + ChatColor.DARK_GRAY + "This gets the current version of GriefLog.",
			ChatColor.GOLD + "/glog help: " + ChatColor.DARK_GRAY + "Shows this help text.",
			ChatColor.GOLD + "/glog get here: " + ChatColor.DARK_GRAY + "This gets the events from the blockChest you are currently standing in.",
			ChatColor.GOLD + "/glog get x y z: " + ChatColor.DARK_GRAY + "Here you have to fill in the x y and z coordinates yourself.",
			ChatColor.GOLD + "/glog rollback <options>: " + ChatColor.DARK_GRAY + "Rolls the actions from the given options(look at the BukkitDev page for help on that) " + ChatColor.RED + "(Warning, this can't be undone)",
			ChatColor.GOLD + "/glog pos:" + ChatColor.DARK_GRAY + " Gets your current position.",
			ChatColor.GOLD + "/glog report here: " + ChatColor.DARK_GRAY + "Creates a report for the admins so they can read who griefed \"Here\"",
			ChatColor.GOLD + "/glog report x y z: " + ChatColor.DARK_GRAY + "Fill in the x y and z coordinates yourself, this will report the blockChest you point to using the coordinates, it will  tell the admins you reported a griefer so they can look at it.",
			ChatColor.GOLD + "/glog tool: " + ChatColor.DARK_GRAY + "Gives you the grieflog tool with what you can check who griefed something.",
			ChatColor.GOLD + "/glog search <options>: " + ChatColor.DARK_GRAY + "This lets you search for the specified arguments, these work the same as with the rollback arguments.",
			ChatColor.GOLD + "/glog tpto <player>: " + ChatColor.DARK_GRAY + "This teleports you to <player>, if he is offline you will teleport to his/her last location."};
	
	private String noPermsMsg = ChatColor.DARK_RED + "I am sorry, but i cannot let you do that! You don't have permission.";
	
	public GLog(GriefLog plugin) {
		this.plugin = plugin;
		logger = new GriefLogger();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {

		// inside this if statement all the magic happens
		if (cmd.getName().equalsIgnoreCase("glog")) {
			GLPlayer player = GLPlayer.getGLPlayer(sender);
			
			// /glog get x y z
			if (args.length == 4) {
				if (args[0].equalsIgnoreCase("get")) {
					if(GriefLog.permission.has(sender, "grieflog.get.xyz")) {
						int x = Integer.parseInt(args[1]);
						int y = Integer.parseInt(args[2]);
						int z = Integer.parseInt(args[3]);
						
						new SearchTask(player, new SearchCallback(player), x + ", " + y + ", " + z);
						
						return true;
					} else {
						sender.sendMessage(ChatColor.YELLOW + "Sorry the admins have decided that you can't use this command, use /glog get here instead.");
						return true;
					}
				} else if (args[0].equalsIgnoreCase("report")) {
					if(GriefLog.permission.has(sender, "grieflog.report.xyz")) {
						int x = Integer.parseInt(args[1]);
						int y = Integer.parseInt(args[2]);
						int z = Integer.parseInt(args[3]);
						
						new SearchTask(player, new ReportCallback(player), x + ", " + y + ", " + z);
						
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
						
						new SearchTask(player, new SearchCallback(player), x + ", " + y + ", " + z);
						return true;
					}
				} else if(args[0].equalsIgnoreCase("tpto")) {
					// check if the sender is a player
					if(!(sender instanceof Player)) {
						sender.sendMessage(ChatColor.RED + "[GriefLog] This command is only for ingame players!");
						return true;
					}
					
					if(teleportIfOnline(player, args[1])) {
						return true;
					} else {
						ArrayList<String> arg = new ArrayList<String>();
						
						// add the right arguments to the arraylist
						arg.add(Events.QUIT.getEventName());
						arg.add(args[1]);
						
						// search and get the results
						new SearchTask(player, new TpToCallback(player), arg);
						return true;
					}
				} else if (args[0].equalsIgnoreCase("report")) {
					if (args[1].equalsIgnoreCase("here")) {
						if(GriefLog.permission.has(sender, "grieflog.report.here")) {
							if (!(sender instanceof Player)) {
								sender.sendMessage(ChatColor.RED + "[GriefLog] This command is only for ingame players!");
								return true;
							}
							
							Player p = (Player) sender;
							
							// get the x, y, z coordinates
							int x = p.getLocation().getBlockX();
							int y = p.getLocation().getBlockY();
							int z = p.getLocation().getBlockZ();
							
							new SearchTask(player, new ReportCallback(player), x + ", " + y + ", " + z);
							return true;
						} else {
							sender.sendMessage(noPermsMsg);
							return true;
						}
					}
				}
			}
			
			// /glog
			if (args.length == 0) {
				if(sender.getName().equalsIgnoreCase("blackwolf12333")) {
					if(ConfigHandler.config.getBoolean("blackwolf12333glog")) {
						sender.sendMessage(ChatColor.GREEN + "[GriefLog] " + plugin.version);
						return true;
					}
				} else if(sender.isOp()) {
					sender.sendMessage(ChatColor.GREEN + "[GriefLog] " + plugin.version);
					return true;
				} else {
					sender.sendMessage(ChatColor.YELLOW + "Sorry this command is disabled for normal users!");
					return true;
				}
			}
			
			// /glog rollback <options>
			if(args.length > 1) {
				if (args[0].equalsIgnoreCase("rollback") || args[0].equalsIgnoreCase("rb")) {
					GLogRollback rb = new GLogRollback(plugin);
					return rb.onCommand(sender, cmd, cmdLabel, args);
				} else if(args[0].equalsIgnoreCase("search")) {
					GLogSearch search = new GLogSearch(plugin);
					return search.onCommand(sender, cmd, cmdLabel, args);
				}
			}
			
			if (args.length == 1) {
				// check if the player requests help
				if (args[0].equalsIgnoreCase("help")) {
					// if so, send him the help text
					sender.sendMessage(helpTxt);
					return true;
				} else if (args[0].equalsIgnoreCase("reload")) {
					if(GriefLog.permission.has(sender, "grieflog.reload")) {
						ConfigHandler.reloadConfig();
						sender.sendMessage(ChatColor.GREEN + "[GriefLog] Configuration reloaded.");
						return true;
					} else {
						sender.sendMessage(noPermsMsg);
						return true;
					}
				} else if (args[0].equalsIgnoreCase("tool")) {
					if(GriefLog.permission.has(sender, "grieflog.tool")) {
						if (!(sender instanceof Player)) {
							sender.sendMessage(ChatColor.RED + "[GriefLog] This command is only for ingame players!");
							return true;
						}
						
						// setup some variables
						Player p = (Player) sender;
						int item = ConfigHandler.values.getTool();
						
						if(player.isUsingTool()) {
							PlayerInventory inv = p.getInventory();
							removeItemFromInventory(inv, new ItemStack(item));
							
							p.sendMessage(ChatColor.YELLOW + "The GriefLog tool was taken from to you.");
							player.setUsingTool(false);
							
							return true;
						} else {
							// add the tool to the players inventory
							PlayerInventory inv = p.getInventory();
							inv.addItem(new ItemStack(item, 1));
							
							p.sendMessage(ChatColor.YELLOW + "The GriefLog tool was given to you:)");
							player.setUsingTool(true);
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
							readReportFile(GriefLog.reportFile, sender);
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
						return player.cancelRollback();
					} else {
						sender.sendMessage(noPermsMsg);
						return true;
					}
				}
			}
			
			if(args[0].equalsIgnoreCase("page")) {
				if(sender.isOp() || sender.hasPermission("grieflog.search")) {
					if(Pages.pages == null) {
						sender.sendMessage(ChatColor.RED + "No pages found, Sorry.");
						return true;
					} else {						
						PageManager.printPage(GLPlayer.getGLPlayer(sender), Integer.parseInt(args[1]) - 1);
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
		}
			
		sender.sendMessage(ChatColor.DARK_RED + "Somewhere in the process there was an error, check you command for any mistakes and try again.");
		return true;
	}
	
	public void readReportFile(File file, CommandSender sender) {
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String line = "";

			sender.sendMessage(ChatColor.RED + "+++++++++ReportStart+++++++++");
			while ((line = br.readLine()) != null) {
				sender.sendMessage(line);
			}
			sender.sendMessage(ChatColor.RED + "++++++++++ReportEnd+++++++++");

			br.close();
			fileReader.close();

		} catch (Exception e) {
			sender.sendMessage(ChatColor.DARK_RED + "No Reports have been found!");
		}
	}
	
	private boolean teleportIfOnline(GLPlayer player, String to) {
		for(Player p : plugin.getServer().getOnlinePlayers()) {
			if(p.getName().equalsIgnoreCase(to)) {
				player.teleport(p.getLocation());
				player.print(ChatColor.YELLOW + "[GriefLog] Teleporting you to " + p.getName() + ".");
				return true;
			}
		}
		return false;
	}
	
	// with thanks to bergerkiller
	private void removeItemFromInventory(Inventory inv, ItemStack item) {
		removeItemFromInventory(inv, item.getType(), item.getAmount());
	}
	
	private void removeItemFromInventory(Inventory inv, Material type, int amount) {
		for(ItemStack is : inv.getContents()) {
			if(is != null && is.getType() == type) {
				int newamount = is.getAmount() - amount;
				if(newamount > 0) {
					is.setAmount(newamount);
					break;
				} else {
					inv.remove(is);
					amount = -newamount;
					if(amount == 0) break;
				}
			}
		}
	}
}
