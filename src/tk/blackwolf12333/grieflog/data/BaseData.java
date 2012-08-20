package tk.blackwolf12333.grieflog.data;

import tk.blackwolf12333.grieflog.utils.Events;

public abstract class BaseData {

	String worldName;
	
	/**
	 * @return the worldName
	 */
	public String getWorldName() {
		return worldName;
	}
	
	/**
	 * @param worldName the worldName to set
	 */
	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}
	
	public static BaseData loadFromString(String line) {
		if(line.contains(Events.BREAK.getEventName()) || line.contains(Events.PLACE.getEventName()) || line.contains(Events.IGNITE.getEventName())) {
			return BaseBlockData.loadFromString(line);
		} else if(line.contains(Events.EXPLODE.getEventName()) || line.contains(Events.ENDERMAN_PICKUP.getEventName()) || line.contains(Events.ENDERMAN_PLACE.getEventName()) || line.contains(Events.ZOMBIEBREAKDOOR.getEventName())) {
			return BaseEntityData.loadFromString(line);
		} else if(line.contains(Events.LAVA.getEventName()) || line.contains(Events.WATER.getEventName())) {
			return BucketData.loadFromString(line);
		} else {
			return BasePlayerData.loadFromString(line);
		}
	}
	
	public abstract void rollback();
}
