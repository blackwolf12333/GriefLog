package tk.blackwolf12333.grieflog.data.player;

import java.util.UUID;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class PlayerChangedWorldData extends BasePlayerData {

	String from;
	
	public PlayerChangedWorldData(String playerName, Integer gamemode, String world, String from) {
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.worldName = world;
		this.from = from;
		this.event = Events.WORLDCHANGE.getEventName();
	}
	
	public PlayerChangedWorldData(String playerName, UUID playerUUID, Integer gamemode, String world, String from) {
		this(playerName, gamemode, world, from);
		this.playerUUID = playerUUID;
	}
	
	public PlayerChangedWorldData(String time, String playerName, Integer gamemode, String world, String from) {
		this(playerName, gamemode, world, from);
		this.time = time;
	}
	
	public PlayerChangedWorldData(String time, String playerName, UUID playerUUID, Integer gamemode, String world, String from) {
		this(time, playerName, gamemode, world, from);
		this.playerUUID = playerUUID;
	}
	
	@Override
	public void tpto(PlayerSession who) {
		// do nothing, you can't tp to this
	}
	
	@Override
	public String getMinimal() {
		return time + " " + playerName + " changed world from " + from + " to " + worldName + ".";
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " Who: " + playerName + " From: " + from + " to: " + worldName;
		}
		return " " + event + " Who: " + playerName + ":" + playerUUID.toString() + " From: " + from + " to: " + worldName;
	}
}
