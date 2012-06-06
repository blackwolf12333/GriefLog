package tk.blackwolf12333.grieflog.utils;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.GriefLogSearcher;

@SuppressWarnings("unused")
public class Rollback {

	GriefLog plugin;
	GriefLogSearcher searcher = new GriefLogSearcher();
	Time t = new Time();
	World world;
	String player;
	int x;
	int y;
	int z;

	public Rollback(GriefLog instance) {
		plugin = instance;
	}

	/*public boolean rollback(String player, Player sender, File file) {
		boolean ret = false;

		String[] data = fu.readLines(file, player);

		for (String element : data) {
			if (element == null) {
				continue;
			}
			if ((element.indexOf(player) > 0) && (element.indexOf("[BLOCK_BREAK]") > 0) || ((element.indexOf("[ENTITY_EXPLODE]") > 0) && (element.indexOf(player) > 0))) {
				String[] split = element.split("\\ ");

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
				if (m == null) {
					plugin.log.info("Could not get the right materials!");
					continue;
				}
				world.getBlockAt(loc).setType(m);
				ret = true;
			} else if ((element.indexOf(player) > 0) && element.indexOf("[BLOCK_PLACE]") > 0) {
				String[] split = element.split("\\ ");

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

		File files = new File("logs/");
		String[] list = files.list();
		if (list.length > 0) {
			for (String element : list) {
				// search through the other logfiles, if the searched text is
				// found break out this for loop
				data = fu.readLines(new File("logs" + File.separator + element), player);

				for (String element2 : data) {
					if (element2 == null) {
						continue;
					}
					if ((element2.indexOf(player) > 0) && (element2.indexOf("[BLOCK_BREAK]") > 0) || ((element2.indexOf("[ENTITY_EXPLODE]") > 0) && (element2.indexOf(player) > 0))) {
						String[] split = element2.split("\\ ");

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
						if (m == null) {
							plugin.log.info("Could not get the right materials!");
							continue;
						}
						world.getBlockAt(loc).setType(m);
						ret = true;
					} else if ((element2.indexOf(player) > 0) && (element2.indexOf("[BLOCK_PLACE]") > 0)) {
						String[] split = element2.split("\\ ");

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
			}
		} else if (list == null) {
			data = fu.readLines(file, player);

			for (String element : data) {
				if (element == null) {
					continue;
				}
				if ((element.indexOf(player) > 0) && (element.indexOf("[BLOCK_BREAK]") > 0) || ((element.indexOf("[ENTITY_EXPLODE]") > 0) && (element.indexOf(player) > 0))) {
					String[] split = element.split("\\ ");

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
					if (m == null) {
						plugin.log.info("Could not get the right materials!");
						continue;
					}
					world.getBlockAt(loc).setType(m);
					ret = true;
				} else if ((element.indexOf(player) > 0) && element.indexOf("[BLOCK_PLACE]") > 0) {
					String[] split = element.split("\\ ");

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
		}

		if (ret) {
			sender.sendMessage("[GriefLog] Rolled the griefer back!");
			return ret;
		}

		return ret;
	}

	public boolean rollbackEntity(Player sender, File file) {
		boolean ret = false;

		String[] data = fu.readLines(file, "[ENTITY_EXPLODE]");

		for (String element : data) {
			if (element == null) {
				continue;
			} else {
				String[] split = element.split("\\ ");

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
				if (m == null) {
					plugin.log.info("Could not get the right materials!");
					continue;
				}
				world.getBlockAt(loc).setTypeId(m.getId());
				ret = true;
			}
		}

		File files = new File("logs/");
		String[] list = files.list();
		if (list.length > 0) {
			for (String element : list) {
				data = fu.readLines(new File("logs" + File.separator + element), "[ENTITY_EXPLODE]");

				for (String element2 : data) {
					if (element2 == null) {
						continue;
					} else {
						String[] split = element2.split("\\ ");

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
						if (m == null) {
							plugin.log.info("Could not get the right materials!");
							continue;
						}
						world.getBlockAt(loc).setType(m);
						ret = true;
					}
				}
			}
		} else if (list == null) {
			data = fu.readLines(file, "[ENTITY_EXPLODE]");

			for (String element : data) {
				if (element == null) {
					continue;
				} else {
					String[] split = element.split("\\ ");

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
					if (m == null) {
						plugin.log.info("Could not get the right materials!");
						continue;
					}
					world.getBlockAt(loc).setType(m);
					ret = true;
				}
			}
		}

		return ret;
	}*/
	
