package tk.blackwolf12333.grieflog.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.GriefLog;


@SuppressWarnings("unused")
public class Rollback {

	GriefLog plugin;
	Time t = new Time();
	World world;
	String player;
	int x;
	int y;
	int z;

	public Rollback(GriefLog instance) {
		plugin = instance;
	}

	public void RollbackDaGrief(String player, Player sender)	{
		File grieflog = GriefLog.file;
		
		String[] data = readLine(grieflog, player);
		if((data == null) || (data.length == 0))
		{
			return;
		}
		
		for(int j = 0; j < data.length; j++)
		{
			if(data[j].indexOf("[BLOCK_BREAK]") < 0)
			{
				try {
					String[] split1 = data[j].split("\\ ");
					// split1[9] == x == "x,"
					String material = split1[6];
					String xStr = split1[10].replace(",", "");
					String yStr = split1[11].replace(",", "");
					String zStr = split1[12].replace(",", "");
					
					int x = Integer.parseInt(xStr);
					int y = Integer.parseInt(yStr);
					int z = Integer.parseInt(zStr);
					
					for(Material m : Material.values())
					{
						if(m.toString() == material)
						{
							Location loc = new Location(world, x, y, z);
							world.getBlockAt(loc).setType(m);
						}
					}
				} catch(Exception e)
				{
					GriefLog.log.warning("Failed to rollback event!");
				}
			}
			sender.sendMessage(data[j]);
			/*try {
				String[] split1 = data[j].split("\\ ");
				// split1[9] == x == "x,"
				String material = split1[6];
				String xStr = split1[10].replace(",", "");
				String yStr = split1[11].replace(",", "");
				String zStr = split1[12].replace(",", "");
				
				int x = Integer.parseInt(xStr);
				int y = Integer.parseInt(yStr);
				int z = Integer.parseInt(zStr);
				
				for(Material m : Material.values())
				{
					if(m.toString() == material)
					{
						Location loc = new Location(world, x, y, z);
						world.getBlockAt(loc).setType(m);
					}
				}
			} catch(Exception e)
			{
				GriefLog.log.warning("Failed to rollback event!");
			}*/
			//break;
		}
		sender.sendMessage("[GriefLog] Rolled the griefer back!");
	}

	//TODO: add this function to the standard FileUtils
	public String[] readLine(File file, String text)
	{
		String[] lines = new String[count(file)];
		String currentline = "";
		
		if(!file.exists())
		{
			GriefLog.log.info("Failed to open file!");
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			
			for(int i = 0; (currentline = in.readLine()) != null; i++)
			{
				if(currentline.indexOf(text) > 0)
				{
					lines[i] = currentline;
				}
				else
				{
					lines[i] = null;
				}
				
				
			}
			
			in.close();
		} catch(Exception e) {
			GriefLog.log.warning(e.getMessage());
		}
		
		if(lines == null)
		{
			lines[0] = "Nothing found";
		}
		
		return lines;
	}
	
	public int count(File file)
	{
		try {
			LineNumberReader reader = new LineNumberReader(new FileReader(file));
			int count = 0;
			String lineRead = "";
			while((lineRead = reader.readLine()) != null) {}
			count = reader.getLineNumber();
			reader.close();
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (int) file.length();
	}
	
	public boolean rollback(String player, Player sender, File file)
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
			}
		}
		
		return ret;
	}
	
	public boolean rollback(String player, Player sender, File file, long from)
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
	}
}