package tk.blackwolf12333.grieflog.rollback;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;

public class SendChangesTask implements Runnable {
	
	public HashSet<Chunk> chunks = new HashSet<Chunk>();
	PlayerSession player;
	
	public SendChangesTask(HashSet<Chunk> chunks, PlayerSession player) {
		this.chunks = chunks;
		this.player = player;
	}
	
	@Override
	public void run() {
		GriefLog.compatibility.sendChanges(this, chunks);
	}

	public HashSet<Player> getPlayers() {
		HashSet<Player> ret = new HashSet<Player>();
		
		for(Chunk c : chunks) {
			if(c.isLoaded()) {
				for(Entity e : c.getEntities()) {
					if(e.getType() == EntityType.PLAYER) {
						ret.add((Player) e);
					}
				}
			}
		}
		ret.add(player.getPlayer());
		ret.addAll(getPlayersThatCanSeeChunk());
		
		return ret;
	}
	
	private HashSet<Player> getPlayersThatCanSeeChunk() {
		HashSet<Player> ret = new HashSet<Player>();
		
		for(Chunk c : chunks) {
			for(Player p : c.getWorld().getPlayers()) {
				if(p.getLocation().distanceSquared(c.getBlock(0, 0, 0).getLocation()) < Bukkit.getServer().getViewDistance()) {
					ret.add(p);
				}
			}
		}
		
		return ret;
	}
}