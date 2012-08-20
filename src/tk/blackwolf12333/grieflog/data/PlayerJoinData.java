package tk.blackwolf12333.grieflog.data;

public class PlayerJoinData extends BasePlayerData {

	String ipaddress;
	Integer x;
	Integer y;
	Integer z;
	
	public PlayerJoinData(String playerName, Integer gamemode, String world, String ipaddress, Integer x, Integer y, Integer z) {
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.worldName = world;
		this.ipaddress = ipaddress;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String getIpaddress() {
		return ipaddress;
	}
	
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
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
		return " [PLAYER_LOGIN] " + playerName + " On: " + ipaddress + " With GameMode: " + gamemode + " " + x + ", " + y + ", " + z + " in: " + worldName + System.getProperty("line.separator");
	}
}
