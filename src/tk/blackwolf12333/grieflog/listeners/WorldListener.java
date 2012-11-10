package tk.blackwolf12333.grieflog.listeners;

import java.io.File;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import tk.blackwolf12333.grieflog.GriefLog;

public class WorldListener implements Listener {

	@EventHandler
	public void onWorldLoad(WorldLoadEvent event) {
		String name = event.getWorld().getName();
		File worldDir = new File(GriefLog.logsDir, name);
		if(!worldDir.exists()) {
			worldDir.mkdirs();
		}
	}
	
	/*@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event) {
		for(String name : GriefLog.sessions.keySet()) {
			if(GriefLog.sessions.get(name).getCurrentRollback() != null) {
				if(GriefLog.sessions.get(name).getCurrentRollback().chunks.contains(event.getChunk())) {
					event.setCancelled(true);
				} else {
					continue;
				}
			} else {
				continue;
			}
		}
	}*/
}
