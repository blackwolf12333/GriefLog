package tk.blackwolf12333.grieflog.data;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_4_6.CraftChunk;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.data.block.BaseBlockData;
import tk.blackwolf12333.grieflog.data.block.BucketData;
import tk.blackwolf12333.grieflog.data.entity.BaseEntityData;
import tk.blackwolf12333.grieflog.data.hanging.BaseHangingData;
import tk.blackwolf12333.grieflog.data.player.BasePlayerData;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.rollback.Undo;
import tk.blackwolf12333.grieflog.utils.logging.Events;
import tk.blackwolf12333.grieflog.utils.logging.Time;

public abstract class BaseData implements Comparable<BaseData> {

	protected String worldName;
	protected String time;
	protected String event;
	
	/**
	 * @return the worldName
	 */
	public String getWorldName() {
		return worldName;
	}
	
	/**
	 * @param worldName the worldName to set
	 */
	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}
	
	/**
	 * @return Returns the time from this event.
	 */
	public String getTime() {
		return time;
	}
	
	/**
	 * Set's the time of this event(usually not used)
	 * @param time The new time String
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
	/**
	 * @return Returns the event name.
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * Set's the event name.
	 * @param event The new event name.
	 */
	public void setEvent(String event) {
		this.event = event;
	}
	
	/**
	 * Parses a string to be a child of BaseData
	 * @param line The String to parse from.
	 * @return Returns a child of BaseData.
	 */
	public BaseData parse(String line) {
		return loadFromString(line);
	}
	
	/**
	 * Set's a block fast, it doesn't use Bukkit methods and doesn't update light and stuff.
	 * @param loc The location of the block.
	 * @param typeID The new type ID.
	 * @param data The new data.
	 */
	public void setBlockFast(Location loc, int typeID, byte data) {
		setBlockFast(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName().trim(), typeID, data);
	}
	
	/**
	 * Set's a block fast, it doesn't use Bukkit methods and doesn't update light and stuff.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param z The z coordinate.
	 * @param world The world of the block.
	 * @param typeID The new type ID.
	 * @param data The new data.
	 */
	public void setBlockFast(int x, int y, int z, String world, int typeID, byte data) {
		Chunk c = Bukkit.getWorld(world).getChunkAt(x >> 4, z >> 4);
		net.minecraft.server.v1_4_6.Chunk chunk = ((CraftChunk) c).getHandle();
		chunk.a(x & 15, y, z & 15, typeID, data);
	}
	
	@Override
	public int compareTo(BaseData o) {
		try {
			if(Time.getTimeStamp(time).equals(Time.getTimeStamp(o.getTime())))
				return 0;
			else if(Time.getTimeStamp(time).before(Time.getTimeStamp(o.getTime()))) 
				return -1;
			else if(Time.getTimeStamp(time).after(Time.getTimeStamp(o.getTime())))
				return 1;
			else
				return 0;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Parses a string to be a child of BaseData
	 * @param line The String to parse from.
	 * @return Returns a child of BaseData.
	 */
	public static BaseData loadFromString(String line) {
		if(line != null) {
			if(line.contains(Events.BREAK.getEventName()) || line.contains(Events.PLACE.getEventName()) || line.contains(Events.IGNITE.getEventName())) {
				return BaseBlockData.loadFromString(line);
			} else if(line.contains(Events.EXPLODE.getEventName()) || line.contains(Events.ENDERMAN_PICKUP.getEventName()) || line.contains(Events.ENDERMAN_PLACE.getEventName()) || line.contains(Events.ZOMBIEBREAKDOOR.getEventName())) {
				return BaseEntityData.loadFromString(line);
			} else if(line.contains(Events.LAVA.getEventName()) || line.contains(Events.WATER.getEventName())) {
				return BucketData.loadFromString(line);
			} else if(line.contains(Events.PAINTINGBREAK.getEventName()) || line.contains(Events.PAINTINGPLACE.getEventName())) {
				return BaseHangingData.loadFromString(line);
			} else {
				return BasePlayerData.loadFromString(line);
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Rolls the event back to before it happened.
	 * @param rollback The Rollback object that started this rollback.
	 */
	public abstract void rollback(Rollback rollback);
	
	/**
	 * This undoes a rollback done before.
	 * This set's the block like it became after the event happened.
	 */
	public abstract void undo(Undo undo);
	
	/**
	 * Teleports the PlayerSession to the location of this event.
	 * @param who The PlayerSession to teleport.
	 */
	public abstract void tpto(PlayerSession who);
	
	/**
	 * @return Returns a minimal version of this Data object.
	 */
	public abstract String getMinimal();
	
	/**
	 * Add's the colors specified in the config to this Data object.
	 * @param colors A HashMap that contains the ChatColors that we want.
	 * @return Returns the same Data object but than with the colors applied.
	 */
	public abstract BaseData applyColors(HashMap<String, ChatColor> colors);
	
	/**
	 * Checks if this event happened inside the WorldEdit selection of the PlayerSession.
	 * @param player The PlayerSession of whom the selection should be.
	 * @return Returns whether the event indeed happened inside the WorldEdit selection of the PlayerSession.
	 */
	public abstract boolean isInWorldEditSelectionOf(PlayerSession player);
	
	/**
	 * @return Returns the name of the player involved in this event.
	 */
	public abstract String getPlayerName();
}
