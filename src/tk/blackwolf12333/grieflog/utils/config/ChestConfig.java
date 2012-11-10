package tk.blackwolf12333.grieflog.utils.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.utils.InventoryStringDeSerializer;

public class ChestConfig {

	static FileConfiguration config = new YamlConfiguration();
	static File configFile = new File(GriefLog.dataFolder, "chests.yml");
	
	public static Inventory getInventory(String chest) {
		try {
			config.load(configFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return InventoryStringDeSerializer.StringToInventory(config.getString(chest));
	}
	
	public static void addChest(String chest, String inv) {
		config.set(chest, inv);
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setup() {
		if(!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