	public void rollback(CommandSender sender, String ...text)
	{
		if(text.length == 1) {
			String result = searcher.searchText(text[0]);
			String[] lines = result.split(System.getProperty("line.separator"));
			for(String line : lines)
			{
				if(line == null) {
					continue;
				} else if(line.contains("[PLAYER_LOGIN]")) {
					continue;
				} else if(line.contains("[PLAYER_COMMAND]")) {
					continue;
				} else if(line.contains("[BLOCK_BREAK]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String type = content[6];
					String worldname = content[13];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
					if (m == null) {
						plugin.log.info("Could not get the right materials!");
						continue;
					} else if(m == Material.LONG_GRASS) {
						world.getBlockAt(loc).setType(m);
						world.getBlockAt(loc).setData((byte) 1);
					}
					world.getBlockAt(loc).setType(m);
				} else if(line.contains("[ENTITY_EXPLODE]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[8].replace(",", "");
					String strY = content[9].replace(",", "");
					String strZ = content[10].replace(",", "");
					String type = content[6];
					String worldname = content[12];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
					if (m == null) {
						plugin.log.info("Could not get the right materials!");
						continue;
					} else if(m == Material.LONG_GRASS) {
						world.getBlockAt(loc).setType(m);
						world.getBlockAt(loc).setData((byte) 1);
					}
					world.getBlockAt(loc).setType(m);
				} else if(line.contains("[BLOCK_PLACE]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String worldname = content[13];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					world.getBlockAt(loc).setType(Material.AIR);
				} else if(line.contains("[BUCKET_LAVA_EMPTY]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[6].replace(",", "");
					String strY = content[7].replace(",", "");
					String strZ = content[8].replace(",", "");
					String worldname = content[10];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					world.getBlockAt(loc).setType(Material.AIR);
				} else if(line.contains("[BUCKET_WATER_EMPTY]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[6].replace(",", "");
					String strY = content[7].replace(",", "");
					String strZ = content[8].replace(",", "");
					String worldname = content[10];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					world.getBlockAt(loc).setType(Material.AIR);
				}
			}
			sender.sendMessage("Rolled back da grief:D");
		} else if(text.length == 2) {
			String result = searcher.searchText(text[0], text[1]);
			String[] lines = result.split(System.getProperty("line.separator"));
			for(String line : lines)
			{
				if(line == null) {
					continue;
				} else if(line.contains("[PLAYER_LOGIN]")) {
					continue;
				} else if(line.contains("[PLAYER_COMMAND]")) {
					continue;
				} else if(line.contains("[BLOCK_BREAK]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String type = content[6];
					String worldname = content[13];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
					if (m == null) {
						plugin.log.info("Could not get the right materials!");
						continue;
					} else if(m == Material.LONG_GRASS) {
						world.getBlockAt(loc).setType(m);
						world.getBlockAt(loc).setData((byte) 1);
					}
					world.getBlockAt(loc).setType(m);
				} else if(line.contains("[ENTITY_EXPLODE]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[8].replace(",", "");
					String strY = content[9].replace(",", "");
					String strZ = content[10].replace(",", "");
					String type = content[6];
					String worldname = content[12];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
					if (m == null) {
						plugin.log.info("Could not get the right materials!");
						continue;
					} else if(m == Material.LONG_GRASS) {
						world.getBlockAt(loc).setType(m);
						world.getBlockAt(loc).setData((byte) 1);
					}
					world.getBlockAt(loc).setType(m);
				} else if(line.contains("[BLOCK_PLACE]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String worldname = content[13];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					world.getBlockAt(loc).setType(Material.AIR);
				} else if(line.contains("[BUCKET_LAVA_EMPTY]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[6].replace(",", "");
					String strY = content[7].replace(",", "");
					String strZ = content[8].replace(",", "");
					String worldname = content[10];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					world.getBlockAt(loc).setType(Material.AIR);
				} else if(line.contains("[BUCKET_WATER_EMPTY]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[6].replace(",", "");
					String strY = content[7].replace(",", "");
					String strZ = content[8].replace(",", "");
					String worldname = content[10];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					world.getBlockAt(loc).setType(Material.AIR);
				}
			}
			sender.sendMessage("Rolled back da grief:D");
		} else if(text.length == 3) {
			String result = searcher.searchText(text[0], text[1], text[2]);
			String[] lines = result.split(System.getProperty("line.separator"));
			for(String line : lines)
			{
				if(line == null) {
					continue;
				} else if(line.contains("[PLAYER_LOGIN]")) {
					continue;
				} else if(line.contains("[PLAYER_COMMAND]")) {
					continue;
				} else if(line.contains("[BLOCK_BREAK]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String type = content[6];
					String worldname = content[13];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
					if (m == null) {
						plugin.log.info("Could not get the right materials!");
						continue;
					} else if(m == Material.LONG_GRASS) {
						world.getBlockAt(loc).setType(m);
						world.getBlockAt(loc).setData((byte) 1);
					}
					world.getBlockAt(loc).setType(m);
				} else if(line.contains("[ENTITY_EXPLODE]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[8].replace(",", "");
					String strY = content[9].replace(",", "");
					String strZ = content[10].replace(",", "");
					String type = content[6];
					String worldname = content[12];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					Material m = Material.getMaterial(type);
					if (m == null) {
						plugin.log.info("Could not get the right materials!");
						continue;
					} else if(m == Material.LONG_GRASS) {
						world.getBlockAt(loc).setType(m);
						world.getBlockAt(loc).setData((byte) 1);
					}
					world.getBlockAt(loc).setType(m);
				} else if(line.contains("[BLOCK_PLACE]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[9].replace(",", "");
					String strY = content[10].replace(",", "");
					String strZ = content[11].replace(",", "");
					String worldname = content[13];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					world.getBlockAt(loc).setType(Material.AIR);
				} else if(line.contains("[BUCKET_LAVA_EMPTY]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[6].replace(",", "");
					String strY = content[7].replace(",", "");
					String strZ = content[8].replace(",", "");
					String worldname = content[10];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					world.getBlockAt(loc).setType(Material.AIR);
				} else if(line.contains("[BUCKET_WATER_EMPTY]")) {
					String[] content = line.split("\\ ");
					
					String strX = content[6].replace(",", "");
					String strY = content[7].replace(",", "");
					String strZ = content[8].replace(",", "");
					String worldname = content[10];
					
					int x = Integer.parseInt(strX);
					int y = Integer.parseInt(strY);
					int z = Integer.parseInt(strZ);

					world = Bukkit.getWorld(worldname);
					Location loc = new Location(world, x, y, z);
					world.getBlockAt(loc).setType(Material.AIR);
				}
			}
			sender.sendMessage("Rolled back da grief:D");
		}
	}

	/*
	 * public boolean rollback(String player, Player sender, File file, long
	 * from) { boolean ret = false;
	 * 
	 * String[] data = readLine(file, player);
	 * 
	 * for(int i = 0; i < data.length; i++) { if(data[i] == null) { continue; }
	 * if((data[i].indexOf(player) > 0) && (data[i].indexOf("[BLOCK_BREAK]") >
	 * 0)) { String[] split = data[i].split("\\ ");
	 * 
	 * String time = split[0] + " " + split[1]; if(t.getTimeStamp(time) < from)
	 * { String type = split[7]; String strX = split[10].replace(",", "");
	 * String strY = split[11].replace(",", ""); String strZ =
	 * split[12].replace(",", ""); int x = Integer.parseInt(strX); int y =
	 * Integer.parseInt(strY); int z = Integer.parseInt(strZ);
	 * 
	 * world = sender.getWorld(); Location loc = new Location(world, x, y, z);
	 * Material m = Material.getMaterial(type); if(m == null) {
	 * GriefLog.log.info("Could not get the right materials!"); continue; }
	 * world.getBlockAt(loc).setType(m); } } else if((data[i].indexOf(player) >
	 * 0) && data[i].indexOf("[BLOCK_PLACE]") > 0) { String[] split =
	 * data[i].split("\\ ");
	 * 
	 * String time = split[0] + " " + split[1]; if(t.getTimeStamp(time) < 0) {
	 * String strX = split[9].replace(",", ""); String strY =
	 * split[10].replace(",", ""); String strZ = split[11].replace(",", ""); int
	 * x = Integer.parseInt(strX); int y = Integer.parseInt(strY); int z =
	 * Integer.parseInt(strZ);
	 * 
	 * world = sender.getWorld(); Location loc = new Location(world, x, y, z);
	 * world.getBlockAt(loc).setType(Material.AIR); } } }
	 * 
	 * return ret; }
	 */// not working
}
