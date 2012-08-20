package tk.blackwolf12333.grieflog.data;

public class PlayerCommandData extends BasePlayerData {

	String command;
	
	public PlayerCommandData(String playerName, Integer gamemode, String world, String command) {
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.worldName = world;
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	@Override
	public void rollback() {
		// do nothing, this can't be rolled back
	}
	
	@Override
	public String toString() {
		return " [PLAYER_COMMAND] Who: " + playerName + " GM: " + gamemode + " Command: " + command + " in: " + worldName + System.getProperty("line.separator");
	}
}
