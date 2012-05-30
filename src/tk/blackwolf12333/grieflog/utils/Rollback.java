package tk.blackwolf12333.grieflog.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.GriefLog;


@SuppressWarnings("unused")
public class Rollback {

	GriefLog plugin;
	Time t = new Time();
	FileUtils fu = new FileUtils();
	World world;
	String player;
	int x;
	int y;
	int z;

	public Rollback(GriefLog instance) {
		plugin = instance;
	}
	
	public boolean rollback(String player, Player sender, File file)
	{
		boolean ret = false;
		
		String[] data = fu.readLines(file, player);
		
		for(int i = 0; i < data.length; i++)
		{
			if(data[i] == null)
			{
				continue;
			}
			if((data[i].indexOf(player) > 0) && (data[i].indexOf("[BLOCK_BREAK]") > 0))
			{
				String[] split = data[i].split("\\ ");
				
				String type = split[7];
				String strX = split[10].replace(",", "");
				String strY = split[11].replace(",", "");
				String strZ = split[12].replace(",", "");
				int x = Integer.parseInt(strX);
				int y = Integer.parseInt(strY);
				int z = Integer.parseInt(strZ);
				
				world = sender.getWorld();
				Location loc = new Location(world, x, y, z);
				Material m = Material.getMaterial(type);
				if(m == null)
				{
					GriefLog.log.info("Could not get the right materials!");
					continue;
				}
				world.getBlockAt(loc).setType(m);
				ret = true;
			}
			else if((data[i].indexOf(player) > 0) && data[i].indexOf("[BLOCK_PLACE]") > 0)
			{
				String[] split = data[i].split("\\ ");
					
				String strX = split[9].replace(",", "");
				String strY = split[10].replace(",", "");
				String strZ = split[11].replace(",", "");
				int x = Integer.parseInt(strX);
				int y = Integer.parseInt(strY);
				int z = Integer.parseInt(strZ);
				
				world = sender.getWorld();
				Location loc = new Location(world, x, y, z);
				world.getBlockAt(loc).setType(Material.AIR);
				ret = true;
			}
		}
		
		if(ret)
		{
			sender.sendMessage("[GriefLog] Rolled the griefer back!");
		}
		
		return ret;
	}
	
	public boolean rollbackEntity(Player sender, File file)
	{
		boolean ret = false;
		
		String[] data = fu.readLines(file, "Creeper");
		
		for(int i = 0; i < data.length; i++)
		{
			if(data[i] == null)
			{
				continue;
			}
			if(data[i].indexOf("[ENTITY_EXPLODE]") > 0)
			{
				String[] split = data[i].split("\\ ");
				
				String blockType = split[6];
				String strX = split[8].replace(",", "");
				String strY = split[9].replace(",", "");
				String strZ = split[10].replace(",", "");
				int x = Integer.parseInt(strX);
				int y = Integer.parseInt(strY);
				int z = Integer.parseInt(strZ);
				
				world = sender.getWorld();
				Location loc = new Location(world, x, y, z);
				Material m = Material.getMaterial(blockType);
				if(m == null)
				{
					GriefLog.log.info("Could not get the right materials!");
					continue;
				}
				world.getBlockAt(loc).setType(m);
				ret = true;
			}
		}
		return ret;
	}
	
	/*public boolean rollback(String player, Player sender, File file, long from)
	{
		boolean ret = false;
		
		String[] data = readLine(file, player);
		
		for(int i = 0; i < data.length; i++)
		{
			if(data[i] == null)
			{
				continue;
			}
			if((data[i].indexOf(player) > 0) && (data[i].indexOf("[BLOCK_BREAK]") > 0))
			{
				String[] split = data[i].split("\\ ");
				
				String time = split[0] + " " + split[1];
				if(t.getTimeStamp(time) < from)
				{
					String type = split[7];
					String strX = split[10].replace(",", "");
					String strY = split[11].replace(",", "");
					String strZ = split[12].replace(",", "");
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);
					
					world = sender.getWorld();
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
					if(m == null)
					{
						GriefLog.log.info("Could not get the right materials!");
						continue;
					}
					world.getBlockAt(loc).setType(m);
				}
			}
			else if((data[i].indexOf(player) > 0) && data[i].indexOf("[BLOCK_PLACE]") > 0)
			{
				String[] split = data[i].split("\\ ");
				
				String time = split[0] + " " + split[1];
				if(t.getTimeStamp(time) < 0)
				{
					String strX = split[9].replace(",", "");
					String strY = split[10].replace(",", "");
					String strZ = split[11].replace(",", "");
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);
					
					world = sender.getWorld();
					Location loc = new Location(world, x, y, z);
					world.getBlockAt(loc).setType(Material.AIR);
				}
			}
		}
		
		return ret;
	}*/ // not working
}