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
		
		if(cmd.getName().equalsIgnoreCase("gltool"))
		{
			Player p = (Player) sender;
			int item = gl.getConfig().getInt("SelectionTool");
			
			PlayerInventory tempInv = p.getInventory();
			ItemStack tempStack = new ItemStack(item,1);
			tempInv.addItem(tempStack);
			
			return true;
		}
		
		return false;
	}

}
