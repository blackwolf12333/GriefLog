package tk.blackwolf12333.grieflog.utils.logging;

public enum Events {
	BREAK ("[BLOCK_BREAK]", true, "break", "block_break"),
	PLACE ("[BLOCK_PLACE]", true, "place", "block_place"),
	LAVA ("[BUCKET_LAVA_EMPTY]", true, "lava", "lava_bucket"),
	WATER ("[BUCKET_WATER_EMPTY]", true, "water", "water_bucket"),
	IGNITE ("[BLOCK_IGNITE]", false, "fire", "ignite", "block_ignite"),
	ENDERMAN_PICKUP ("[ENDERMAN_PICKUP]", true, "pickup", "enderman_pickup", "events_enderman", "endermangrief"),
	ENDERMAN_PLACE ("[ENDERMAN_PLACE]", true, "enderman_place", "events_enderman", "endermangrief"),
	ZOMBIEBREAKDOOR ("[ENTITY_BREAK_DOOR]", true, "zombiebreakdoor"),
	EXPLODE ("[ENTITY_EXPLODE]", true, "explode", "explosion"),
	JOIN ("[PLAYER_LOGIN]", false, "player_join", "player_login", "login", "join"),
	QUIT ("[PLAYER_QUIT]", false, "player_quit", "quit"),
	COMMAND ("[PLAYER_COMMAND]",false, "player_command", "command"),
	GAMEMODE ("[GAMEMODE_CHANGE]", false, "gamemode_change", "gamemode"),
	WORLDCHANGE ("[WORLD_CHANGE]", false, "world_change", "world"),
	CHESTACCESS ("[CHEST_ACCESS]", true, "chestaccess", "chest"),
	WORLDEDIT ("[WORLDEDIT]", true, "worldedit", "we", "worlde");
	
	private final String event;
	private final String[] alias;
	private  final boolean canRollback;
	
	Events(String event, boolean canRollback){
		this.event = event;
		this.canRollback = canRollback;
		this.alias = null;
	}
	
	Events(String event, boolean canRollback, String ...alias){
		this.event = event;
		this.alias = alias;
		this.canRollback = canRollback;
	}
	
	public String getEventName() {
		return event;
	}
	
	public String[] getAlias() {
		return alias;
	}
	
	public boolean getCanRollback() {
		return canRollback;
	}
	
	private static String getEventFromAlias(String alias) {
		for(Events event : Events.values()) {
			if(event.getAlias() != null) {
				for(String a : event.getAlias()) {
					if(alias.equalsIgnoreCase(a)) {
						return event.getEventName();
					} else {
						continue;
					}
				}
			} else {
				continue;
			}
		}
		return null;
	}
	
	public static boolean isAlias(String eventName) {
		for(Events event : Events.values()) {
			if(event.getAlias() != null) {
				for(String a : event.getAlias()) {
					if(eventName.equalsIgnoreCase(a)) {
						return true;
					} else {
						continue;
					}
				}
			} else {
				continue;
			}
		}
		return false;
	}
	
	public static Events getEvent(String name) {
		if(isEvent(name.trim())) {
			if(isAlias(name.trim())) {
				for(Events event : Events.values()) {
					if(event.getEventName().equals(getEventFromAlias(name.trim()))) {
						return event;
					} else {
						continue;
					}
				}
			} else {
				for(Events event : Events.values()) {
					if(event.getEventName().equals(name.trim())) {
						return event;
					} else {
						continue;
					}
				}
			}
		}
		
		return null;
	}
	
	public static boolean isEvent(String event) {
		if(isAlias(event)) {
			return true;
		} else {
			for(Events e : Events.values()) {
				if(e.getEventName().equalsIgnoreCase(event)) {
					return true;
				} else {
					continue;
				}
			}
			return false;
		}
	}
	
	@Override
	public String toString() {
		return event;
	}
}
