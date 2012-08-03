package tk.blackwolf12333.grieflog.commands;

//import java.util.ArrayList;

//import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import tk.blackwolf12333.grieflog.GLPlayer;
import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.SearchTask;
import tk.blackwolf12333.grieflog.action.SearchAction;
//import tk.blackwolf12333.grieflog.listeners.BlockListener;
//import tk.blackwolf12333.grieflog.search.GriefLogSearcher;
import tk.blackwolf12333.grieflog.utils.ArgumentParser;
import tk.blackwolf12333.grieflog.utils.Events;
//import tk.blackwolf12333.grieflog.utils.Pages;

public class GLogSearch {

	GriefLog plugin;
	
	public GLogSearch(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("glog")) {
			if(sender.isOp() || GriefLog.permission.has(sender, "grieflog.search")) {
				GLPlayer player = GLPlayer.getGLPlayer(sender);
				//GriefLogSearcher searcher = new GriefLogSearcher(player, plugin);
				
				ArgumentParser parser = new ArgumentParser(args);
				
				new SearchTask(player, new SearchAction(player), parser.getResult());
				/*player.search(false, parser.getResult());
				Pages.makePages(player.getSearchResult());
				String[] page1 = Pages.getPage(0);
				
				Integer timesIgniteTnt = BlockListener.tntIgnited.get(parser.player);
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
				}*/
				
				
				/*if(args.length == 2) {
					String[] arguments = args[1].split(":");
					if(arguments[0].equalsIgnoreCase("p")){
						searcher.searchText(arguments[1]);
						player.search(false, parser.getResult());
						pages.makePages(player.getSearchResult());
						String[] page1 = Pages.getPage(0);
						
						Integer timesIgniteTnt = BlockListener.tntIgnited.get(arguments[1]);
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
						searcher.searchText(getEventFromAlias(arguments[1]));
						pages.makePages(player.getSearchResult());
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
							searcher.searchText(arg[1], getEventFromAlias(arg2[1]));
							pages.makePages(player.getSearchResult());
							String[] page1 = Pages.getPage(0);
							
							Integer timesIgniteTnt = BlockListener.tntIgnited.get(arg[1]);
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
							searcher.searchText(arg2[1], getEventFromAlias(arg[1]));
							pages.makePages(player.getSearchResult());
							String[] page1 = Pages.getPage(0);
							
							Integer timesIgniteTnt = BlockListener.tntIgnited.get(arg2[1]);
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
							searcher.searchText(arg2[1], getEventFromAlias(arg[1]));
							pages.makePages(player.getSearchResult());
							String[] page1 = Pages.getPage(0);
							
							Integer timesIgniteTnt = BlockListener.tntIgnited.get(arg2[1]);
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
							searcher.searchText(arg[1], getEventFromAlias(arg2[1]));
							pages.makePages(player.getSearchResult());
							String[] page1 = Pages.getPage(0);
							
							Integer timesIgniteTnt = BlockListener.tntIgnited.get(arg[1]);
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
								searcher.searchText(arg[1], getEventFromAlias(arg2[1]), arg3[1]);
								pages.makePages(player.getSearchResult());
								String[] page1 = Pages.getPage(0);
								
								Integer timesIgniteTnt = BlockListener.tntIgnited.get(arg[1]);
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
								searcher.searchText(arg[1], getEventFromAlias(arg2[1]), arg3[1]);
								pages.makePages(player.getSearchResult());
								String[] page1 = Pages.getPage(0);
								
								Integer timesIgniteTnt = BlockListener.tntIgnited.get(arg[1]);
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
								searcher.searchText(arg[1], getEventFromAlias(arg2[1]), arg3[1]);
								pages.makePages(player.getSearchResult());
								String[] page1 = Pages.getPage(0);
								
								Integer timesIgniteTnt = BlockListener.tntIgnited.get(arg[1]);
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
								searcher.searchText(arg[1], getEventFromAlias(arg2[1]), arg3[1]);
								pages.makePages(player.getSearchResult());
								String[] page1 = Pages.getPage(0);
								
								Integer timesIgniteTnt = BlockListener.tntIgnited.get(arg[1]);
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
								searcher.searchText(arg[1], getEventFromAlias(arg2[1]), arg3[1]);
								pages.makePages(player.getSearchResult());
								String[] page1 = Pages.getPage(0);
								
								Integer timesIgniteTnt = BlockListener.tntIgnited.get(arg[1]);
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
								searcher.searchText(arg[1], getEventFromAlias(arg2[1]), arg3[1]);
								pages.makePages(player.getSearchResult());
								String[] page1 = Pages.getPage(0);
								
								Integer timesIgniteTnt = BlockListener.tntIgnited.get(arg[1]);
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
				}*/
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
