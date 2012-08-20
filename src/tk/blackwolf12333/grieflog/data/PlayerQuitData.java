package tk.blackwolf12333.grieflog.data;

public class PlayerQuitData extends BasePlayerData {

	Integer x;
	Integer y;
	Integer z;
	
	public PlayerQuitData(String playerName, Integer gamemode, String world, Integer x, Integer y, Integer z) {
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.worldName = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Integer getX() {
		return x;
	}
	
	public void setX(Integer x) {
		this.x = x;
	}
	
	public Integer getY() {
		return y;
	}
	
	public void setY(Integer y) {
		this.y = y;
	}
	
	public Integer getZ() {
		return z;
	}
	
	public void setZ(Integer z) {
		this.z = z;
	}
	
	@Override
	public void rollback() {
		// do nothing, this can't be rolled back
	}
	
	@Override
	public String toString() {
		return " [PLAYER_QUIT] " + playerName + " GM: " + gamemode + " Where: " + x + ", " + y + ", " + z + " in: " + worldName + System.getProperty("line.separator");
	}
}
