package tk.blackwolf12333.grieflog.utils.logging.worldedit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.data.block.BlockWorldEditChangeData;
import tk.blackwolf12333.grieflog.utils.logging.GriefLogger;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.extent.inventory.BlockBag;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;

public class GriefLogEditSession extends EditSession {

	LocalPlayer player;
	GriefLog plugin;

	public GriefLogEditSession(LocalWorld world, int maxBlocks, LocalPlayer player, GriefLog plugin) {
		super(world, maxBlocks);
		this.player = player;
		this.plugin = plugin;
	}

	public GriefLogEditSession(LocalWorld world, int maxBlocks, BlockBag blockBag, LocalPlayer player, GriefLog plugin) {
		super(world, maxBlocks, blockBag);
		this.player = player;
		this.plugin = plugin;
	}
	
	@Override
	public boolean rawSetBlock(Vector pt, BaseBlock block) {
		this.plugin.log.info("tes2t");
		if (!(player.getWorld() instanceof BukkitWorld)) {
			return super.rawSetBlock(pt, block);
		}

		Material typeBefore = Material.getMaterial(((BukkitWorld) player.getWorld()).getWorld().getBlockTypeIdAt(pt.getBlockX(), pt.getBlockY(), pt.getBlockZ()));
		byte dataBefore = ((BukkitWorld) player.getWorld()).getWorld().getBlockAt(pt.getBlockX(), pt.getBlockY(), pt.getBlockZ()).getData();
		
		boolean success = super.rawSetBlock(pt, block);
		if (success) {
			Location location = new Location(((BukkitWorld) player.getWorld()).getWorld(), pt.getBlockX(), pt.getBlockY(), pt.getBlockZ());
			Block affectedBlock = Bukkit.getWorld(player.getWorld().getName()).getBlockAt(location);
			
			if(affectedBlock.getType() == Material.AIR) {
				new GriefLogger(new BlockWorldEditChangeData(affectedBlock, player.getName(), typeBefore.toString(), dataBefore, affectedBlock.getType().toString(), affectedBlock.getData()));
			} else {
				new GriefLogger(new BlockWorldEditChangeData(affectedBlock, player.getName(), affectedBlock.getType().toString(), affectedBlock.getData(), typeBefore.toString(), dataBefore));				
			}
		}
		return success;
	}
}
