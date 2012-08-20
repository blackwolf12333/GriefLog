package tk.blackwolf12333.grieflog.rollback;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import tk.blackwolf12333.grieflog.GriefLog;

public class ExplodeRollback extends BaseRollback {

	static World world;
	
	public boolean rollback(String line) {
		String[] content = line.split("\\ ");
		
		if(content.length == 13) {
			String strX = content[8].replace(",", "");
			String strY = content[9].replace(",", "");
			String strZ = content[10].replace(",", "");
			String type = content[6];
			String worldname = content[12].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			
			world = Bukkit.getServer().getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			Material m = Material.getMaterial(type);
			if (m == null) {
				GriefLog.log.info("Could not get the right materials!");
				return false;
			} else if(m == Material.LONG_GRASS) {
				world.getBlockAt(loc).setType(m);
				world.getBlockAt(loc).setData((byte) 1);
				return true;
			} else {
				Bukkit.getWorld(worldname).getBlockAt(loc).setType(m);
				return true;
			}
		} else if(content.length == 15) {
			String strX = content[10].replace(",", "");
			String strY = content[11].replace(",", "");
			String strZ = content[12].replace(",", "");
			String type = content[8];
			if(type.contains(":")) {
				return rollbackWithData(line);
			}
			String worldname = content[14].trim();
			
			int x = Integer.parseInt(strX);
			int y = Integer.parseInt(strY);
			int z = Integer.parseInt(strZ);
			
			world = Bukkit.getServer().getWorld(worldname);
			Location loc = new Location(world, x, y, z);
			Material m = Material.getMaterial(type);
			if (m == null) {
				GriefLog.log.info("Could not get the right materials!");
				return false;
			} else if(m == Material.LONG_GRASS) {
				world.getBlockAt(loc).setType(m);
				world.getBlockAt(loc).setData((byte) 1);
				return true;
			} else {
				Bukkit.getWorld(worldname).getBlockAt(loc).setType(m);
				return true;
			}
		} else {
			return false;
		}
	}
	
	public boolean rollbackWithData(String line) {
		String[] content = line.split("\\ ");
		String strX = content[10].replace(",", "");
		String strY = content[11].replace(",", "");
		String strZ = content[12].replace(",", "");
		String[] typeAndData = content[8].split(":");
		String type = typeAndData[0];
		byte data = Byte.parseByte(typeAndData[1]);
		String worldname = content[14].trim();
		
		int x = Integer.parseInt(strX);
		int y = Integer.parseInt(strY);
		int z = Integer.parseInt(strZ);
		
		world = Bukkit.getServer().getWorld(worldname);
		Location loc = new Location(world, x, y, z);
		Material m = Material.getMaterial(type);
		if (m == null) {
			GriefLog.log.info("Could not get the right materials, Material DOESN'T exist!!!");
			return false;
		} else {
			Bukkit.getWorld(worldname).getBlockAt(loc).setTypeIdAndData(m.getId(), data, true);
			return true;
		}
	}
}
