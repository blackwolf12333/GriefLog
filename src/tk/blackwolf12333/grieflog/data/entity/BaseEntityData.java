package tk.blackwolf12333.grieflog.data.entity;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.data.OldVersionException;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public abstract class BaseEntityData extends BaseData {

	Integer blockX;
	Integer blockY;
	Integer blockZ;
	String xyz;
	String entityType;
	String blockType;
	byte blockData;
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public byte getBlockData() {
		return blockData;
	}
	
	public void setBlockData(byte blockData) {
		this.blockData = blockData;
	}
	
	public String getBlockType() {
		return blockType;
	}
	
	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}
	
	public String getEntityType() {
		return entityType;
	}
	
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	public String getWorld() {
		return worldName;
	}
	
	public void setWorld(String world) {
		this.worldName = world;
	}
	
	public Integer getX() {
		return blockX;
	}
	
	public void setX(Integer x) {
		this.blockX = x;
	}
	
	public Integer getY() {
		return blockY;
	}
	
	public void setY(Integer y) {
		this.blockY = y;
	}
	
	public Integer getZ() {
		return blockZ;
	}
	
	public void setZ(Integer z) {
		this.blockZ = z;
	}
	
	@Override
	public String getPlayerName() {
		return null;
	}
	
	public abstract void rollback(Rollback rollback);
	
	public void tpto(PlayerSession who) {
		who.teleport(Bukkit.getWorld(worldName).getBlockAt(blockX, blockY, blockZ).getLocation());
	}
	
	@Override
	public boolean isInWorldEditSelectionOf(PlayerSession player) {
		World w = Bukkit.getWorld(worldName);
		if(w != null) {
			Location loc = new Location(w, blockX, blockY, blockZ);
			return player.getWorldEditSelection().contains(loc);
		} else {
			return false;
		}
	}
	
	@Override
	public BaseData applyColors(HashMap<String, ChatColor> colors) {
		this.time = colors.get("time") + time + ChatColor.WHITE;
		this.event = colors.get("event") + event + ChatColor.WHITE;
		this.blockType = colors.get("blockinfo") + blockType + ChatColor.WHITE;
		this.xyz = colors.get("location") + xyz + ChatColor.WHITE;
		this.worldName = colors.get("world") + worldName + ChatColor.WHITE;
		return this;
	}
	
	@Override
	public Location getLocation() {
		return new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
	}
	
	public static BaseEntityData loadFromString(String line) {
		try {
			if(line.contains(Events.EXPLODE.getEventName())) {
				return handleEntityExplodeData(line.split(" "));
			} else if(line.contains(Events.ZOMBIEBREAKDOOR.getEventName())) {
				return handleEntityBreakDoorData(line.split(" "));
			} else if(line.contains(Events.ENDERMAN_PICKUP.getEventName()) || line.contains(Events.ENDERMAN_PLACE.getEventName())) {
				return handleEntityEndermanData(line.split(" "));
			}
		} catch(OldVersionException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static EntityExplodeData handleEntityExplodeData(String[] data) throws OldVersionException {
		try {
			String time = data[0] + " " + data[1];
			String strX = data[10].replace(",", "");
			String strY = data[11].replace(",", "");
			String strZ = data[12].replace(",", "");
			String entityType = data[6];
			String[] typeAndData = data[8].split(":");
			String type = typeAndData[0];
			byte blockData = Byte.parseByte(typeAndData[1]);
			String world = data[14].trim();
			String playerName = data[4];
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			return new EntityExplodeData(time, x, y, z, world, type, blockData, entityType, playerName);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
	
	private static EntityBreakDoorData handleEntityBreakDoorData(String[] data) throws OldVersionException {
		try {
			String time = data[0] + " " + data[1];
			String strX = data[6].replace(",", "");
			String strY = data[7].replace(",", "");
			String strZ = data[8].replace(",", "");
			String entityType = data[4];
			String world = data[10].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			return new EntityBreakDoorData(time, x, y, z, world, entityType);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
	
	private static EntityEndermanData handleEntityEndermanData(String[] data) throws OldVersionException {
		try {
			String time = data[0] + " " + data[1];
			boolean pickup = Arrays.asList(data).contains(Events.ENDERMAN_PICKUP.getEventName());
			String type = data[4].split(":")[0];
			byte blockData = Byte.parseByte(data[4].split(":")[1]);
			int x = Integer.parseInt(data[6].replace(",", ""));
			int y = Integer.parseInt(data[7].replace(",", ""));
			int z = Integer.parseInt(data[8].replace(",", ""));
			String world = data[10].trim();
			return new EntityEndermanData(time, x, y, z, world, type, blockData, pickup);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
}
