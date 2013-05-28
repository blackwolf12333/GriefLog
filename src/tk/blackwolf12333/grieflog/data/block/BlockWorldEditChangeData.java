package tk.blackwolf12333.grieflog.data.block;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import tk.blackwolf12333.grieflog.rollback.Rollback;
import tk.blackwolf12333.grieflog.rollback.Undo;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class BlockWorldEditChangeData extends BaseBlockData {

	String changedTo;
	byte changedToData;
	
	public BlockWorldEditChangeData(Block b, String player, String changedFromType, byte changedFromData, String changedTo, byte changedToData) {
		putBlock(b);
		this.blockType = changedFromType;
		this.blockData = changedFromData;
		this.changedTo = changedTo;
		this.changedToData = changedToData;
		this.xyz = this.blockX + ", " + this.blockY + ", " + this.blockZ;
		this.playerName = player;
		this.event = Events.WORLDEDIT.getEventName();
	}
	
	public BlockWorldEditChangeData(String time, int x, int y, int z, String world, String player, String changedFromType, byte changedFromData, String changedTo, byte changedToData) {
		this.time = time;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.worldName = world;
		this.blockType = changedFromType;
		this.blockData = changedFromData;
		this.changedTo = changedTo;
		this.changedToData = changedToData;
		this.playerName = player;
		this.xyz = this.blockX + ", " + this.blockY + ", " + this.blockZ;
		this.event = Events.WORLDEDIT.getEventName();
	}
	
	@Override
	public void rollback(Rollback rollback) {
		Location loc = new Location(Bukkit.getWorld(worldName), this.blockX, this.blockY, this.blockZ);
		if(this.changedTo != Material.AIR.toString()) {
			int materialID = Material.getMaterial(blockType).getId();
			this.setBlockFast(loc, materialID, blockData);
		} else {
			int materialID = Material.getMaterial(this.changedTo).getId();
			this.setBlockFast(loc, materialID, changedToData);
		}
		rollback.chunks.add(loc.getChunk());
	}

	@Override
	public void undo(Undo undo) {
		Location loc = new Location(Bukkit.getWorld(worldName), this.blockX, this.blockY, this.blockZ);
		if(this.changedTo != Material.AIR.toString()) {
			int materialID = Material.getMaterial(this.changedTo).getId();
			this.setBlockFast(loc, materialID, changedToData);
		} else {
			int materialID = Material.getMaterial(blockType).getId();
			this.setBlockFast(loc, materialID, blockData);
		}
		undo.chunks.add(loc.getChunk());
	}
	
	@Override
	public String toString() {
		if(time != null) {
			return this.time + " " + this.event + " By: " + this.playerName + " from: " + this.blockType + ":" + this.blockData + " to: " + this.changedTo + ":" + this.changedToData + " at: " + this.xyz + " in: " + this.worldName;
		}
		return " " + this.event + " By: " + this.playerName + " from: " + this.blockType + ":" + this.blockData + " to: " + this.changedTo + ":" + this.changedToData + " at: " + this.xyz + " in: " + this.worldName;
	}

	@Override
	public String getMinimal() {
		return this.time + " " + this.playerName + " made a change here with WorldEdit.";
	}

}
