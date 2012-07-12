package tk.blackwolf12333.grieflog.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.listeners.GLBlockListener;
import tk.blackwolf12333.grieflog.search.GriefLogSearcher;
import tk.blackwolf12333.grieflog.utils.Events;
import tk.blackwolf12333.grieflog.utils.Pages;

public class GLogSearch {

	GriefLog plugin;
	
	public GLogSearch(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("glog")) {
			if(sender.isOp() || GriefLog.permission.has(sender, "grieflog.search")) {
				Pages pages = new Pages();
				GriefLogSearcher searcher = new GriefLogSearcher(GriefLog.players.get(sender.getName()), plugin);
				
				if(args.length == 2) {
					String[] arguments = args[1].split(":");
					if(arguments[0].equalsIgnoreCase("p")){
						pages.makePages(searcher.searchText(arguments[1]));
						String[] page1 = Pages.getPage(0);
						
						Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(arguments[1]);
						if(timesIgniteTnt == null) {
							sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
							sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
							for(String line : page1) {
								if(line != null)
									sender.sendMessage(line);
							}
							sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
							return true;
						} else {
							sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
							sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
							for(String line : page1) {
								if(line != null)
									sender.sendMessage(line);
							}
							sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
							return true;
						}
					} else if(arguments[0].equalsIgnoreCase("e")) {
						pages.makePages(searcher.searchText(getEventFromAlias(arguments[1])));
						String[] page1 = Pages.getPage(0);
						
						sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
						for(String line : page1) {
							if(line != null)
								sender.sendMessage(line);
						}
						sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
						
						return true;
					}
				} else if(args.length == 3) {
					String[] arg = args[1].split(":");
					String[] arg2 = args[2].split(":");
					
					if(arg[0].equalsIgnoreCase("p")) {
						if(arg2[0].equalsIgnoreCase("e")) {
							pages.makePages(searcher.searchText(arg[1], getEventFromAlias(arg2[1])));
							String[] page1 = Pages.getPage(0);
							
							Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(arg[1]);
							if(timesIgniteTnt == null) {
								sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
								sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
								for(String line : page1) {
									if(line != null)
										sender.sendMessage(line);
								}
								sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
								return true;
							} else {
								sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
								sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
								for(String line : page1) {
									if(line != null)
										sender.sendMessage(line);
								}
								sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
								return true;
							}
						}
					} else if(arg[0].equalsIgnoreCase("e")) {
						if(arg2[0].equalsIgnoreCase("p")) {
							pages.makePages(searcher.searchText(arg2[1], getEventFromAlias(arg[1])));
							String[] page1 = Pages.getPage(0);
							
							Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(arg2[1]);
							if(timesIgniteTnt == null) {
								sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
								sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
								for(String line : page1) {
									if(line != null)
										sender.sendMessage(line);
								}
								sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
								return true;
							} else {
								sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
								sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
								for(String line : page1) {
									if(line != null)
										sender.sendMessage(line);
								}
								sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
								return true;
							}
						}
					} else if(arg2[0].equalsIgnoreCase("p")) {
						if(arg[0].equalsIgnoreCase("e")) {
							pages.makePages(searcher.searchText(arg2[1], getEventFromAlias(arg[1])));
							String[] page1 = Pages.getPage(0);
							
							Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(arg2[1]);
							if(timesIgniteTnt == null) {
								sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
								sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
								for(String line : page1) {
									if(line != null)
										sender.sendMessage(line);
								}
								sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
								return true;
							} else {
								sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
								sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
								for(String line : page1) {
									if(line != null)
										sender.sendMessage(line);
								}
								sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
								return true;
							}
						}
					} else if(arg2[0].equalsIgnoreCase("e")) {
						if(arg[0].equalsIgnoreCase("p")) {
							pages.makePages(searcher.searchText(arg[1], getEventFromAlias(arg2[1])));
							String[] page1 = Pages.getPage(0);
							
							Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(arg[1]);
							if(timesIgniteTnt == null) {
								sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
								sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
								for(String line : page1) {
									if(line != null)
										sender.sendMessage(line);
								}
								sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
								return true;
							} else {
								sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
								sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
								for(String line : page1) {
									if(line != null)
										sender.sendMessage(line);
								}
								sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
								return true;
							}
						}
					}
					return true;
				} else if(args.length == 4) {
					String[] arg = args[1].split(":");
					String[] arg2 = args[2].split(":");
					String[] arg3 = args[3].split(":");
					
					if(arg[0].equalsIgnoreCase("p")) {
						if(arg2[0].equalsIgnoreCase("e")) {
							if(arg3[0].equalsIgnoreCase("w")) {
								pages.makePages(searcher.searchText(arg[1], getEventFromAlias(arg2[1]), arg3[1]));
								String[] page1 = Pages.getPage(0);
								
								Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(arg[1]);
								if(timesIgniteTnt == null) {
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
									for(String line : page1) {
										if(line != null)
											sender.sendMessage(line);
									}
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								} else {
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
									for(String line : page1) {
										if(line != null)
											sender.sendMessage(line);
									}
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								}
							}
						} else if(arg2[0].equalsIgnoreCase("w")) {
							if(arg3[0].equalsIgnoreCase("e")) {
								pages.makePages(searcher.searchText(arg[1], getEventFromAlias(arg2[1]), arg3[1]));
								String[] page1 = Pages.getPage(0);
								
								Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(arg[1]);
								if(timesIgniteTnt == null) {
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
									for(String line : page1) {
										if(line != null)
											sender.sendMessage(line);
									}
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								} else {
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
									for(String line : page1) {
										if(line != null)
											sender.sendMessage(line);
									}
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								}
							}
						}
					} else if(arg[0].equalsIgnoreCase("e")) {
						if(arg2[0].equalsIgnoreCase("p")) {
							if(arg3[0].equalsIgnoreCase("w")) {
								pages.makePages(searcher.searchText(arg[1], getEventFromAlias(arg2[1]), arg3[1]));
								String[] page1 = Pages.getPage(0);
								
								Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(arg[1]);
								if(timesIgniteTnt == null) {
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
									for(String line : page1) {
										if(line != null)
											sender.sendMessage(line);
									}
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								} else {
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
									for(String line : page1) {
										if(line != null)
											sender.sendMessage(line);
									}
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								}
							}
						} else if(arg2[0].equalsIgnoreCase("w")) {
							if(arg3[0].equalsIgnoreCase("p")) {
								pages.makePages(searcher.searchText(arg[1], getEventFromAlias(arg2[1]), arg3[1]));
								String[] page1 = Pages.getPage(0);
								
								Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(arg[1]);
								if(timesIgniteTnt == null) {
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
									for(String line : page1) {
										if(line != null)
											sender.sendMessage(line);
									}
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								} else {
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
									for(String line : page1) {
										if(line != null)
											sender.sendMessage(line);
									}
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								}
							}
						}
					} else if(arg[0].equalsIgnoreCase("w")) {
						if(arg2[0].equalsIgnoreCase("p")) {
							if(arg3[0].equalsIgnoreCase("e")) {
								pages.makePages(searcher.searchText(arg[1], getEventFromAlias(arg2[1]), arg3[1]));
								String[] page1 = Pages.getPage(0);
								
								Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(arg[1]);
								if(timesIgniteTnt == null) {
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
									for(String line : page1) {
										if(line != null)
											sender.sendMessage(line);
									}
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								} else {
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
									for(String line : page1) {
										if(line != null)
											sender.sendMessage(line);
									}
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								}
							}
						} else if(arg2[0].equalsIgnoreCase("e")) {
							if(arg3[0].equalsIgnoreCase("p")) {
								pages.makePages(searcher.searchText(arg[1], getEventFromAlias(arg2[1]), arg3[1]));
								String[] page1 = Pages.getPage(0);
								
								Integer timesIgniteTnt = GLBlockListener.tntIgnited.get(arg[1]);
								if(timesIgniteTnt == null) {
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited 0 blocks of TNT.");
									for(String line : page1) {
										if(line != null)
											sender.sendMessage(line);
									}
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								} else {
									sender.sendMessage(ChatColor.DARK_GREEN + "+++++++SearchResults++++++++");
									sender.sendMessage(ChatColor.YELLOW + "This player has ignited " + timesIgniteTnt + " blocks of TNT.");
									for(String line : page1) {
										if(line != null)
											sender.sendMessage(line);
									}
									sender.sendMessage(ChatColor.DARK_GREEN + "++++++SearchResultsEnd++++++");
									return true;
								}
							}
						}
					}
					
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
