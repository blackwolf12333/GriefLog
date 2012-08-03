package tk.blackwolf12333.grieflog.rollback;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class PlaceRollback extends BaseRollback {

	static World world;
	
	public boolean rollback(String line) {
		String[] content = line.split("\\ ");
		
		if(content.length == 13) {
			String strX = content[8].replace(",", "");
			String strY = content[9].replace(",", "");
			String strZ = content[10].replace(",", "");
			String worldname = content[12].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);

			world = Bukkit.getWorld(worldname);
			Location loc = new Location(world, x, y, z);

			world.getBlockAt(loc).setType(Material.AIR);
			return true;
		} else if(content.length == 14) {
			String strX = content[9].replace(",", "");
			String strY = content[10].replace(",", "");
			String strZ = content[11].replace(",", "");
			String worldname = content[13].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);

			world = Bukkit.getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			
			world.getBlockAt(loc).setType(Material.AIR);
			return true;
		} else if(content.length == 15) {
			String strX = content[10].replace(",", "");
			String strY = content[11].replace(",", "");
			String strZ = content[12].replace(",", "");
			String worldname = content[14].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);

			world = Bukkit.getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			
			world.getBlockAt(loc).setType(Material.AIR);
			return true;
		} else if(content.length == 16) {
			String strX = content[11].replace(",", "");
			String strY = content[12].replace(",", "");
			String strZ = content[13].replace(",", "");
			String worldname = content[15].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);

			world = Bukkit.getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			
			world.getBlockAt(loc).setType(Material.AIR);
			return true;
		} else {
			return false;
		}
	}
}
