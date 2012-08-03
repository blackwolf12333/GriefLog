package tk.blackwolf12333.grieflog.utils.config;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigValues {

	FileConfiguration grieflogConfig = ConfigHandler.config;
	
	int tool;
	int mb;
	boolean blockprotection;
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
	boolean antilava;
	boolean antitnt;
	boolean anticreeper;
	boolean antifire;
	boolean antienderman;
	
	/**
	 * @return the grieflogConfig
	 */
	public FileConfiguration getGrieflogConfig() {
		return grieflogConfig;
	}
	/**
	 * @param grieflogConfig the grieflogConfig to set
	 */
	public void setGrieflogConfig(FileConfiguration grieflogConfig) {
		this.grieflogConfig = grieflogConfig;
	}
	/**
	 * @return the tool
	 */
	public int getTool() {
		return ConfigHandler.config.getInt("SelectionTool");
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
		return ConfigHandler.config.getInt("mb");
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
		return ConfigHandler.config.getBoolean("block-protection");
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
		return ConfigHandler.config.getIntegerList("block-protection-blacklist");
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
		return ConfigHandler.config.getBoolean("DoCommand");
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
		return ConfigHandler.config.getBoolean("ChangeWorld");
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
		return ConfigHandler.config.getBoolean("ChangeGameMode");
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
		return ConfigHandler.config.getBoolean("BlockIgnite");
	}
	/**
	 * @param blockIgnite the blockIgnite to set
	 */
	public void setBlockIgnite(boolean blockIgnite) {
		this.blockIgnite = blockIgnite;
	}
	/**
	 * @return the ignoreEnvironment
	 */
	public boolean getIgnoreEnvironment() {
		return ConfigHandler.config.getBoolean("IgnoreEnvironment");
	}
	/**
	 * @param ignoreEnvironment the ignoreEnvironment to set
	 */
	public void setIgnoreEnvironment(boolean ignoreEnvironment) {
		this.ignoreEnvironment = ignoreEnvironment;
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
		return ConfigHandler.config.getBoolean("EnderManPlaceAndPickup");
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
		return ConfigHandler.config.getBoolean("ZombieBreakDoor");
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
		return ConfigHandler.config.getBoolean("Explosions");
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
		return ConfigHandler.config.getBoolean("PlayerJoin");
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
		return ConfigHandler.config.getBoolean("anti-lava");
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
		return ConfigHandler.config.getBoolean("anti-tnt");
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
		return ConfigHandler.config.getBoolean("anti-creeper");
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
		return ConfigHandler.config.getBoolean("anti-fire");
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
		return ConfigHandler.config.getBoolean("anti-endermangrief");
	}
	
	public void setAntiEnderMan(boolean antienderman) {
		this.antienderman = antienderman;
	}
	
	public boolean getPlayerQuit() {
		return playerQuit = ConfigHandler.config.getBoolean("PlayerQuit");
	}
	
	
}
