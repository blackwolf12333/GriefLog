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

    /*private boolean a(net.minecraft.server.v1_8_R1.Chunk that, int i, int j, int k, net.minecraft.server.v1_8_R1.Block block, int data) {
        int i1 = k << 4 | i;

        if (j >= that.heightMap[i1] - 1) {
            that.heightMap[i1] = -999;
        }

        int j1 = that.heightMap[i1];
        net.minecraft.server.v1_8_R1.Block block1 = that.getType(new net.minecraft.server.v1_8_R1.BlockPosition(i, j, k));
        int k1 = that.b(new net.minecraft.server.v1_8_R1.BlockPosition(i, j, k));

        if (block1 == block && k1 == data) {
            return false;
        } else {
            boolean flag = false;
            net.minecraft.server.v1_8_R1.ChunkSection chunksection = that.getSections()[j >> 4];

            if (chunksection == null) {
                if (block == net.minecraft.server.v1_8_R1.Blocks.AIR) {
                    return false;
                }

                chunksection = that.getSections()[j >> 4] = new net.minecraft.server.v1_8_R1.ChunkSection(j >> 4 << 4, !that.world.worldProvider.g);
                flag = j >= j1;
            }

            int l1 = that.locX * 16 + i;
            int i2 = that.locZ * 16 + k;

            if (!that.world.isStatic) {
                block1.f(that.world, new net.minecraft.server.v1_8_R1.BlockPosition(l1, j, i2));
            }

            // CraftBukkit start - Delay removing containers until after they're cleaned up
            if (!(block1 instanceof net.minecraft.server.v1_8_R1.IContainer)) {
                chunksection.setType(i, j & 15, k, block.getBlockData());
            }
            // CraftBukkit end

            if (!that.world.isStatic) {
                block1.remove(that.world, l1, j, i2, block1, k1);
            } else if (block1 instanceof net.minecraft.server.v1_8_R1.IContainer && block1 != block) {
                that.world.p(l1, j, i2);
            }

            // CraftBukkit start - Remove containers now after cleanup
            if (block1 instanceof net.minecraft.server.v1_8_R1.IContainer) {
                chunksection.setType(i, j & 15, k, block.getBlockData());
            }
            // CraftBukkit end

            if (chunksection.getType(i, j & 15, k) != block) {
                return false;
            } else {
                chunksection.setData(i, j & 15, k, data);
                if (flag) {
                    that.initLighting();
                }
                net.minecraft.server.v1_8_R1.TileEntity tileentity;

                if (block1 instanceof net.minecraft.server.v1_8_R1.IContainer) {
                    tileentity = that.e(new net.minecraft.server.v1_8_R1.BlockPosition(i, j, k));
                    if (tileentity != null) {
                        tileentity.u();
                    }
                }

                // CraftBukkit - Don't place while processing the BlockPlaceEvent, unless it's a BlockContainer
                if (!that.world.isStatic && (!that.world.callingPlaceEvent || (block instanceof net.minecraft.server.v1_8_R1.BlockContainer))) {
                    block.onPlace(that.world, l1, j, i2);
                }

                if (block instanceof net.minecraft.server.v1_8_R1.IContainer) {
                    // CraftBukkit start - Don't create tile entity if placement failed
                    if (that.getType(new net.minecraft.server.v1_8_R1.BlockPosition(i, j, k)) != block) {
                        return false;
                    }
                    // CraftBukkit end

                    tileentity = that.e(new net.minecraft.server.v1_8_R1.BlockPosition(i, j, k));
                    if (tileentity == null) {
                        tileentity = ((net.minecraft.server.v1_8_R1.IContainer) block).a(that.world, data);
                        that.world.setTileEntity(new net.minecraft.server.v1_8_R1.BlockPosition(l1, j, i2), tileentity);
                    }

                    if (tileentity != null) {
                        tileentity.u();
                    }
                }

                that.mustSave = true;
                return true;
            }
        }
    }*/
}
