package tk.blackwolf12333.grieflog.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import org.anjocaido.groupmanager.permissions.BukkitPermissions;

import tk.blackwolf12333.grieflog.GriefLog;

public class PermsHandler {

	GriefLog plugin;
	PermissionManager permsEx;
	PermissionHandler permissionPlugin;
	BukkitPermissions essentials;
	
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
	            return;
	        }
		}
		
		if(pm.isPluginEnabled("PermissionsEx")) {
			handler = Perms.PERMSEX;
			permsEx = PermissionsEx.getPermissionManager();
		} else if(pm.isPluginEnabled("Permissions")) {
			permissionPlugin = ((Permissions)plugin.getServer().getPluginManager().getPlugin("Permissions")).getHandler();
		} else {
		}
	}
	
	public boolean has(CommandSender sender, String node) {
		if(!(sender instanceof Player)) {
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
			case ESSENTIALS:
				//TODO: do this...
				break;
			case BUKKIT:
				return p.hasPermission(node);
		}
		
		return false;
	}
	
	enum Perms {
		PERMSEX,
		VAULT,
		ESSENTIALS,
		PERMISSIONS,
		BUKKIT;
	}
}
