package tk.blackwolf12333.grieflog.data.hanging;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class HangingBreakData extends BaseHangingData {

	public HangingBreakData(int x, int y, int z, String world, String player, int gm, String hangingType) {
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.xyz = x + ", " + y + ", " + z;
		this.worldName = world;
		this.playerName = player;
		this.hangingType = hangingType;
		this.gamemode = gm;
		this.event = Events.PAINTINGBREAK.getEventName();
	}
	
	public HangingBreakData(String time, int x, int y, int z, String world, String player, int gm, String hangingType) {
		this.time = time;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.xyz = x + ", " + y + ", " + z;
		this.worldName = world;
		this.playerName = player;
		this.hangingType = hangingType;
		this.gamemode = gm;
		this.event = Events.PAINTINGBREAK.getEventName();
	}
	
	@Override
	public void rollback(Rollback rollback) {
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		Bukkit.getWorld(worldName).spawnEntity(loc, EntityType.fromName(this.hangingType)); //TODO: bukkit can't spawn paintings this way
	}

	@Override
	public void undo() {
		Location loc = new Location(Bukkit.getWorld(worldName), this.blockX, this.blockY, this.blockZ);
		getEntityAt(loc).remove();
	}

	private Entity getEntityAt(Location loc) {
		for(Entity e : loc.getWorld().getEntities()) {
			e.getLocation().equals(loc);
		}
		return null;
	}

	@Override
	public String getMinimal() {
		return "Player " + this.playerName + " broke a " + this.hangingType + " with gm: " + this.gamemode;
	}

	@Override
	public BaseData applyColors(HashMap<String, ChatColor> colors) {
		return super.applyColors(colors);
	}
	
	@Override
	public String toString() {
		if(this.time != null) {
			return time + " " + this.event + " By: " + this.playerName + " GM: " + this.gamemode + " What: " + hangingType + " Where: " + this.xyz + " in: " + this.worldName;
		}
		return " " + this.event + " By: " + this.playerName + " GM: " + this.gamemode + " What: " + hangingType + " Where: " + this.xyz + " in: " + this.worldName;
	}
}
