package tk.blackwolf12333.grieflog.utils;

public enum Events {
	BREAK ("[BLOCK_BREAK]", "break", "block_break"),
	PLACE ("[BLOCK_PLACE]", "place", "block_place"),
	EXPLODE("[ENTITY_EXPLODE]", "explode", "explosion"),
	LAVA("[BUCKET_LAVA_EMPTY", "lava", "lava_bucket"),
	WATER("[BUCKET_WATER_EMPTY", "water", "water_bucket"),
	ENDERMAN_PICKUP("[ENDERMAN_PICKUP]", "pickup", "enderman_pickup"),
	ENDERMAN_PLACE("[ENDERMAN_PLACE]", "place", "enderman_place");
	
	private String event;
	private String[] alias;
	
	Events(String event){
		this.event = event;
	}
	
	Events(String event, String ...alias){
		this.event = event;
		this.alias = alias;
	}
	
	public String getEvent() {
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
}
