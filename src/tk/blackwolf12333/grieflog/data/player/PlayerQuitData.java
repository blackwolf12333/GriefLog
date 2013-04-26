package tk.blackwolf12333.grieflog.data.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class PlayerQuitData extends BasePlayerData {

	Integer x;
	Integer y;
	Integer z;
	
	public PlayerQuitData(String playerName, Integer gamemode, String world, Integer x, Integer y, Integer z) {
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.worldName = world.trim();
		this.x = x;
		this.y = y;
		this.z = z;
		this.event = Events.QUIT.getEventName();
	}
	
	public PlayerQuitData(String time, String playerName, Integer gamemode, String world, Integer x, Integer y, Integer z) {
		this.time = time;
		this.playerName = playerName;
		this.gamemode = gamemode;
		this.worldName = world.trim();
		this.x = x;
		this.y = y;
		this.z = z;
		this.event = Events.QUIT.getEventName();
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
		World world = Bukkit.getWorld(worldName);
		PlayerSession player = who;
		
		player.print(ChatColor.YELLOW + "[GriefLog] Teleporting you to " + playername + ".");
		player.teleport(world.getBlockAt(x, y, z).getLocation());
	}
	
	@Override
	public String getMinimal() {
		return time + " " + playerName + " quit the server.";
	}
	
	@Override
	public Location getLocation() {
		return new Location(Bukkit.getWorld(worldName), x, y, z);
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return time + " " + event + " " + playerName + " GM: " + gamemode + " Where: " + x + ", " + y + ", " + z + " in: " + worldName;
		}
		return " " + event + " " + playerName + " GM: " + gamemode + " Where: " + x + ", " + y + ", " + z + " in: " + worldName;
	}
}
