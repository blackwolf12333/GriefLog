package blackwolf12333.maatcraft.grieflog.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import blackwolf12333.maatcraft.grieflog.GriefLog;

public class GLTool implements CommandExecutor {

	GriefLog gl;
	
	public GLTool(GriefLog plugin) {
		gl = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		// checks if the sender is a player, if not, return false
		if(!(sender instanceof Player))
		{
			return false;
		}
		
		// check if the command == "gltool"
		if(cmd.getName().equalsIgnoreCase("gltool"))
		{
			Player p = (Player) sender;
			int item = gl.getConfig().getInt("SelectionTool");
			
			// add a item to the players inventory
			PlayerInventory inv = p.getInventory();
			inv.addItem(new ItemStack(item,1));
			
			return true;
		}
		
		return false;
	}

}
