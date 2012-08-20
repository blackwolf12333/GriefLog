package tk.blackwolf12333.grieflog.rollback;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public abstract class FluidRollback extends BaseRollback {

	ArrayList<Block> waterBlocks = new ArrayList<Block>();
	ArrayList<Block> lavaBlocks = new ArrayList<Block>();
	
	public Block[] getFluidStream(Block source) {
		BlockFace[] faces = new BlockFace[] {BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST, BlockFace.DOWN, BlockFace.UP};
		Block[] fluids = new Block[16];
		int count = 0;
		
		for(BlockFace face : faces) {
			Block next = source.getRelative(face);
			if(source.getType() == Material.STATIONARY_WATER) {
				if((next.getType() == Material.WATER)) {
					fluids[count] = next;
					count++;
				}
			} else {
				if((next.getType() == Material.LAVA)) {
					fluids[count] = next;
					count++;
				}
			}
		}
		
		return fluids;
	}
	
	public ArrayList<Block> getStream(Block source) {
		BlockFace[] faces = new BlockFace[] {BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST, BlockFace.DOWN, BlockFace.UP};
		
		for(BlockFace face : faces) {
			Block next = source.getRelative(face);
			if((next.getType() == Material.WATER) || (next.getType() == Material.STATIONARY_WATER)) {
				waterBlocks.add(next);
				waterBlocks.addAll(getStream(next));
				return waterBlocks;
			} else if((next.getType() == Material.LAVA) || (next.getType() == Material.STATIONARY_LAVA)) {
				lavaBlocks.add(next);
				lavaBlocks.addAll(getStream(next));
				return lavaBlocks;
			} else {
				return null;
			}
		}
		
		return null;
	}
	
	@Override
	public abstract boolean rollback(String line);
}
