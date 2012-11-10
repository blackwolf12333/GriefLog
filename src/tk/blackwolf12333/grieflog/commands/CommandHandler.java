package tk.blackwolf12333.grieflog.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import tk.blackwolf12333.grieflog.callback.TpToCallback;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;
import tk.blackwolf12333.grieflog.utils.logging.Events;
import tk.blackwolf12333.grieflog.utils.searching.PageManager;
import tk.blackwolf12333.grieflog.utils.searching.SearchTask;

public class CommandHandler {
	
	PlayerSession sender;
	GriefLog plugin;
	
	private String noPermsMsg = ChatColor.DARK_RED + "I am sorry Dave, but i cannot let you do that! You don't have permission.";
	ArrayList<String> searchArgs = new ArrayList<String>();
	
	public CommandHandler(PlayerSession sender) {
		this.sender = sender;
		this.plugin = sender.getGriefLog();
	}
	
	public boolean getVersion() {
		if(sender.getName().equalsIgnoreCase("blackwolf12333")) {
			if(ConfigHandler.values.getBw12333glog()) {
				sender.print(ChatColor.GREEN + "[GriefLog] " + plugin.getDescription().getVersion());
				return true;
			} else {
				sender.print(ChatColor.GREEN + "[GriefLog] Sorry, ya can't view the version, disabled by admins:(");
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
	
	public boolean getHere() {
		if (sender.getPlayer() == null) {
			sender.print(ChatColor.RED + "[GriefLog] This command is only for ingame sessions!");
			return true;
		}
		
		Player p = sender.getPlayer();
		int x = p.getLocation().getBlockX();
		int y = p.getLocation().getBlockY();
		int z = p.getLocation().getBlockZ();
		
		searchArgs.add(x + ", " + y + ", " + z);
		new SearchTask(sender, new SearchCallback(sender), searchArgs);
		return true;
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
				ArrayList<String> arg = new ArrayList<String>();
				
				arg.add(Events.QUIT.getEventName());
				arg.add(to);
				
				new SearchTask(sender, new TpToCallback(sender), arg);
				return true;
			}
		} else {
			sender.print(noPermsMsg);
			return true;
		}
	}
	
	public boolean getXYZ(String x, String y, String z) {
		if(sender.hasPermission("grieflog.get.xyz")) {
			searchArgs.add(x + ", " + y + ", " + z);
			new SearchTask(sender, new SearchCallback(sender), searchArgs);
			
			return true;
		} else {
			sender.print(ChatColor.YELLOW + "Sorry the admins have decided that you can't use this command, use /glog get here instead.");
			return true;
		}
	}
	
	public boolean page(String page) {
		if(sender.hasPermission("grieflog.search")) {
			if(PageManager.pages.size() <= 0) {
				sender.print(ChatColor.RED + "No pages found, Sorry.");
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
	
	public void readReportFile(File file, PlayerSession sender) {
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String line = "";

			sender.print(ChatColor.RED + "+++++++++ReportStart+++++++++");
			while ((line = br.readLine()) != null) {
				sender.print(line);
			}
			sender.print(ChatColor.RED + "++++++++++ReportEnd+++++++++");

			br.close();
			fileReader.close();

		} catch (Exception e) {
			sender.print(ChatColor.DARK_RED + "No Reports have been found!");
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
