package tk.blackwolf12333.grieflog.data;

public class PlayerChangedWorldData extends BasePlayerData {

	String from;
	
	public PlayerChangedWorldData(String playerName, Integer gamemode, String world, String from) {
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.worldName = world;
		this.from = from;
	}
	
	@Override
	public void rollback() {
		// do nothing, this can't be rolled back
	}
	
	@Override
	public String toString() {
		return " [WORLD_CHANGE] Who: " + playerName + " From: " + from + " to: " + worldName + System.getProperty("line.separator");
	}
}
