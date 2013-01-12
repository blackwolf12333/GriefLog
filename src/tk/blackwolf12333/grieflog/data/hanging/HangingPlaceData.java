package tk.blackwolf12333.grieflog.data.hanging;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.rollback.Undo;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class HangingPlaceData extends BaseHangingData {

	public HangingPlaceData(int x, int y, int z, String world, String player, int gm, String hangingType) {
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.xyz = x + ", " + y + ", " + z;
		this.worldName = world;
		this.playerName = player;
		this.hangingType = hangingType;
		this.gamemode = gm;
		this.event = Events.HANGINGPLACE.getEventName();
	}
	
	public HangingPlaceData(String time, int x, int y, int z, String world, String player, int gm, String hangingType) {
		this.time = time;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.xyz = x + ", " + y + ", " + z;
		this.worldName = world;
		this.playerName = player;
		this.hangingType = hangingType;
		this.gamemode = gm;
		this.event = Events.HANGINGPLACE.getEventName();
	}
	
	@Override
	public void rollback(Rollback rollback) {
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		this.getEntityAt(loc).remove();
		rollback.chunks.add(loc.getChunk());
	}

	@Override
	public void undo(Undo undo) {
		Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
		setBlockFast(loc, Material.getMaterial(hangingType).getId(), (byte) 0x0);
		undo.chunks.add(loc.getChunk());
	}
	
	@Override
	public BaseData applyColors(HashMap<String, ChatColor> colors) {
		return super.applyColors(colors);
	}

	@Override
	public String getMinimal() {
		return "Player " + this.playerName + " placed a " + this.hangingType + " with gm: " + this.gamemode;
	}
	
	@Override
	public String toString() {
		if(this.time != null) {
			return time + " " + this.event + " By: " + this.playerName + " GM: " + this.gamemode + " What: " + hangingType + " Where: " + this.xyz + " in: " + this.worldName;
		}
		return " " + this.event + " By: " + this.playerName + " GM: " + this.gamemode + " What: " + hangingType + " Where: " + this.xyz + " in: " + this.worldName;
	}
}
