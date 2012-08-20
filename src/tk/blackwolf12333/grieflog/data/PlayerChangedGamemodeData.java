package tk.blackwolf12333.grieflog.data;

public class PlayerChangedGamemodeData extends BasePlayerData {

	Integer newGamemode;
	
	public PlayerChangedGamemodeData(String playerName, Integer gamemode, String world, Integer newGamemode) {
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.worldName = world;
		this.newGamemode = newGamemode;
	}
	
	@Override
	public void rollback() {
		// do nothing, this can't be rolled back
	}
	
	@Override
	public String toString() {
		return " [GAMEMODE_CHANGE] " + playerName + " New Gamemode: " + newGamemode + " Where: " + worldName + System.getProperty("line.separator");
	}
}
