package tk.blackwolf12333.grieflog;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import tk.blackwolf12333.grieflog.action.RollbackAction;
import tk.blackwolf12333.grieflog.action.SearchAction;
import tk.blackwolf12333.grieflog.action.WorldEditFilterAction;

public class GLPlayer {

	World world;
	Player player;
	GriefLog plugin;
	CommandSender sender;
	Boolean worldedit;
	String[][] pages;
	public Integer rollbackTaskID;
	public Thread worldeditFilter;
	public Thread searchTask;
	public Thread rollbackThread;
	
	boolean isDoingRollback = false;
	boolean isSearching = false;
	boolean isUsingTool = false;
	public boolean we = false;
	public HashMap<Location, String> playersIgnitedTNT = new HashMap<Location, String>();
	public ArrayList<String> result = new ArrayList<String>();
	
	public GLPlayer(GriefLog plugin, Player player) {
		this.player = player;
		this.plugin = plugin;
	}
	
	public GLPlayer(GriefLog plugin, CommandSender sender) {
		this.sender = sender;
		this.plugin = plugin;
	}
	
	public void search(boolean we, ArrayList<String> args) {
		String[] arg = new String[args.size()];
		for(int i = 0; i < args.size(); i++) {
			arg[i] = args.get(i);
		}
		
		new SearchTask(this, new SearchAction(this), arg);
	}
	
	public void rollback(boolean we, ArrayList<String> args) {
		String[] arg = new String[args.size()];
		for(int i = 0; i < args.size(); i++) {
			arg[i] = args.get(i);
		}
		
		if(we) {
			new SearchTask(this, new WorldEditFilterAction(this), arg);
		} else {
			new SearchTask(this, new RollbackAction(this), arg);
		}
	}
	
	/*@SuppressWarnings("unused")
	private boolean rollback2(boolean we, ArrayList<String> args) {
		if(this.isSearching()) {
			rollback2(we, args);
			return false;
		} else if(this.worldeditFilter.isAlive()) {
			rollback2(we, args);
			return false;
		} else {
			isDoingRollback = true;
			
			if(we) {
				worldeditFilter = new Thread(new WorldEditFilter(result, plugin, this));
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, worldeditFilter);
				
				player.sendMessage(ChatColor.YELLOW + "[GriefLog] Now going to rollback " + result.size() + " events!");
				Rollback rb = new Rollback(plugin, this);
			} else {
				player.sendMessage(ChatColor.YELLOW + "[GriefLog] Now going to rollback " + result.size() + " events!");
				Rollback rb = new Rollback(plugin, this);
			}
			result = null;
			
			isDoingRollback = false;
			
			return true;
		}
	}*/
	
	public void print(String msg) {
		player.sendMessage(msg);
	}
	
	public void print(String[] msg) {
		if(msg != null) {
			for(int i = 0; i < msg.length; i++) {
				if(msg[i] != null) {
					player.sendMessage(msg[i]);
				}
			}
		}
	}
	
	public void print(ArrayList<String> msg) {
		if(msg != null) {
			for(int i = 0; i < msg.size(); i++) {
				if(msg.get(i) != null) {
					player.sendMessage(msg.get(i));
				}
			}
		}
	}
	
	public boolean teleport(Location to) {
		return player.teleport(to);
	}
	
	public GriefLog getGriefLog() {
		return plugin;
	}
	
	public World getWorld() {
		return player.getWorld();
	}
	
	public Server getServer() {
		return player.getServer();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ArrayList<String> getSearchResult() {
		return result;
	}
	
	public void setSearchResult(ArrayList<String> result) {
		this.result = result;
	}
	
	public Selection getWorldEditSelection() {
		WorldEditPlugin we = (WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit");
		return we.getSelection(player);
	}
	
	public Integer getRollbackTaskID() {
		return rollbackTaskID;
	}
	
	public Thread getSearchTask() {
		return searchTask;
	}
	
	public boolean isUsingTool() {
		return isUsingTool;
	}
	
	public void setUsingTool(boolean isUsingTool) {
		this.isUsingTool = isUsingTool;
	}
	
	public boolean isDoingRollback() {
		return isDoingRollback;
	}
	
	public void setDoingRollback(boolean rollback) {
		this.isDoingRollback = rollback;
	}
	
	public boolean isSearching() {
		return isSearching;
	}
	
	public void setSearching(boolean searching) {
		this.isSearching = searching;
	}
	
	public String[][] getPages() {
		return pages;
	}
	
	public void setPages(String[][] pages) {
		this.pages = pages;
	}
	
	public static GLPlayer getGLPlayer(Player p) {
		return GriefLog.players.get(p.getName());
	}
	
	public static GLPlayer getGLPlayer(CommandSender sender) {
		return GriefLog.players.get(sender.getName());
	}
	
	public static GLPlayer getGLPlayer(String p) {
		return GriefLog.players.get(p);
	}
	
	@Override
	public String toString() {
		return "{GLPlayer} name: " + player.getName() + " rollback: " + isDoingRollback + " search: " + isSearching;
	}
	
	/**
	 * This equals if the object is an instance of GLPlayer,
	 * if the hashcode of this GLPlayer == hashcode of the object,
	 * if the toString()'s are equal 
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof GLPlayer)) {
			return false;
		} else {
			GLPlayer p = (GLPlayer) obj;
			if(this.hashCode() == p.hashCode()) {
				return true;
			} else if(this.toString().equals(p.toString())) {
				return true;
			}
			return super.equals(obj);
		}
	}
}
