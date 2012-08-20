package tk.blackwolf12333.grieflog.data;

import tk.blackwolf12333.grieflog.utils.Events;

public abstract class BasePlayerData extends BaseData {

	Integer gamemode;
	String playerName;
	
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
	public abstract void rollback();
	
	public static BasePlayerData loadFromString(String line) {
		if(line.contains(Events.JOIN.getEventName())) {
			return handlePlayerJoinData(line.split(" "));
		} else if(line.contains(Events.QUIT.getEventName())) {
			return handlePlayerQuitData(line.split(" "));
		} else if(line.contains(Events.COMMAND.getEventName())) {
			return handlePlayerCommandData(line.split(" "));
		} else if(line.contains(Events.GAMEMODE.getEventName())) {
			return handlePlayerChangedGamemodeData(line.split(" "));
		} else if(line.contains(Events.WORLDCHANGE.getEventName())) {
			return handlePlayerChangedWorldData(line.split(" "));
		}
		
		return null;
	}
	
	private static PlayerJoinData handlePlayerJoinData(String[] data) {
		try {
			String playerName = data[3];
			Integer gamemode = Integer.parseInt(data[8]);
			String ipaddress = data[5];
			Integer x = Integer.parseInt(data[9].replace("", ""));
			Integer y = Integer.parseInt(data[10].replace("", ""));
			Integer z = Integer.parseInt(data[11].replace("", ""));
			String world = data[13].trim();
			return new PlayerJoinData(playerName, gamemode, world, ipaddress, x, y, z);
		} catch(ArrayIndexOutOfBoundsException e) {
			new OldVersionException(e);
		}
		return null;
	}
	
	private static PlayerQuitData handlePlayerQuitData(String[] data) {
		try {
			Integer x = Integer.parseInt(data[7].replace("", ""));
			Integer y = Integer.parseInt(data[8].replace("", ""));
			Integer z = Integer.parseInt(data[9].replace("", ""));
			String world = data[11];
			String playerName = data[3];
			Integer gamemode = Integer.parseInt(data[5]);
			return new PlayerQuitData(playerName, gamemode, world, x, y, z);
		} catch(ArrayIndexOutOfBoundsException e) {
			new OldVersionException(e);
		}
		return null;
	}
	
	private static PlayerCommandData handlePlayerCommandData(String[] data) {
		try {
			String command = data[8];
			String playerName = data[4];
			String world = data[data.length - 1].trim();
			Integer gamemode = Integer.parseInt(data[6]);
			return new PlayerCommandData(playerName, gamemode, world, command);
		} catch(ArrayIndexOutOfBoundsException e) {
			new OldVersionException(e);
		}
		return null;
	}
	
	private static PlayerChangedGamemodeData handlePlayerChangedGamemodeData(String[] data) {
		try {
			Integer newGamemode = Integer.parseInt(data[6]);
			String playerName = data[3];
			Integer gamemode = 0; // is not set in the data
			String worldName = data[data.length - 1].trim();
			return new PlayerChangedGamemodeData(playerName, gamemode, worldName, newGamemode);
		} catch(ArrayIndexOutOfBoundsException e) {
			new OldVersionException(e);
		}
		return null;
	}
	
	private static PlayerChangedWorldData handlePlayerChangedWorldData(String[] data) {
		try {
			String from = data[6];
			String to = data[8];
			String playerName = data[4];
			Integer gamemode = 0; // is not set in the data
			return new PlayerChangedWorldData(playerName, gamemode, to, from);
		} catch(ArrayIndexOutOfBoundsException e) {
			new OldVersionException(e);
		}
		return null;
	}
}
