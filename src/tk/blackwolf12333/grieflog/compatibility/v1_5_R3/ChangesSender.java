package tk.blackwolf12333.grieflog.compatibility.v1_5_R3;

import java.util.HashSet;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.compatibility.ChangesSenderInterface;
import tk.blackwolf12333.grieflog.rollback.SendChangesTask;

public class ChangesSender implements ChangesSenderInterface {
	@SuppressWarnings("unchecked")
	@Override
	public void sendChanges(SendChangesTask task, HashSet<Chunk> chunks) {
		HashSet<net.minecraft.server.v1_5_R3.ChunkCoordIntPair> pairs = new HashSet<net.minecraft.server.v1_5_R3.ChunkCoordIntPair>();
		for (Chunk c : chunks) {
			pairs.add(new net.minecraft.server.v1_5_R3.ChunkCoordIntPair(c.getX(), c.getZ()));
		}

		for (Player p : task.getPlayers()) {
			HashSet<net.minecraft.server.v1_5_R3.ChunkCoordIntPair> queued = new HashSet<net.minecraft.server.v1_5_R3.ChunkCoordIntPair>();
			if (p != null) {
				net.minecraft.server.v1_5_R3.EntityPlayer ep = ((org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer) p).getHandle();
				for (Object o : ep.chunkCoordIntPairQueue) {
					queued.add((net.minecraft.server.v1_5_R3.ChunkCoordIntPair) o);
				}
				for (net.minecraft.server.v1_5_R3.ChunkCoordIntPair pair : pairs) {
					if (!queued.contains(pair)) {
						ep.chunkCoordIntPairQueue.add(pair);
					}
				}
			}
		}
	}
}
