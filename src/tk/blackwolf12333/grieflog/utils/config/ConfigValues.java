package tk.blackwolf12333.grieflog.utils.config;

public class ConfigValues {

	int tool = ConfigHandler.config.getInt("general.tool");
	int mb = ConfigHandler.config.getInt("general.mb");
	String loggingMethod = ConfigHandler.config.getString("general.logging-method");
	boolean bw12333glog = ConfigHandler.config.getBoolean("general.blackwolf12333glog");
	boolean debug = ConfigHandler.config.getBoolean("general.debug");
	String logsDir = ConfigHandler.config.getString("general.path");
	
	boolean command = ConfigHandler.config.getBoolean("events.command");
	boolean worldChange = ConfigHandler.config.getBoolean("events.changeworld");
	boolean gmChange = ConfigHandler.config.getBoolean("events.changegamemode");
	boolean blockIgnite = ConfigHandler.config.getBoolean("events.blockignite");
	boolean bucketWater = ConfigHandler.config.getBoolean("events.bucketwater");
	boolean bucketLava = ConfigHandler.config.getBoolean("events.bucketlava");
	boolean enderman = ConfigHandler.config.getBoolean("events.enderman");
	boolean zombie = ConfigHandler.config.getBoolean("events.zombie");
	boolean explosions = ConfigHandler.config.getBoolean("events.explosions");
	boolean playerJoin = ConfigHandler.config.getBoolean("events.playerjoin");
	boolean playerQuit = ConfigHandler.config.getBoolean("events.playerquit");
    boolean inventoryLogging = ConfigHandler.config.getBoolean("events.inventory");
	boolean putItemsBackOnRollback = ConfigHandler.config.getBoolean("events.putitemsbackonrollback");
	String ignoredCommands = ConfigHandler.config.getString("events.ignored-commands");
	
	boolean antilava = ConfigHandler.config.getBoolean("antigrief.antilava");
	boolean antitnt = ConfigHandler.config.getBoolean("antigrief.antitnt");
	boolean anticreeper = ConfigHandler.config.getBoolean("antigrief.anticreeper");
	boolean antifire = ConfigHandler.config.getBoolean("antigrief.antifire");
	boolean antienderman = ConfigHandler.config.getBoolean("antigrief.antiendermangrief");
	boolean antispam = ConfigHandler.config.getBoolean("antigrief.antispam");
	
	String show = ConfigHandler.config.getString("show");
	String timecolor = ConfigHandler.config.getString("colors.time");
	String eventcolor = ConfigHandler.config.getString("colors.event");
	String playercolor = ConfigHandler.config.getString("colors.player");
	String blockinfocolor = ConfigHandler.config.getString("colors.block-info");
	String locationcolor = ConfigHandler.config.getString("colors.location");
	String worldcolor = ConfigHandler.config.getString("colors.world");
	
	/**
	 * @return the tool
	 */
	public int getTool() {
		return tool;
	}

	/**
	 * @param tool the tool to set
	 */
	public void setTool(int tool) {
		this.tool = tool;
	}

	/**
	 * @return the mb
	 */
	public int getMb() {
		mb = ConfigHandler.config.getInt("general.mb");
		return mb;
	}

	/**
	 * @param mb the mb to set
	 */
	public void setMb(int mb) {
		this.mb = mb;
	}

	public boolean getBw12333glog() {
		return bw12333glog;
	}

	public void setBw12333glog(boolean bw12333glog) {
		this.bw12333glog = bw12333glog;
	}

	/**
	 * @return the command
	 */
	public boolean getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(boolean command) {
		this.command = command;
	}

	/**
	 * @return the worldChange
	 */
	public boolean getWorldChange() {
		return worldChange;
	}

	/**
	 * @param worldChange the worldChange to set
	 */
	public void setWorldChange(boolean worldChange) {
		this.worldChange = worldChange;
	}

	/**
	 * @return the gmChange
	 */
	public boolean getGmChange() {
		return gmChange;
	}

	/**
	 * @param gmChange the gmChange to set
	 */
	public void setGmChange(boolean gmChange) {
		this.gmChange = gmChange;
	}

	/**
	 * @return the blockIgnite
	 */
	public boolean getBlockIgnite() {
		return blockIgnite;
	}

	/**
	 * @param blockIgnite the blockIgnite to set
	 */
	public void setBlockIgnite(boolean blockIgnite) {
		this.blockIgnite = blockIgnite;
		ConfigHandler.config.set("events.blockignite", blockIgnite);
	}

	/**
	 * @return the bucketWater
	 */
	public boolean getBucketWater() {
		return bucketWater;
	}

	/**
	 * @param bucketWater the bucketWater to set
	 */
	public void setBucketWater(boolean bucketWater) {
		this.bucketWater = bucketWater;
	}

	/**
	 * @return the bucketLava
	 */
	public boolean getBucketLava() {
		return bucketLava;
	}

