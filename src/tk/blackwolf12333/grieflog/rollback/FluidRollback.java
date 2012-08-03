package tk.blackwolf12333.grieflog.rollback;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public abstract class FluidRollback extends BaseRollback {

	public Block[] getFluidStream(Block source) {
		BlockFace[] faces = new BlockFace[] {BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST};
		Block[] fluids = new Block[8];
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
	
	@Override
	public abstract boolean rollback(String line);
}
