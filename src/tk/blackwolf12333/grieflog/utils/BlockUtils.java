package tk.blackwolf12333.grieflog.utils;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockUtils {

	BlockFace[] faces = new BlockFace[] {BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH,
			BlockFace.SOUTH_WEST, BlockFace.SOUTH_EAST, BlockFace.WEST, BlockFace.EAST};
	
	/**
	 * This function uses recursion to get all the water blocks in a stream.
	 * @param source The source block from which the stream starts.
	 * @param stream A HashSet<Block> that will contain all the blocks from the stream.
	 */
	public void getWaterStream(Block source, HashSet<Block> stream) {
		if(!isWater(source)) return;
		if(stream.contains(source)) return;
		stream.add(source);
		
		for(BlockFace face : faces) {
			getWaterStream(source.getRelative(face), stream);
		}
		
		/*getWaterStream(source.getRelative(BlockFace.UP), stream);
		getWaterStream(source.getRelative(BlockFace.DOWN), stream);
		getWaterStream(source.getRelative(BlockFace.NORTH), stream);
		getWaterStream(source.getRelative(BlockFace.NORTH_EAST), stream);
		getWaterStream(source.getRelative(BlockFace.NORTH_WEST), stream);
		getWaterStream(source.getRelative(BlockFace.SOUTH), stream);
		getWaterStream(source.getRelative(BlockFace.SOUTH_WEST), stream);
		getWaterStream(source.getRelative(BlockFace.SOUTH_EAST), stream);
		getWaterStream(source.getRelative(BlockFace.WEST), stream);
		getWaterStream(source.getRelative(BlockFace.EAST), stream);*/
	}
	
	/**
	 * This function uses recursion to get all the lava blocks in a stream.
	 * @param source The source block from which the stream starts.
	 * @param stream A HashSet<Block> that will contain all the blocks from the stream.
	 */
	public void getLavaStream(Block source, HashSet<Block> stream) {
		if(!isLava(source)) return;
		if(stream.contains(source)) return;
		stream.add(source);
		
		for(BlockFace face : faces) {
			getLavaStream(source.getRelative(face), stream);
		}
		
		/*if(!stream.contains(source)) {
			if(isLava(source)) {
				stream.add(source);
				getLavaStream(source.getRelative(BlockFace.UP), stream);
				getLavaStream(source.getRelative(BlockFace.DOWN), stream);
				getLavaStream(source.getRelative(BlockFace.NORTH), stream);
				getLavaStream(source.getRelative(BlockFace.NORTH_EAST), stream);
				getLavaStream(source.getRelative(BlockFace.NORTH_WEST), stream);
				getLavaStream(source.getRelative(BlockFace.SOUTH), stream);
				getLavaStream(source.getRelative(BlockFace.SOUTH_WEST), stream);
				getLavaStream(source.getRelative(BlockFace.SOUTH_EAST), stream);
				getLavaStream(source.getRelative(BlockFace.WEST), stream);
				getLavaStream(source.getRelative(BlockFace.EAST), stream);
			}
		}*/
	}
	
	/**
	 * Checks if the block is water.
	 * @param block The block to check
	 * @return Returns true if the type of the block equals Material.WATER or Material.STATIONARY_WATER
	 */
	private boolean isWater(Block block) {
		return (block.getType() == Material.WATER) || (block.getType() == Material.STATIONARY_WATER);
	}
	
	/**
	 * Checks if the block is lava.
	 * @param block The block to check
	 * @return Returns true if the type of the block equals Material.LAVA or Material.STATIONARY_LAVA
	 */
	private boolean isLava(Block block) {
		return (block.getType() == Material.LAVA) || (block.getType() == Material.STATIONARY_LAVA);
	}
}
