package tk.blackwolf12333.grieflog.compatibility.v1_6_R1;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import tk.blackwolf12333.grieflog.compatibility.FastBlockSetterInterface;

public class FastBlockSetter implements FastBlockSetterInterface {
	@Override
	public void setBlockFast(int x, int y, int z, String world, int typeID,	byte data) {
		Chunk c = Bukkit.getWorld(world).getChunkAt(x >> 4, z >> 4);
		net.minecraft.server.v1_6_R1.Chunk chunk = ((org.bukkit.craftbukkit.v1_6_R1.CraftChunk) c).getHandle();
		chunk.a(x & 15, y, z & 15, typeID, data);
	}
}
