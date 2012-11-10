package tk.blackwolf12333.grieflog.data.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.utils.logging.Events;

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
		this.event = Events.JOIN.getEventName();
	}
	
	public PlayerJoinData(String time, String playerName, Integer gamemode, String world, String ipaddress, Integer x, Integer y, Integer z) {
		this.time = time;
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.worldName = world;
		this.ipaddress = ipaddress;
		this.x = x;
		this.y = y;
		this.z = z;
		this.event = Events.JOIN.getEventName();
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
	public void tpto(PlayerSession who) {
		String playername = playerName;
		World world = Bukkit.getServer().getWorld(worldName);
		Location loc = new Location(world, x, y, z);
		PlayerSession player = who;
		
		player.print(ChatColor.YELLOW + "[GriefLog] Teleporting you to " + playername + ".");
		player.teleport(loc);
	}
	
	@Override
	public String getMinimal() {
		return time + " " + playerName + " logged in with IP " + ipaddress + ".";
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " " + playerName + " On: " + ipaddress + " With GameMode: " + gamemode + " " + x + ", " + y + ", " + z + " in: " + worldName;
		}
		return " " + event + " " + playerName + " On: " + ipaddress + " With GameMode: " + gamemode + " " + x + ", " + y + ", " + z + " in: " + worldName;
	}
}
