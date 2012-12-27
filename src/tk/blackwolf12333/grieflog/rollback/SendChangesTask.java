package tk.blackwolf12333.grieflog.rollback;

import java.util.HashSet;

import net.minecraft.server.v1_4_6.ChunkCoordIntPair;
import net.minecraft.server.v1_4_6.EntityPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_4_6.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.PlayerSession;

public class SendChangesTask implements Runnable {
	
	public HashSet<Chunk> chunks = new HashSet<Chunk>();
	PlayerSession player;
	
	public SendChangesTask(HashSet<Chunk> chunks, PlayerSession player) {
		this.chunks = chunks;
		this.player = player;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void run() {
		HashSet<ChunkCoordIntPair> pairs = new HashSet<ChunkCoordIntPair>();
		for(Chunk c : this.chunks) {
			pairs.add(new ChunkCoordIntPair(c.getX(), c.getZ()));
		}
		
		for(Player p : getPlayers()) {
			HashSet<ChunkCoordIntPair> queued = new HashSet<ChunkCoordIntPair>();
			if(p != null) {
				EntityPlayer ep = ((CraftPlayer) p).getHandle();
				for(Object o : ep.chunkCoordIntPairQueue) {
					queued.add((ChunkCoordIntPair) o);
				}
				for(ChunkCoordIntPair pair : pairs) {
					if(!queued.contains(pair)) {
						ep.chunkCoordIntPairQueue.add(pair);
					}
				}
			}
		}
	}

	private HashSet<Player> getPlayers() {
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