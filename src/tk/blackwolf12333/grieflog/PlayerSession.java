package tk.blackwolf12333.grieflog;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;
import tk.blackwolf12333.grieflog.utils.searching.ResultPage;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

/**
 * This class contains all kinds of data I need to keep track of.
 * It also contains useful methods to easily do some things.
 * A new instance of this class is created for every player that joins the server.
 * When the player quit's again, the instance is removed.
 * @author blackwolf12333
 *
 */
public class PlayerSession implements Conversable {

	Player player;
	GriefLog plugin;
	ConsoleCommandSender sender;
	HashMap<Integer, ResultPage> pages;
	Conversation conversation;
	Rollback currentRollback;
	public Integer rollbackTaskID;
	
	boolean isDoingRollback = false;
	boolean isSearching = false;
	boolean isUsingTool = false;
	public ArrayList<BaseData> searchResult = new ArrayList<BaseData>();
	
	public PlayerSession(GriefLog plugin, Player player) {
		this.player = player;
		this.plugin = plugin;
	}
	
	public PlayerSession(GriefLog plugin, ConsoleCommandSender sender) {
		this.sender = sender;
		this.plugin = plugin;
	}
	
	/**
	 * Show this instance a message.
	 * If the {@code player} equals to {@code null} the message will be send to {@code sender}.
	 * @param msg The message to be send.
	 */
	public void print(String msg) {
		if(player == null) {
			sender.sendMessage(msg);
		} else {
			player.sendMessage(msg);
		}
	}
	
	/**
	 * Show this instance a message that contains multiple lines.
	 * If the {@code player} equals to {@code null} the message will be send to {@code sender}.
	 * @param msg The message, every String in {@code msg} is one new line.
	 */
	public void print(String[] msg) {
		if(msg != null) {
			for(int i = 0; i < msg.length; i++) {
				if(msg[i] != null) {
					if(player == null) {
						sender.sendMessage(msg[i]);
					} else {
						player.sendMessage(msg[i]);
					}
				}
			}
		}
	}
	
	/**
	 * Show this instance a message that contains multiple lines.
	 * If the {@code player} equals to {@code null} the message will be send to {@code sender}.
	 * @param msg The message in multiple lines existing of BaseData child's.
	 */
	public void print(BaseData[] msg) {
		if(msg != null) {
			for(int i = 0; i < msg.length; i++) {
				if(msg[i] != null) {
					if(player == null) {
						sender.sendMessage(msg[i].toString());
					} else {
						player.sendMessage(msg[i].toString());
					}
				}
			}
		}
	}
	
	/**
	 * Show this instance a message that contains multiple lines in the minimal form of the BaseData.
	 * If the {@code player} equals to {@code null} the message will be send to {@code sender}.
	 * @param msg The message in multiple lines existing of BaseData child's.
	 */
	public void printMinimal(BaseData[] msg) {
		if(msg != null) {
			for(int i = 0; i < msg.length; i++) {
				if(msg[i] != null) {
					if(player == null) {
						sender.sendMessage(msg[i].getMinimal());
					} else {
						player.sendMessage(msg[i].getMinimal());
					}
				}
			}
		}
	}
	
	/**
	 * Show this instance a message that contains multiple lines.
	 * If the {@code player} equals to {@code null} the message will be send to {@code sender}.
	 * @param msg The message, every String in {@code msg} is one new line.
	 */
	public void print(ArrayList<String> msg) {
		if(msg != null) {
			for(int i = 0; i < msg.size(); i++) {
				if(msg.get(i) != null) {
					if(player == null) {
						sender.sendMessage(msg.get(i));
					} else {
						player.sendMessage(msg.get(i));
					}
				}
			}
		}
	}
	
	/**
	 * Teleport the player to {@code to}.
	 * @param to Where to teleport the player to.
	 * @return Returns true if the teleport was successful.
	 */
	public boolean teleport(Location to) {
		return player.teleport(to);
	}
	
