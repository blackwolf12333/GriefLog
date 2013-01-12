package tk.blackwolf12333.grieflog.data.hanging;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.data.OldVersionException;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public abstract class BaseHangingData extends BaseData {

	protected Integer blockX;
	protected Integer blockY;
	protected Integer blockZ;
	protected String xyz;
	protected String playerName;
	protected String hangingType;
	protected String art;
	protected Integer gamemode;
	
	@Override
	public String getPlayerName() {
		return playerName;
	}
	
	@Override
	public String getEvent() {
		return this.event;
	}
	
	@Override
	public String getTime() {
		return this.time;
	}
	
	@Override
	public String getWorldName() {
		return this.worldName;
	}
	
	public String getBlockType() {
		return hangingType;
	}
	
	public Integer getBlockX() {
		return blockX;
	}
	
	public Integer getBlockY() {
		return blockY;
	}
	
	public Integer getBlockZ() {
		return blockZ;
	}
	
	public Integer getGamemode() {
		return gamemode;
	}
	
	public String getXyz() {
		return xyz;
	}
	
	protected Entity getEntityAt(Location loc) {
		Collection<Hanging> hanging = Bukkit.getWorld(worldName).getEntitiesByClass(Hanging.class);
		for(Hanging h : hanging) {
			if(h.getLocation().equals(loc)) {
				return h;
			}
		}
		return null;
	}
	
	@Override
	public void tpto(PlayerSession who) {
		World world = Bukkit.getServer().getWorld(worldName);
		who.print(ChatColor.YELLOW + "[GriefLog] Teleporting you to the event's location.");
		who.teleport(world.getBlockAt(blockX, blockY, blockZ).getLocation());
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
		this.event = colors.get("event") + this.event + ChatColor.WHITE;
		this.playerName = colors.get("player") + playerName + ChatColor.WHITE;
		this.hangingType = colors.get("blockinfo") + hangingType + ChatColor.WHITE;
		this.xyz = colors.get("location") + xyz + ChatColor.WHITE;
		this.worldName = colors.get("world") + worldName + ChatColor.WHITE;
		return this;
	}
	
	public static BaseHangingData loadFromString(String data) {
		if(data.contains(Events.HANGINGBREAK.getEventName())) {
			return handleHangingBreakData(data.split(" "));
		} else if(data.contains(Events.HANGINGPLACE.getEventName())) {
			return handleHangingPlaceData(data.split(" "));
		}
		return null;
	}
	
	private static BaseHangingData handleHangingPlaceData(String[] data) {
		try {
			String time = data[0] + " " + data[1];
			int x = Integer.parseInt(data[10].replaceAll(",", ""));
			int y = Integer.parseInt(data[11].replaceAll(",", ""));
			int z = Integer.parseInt(data[12].replaceAll(",", ""));
			String worldName = data[14].trim();
			String playerName = data[4];
			String hangingType = data[8];
			int gm = Integer.parseInt(data[6]);
			return new HangingPlaceData(time, x, y, z, worldName, playerName, gm, hangingType);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}

	private static BaseHangingData handleHangingBreakData(String[] data) throws OldVersionException {
		try {
			String time = data[0] + " " + data[1];
			int x = Integer.parseInt(data[10].replaceAll(",", ""));
			int y = Integer.parseInt(data[11].replaceAll(",", ""));
			int z = Integer.parseInt(data[12].replaceAll(",", ""));
			String worldName = data[14].trim();
			String playerName = data[4];
			String hangingType = data[8];
			int gm = Integer.parseInt(data[6]);
			return new HangingBreakData(time, x, y, z, worldName, playerName, gm, hangingType);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
}
