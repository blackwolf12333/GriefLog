package tk.blackwolf12333.grieflog.data;

import org.bukkit.block.Block;

import tk.blackwolf12333.grieflog.utils.Events;

public abstract class BaseBlockData extends BaseData {

	String time;
	Integer blockX;
	Integer blockY;
	Integer blockZ;
	String playerName;
	String blockType;
	byte blockData;
	Integer gamemode;
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	/**
	 * @return the blockX
	 */
	public Integer getBlockX() {
		return blockX;
	}
	
	/**
	 * @param blockX the blockX to set
	 */
	public void setBlockX(Integer blockX) {
		this.blockX = blockX;
	}
	
	/**
	 * @return the blockY
	 */
	public Integer getBlockY() {
		return blockY;
	}
	
	/**
	 * @param blockY the blockY to set
	 */
	public void setBlockY(Integer blockY) {
		this.blockY = blockY;
	}
	
	/**
	 * @return the blockZ
	 */
	public Integer getBlockZ() {
		return blockZ;
	}
	
	/**
	 * @param blockZ the blockZ to set
	 */
	public void setBlockZ(Integer blockZ) {
		this.blockZ = blockZ;
	}
	
	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	/**
	 * @return the blockType
	 */
	public String getBlockType() {
		return blockType;
	}
	
	/**
	 * @param blockType the blockType to set
	 */
	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}
	
	/**
	 * @return the blockData
	 */
	public byte getBlockData() {
		return blockData;
	}
	
	/**
	 * @param blockData the blockData to set
	 */
	public void setBlockData(byte blockData) {
		this.blockData = blockData;
	}
	
	/**
	 * @return the gamemode
	 */
	public Integer getGamemode() {
		return gamemode;
	}
	
	/**
	 * @param gamemode the gamemode to set
	 */
	public void setGamemode(Integer gamemode) {
		this.gamemode = gamemode;
	}
	
	protected void putBlock(Block b) {
		this.blockX = b.getX();
		this.blockY = b.getY();
		this.blockZ = b.getZ();
		this.blockType = b.getType().toString();
		this.blockData = b.getData();
		this.worldName = b.getWorld().getName();
	}
	
	public abstract void rollback();
	
	public static BaseBlockData loadFromString(String data) {
		if(data.contains(Events.BREAK.getEventName())) {
			return handleBlockBreakData(data.split(" "));
		} else if(data.contains(Events.PLACE.getEventName())) {
			return handleBlockPlaceData(data.split(" "));
		} else if(data.contains(Events.IGNITE.getEventName())) {
			return handleBlockIgniteData(data.split(" "));
		}
		
		return null;
	}
	
	private static BaseBlockData handleBlockBreakData(String[] data) {
		try {
			String time = data[0] + " " + data[1];
			String strX = data[11].replace(",", "");
			String strY = data[12].replace(",", "");
			String strZ = data[13].replace(",", "");
			String[] typeAndData = data[8].split(":");
			String type = typeAndData[0];
			byte blockData = Byte.parseByte(typeAndData[1]);
			Integer gamemode = Integer.parseInt(data[6]);
			String world = data[15].trim();
			String playerName = data[4];
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			return new BlockBreakData(time, x, y, z, world, type, blockData, playerName, gamemode);
		} catch(ArrayIndexOutOfBoundsException e) {
			new OldVersionException(e);
		}
		
		return null;
	}
	
	private static BaseBlockData handleBlockPlaceData(String[] data) {
		try {
			String time = data[0] + " " + data[1];
			String strX = data[11].replace(",", "");
			String strY = data[12].replace(",", "");
			String strZ = data[13].replace(",", "");
			String playerName = data[4];
			Integer gamemode = Integer.parseInt(data[6]);
			String[] typeAndData = data[8].split(":");
			String blockType = typeAndData[0];
			byte blockData = Byte.parseByte(typeAndData[1]);
			String world = data[15].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			return new BlockPlaceData(time, x, y, z, blockType, blockData, world, playerName, gamemode);
		} catch(ArrayIndexOutOfBoundsException e) {
			new OldVersionException(e);
		}
		
		return null;
	}
	
	private static BaseBlockData handleBlockIgniteData(String[] data) {
		try {
			String time = data[0] + " " + data[1];
			String strX = data[12].replace(",", "");
			String strY = data[13].replace(",", "");
			String strZ = data[14].replace(",", "");
			String playerName = data[4];
			Integer gamemode = Integer.parseInt(data[6]);
			String[] typeAndData = data[8].split(":");
			String blockType = typeAndData[0];
			byte blockData = Byte.parseByte(typeAndData[1]);
			String cause = data[10];
			String world = data[16].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			return new BlockIgniteData(time, x, y, z, blockType, blockData, world, cause, playerName, gamemode);
		} catch(ArrayIndexOutOfBoundsException e) {
			new OldVersionException(e);
		}
		return null;
	}
}
