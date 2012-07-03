package tk.blackwolf12333.grieflog.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import tk.blackwolf12333.grieflog.GriefLog;

public class PermsHandler {

	GriefLog plugin;
	PermissionManager permsEx;
	PermissionHandler permissionPlugin;
	
	public static net.milkbowl.vault.permission.Permission vaultPermission;
	Perms handler = Perms.BUKKIT;
	
	
	public PermsHandler(GriefLog plugin) {
		this.plugin = plugin;
		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		if(pm.isPluginEnabled("Vault")) {
			RegisteredServiceProvider<net.milkbowl.vault.permission.Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
	        if (permissionProvider != null) {
	            vaultPermission = permissionProvider.getProvider();
	            handler = Perms.VAULT;
	            GriefLog.log.info("Using Vault permissions plugin for permissions.");
	            return;
	        }
		}
		
		if(pm.isPluginEnabled("PermissionsEx")) {
			handler = Perms.PERMSEX;
			permsEx = PermissionsEx.getPermissionManager();
			GriefLog.log.info("Using PermissionsEx permissions plugin for permissions.");
		} else if(pm.isPluginEnabled("Permissions")) {
			permissionPlugin = ((Permissions)plugin.getServer().getPluginManager().getPlugin("Permissions")).getHandler();
			GriefLog.log.info("Using Permissions plugin for permissions.");
		} else {
			GriefLog.log.info("Using default server permissions for permissions.");
		}
	}
	
	public boolean has(CommandSender sender, String node) {
		if(sender instanceof ConsoleCommandSender) {
			return true;
		}
		Player p = (Player) sender;
		
		if(p.isOp()) {
			return true;
		}
		
		switch(handler) {
			case VAULT:
				return vaultPermission.has(p, node);
			case PERMSEX:
				return permsEx.has(p, node);
			case PERMISSIONS:
				return permissionPlugin.has(p, node);
			case BUKKIT:
				return p.hasPermission(node);
		}
		
		return false;
	}
	
	enum Perms {
		PERMSEX,
		VAULT,
		PERMISSIONS,
		BUKKIT;
	}
}
