package tk.blackwolf12333.grieflog.data.player;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.data.OldVersionException;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.rollback.Undo;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public abstract class BasePlayerData extends BaseData {

	Integer gamemode;
	String playerName;
	UUID playerUUID;
	
	public Integer getGamemode() {
		return gamemode;
	}
	
	public void setGamemode(Integer gamemode) {
		this.gamemode = gamemode;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	@Override
	public UUID getPlayerUUID() {
		return playerUUID;
	}
	
	@Override
	public void rollback(Rollback rollback) {
		// do nothing cannot rollback player data
	}
	
	@Override
	public void undo(Undo undo) {
		// cannot undo player data
	}
	
	@Override
	public boolean isInWorldEditSelectionOf(PlayerSession player) {
		return false;
	}
	
	public abstract void tpto(PlayerSession who);
	
	@Override
	public BaseData applyColors(HashMap<String, ChatColor> colors) {
		this.time = colors.get("time") + time + ChatColor.WHITE;
		this.event = colors.get("event") + event + ChatColor.WHITE;
		this.playerName = colors.get("player") + playerName + ChatColor.WHITE;
		this.worldName = colors.get("world") + worldName + ChatColor.WHITE;
		return this;
	}
	
	@Override
	public Location getLocation() {
		return null;
	}
	
	public static BasePlayerData loadFromString(String line) {
		try {
			if(line.contains(Events.JOIN.getEventName())) {
				return handlePlayerJoinData(line.split(" "));
			} else if(line.contains(Events.QUIT.getEventName())) {
				return handlePlayerQuitData(line.split(" "));
			} else if(line.contains(Events.COMMAND.getEventName())) {
				return handlePlayerCommandData(line);
			} else if(line.contains(Events.GAMEMODE.getEventName())) {
				return handlePlayerChangedGamemodeData(line.split(" "));
			} else if(line.contains(Events.WORLDCHANGE.getEventName())) {
				return handlePlayerChangedWorldData(line.split(" "));
			} else if(line.contains(Events.CHESTACCESS.getEventName())) {
				return handleChestAccess(line.split(" "));
			}
		} catch(OldVersionException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static BasePlayerData handleChestAccess(String[] data) throws OldVersionException {
		try {
			String time = data[0] + " " + data[1];
			String playerName = data[4];
			UUID playerUUID = null;
			if(playerName.contains(":")) {
				playerUUID = UUID.fromString(playerName.split(":")[1]);
				playerName = playerName.split(":")[0];
			}
			String taken = data[6];
			String put = data[8];
			Integer x = Integer.parseInt(data[10].replaceAll(",", ""));
			Integer y = Integer.parseInt(data[11].replaceAll(",", ""));
			Integer z = Integer.parseInt(data[12].replaceAll(",", ""));
			String world = data[14];
			if(playerUUID != null) {
				return new ChestAccessData(time, playerName, playerUUID, x, y, z, world, taken, put);
			}
			return new ChestAccessData(time, playerName, x, y, z, world, taken, put);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}

	private static PlayerJoinData handlePlayerJoinData(String[] data) throws OldVersionException {
		try {
			String time = data[0] + " " + data[1];
			String playerName = data[3];
			UUID playerUUID = null;
			if(playerName.contains(":")) {
				playerUUID = UUID.fromString(playerName.split(":")[1]);
				playerName = playerName.split(":")[0];
			}
			Integer gamemode = Integer.parseInt(data[8]);
			String ipaddress = data[5];
			Integer x = Integer.parseInt(data[9].replace(",", ""));
			Integer y = Integer.parseInt(data[10].replace(",", ""));
			Integer z = Integer.parseInt(data[11].replace(",", ""));
			String world = data[13].trim();
			if(playerUUID != null) {
				return new PlayerJoinData(time, playerName, playerUUID, gamemode, world, ipaddress, x, y, z);
			}
			return new PlayerJoinData(time, playerName, gamemode, world, ipaddress, x, y, z);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
	
	private static PlayerQuitData handlePlayerQuitData(String[] data) throws OldVersionException {
		try {
			String time = data[0] + " " + data[1];
			Integer x = Integer.parseInt(data[7].replace(",", ""));
			Integer y = Integer.parseInt(data[8].replace(",", ""));
			Integer z = Integer.parseInt(data[9].replace(",", ""));
			String world = data[11].trim();
			String playerName = data[3];
			UUID playerUUID = null;
			if(playerName.contains(":")) {
				playerUUID = UUID.fromString(playerName.split(":")[1]);
				playerName = playerName.split(":")[0];
			}
			Integer gamemode = Integer.parseInt(data[5]);
			if(playerUUID != null) {
				return new PlayerQuitData(time, playerName, playerUUID, gamemode, world, x, y, z);
			}
			return new PlayerQuitData(time, playerName, gamemode, world, x, y, z);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
	
	private static PlayerCommandData handlePlayerCommandData(String line) throws OldVersionException {
		try {
			String[] data = line.split(" ");
			String time = data[0] + " " + data[1];
			String command = getCommand(line);
			String playerName = data[4];
			UUID playerUUID = null;
			if(playerName.contains(":")) {
				playerUUID = UUID.fromString(playerName.split(":")[1]);
				playerName = playerName.split(":")[0];
			}
			String world = data[data.length - 1].trim();
			Integer gamemode = Integer.parseInt(data[6]);
			if(playerUUID != null) {
				return new PlayerCommandData(time, playerName, playerUUID, gamemode, world, command);
			}
			return new PlayerCommandData(time, playerName, gamemode, world, command);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
	
	private static String getCommand(String data) {
		String ret = "";
		try {
			int i = 0;
			for(String s : data.split(" ")) {
				if(i >= 8) {
					if(!s.equals("in:")) {
						ret += s + " ";
					} else {
						break;
					}
				}
				i++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret.trim();
	}

	private static PlayerChangedGamemodeData handlePlayerChangedGamemodeData(String[] data) throws OldVersionException {
		try {
			String time = data[0] + " " + data[1];
			Integer newGamemode = Integer.parseInt(data[6]);
			String playerName = data[3];
			UUID playerUUID = null;
			if(playerName.contains(":")) {
				playerUUID = UUID.fromString(playerName.split(":")[1]);
				playerName = playerName.split(":")[0];
			}
			Integer gamemode = 0; // is not set in the data
			String worldName = data[data.length - 1].trim();
			if(playerUUID != null) {
				return new PlayerChangedGamemodeData(time, playerName, playerUUID, gamemode, worldName, newGamemode);
			}
			return new PlayerChangedGamemodeData(time, playerName, gamemode, worldName, newGamemode);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
	
	private static PlayerChangedWorldData handlePlayerChangedWorldData(String[] data) throws OldVersionException {
		try {
			String time = data[0] + " " + data[1];
			String from = data[6].trim();
			String to = data[8].trim();
			String playerName = data[4];
			UUID playerUUID = null;
			if(playerName.contains(":")) {
				playerUUID = UUID.fromString(playerName.split(":")[1]);
				playerName = playerName.split(":")[0];
			}
			Integer gamemode = 0; // is not set in the data
			if(playerUUID != null) {
				return new PlayerChangedWorldData(time, playerName, playerUUID, gamemode, to, from);
			}
			return new PlayerChangedWorldData(time, playerName, gamemode, to, from);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
}
