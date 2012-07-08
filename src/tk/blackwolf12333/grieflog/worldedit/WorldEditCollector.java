package tk.blackwolf12333.grieflog.worldedit;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.GriefLog;

import com.sk89q.worldedit.BlockVector;

public class WorldEditCollector {

	Player p;
	Iterator<BlockVector> blocks;
	GriefLog plugin;
	
	HashMap<Block, Material> blocksChanged = new HashMap<Block, Material>();
	
	public WorldEditCollector(GriefLog plugin, Player p, Iterator<BlockVector> blocks) {
		this.p = p;
		this.blocks = blocks;
		this.plugin = plugin;
	}
	
	public void collect() {
		int blockCount = 0;
		for(Iterator<BlockVector> blocksIt = blocks; blocksIt.hasNext();) {
			BlockVector blockVector = blocksIt.next();
			int x = blockVector.getBlockX();
			int y = blockVector.getBlockY();
			int z = blockVector.getBlockZ();
			Location theBlock = new Location(p.getWorld(), x, y, z);
			Block b = p.getWorld().getBlockAt(theBlock);
			if(!(b.getType() == Material.AIR)) {
				blocksChanged.put(b, b.getType());
			}
			
			blockCount++;
			if(blockCount == 200) {
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new WorldEditLogger(plugin, p, blocksChanged));
				blockCount = 0;
				blocksChanged.clear();
			}
		}
	}
}
