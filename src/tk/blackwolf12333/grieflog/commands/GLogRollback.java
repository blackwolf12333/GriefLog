package tk.blackwolf12333.grieflog.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.utils.Events;
import tk.blackwolf12333.grieflog.utils.Rollback;

public class GLogRollback {

	private String noPermsMsg = ChatColor.DARK_RED + "I am sorry, but i cannot let you do that! You don't have permission.";
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("glog")) {
			// /glog rollback <options>
			if (args[0].equalsIgnoreCase("rollback")) {
				if (sender.isOp() || GriefLog.permission.has(sender, "grieflog.rollback")) {
					if (args[1].equalsIgnoreCase("explosion")) {
						Rollback rb = new Rollback(sender, "[ENTITY_EXPLODE]");
						rb.rollback();
						sender.sendMessage("Tried to rollback the grief!");
						return true;
					} else {
						if(args.length == 2) {
							String[] arguments = args[1].split(":");
							if(arguments[0].equalsIgnoreCase("p")){
								Rollback rb = new Rollback(sender, arguments[1]);
								rb.rollback();
								sender.sendMessage("Tried to rollback the grief!");
								return true;
							} else if(arguments[0].equalsIgnoreCase("e")) {
								Rollback rb = new Rollback(sender, getEventFromAlias(arguments[1]));
								rb.rollback();
								sender.sendMessage("Tried to rollback the grief!");
								return true;
							}
						} else if(args.length == 3) {
							String[] arguments = args[1].split(":");
							String[] arguments2 = args[2].split(":");
							
							if(arguments[0].equalsIgnoreCase("p")) {
								if(arguments2[0].equalsIgnoreCase("e")) {
									Rollback rb = new Rollback(sender, arguments[1], getEventFromAlias(arguments2[1]));
									rb.rollback();
									sender.sendMessage("Tried to rollback the grief!");
									return true;
								}
							} else if(arguments[0].equalsIgnoreCase("e")) {
								if(arguments2[0].equalsIgnoreCase("p")) {
									Rollback rb = new Rollback(sender, arguments2[1], getEventFromAlias(arguments[1]));
									rb.rollback();
									sender.sendMessage("Tried to rollback the grief!");
									return true;
								}
							} else if(arguments2[0].equalsIgnoreCase("p")) {
								if(arguments[0].equalsIgnoreCase("e")) {
									Rollback rb = new Rollback(sender, arguments2[1], getEventFromAlias(arguments[1]));
									rb.rollback();
									sender.sendMessage("Tried to rollback the grief!");
									return true;
								}
							} else if(arguments2[0].equalsIgnoreCase("e")) {
								if(arguments[0].equalsIgnoreCase("p")) {
									Rollback rb = new Rollback(sender, arguments[1], getEventFromAlias(arguments2[1]));
									rb.rollback();
									sender.sendMessage("Tried to rollback the grief!");
									return true;
								}
							}
							sender.sendMessage("Tried to rollback the grief!");
							return true;
						} else if(args.length == 4) {
							String[] arg1 = args[1].split(":");
							String[] arg2 = args[2].split(":");
							String[] arg3 = args[3].split(":");
							
							if(arg1[0].equalsIgnoreCase("p")) {
								if(arg2[0].equalsIgnoreCase("e")) {
									if(arg3[0].equalsIgnoreCase("w")) {
										Rollback rb = new Rollback(sender, arg1[1], arg2[1], arg3[1]);
										rb.rollback();
										sender.sendMessage("Tried to rollback the grief!");
										return true;
									}
								} else if(arg2[0].equalsIgnoreCase("w")) {
									if(arg3[0].equalsIgnoreCase("e")) {
										Rollback rb = new Rollback(sender, arg1[1], arg2[1], arg3[1]);
										rb.rollback();
										sender.sendMessage("Tried to rollback the grief!");
										return true;
									}
								}
							} else if(arg1[0].equalsIgnoreCase("e")) {
								if(arg2[0].equalsIgnoreCase("p")) {
									if(arg3[0].equalsIgnoreCase("w")) {
										Rollback rb = new Rollback(sender, arg1[1], arg2[1], arg3[1]);
										rb.rollback();
										sender.sendMessage("Tried to rollback the grief!");
										return true;
									}
								} else if(arg2[0].equalsIgnoreCase("w")) {
									if(arg3[0].equalsIgnoreCase("p")) {
										Rollback rb = new Rollback(sender, arg1[1], arg2[1], arg3[1]);
										rb.rollback();
										sender.sendMessage("Tried to rollback the grief!");
										return true;
									}
								}
							} else if(arg1[0].equalsIgnoreCase("w")) {
								if(arg2[0].equalsIgnoreCase("p")) {
									if(arg3[0].equalsIgnoreCase("e")) {
										Rollback rb = new Rollback(sender, arg1[1], arg2[1], arg3[1]);
										rb.rollback();
										sender.sendMessage("Tried to rollback the grief!");
										return true;
									}
								} else if(arg2[0].equalsIgnoreCase("e")) {
									if(arg3[0].equalsIgnoreCase("p")) {
										Rollback rb = new Rollback(sender, arg1[1], arg2[1], arg3[1]);
										rb.rollback();
										sender.sendMessage("Tried to rollback the grief!");
										return true;
									}
								}
							}
							
							return true;
						}
					}
				} else {
					sender.sendMessage(noPermsMsg);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public String getEventFromAlias(String alias) {
		for(Events event : Events.values()) {
			for(String a : event.getAlias()) {
				if(alias.equalsIgnoreCase(a)) {
					return event.getEvent();
				}
			}
		}
		return null;
	}
}
