package tk.blackwolf12333.grieflog.data.block;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.data.OldVersionException;
import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public abstract class BaseBlockData extends BaseData {

	protected Integer blockX;
	protected Integer blockY;
	protected Integer blockZ;
	protected String xyz;
	protected String playerName;
	protected String blockType;
	protected byte blockData;
	protected Integer gamemode;
	
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
		this.xyz = blockX + ", " + blockY + ", " + blockZ;
	}
	
	public abstract void rollback(Rollback rollback);
	public abstract String toString();
	
	public void tpto(PlayerSession who) {
		World world = Bukkit.getServer().getWorld(worldName);
		who.print(ChatColor.YELLOW + "[GriefLog] Teleporting you to the event's location.");
		who.teleport(world.getBlockAt(blockX, blockY, blockZ).getLocation());
	}
	
	@Override
	public boolean isInWorldEditSelectionOf(PlayerSession player) {
		World w = Bukkit.getWorld(worldName);
		if(w != null) {
			Location loc = new Location(w, blockX, blockY, blockZ);
			return player.getWorldEditSelection().contains(loc);
		} else {
			return false;
		}
	}
	
	public BaseData applyColors(HashMap<String, ChatColor> colors) {
		this.time = colors.get("time") + time + ChatColor.WHITE;
		this.event = colors.get("event") + this.event + ChatColor.WHITE;
		this.playerName = colors.get("player") + playerName + ChatColor.WHITE;
		this.blockType = colors.get("blockinfo") + blockType + ChatColor.WHITE;
		this.xyz = colors.get("location") + xyz + ChatColor.WHITE;
		this.worldName = colors.get("world") + worldName + ChatColor.WHITE;
		return this;
	}
	
	protected Location getOtherDoorBlock(Block door) {
		BlockFace[] faces = new BlockFace[] {BlockFace.DOWN, BlockFace.UP};
		for(BlockFace face : faces) {
			if(door.getRelative(face).getType().equals(Material.AIR)) {
				return door.getRelative(face).getLocation();
			}
		}
		return null;
	}
	
	public static BaseBlockData loadFromString(String data) {
		try {
			if(data.contains(Events.BREAK.getEventName())) {
				return handleBlockBreakData(data.split(" "));
			} else if(data.contains(Events.PLACE.getEventName())) {
				return handleBlockPlaceData(data.split(" "));
			} else if(data.contains(Events.IGNITE.getEventName())) {
				return handleBlockIgniteData(data.split(" "));
			}
		} catch(OldVersionException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static BaseBlockData handleBlockBreakData(String[] data)/* throws OldVersionException*/ {
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
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
	
	private static BaseBlockData handleBlockPlaceData(String[] data) throws OldVersionException {
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
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
	
	private static BaseBlockData handleBlockIgniteData(String[] data) throws OldVersionException {
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
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
}
