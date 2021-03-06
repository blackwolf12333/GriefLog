package tk.blackwolf12333.grieflog.data.block;

import java.util.HashMap;
import java.util.UUID;

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
	protected UUID playerUUID;
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
	
	/**
	 * @return the player's UUID
	 */
	public UUID getPlayerUUID() {
		return playerUUID;
	}
	
	/**
	 * @param playerUUID the playerUUID to set
	 */
	public void setPlayerUUID(UUID playerUUID) {
		this.playerUUID = playerUUID;
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
	
	public Location getLocation() {
		return new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
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
			} else if(data.contains(Events.WORLDEDIT.getEventName())) {
				return handleBlockWorldEditChangeData(data.split(" "));
			} else if(data.contains(Events.BURN.getEventName())) {
				return handleBlockBurnData(data.split(" "));
			}
		} catch(OldVersionException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static BaseBlockData handleBlockBurnData(String[] data) {
		try {
			String time = data[0] + " " + data[1];
			String[] typeAndData = data[4].split(":");
			String type = typeAndData[0];
			byte blockData = Byte.parseByte(typeAndData[1]);

			int x = Integer.parseInt(data[7].replace(",", ""));
			int y = Integer.parseInt(data[8].replace(",", ""));
			int z = Integer.parseInt(data[9].replace(",", ""));
			String world = data[11].trim();
			return new BlockBurnData(time, type, blockData, x, y, z, world);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
	
	private static BaseBlockData handleBlockWorldEditChangeData(String[] data) {
		try {
			String time = data[0] + " " + data[1];
			String player = data[4];
			UUID playerUUID = null;
			if(player.contains(":")) {
				playerUUID = UUID.fromString(player.split(":")[1]);
				player = player.split(":")[0];
			}
			String[] changedFromTypeAndData = data[6].split(":");
			String blockType = changedFromTypeAndData[0];
			byte blockData = Byte.parseByte(changedFromTypeAndData[1]);
			String[] changedToTypeAndData = data[8].split(":");
			String changedTo = changedToTypeAndData[0];
			byte changedToData = Byte.parseByte(changedToTypeAndData[1]);
			
			int x = Integer.parseInt(data[10].replace(",", ""));
			int y = Integer.parseInt(data[11].replace(",", ""));
			int z = Integer.parseInt(data[12].replace(",", ""));
			String world = data[14].trim();
			if(playerUUID != null) {
				return new BlockWorldEditChangeData(time, x, y, z, world, player, playerUUID, blockType, blockData, changedTo, changedToData);
			}
			return new BlockWorldEditChangeData(time, x, y, z, world, player, blockType, blockData, changedTo, changedToData);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}

	private static BaseBlockData handleBlockBreakData(String[] data) throws OldVersionException {
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
			String player = data[4];
			UUID playerUUID = null;
			if(player.contains(":")) {
				playerUUID = UUID.fromString(player.split(":")[1]);
				player = player.split(":")[0];
			}
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			if(playerUUID != null) {
				return new BlockBreakData(time, x, y, z, world, type, blockData, player, playerUUID, gamemode);
			}
			return new BlockBreakData(time, x, y, z, world, type, blockData, player, gamemode);
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
			String player = data[4];
			UUID playerUUID = null;
			if(player.contains(":")) {
				playerUUID = UUID.fromString(player.split(":")[1]);
				player = player.split(":")[0];
			}
			Integer gamemode = Integer.parseInt(data[6]);
			String[] typeAndData = data[8].split(":");
			String blockType = typeAndData[0];
			byte blockData = Byte.parseByte(typeAndData[1]);
			String world = data[15].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			if(playerUUID != null) {
				return new BlockPlaceData(time, x, y, z, blockType, blockData, world, player, playerUUID, gamemode);
			}
			return new BlockPlaceData(time, x, y, z, blockType, blockData, world, player, gamemode);
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
			String player = data[4];
			UUID playerUUID = null;
			if(player.contains(":")) {
				playerUUID = UUID.fromString(player.split(":")[1]);
				player = player.split(":")[0];
			}
			Integer gamemode = Integer.parseInt(data[6]);
			String[] typeAndData = data[8].split(":");
			String blockType = typeAndData[0];
			byte blockData = Byte.parseByte(typeAndData[1]);
			String cause = data[10];
			String world = data[16].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			if(playerUUID != null) {
				return new BlockIgniteData(time, x, y, z, blockType, blockData, world, cause, player, playerUUID, gamemode);
			}
			return new BlockIgniteData(time, x, y, z, blockType, blockData, world, cause, player, gamemode);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new OldVersionException("Data was not successfully parsed because it came from an outdated file!");
		}
	}
}
