package tk.blackwolf12333.grieflog.data;

import java.util.Arrays;

import tk.blackwolf12333.grieflog.utils.Events;

public abstract class BaseEntityData extends BaseData {

	String time;
	Integer x;
	Integer y;
	Integer z;
	String entityType;
	String blockType;
	byte blockData;
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public byte getBlockData() {
		return blockData;
	}
	
	public void setBlockData(byte blockData) {
		this.blockData = blockData;
	}
	
	public String getBlockType() {
		return blockType;
	}
	
	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}
	
	public String getEntityType() {
		return entityType;
	}
	
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	public String getWorld() {
		return worldName;
	}
	
	public void setWorld(String world) {
		this.worldName = world;
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
	
	public abstract void rollback();
	
	public static BaseEntityData loadFromString(String line) {
		if(line.contains(Events.EXPLODE.getEventName())) {
			return handleEntityExplodeData(line.split(" "));
		} else if(line.contains(Events.ZOMBIEBREAKDOOR.getEventName())) {
			return handleEntityBreakDoorData(line.split(" "));
		} else if(line.contains(Events.ENDERMAN_PICKUP.getEventName()) || line.contains(Events.ENDERMAN_PLACE.getEventName())) {
			return handleEntityEndermanData(line.split(" "));
		}
		
		return null;
	}
	
	private static EntityExplodeData handleEntityExplodeData(String[] data) {
		try {
			String time = data[0] + " " + data[1];
			String strX = data[10].replace(",", "");
			String strY = data[11].replace(",", "");
			String strZ = data[12].replace(",", "");
			String entityType = data[6];
			String[] typeAndData = data[8].split(":");
			String type = typeAndData[0];
			byte blockData = Byte.parseByte(typeAndData[1]);
			String world = data[14].trim();
			String playerName = data[4];
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			return new EntityExplodeData(time, x, y, z, world, type, blockData, entityType, playerName);
		} catch(ArrayIndexOutOfBoundsException e) {
			new OldVersionException(e);
		}
		return null;
	}
	
	private static EntityBreakDoorData handleEntityBreakDoorData(String[] data) {
		try {
			String time = data[0] + " " + data[1];
			String strX = data[6].replace(",", "");
			String strY = data[7].replace(",", "");
			String strZ = data[8].replace(",", "");
			String entityType = data[4];
			String world = data[10].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			return new EntityBreakDoorData(time, x, y, z, world, entityType);
		} catch(ArrayIndexOutOfBoundsException e) {
			new OldVersionException(e);
		}
		return null;
	}
	
	private static EntityEndermanData handleEntityEndermanData(String[] data) {
		try {
			boolean pickup = Arrays.asList(data).contains(Events.ENDERMAN_PICKUP.getEventName());
			String type = data[4].split(":")[0];
			byte blockData = Byte.parseByte(data[4].split(":")[1]);
			int x = Integer.parseInt(data[6].replace(",", ""));
			int y = Integer.parseInt(data[7].replace(",", ""));
			int z = Integer.parseInt(data[8].replace(",", ""));
			String world = data[10].trim();
			return new EntityEndermanData(x, y, z, world, type, blockData, pickup);
		} catch(ArrayIndexOutOfBoundsException e) {
			new OldVersionException(e);
		}
		return null;
	}
}
