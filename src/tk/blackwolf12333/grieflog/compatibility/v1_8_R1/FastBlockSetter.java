package tk.blackwolf12333.grieflog.compatibility.v1_8_R1;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;

import tk.blackwolf12333.grieflog.compatibility.FastBlockSetterInterface;

public class FastBlockSetter implements FastBlockSetterInterface {
    public FastBlockSetter() {}
    
    @Override
    public void setBlockFast(int x, int y, int z, String world, int typeID, byte data) {
        Chunk c = Bukkit.getWorld(world).getChunkAt(x >> 4, z >> 4);
        net.minecraft.server.v1_8_R1.Chunk chunk = ((org.bukkit.craftbukkit.v1_8_R1.CraftChunk) c).getHandle();
        net.minecraft.server.v1_8_R1.Block block = net.minecraft.server.v1_8_R1.Block.getById(typeID);
        chunk.a(new net.minecraft.server.v1_8_R1.BlockPosition(x, y, z), block.getBlockData()); // sets the block at (x,y,z)
    }
    
    private net.minecraft.server.v1_8_R1.Block getBlockType(int typeID) {
        for(Material m : Material.values()) {
            if(m.getId() == typeID) {
                try {
                    return (net.minecraft.server.v1_8_R1.Block) net.minecraft.server.v1_8_R1.Blocks.class.getDeclaredField(m.toString()).get(null);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return net.minecraft.server.v1_8_R1.Blocks.AIR;
    }
}
