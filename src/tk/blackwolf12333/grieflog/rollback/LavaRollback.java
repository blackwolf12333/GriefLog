package tk.blackwolf12333.grieflog.rollback;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class LavaRollback extends FluidRollback {

	@Override
	public boolean rollback(String line) {
		String[] content = line.split("\\ ");
		
		if(content.length == 11) {
			String strX = content[6].replace(",", "");
			String strY = content[7].replace(",", "");
			String strZ = content[8].replace(",", "");
			String worldname = content[10].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);

			world = Bukkit.getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			Block[] fluids = getFluidStream(world.getBlockAt(loc));
			for(Block b : fluids) {
				world.getBlockAt(b.getLocation()).setType(Material.AIR);
			}
			world.getBlockAt(loc).setType(Material.AIR);
			return true;
		} else if(content.length == 12) {
			String strX = content[7].replace(",", "");
			String strY = content[8].replace(",", "");
			String strZ = content[9].replace(",", "");
			String worldname = content[11].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			
			world = Bukkit.getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			Block[] fluids = getFluidStream(world.getBlockAt(loc));
			for(Block b : fluids) {
				world.getBlockAt(b.getLocation()).setType(Material.AIR);
			}
			world.getBlockAt(loc).setType(Material.AIR);
			return true;
		} else if(content.length == 13) {
			String strX = content[8].replace(",", "");
			String strY = content[9].replace(",", "");
			String strZ = content[10].replace(",", "");
			String worldname = content[12].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			
			world = Bukkit.getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			Block[] fluids = getFluidStream(world.getBlockAt(loc));
			for(Block b : fluids) {
				if(b != null) {
					world.getBlockAt(b.getLocation()).setType(Material.AIR);
				}
			}
			world.getBlockAt(loc).setType(Material.AIR);
			return true;
		} else {
			return false;
		}
	}
}
