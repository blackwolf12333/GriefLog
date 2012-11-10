package tk.blackwolf12333.grieflog.data.player;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class PlayerChangedGamemodeData extends BasePlayerData {

	Integer newGamemode;
	
	public PlayerChangedGamemodeData(String playerName, Integer gamemode, String world, Integer newGamemode) {
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.worldName = world;
		this.newGamemode = newGamemode;
		this.event = Events.GAMEMODE.getEventName();
	}
	
	public PlayerChangedGamemodeData(String time, String playerName, Integer gamemode, String world, Integer newGamemode) {
		this.time = time;
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.worldName = world;
		this.newGamemode = newGamemode;
		this.event = Events.GAMEMODE.getEventName();
	}
	
	@Override
	public void tpto(PlayerSession who) {
		// do nothing, you can't tp to this
	}
	
	@Override
	public String getMinimal() {
		return time + " " + playerName + " changed gamemode to: " + newGamemode + ".";
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " " + playerName + " New Gamemode: " + newGamemode + " Where: " + worldName;
		}
		return " " + event + " " + playerName + " New Gamemode: " + newGamemode + " Where: " + worldName;
	}
}
