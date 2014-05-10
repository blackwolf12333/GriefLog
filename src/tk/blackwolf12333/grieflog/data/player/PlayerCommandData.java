package tk.blackwolf12333.grieflog.data.player;

import java.util.UUID;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class PlayerCommandData extends BasePlayerData {

	String command;
	
	public PlayerCommandData(String playerName, Integer gamemode, String world, String command) {
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.worldName = world;
		this.command = command;
		this.event = Events.COMMAND.getEventName();
	}
	
	public PlayerCommandData(String playerName, UUID playerUUID, Integer gamemode, String world, String command) {
		this(playerName, gamemode, world, command);
		this.playerUUID = playerUUID;
	}
	
	public PlayerCommandData(String time, String playerName, Integer gamemode, String world, String command) {
		this(playerName, gamemode, world, command);
		this.time = time;
	}
	
	public PlayerCommandData(String time, String playerName, UUID playerUUID, Integer gamemode, String world, String command) {
		this(time, playerName, gamemode, world, command);
		this.playerUUID = playerUUID;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	@Override
	public void tpto(PlayerSession who) {
		// do nothing, you can't tp to this
	}
	
	@Override
	public String getMinimal() {
		return time + " " + playerName + " did the events_command " + command + ".";
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " Who: " + playerName + " GM: " + gamemode + " Command: " + command + " in: " + worldName;
		}
		return " " + event + " Who: " + playerName + ":" + playerUUID.toString() + " GM: " + gamemode + " Command: " + command + " in: " + worldName;
	}
}
