package tk.blackwolf12333.grieflog.utils.config;

import java.util.List;

public class ConfigValues {

	int tool;
	int mb;
	boolean blockprotection;
	String worlds;
	List<Integer> itemBlacklist;
	boolean command;
	boolean worldChange;
	boolean gmChange;
	boolean blockIgnite;
	boolean ignoreEnvironment;
	boolean bucketWater;
	boolean bucketLava;
	boolean enderman;
	boolean zombie;
	boolean explosions;
	boolean playerJoin;
	boolean playerQuit;
	boolean putItemsBackOnRollback;
	boolean antilava;
	boolean antitnt;
	boolean anticreeper;
	boolean antifire;
	boolean antienderman;
	String show;
	
	
	/**
	 * @return the tool
	 */
	public int getTool() {
		tool = ConfigHandler.config.getInt("SelectionTool");
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
		mb = ConfigHandler.config.getInt("mb");
		return mb;
	}
	
	/**
	 * @param mb the mb to set
	 */
	public void setMb(int mb) {
		this.mb = mb;
	}
	
	/**
	 * @return the blockprotection
	 */
	public boolean getBlockprotection() {
		blockprotection = ConfigHandler.config.getBoolean("block-protection");
		return blockprotection;
	}
	
	/**
	 * @param blockprotection the blockprotection to set
	 */
	public void setBlockprotection(boolean blockprotection) {
		this.blockprotection = blockprotection;
	}
	
	/**
	 * @return the itemBlacklist
	 */
	public List<Integer> getItemBlacklist() {
		itemBlacklist = ConfigHandler.config.getIntegerList("block-protection-blacklist");
		return itemBlacklist;
	}
	
	/**
	 * @param itemBlacklist the itemBlacklist to set
	 */
	public void setItemBlacklist(List<Integer> itemBlacklist) {
		this.itemBlacklist = itemBlacklist;
	}
	
	/**
	 * @return the command
	 */
	public boolean getCommand() {
		command = ConfigHandler.config.getBoolean("DoCommand");
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
		worldChange = ConfigHandler.config.getBoolean("ChangeWorld");
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
		gmChange = ConfigHandler.config.getBoolean("ChangeGameMode");
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
		blockIgnite = ConfigHandler.config.getBoolean("BlockIgnite");
		return blockIgnite;
	}
	
	/**
	 * @param blockIgnite the blockIgnite to set
	 */
	public void setBlockIgnite(boolean blockIgnite) {
		this.blockIgnite = blockIgnite;
		ConfigHandler.config.set("BlockIgnite", blockIgnite);
	}
	
	/**
	 * @return the ignoreEnvironment
	 */
	public boolean getIgnoreEnvironment() {
		ignoreEnvironment = ConfigHandler.config.getBoolean("IgnoreEnvironment");
		return ignoreEnvironment;
	}
	
	/**
	 * @param ignoreEnvironment the ignoreEnvironment to set
	 */
	public void setIgnoreEnvironment(boolean ignoreEnvironment) {
		this.ignoreEnvironment = ignoreEnvironment;
		ConfigHandler.config.set("IgnoreEnvironment", ignoreEnvironment);
	}
	
	/**
	 * @return the bucketWater
	 */
	public boolean getBucketWater() {
		return ConfigHandler.config.getBoolean("BucketWaterEmpty");
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
		return ConfigHandler.config.getBoolean("BucketLavaEmpty");
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
		enderman = ConfigHandler.config.getBoolean("EnderManPlaceAndPickup");
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
		zombie = ConfigHandler.config.getBoolean("ZombieBreakDoor");
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
		explosions = ConfigHandler.config.getBoolean("Explosions");
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
		playerJoin = ConfigHandler.config.getBoolean("PlayerJoin");
		return playerJoin;
	}
	
	/**
	 * @param playerJoin the playerJoin to set
	 */
	public void setPlayerJoin(boolean playerJoin) {
		this.playerJoin = playerJoin;
	}
	
	/**
	 * @return the antilava
	 */
	public boolean getAntilava() {
		antilava = ConfigHandler.config.getBoolean("anti-lava");
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
		antitnt = ConfigHandler.config.getBoolean("anti-tnt");
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
		anticreeper = ConfigHandler.config.getBoolean("anti-creeper");
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
		antifire = ConfigHandler.config.getBoolean("anti-fire");
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
		antienderman = ConfigHandler.config.getBoolean("anti-endermangrief");
		return antienderman;
	}
	
	public void setAntiEnderMan(boolean antienderman) {
		this.antienderman = antienderman;
	}
	
	public boolean getPlayerQuit() {
		playerQuit = ConfigHandler.config.getBoolean("PlayerQuit");
		return playerQuit;
	}
	
	public String getWhatToShow() {
		show = ConfigHandler.config.getString("show");
		return show;
	}
	
	public boolean getPutItemsBackOnRollback() {
		putItemsBackOnRollback = ConfigHandler.config.getBoolean("put-items-back-on-rollback");
		return putItemsBackOnRollback;
	}
	
	public String getWorlds() {
		worlds = ConfigHandler.config.getString("worlds-with-block-protection");
		return worlds;
	}
}
