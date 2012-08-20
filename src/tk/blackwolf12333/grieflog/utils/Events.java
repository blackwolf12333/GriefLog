package tk.blackwolf12333.grieflog.utils;

public enum Events {
	BREAK ("[BLOCK_BREAK]", true, "break", "block_break"),
	PLACE ("[BLOCK_PLACE]", true, "place", "block_place"),
	LAVA ("[BUCKET_LAVA_EMPTY]", true, "lava", "lava_bucket"),
	WATER ("[BUCKET_WATER_EMPTY]", true, "water", "water_bucket"),
	IGNITE ("[BLOCK_IGNITE]", false, "fire", "ignite", "block_ignite"),
	ENDERMAN_PICKUP ("[ENDERMAN_PICKUP]", false, "pickup", "enderman_pickup", "enderman", "endermangrief"),
	ENDERMAN_PLACE ("[ENDERMAN_PLACE]", false, "place", "enderman_place", "enderman", "endermangrief"),
	ZOMBIEBREAKDOOR ("[ENTITY_BREAK_DOOR]", false, "zombiebreakdoor"),
	EXPLODE ("[ENTITY_EXPLODE]", true, "explode", "explosion"),
	JOIN ("[PLAYER_LOGIN]", false),
	QUIT ("[PLAYER_QUIT]", false),
	COMMAND ("[PLAYER_COMMAND]", false),
	GAMEMODE ("[GAMEMODE_CHANGE]", false),
	WORLDCHANGE ("[WORLD_CHANGE]", false);
	
	private String event;
	private String[] alias;
	private boolean canRollback;
	
	Events(String event, boolean canRollback){
		this.event = event;
		this.canRollback = canRollback;
	}
	
	Events(String event, boolean canRollback, String ...alias){
		this.event = event;
		this.alias = alias;
		this.canRollback = canRollback;
	}
	
	public String getEventName() {
		return event;
	}
	
	public void setEvent(String newEvent) {
		this.event = newEvent;
	}
	
	public String[] getAlias() {
		return alias;
	}
	
	public void setAlias(String[] alias) {
		this.alias = alias;
	}
	
	public boolean getCanRollback() {
		return canRollback;
	}
	
	public void setCanRollback(boolean canRollback) {
		this.canRollback = canRollback;
	}
	
	public static Events getEvent(String name) {
		for(Events event : Events.values()) {
			if(event.getEventName().equals(name.trim())) {
				return event;
			} else {
				continue;
			}
		}
		
		return null;
	}
}
