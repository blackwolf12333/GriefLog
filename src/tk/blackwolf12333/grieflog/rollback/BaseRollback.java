package tk.blackwolf12333.grieflog.rollback;

import org.bukkit.World;

public abstract class BaseRollback {

	static World world;
	
	public abstract boolean rollback(String line);
}