	/**
	 * @param bucketLava the bucketLava to set
	 */
	public void setBucketLava(boolean bucketLava) {
		this.bucketLava = bucketLava;
	}

	/**
	 * @return the enderman
	 */
	public boolean getEnderman() {
		return enderman;
	}

	/**
	 * @param enderman the enderman to set
	 */
	public void setEnderman(boolean enderman) {
		this.enderman = enderman;
	}

	/**
	 * @return the zombie
	 */
	public boolean getZombie() {
		return zombie;
	}

	/**
	 * @param zombie the zombie to set
	 */
	public void setZombie(boolean zombie) {
		this.zombie = zombie;
	}

	/**
	 * @return the explosions
	 */
	public boolean getExplosions() {
		return explosions;
	}

	/**
	 * @param explosions the explosions to set
	 */
	public void setExplosions(boolean explosions) {
		this.explosions = explosions;
	}

	/**
	 * @return the playerJoin
	 */
	public boolean getPlayerJoin() {
		return playerJoin;
	}

	/**
	 * @param playerJoin the playerJoin to set
	 */
	public void setPlayerJoin(boolean playerJoin) {
		this.playerJoin = playerJoin;
	}
	
	public boolean getInventoryLogging() {
	    return inventoryLogging;
	}
	
	public void setInventoryLogging(boolean inventoryLogging) {
	    this.inventoryLogging = inventoryLogging;
	}

	/**
	 * @return the antilava
	 */
	public boolean getAntilava() {
		return antilava;
	}

	/**
	 * @param antilava the antilava to set
	 */
	public void setAntilava(boolean antilava) {
		this.antilava = antilava;
	}

	/**
	 * @return the antitnt
	 */
	public boolean getAntitnt() {
		return antitnt;
	}

	/**
	 * @param antitnt the antitnt to set
	 */
	public void setAntitnt(boolean antitnt) {
		this.antitnt = antitnt;
	}

	/**
	 * @return the anticreeper
	 */
	public boolean getAnticreeper() {
		return anticreeper;
	}

	/**
	 * @param anticreeper the anticreeper to set
	 */
	public void setAnticreeper(boolean anticreeper) {
		this.anticreeper = anticreeper;
	}

	/**
	 * @return the antifire
	 */
	public boolean getAntifire() {
		return antifire;
	}

	/**
	 * @param antifire the antifire to set
	 */
	public void setAntifire(boolean antifire) {
		this.antifire = antifire;
	}

	/**
	 * @return anti-endermangrief 
	 */
	public boolean getAntiEnderMan() {
		return antienderman;
	}

	/**
	 * @param antienderman
	 */
	public void setAntiEnderMan(boolean antienderman) {
		this.antienderman = antienderman;
	}

	public boolean getAntispam() {
		return antispam;
	}

	public void setAntispam(boolean antispam) {
		this.antispam = antispam;
	}

	/**
	 * @return playerQuit
	 */
	public boolean getPlayerQuit() {
		return playerQuit;
	}

	/**
	 * @return show
	 */
	public String getWhatToShow() {
		return show;
	}

	/**
	 * @return put=items-back-on-rollback
	 */
	public boolean getPutItemsBackOnRollback() {
		return putItemsBackOnRollback;
	}

	public String getTimecolor() {
		return timecolor;
	}

	public void setTimecolor(String timecolor) {
		this.timecolor = timecolor;
	}

	public String getEventcolor() {
		return eventcolor;
	}

	public void setEventcolor(String eventcolor) {
		this.eventcolor = eventcolor;
	}

	public String getBlockinfocolor() {
		return blockinfocolor;
	}

	public void setBlockinfocolor(String blockinfocolor) {
		this.blockinfocolor = blockinfocolor;
	}

	public String getLocationcolor() {
		return locationcolor;
	}

	public void setLocationcolor(String locationcolor) {
		this.locationcolor = locationcolor;
	}

	public String getWorldcolor() {
		return worldcolor;
	}

	public void setWorldcolor(String worldcolor) {
		this.worldcolor = worldcolor;
	}

	public String getPlayercolor() {
		return playercolor;
	}

	public void setPlayercolor(String playercolor) {
		this.playercolor = playercolor;
	}

	public boolean getDebug() {
		return debug;
	}

	public String getLogsDir() {
		return logsDir;
	}
	
	public void setLogsDir(String logsDir) {
		this.logsDir = logsDir;
	}
	
	public String getIgnoredCommands() {
		return ignoredCommands;
	}
	
	public void setIgnoredCommands(String ignoredCommands) {
		this.ignoredCommands = ignoredCommands;
	}
	
	public String getLoggingMethod() {
		return loggingMethod;
	}
	
	public void setLoggingMethod(String loggingMethod) {
		this.loggingMethod = loggingMethod;
	}
}