	/**
	 * Check's if this player has the permission {@code perm}.
	 * @param perm The permission to check for.
	 * @return Returns true if the player has the permission.
	 */
	public boolean hasPermission(String perm) {
		if(player != null) {
			return player.hasPermission(perm);
		}
		return true;
	}
	
	/**
	 * @return The name of this PlayerSession
	 */
	public String getName() {
		return player != null ? player.getName() : sender.getName();
	}
	
	/** 
	 * @return Returns whether this player is OP or not.
	 */
	public boolean isOp() {
		return player != null ? player.isOp() : true;
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
	
	public ArrayList<BaseData> getSearchResult() {
		return searchResult;
	}
	
	public void setSearchResult(ArrayList<BaseData> result) {
		this.searchResult = result;
	}

	public Selection getWorldEditSelection() {
		WorldEditPlugin we = (WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit");
		return we.getSelection(player);
	}
	
	public Integer getRollbackTaskID() {
		return rollbackTaskID;
	}
	
	public boolean isUsingTool() {
		this.isUsingTool = this.player.getInventory().contains(ConfigHandler.values.getTool()); 
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
	
	public HashMap<Integer, ResultPage> getPages() {
		return pages;
	}
	
	public void setPages(HashMap<Integer, ResultPage> pages) {
		this.pages = pages;
	}
	
	public Conversation getConversation() {
		return conversation;
	}
	
	public void setConversation(Conversation conversation) {
		player.beginConversation(conversation);
		this.conversation = conversation;
	}
	
	public Rollback getCurrentRollback() {
		return currentRollback;
	}
	
	public void setCurrentRollback(Rollback currentRollback) {
		this.currentRollback = currentRollback;
	}
	
	public static PlayerSession getGLPlayer(Player p) {
		return GriefLog.sessions.get(p.getName());
	}
	
	public static PlayerSession getGLPlayer(ConsoleCommandSender sender) {
		return GriefLog.sessions.get(sender.getName());
	}
	
	public static PlayerSession getGLPlayer(String p) {
		return GriefLog.sessions.get(p);
	}
	
	@Override
	public String toString() {
		if(player == null) {
			return "{PlayerSession} name: " + sender.getName() + " rollback: " + isDoingRollback + " search: " + isSearching;
		}
		return "{PlayerSession} name: " + player.getName() + " rollback: " + isDoingRollback + " search: " + isSearching;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof PlayerSession)) {
			return false;
		} else {
			PlayerSession p = (PlayerSession) obj;
			if(this.hashCode() == p.hashCode()) {
				return true;
			} else if(this.toString().equals(p.toString())) {
				return true;
			}
			return super.equals(obj);
		}
	}
	
	@Override
	public int hashCode() {
		String hash = "";
		for(int i = 0; i < player.getName().length(); i++) {
			hash += player.getName().codePointAt(i);
		}
		if(isDoingRollback) {
			hash += "1";
		} else {
			hash += "0";
		}
		return Integer.parseInt(hash);
	}

	@Override
	public void abandonConversation(Conversation conversation) {
		if(player == null) {
			sender.abandonConversation(conversation);
			return;
		}
		player.abandonConversation(conversation);
	}

	@Override
	public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
		if(player == null) {
			sender.abandonConversation(conversation, details);
			return;
		}
		player.abandonConversation(conversation, details);
	}

	@Override
	public void acceptConversationInput(String input) {
		if(player == null) {
			sender.acceptConversationInput(input);
			return;
		}
		player.acceptConversationInput(input);
	}

	@Override
	public boolean beginConversation(Conversation conversation) {
		if(player == null) {
			return sender.beginConversation(conversation);
		}
		return player.beginConversation(conversation);
	}

	@Override
	public boolean isConversing() {
		if(player == null) {
			return sender.isConversing();
		}
		return player.isConversing();
	}

	@Override
	public void sendRawMessage(String message) {
		if(player == null) {
			sender.sendRawMessage(message);
			return;
		}
		player.sendRawMessage(message);
	}
}
