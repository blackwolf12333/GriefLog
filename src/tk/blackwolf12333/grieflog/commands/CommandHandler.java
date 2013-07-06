package tk.blackwolf12333.grieflog.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.callback.SearchCallback;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;
import tk.blackwolf12333.grieflog.utils.logging.Events;
import tk.blackwolf12333.grieflog.utils.reports.Report;
import tk.blackwolf12333.grieflog.utils.searching.ArgumentParser;
import tk.blackwolf12333.grieflog.utils.searching.PageManager;
import tk.blackwolf12333.grieflog.utils.searching.SearchTask;

public class CommandHandler {
	
	PlayerSession sender;
	GriefLog plugin;
	
	private String noPermsMsg = ChatColor.DARK_RED + "I am sorry, You do not have permission to run this command.";
	ArrayList<String> searchArgs = new ArrayList<String>();
	
	public CommandHandler(PlayerSession sender) {
		this.sender = sender;
		this.plugin = GriefLog.getGriefLog();
	}
	
	public boolean getVersion() {
		if(sender.getName().equalsIgnoreCase("blackwolf12333")) {
			if(ConfigHandler.values.getBw12333glog()) {
				sender.print(ChatColor.GREEN + "[GriefLog] " + plugin.getDescription().getVersion());
				return true;
			} else {
				sender.print(ChatColor.GREEN + "[GriefLog] Sorry, you can't view the version, disabled by admins.");
				return true;
			}
		} else if(sender.isOp()) {
			sender.print(ChatColor.GREEN + "[GriefLog] " + plugin.getDescription().getVersion());
			return true;
		} else {
			sender.print(ChatColor.YELLOW + "Sorry this command is disabled for normal users!");
			return true;
		}
	}
	
	public boolean reload() {
		if(sender.hasPermission("grieflog.reload")) {
			ConfigHandler.reloadConfig();
			sender.print(ChatColor.GREEN + "[GriefLog] Configuration reloaded.");
			return true;
		} else {
			sender.print(noPermsMsg);
			return true;
		}
	}
	
	public boolean getPositionOf(String player) {
		if(sender.isOp()) {
			for(Player p : plugin.getServer().getOnlinePlayers()) {
				if(p.getName().contains(player)) {
					Block b = p.getLocation().getBlock();
					int x = b.getX();
					int y = b.getY();
					int z = b.getZ();
					
					sender.print(x +  ", " + y + ", " + z);
					return true;
				} else {
					continue;
				}
			}
		} else {
			sender.print(noPermsMsg);
			return true;
		}
		return true;
	}
	
	public boolean getPosition() {
		if (sender.getPlayer() == null) {
			sender.print(ChatColor.RED + "[GriefLog] This command is only for ingame sessions!");
			return true;
		}
		
		Player p = sender.getPlayer();
		Block b = p.getLocation().getBlock();
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		
		p.sendMessage(x + ", " + y + ", " + z);
		return true;
	}
	
	public boolean giveTool() {
		if(sender.hasPermission("grieflog.tool")) {
			if (sender.getPlayer() == null) {
				sender.print(ChatColor.RED + "[GriefLog] This command is only for ingame sessions!");
				return true;
			}
			
			Player p = sender.getPlayer();
			int item = ConfigHandler.values.getTool();
			
			if(sender.isUsingTool()) {
				PlayerInventory inv = p.getInventory();
				removeItemFromInventory(inv, new ItemStack(item));
				
				p.sendMessage(ChatColor.YELLOW + "The GriefLog tool was taken from to you.");
				sender.setUsingTool(false);
				
				return true;
			} else {
				PlayerInventory inv = p.getInventory();
				inv.addItem(new ItemStack(item, 1));
				
				p.sendMessage(ChatColor.YELLOW + "The GriefLog tool was given to you:)");
				sender.setUsingTool(true);
				return true;
			}
		} else {
			sender.print(noPermsMsg);
			return true;
		}
	}
	
	public boolean tpto(String to) {
		if(sender.hasPermission("grieflog.tpto")) {
			if(sender.getPlayer() == null) {
				sender.print(ChatColor.RED + "[GriefLog] This command is only for ingame sessions!");
				return true;
			}
			
			if(teleportIfOnline(sender, to)) {
				sender.print("error");
				return true;
			} else {
				ArgumentParser parser = new ArgumentParser(null);
				
				parser.event = Events.QUIT.getEventName();
				parser.player = to;
				
				new SearchTask(sender, new SearchCallback(sender, SearchCallback.Type.TPTO), parser);
				return true;
			}
		} else {
			sender.print(noPermsMsg);
			return true;
		}
	}
	
	public boolean page(String page) {
		if(sender.hasPermission("grieflog.page")) {
			if(PageManager.pages.size() <= 0) {
				sender.print(ChatColor.RED + "No pages found.");
				return true;
			} else {
				PageManager.printPage(sender, Integer.parseInt(page) - 1);
				return true;
			}
		} else {
			sender.print(noPermsMsg);
			return true;
		}
	}
	
	public boolean viewReports(PlayerSession sender) {
		ArrayList<Report> reports = GriefLog.reporter.getReports();
		if(!sender.hasPermission("grieflog.report.view")) {
			sender.print(noPermsMsg);
			return true;
		} else if(reports.size() == 0) {
			sender.print("No reports found!");
			return true;
		} else {
			for(int i = 0; i < reports.size(); i++) {
				Report r = reports.get(i);
				sender.print("Report " + i + ": x: " + r.getX() + " y: " + r.getY() + " z: " + r.getZ() + " world: " + r.getWorld());
			}
			return true;
		}
	}
	
	private boolean teleportIfOnline(PlayerSession player, String to) {
		for(Player p : plugin.getServer().getOnlinePlayers()) {
			if(p.getName().equalsIgnoreCase(to)) {
				player.teleport(p.getLocation());
				player.print(ChatColor.YELLOW + "[GriefLog] Teleporting you to " + p.getName() + ".");
				return true;
			}
		}
		return false;
	}
	
	public boolean report(PlayerSession player) {
		if(!player.hasPermission("grieflog.report.report")) {
			player.print(noPermsMsg);
			return true;
		} else {
			if(GriefLog.reporter.createReport(player)) {
				player.print(ChatColor.YELLOW + "This grief has been reported. The admins can check it soon.");
			} else {
				player.print(ChatColor.YELLOW + "Your report could not be saved, please try again soon!");
			}
			return true;
		}
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
