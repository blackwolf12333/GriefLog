package tk.blackwolf12333.grieflog.commands;

import java.io.File;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.utils.*;


public class GLog implements CommandExecutor {

	public GriefLog gl;
	FileUtils fu = new FileUtils();
	Time t = new Time();
	
	public String[] helpTxt = {"+++++++++++++ [GriefLog] ++++++++++++++++",
							"Commands:",
							"/glog: This gets the current version of GriefLog.",
							"/glog get here: This gets the events from the block you are currently standing in.",
							"/glog get x y z: Here you have to fill in the x y and z coordinates yourself.",
							"/glog rollback <playername> (Warning, this can't be undone)",
							"/glog pos: Gets your current position.",
							"/glog report here: Not functioning yet.",
							"/glog report x y z: Fill in the x y and z coordinates yourself, this will report the block you point to using the coordinates, it will  tell the admins you reported a griefer so they can look at it.",
							"/glog tool: Gives you the grieflog tool with what you can check who griefed something."};
	
	public GLog(GriefLog plugin) {
		gl = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel,
			String[] args) {
		
		// inside this if statement all the magic happens
		if(cmd.getName().equalsIgnoreCase("glog"))
		{
			try {
				// /glog get x y z
				if(args.length == 4) {
					if(args[0].equalsIgnoreCase("get"))
					{
						// checks if the sender is a player, if not, return false
						if(!(sender instanceof Player))
						{
							return false;
						}
						
						String x = args[1];
						String y = args[2];
						String z = args[3];
						
						File file = new File("logs/");
						String[] list = file.list();
						// check if there are other file through which we have to search through too
						// if not search through the current log
						if(list == null)
						{
							fu.searchText(x+", "+y+", "+z, GriefLog.file, (Player)sender);
							return true;
						}
						for(int i = 0; i < list.length; i++)
						{
							// search through the other logfiles, if the searched text is found break out this for loop
							if(fu.searchText(x + ", " + y + ", " + z, new File("logs" + File.separator + list[i]), (Player)sender))
							{
								break;
							}
						}
						
						return true;
					}
				}
			
				// /glog get here
				if(args.length == 2)
				{
					// get command
					if(args[0].equalsIgnoreCase("get"))
					{
						// check the second argument
						if(args[1].equalsIgnoreCase("here"))
						{
							// checks if the sender is a player, if not, return false
							if(!(sender instanceof Player))
							{
								return false;
							}
							
							// declare variables
							Player p = (Player)sender;
							String pos = p.getLocation().getBlockX() + ", " + p.getLocation().getBlockY() + ", " + p.getLocation().getBlockZ();
							File file = new File("logs/");
							String[] list = file.list();
							
							// check if there are other file through which we have to search through too
							// if not search through the current log
							if(list == null)
							{
								fu.searchText(pos, GriefLog.file, p);
								return true;
							}
							for(int i = 0; i < list.length; i++)
							{
								// search through the other logfiles, if the searched text is found break out this for loop
								if(fu.searchText(pos, new File("logs" + File.separator + list[i]), p))
								{
									break;
								}
							}
							
							return true;
						}
					}
				}
				
				// /glog
				if(args.length == 0)
				{
					sender.sendMessage("[GriefLog] " + gl.version);
					return true;
				}
				
				// /glog help/reload
				if(args.length == 1)
				{
					// check if the player requests help
					if(args[0].equalsIgnoreCase("help"))
					{
						// if so, send him the help text
						sender.sendMessage(helpTxt);
						return true;
					}
					else if(args[0].equalsIgnoreCase("reload"))
					{
						gl.reloadConfig();
					}
				}
				
				// /glog rollback <playername>
				if(args.length == 2)
				{
					if(args[0].equalsIgnoreCase("rollback"))
					{
						Player p = (Player)sender;
						if(!p.isOp())
						{
							p.sendMessage("You can't use this command if you aren't OP!");
							return true;
						}
						else
						{
							Rollback rb = new Rollback(gl);
							rb.rollback(args[1], p, GriefLog.file);
							return true;
						}
					}
				}
				
				if(args.length == 4)
				{
					if(args[0].equalsIgnoreCase("rollback"))
					{
						Player p = (Player)sender;
						if(!p.isOp())
						{
							p.sendMessage("You can't use this command if you aren't OP!");
							return true;
						}
						else
						{
							long from = t.getTimeStamp(args[2] + " " + args[3]);
							Rollback rb = new Rollback(gl);
							rb.rollback(args[1], p, GriefLog.file, from);
							return true;
						}
					}
				}
				
				// /glog report here
				if(args.length == 2)
				{
					if(args[0].equalsIgnoreCase("report"))
					{
						if(args[1].equalsIgnoreCase("here"))
						{
							Player p = (Player) sender;					
							
							// get the x, y, z coordinates
							int x = p.getLocation().getBlockX();
							int y = p.getLocation().getBlockY();
							int z = p.getLocation().getBlockZ();
							
							String result = fu.searchText(x + ", " + y + ", " + z, GriefLog.file);
							
							// check if the result of searching through the current file didn´t return anything
							if(result == null || result == "")
							{
								// if so, search through the other files
								File file = new File("logs/");
								String[] list = file.list();
								for(int i = 0; i < list.length; i++)
								{
									result = fu.searchText(x+", "+y+", "+z, new File("logs" + File.separator + list[i]));
									// check if the result isn´t null
									if(result != null)
									{
										// if it isn't null, report it by writing it to the file
										fu.writeFile(GriefLog.reportFile, result, false);
										fu.writeFile(GriefLog.reportFile, "", true);
										fu.writeFile(GriefLog.reportFile, "Reported by: " + p.getName());
										fu.writeFile(GriefLog.reportFile, "", true);
										break;
									}
								}
							}
							else
							{
								// if the result wasn't null, report it directly
								fu.writeFile(GriefLog.reportFile, result, false);
								fu.writeFile(GriefLog.reportFile, "", true);
								fu.writeFile(GriefLog.reportFile, "Reported by: " + p.getName());
								fu.writeFile(GriefLog.reportFile, "", true);
							}
							
							return true;
						}
					}
				}
				
				// /glog report x y z
				if(args.length == 4)
				{
					if(args[0].equalsIgnoreCase("report"))
					{
						Player p = (Player) sender;
						String x = args[1];
						String y = args[2];
						String z = args[3];
						
						String result = fu.searchText(x + ", " + y + ", " + z, GriefLog.file);
						
						if(result == null || result == "")
						{
							File file = new File("logs/");
							String[] list = file.list();
							for(int i = 0; i < list.length; i++)
							{
								result = fu.searchText(x+", "+y+", "+z, new File("logs" + File.separator + list[i]));
								if(result != null)
								{
									fu.writeFile(GriefLog.reportFile, result, false);
									fu.writeFile(GriefLog.reportFile, "", true);
									fu.writeFile(GriefLog.reportFile, "Reported by: " + p.getName());
									fu.writeFile(GriefLog.reportFile, "", true);
									break;
								}
							}
						}
						else
						{
							fu.writeFile(GriefLog.reportFile, result, false);
							fu.writeFile(GriefLog.reportFile, "", true);
							fu.writeFile(GriefLog.reportFile, "Reported by: " + p.getName());
							fu.writeFile(GriefLog.reportFile, "", true);
						}
						
						return true;
					}
				}
				
				// /glog tool
				if(args.length == 1)
				{
					if(args[0].equalsIgnoreCase("tool"))
					{
						Player p = (Player) sender;
						int item = gl.getConfig().getInt("SelectionTool");
						
						// add a item to the players inventory
						PlayerInventory inv = p.getInventory();
						inv.addItem(new ItemStack(item,1));
						
						return true;
					}
				}
				
				// /glog pos
				if(args.length == 1)
				{
					if(args[0].equalsIgnoreCase("pos"))
					{
						if (!(sender instanceof Player)) return true;
						
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
				if(args.length == 1)
				{
					if(args[0].equalsIgnoreCase("read"))
					{
						Player p = (Player)sender;
						
						// check if the player issuing the command is a Op
						if(!p.isOp())
						{
							p.sendMessage("You cannot use this command because you aren't Op");
							return true;
						}
						
						try {
							// read the file using the readFile(File, Player) function
							fu.readFile(GriefLog.reportFile, p);
							return true;
						} catch (Exception e) {
							p.sendMessage("No report files have been found:)");
							GriefLog.log.warning("File Not Found Exception, the file: " + GriefLog.reportFile.getName() + " could not be found.");
						}
					}
				}
				
				// /glog delete
				if(args.length == 1)
				{
					if(args[0].equalsIgnoreCase("delete"))
					{
						// check if the player issuing the command is a Op
						if(!sender.isOp())
						{
							sender.sendMessage("You cannot use this command because you aren't Op");
							return true;
						}
						else
						{
							// check if the file could be deleted, if not, tell the sender of the command
							if(!GriefLog.reportFile.delete())
								sender.sendMessage("Report file could not be deleted:(");
							sender.sendMessage("Report file is deleted");
							return true;
						}
					}
				}
			} catch(CommandException e) {
				GriefLog.log.warning(e.getMessage());
			}
		}
		return false;
	}
}