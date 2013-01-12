package tk.blackwolf12333.grieflog.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

public class BlockUtils {

	BlockFace[] faces = new BlockFace[] {BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH,
			BlockFace.SOUTH_WEST, BlockFace.SOUTH_EAST, BlockFace.WEST, BlockFace.EAST};
	
	/**
	 * This function uses recursion to get all the water blocks in a stream.
	 * @param source The source block from which the stream starts.
	 * @param stream A HashSet<Block> that will contain all the blocks from the stream.
	 */
	public void getWaterStream(Block source, HashSet<Block> stream) {
		if(!isWater(source)) return;
		if(stream.contains(source)) return;
		stream.add(source);
		
		for(BlockFace face : faces) {
			getWaterStream(source.getRelative(face), stream);
		}
	}
	
	/**
	 * This function uses recursion to get all the lava blocks in a stream.
	 * @param source The source block from which the stream starts.
	 * @param stream A HashSet<Block> that will contain all the blocks from the stream.
	 */
	public void getLavaStream(Block source, HashSet<Block> stream) {
		if(!isLava(source)) return;
		if(stream.contains(source)) return;
		stream.add(source);
		
		for(BlockFace face : faces) {
			getLavaStream(source.getRelative(face), stream);
		}
	}
	
	/**
	 * Checks if the block is water.
	 * @param block The block to check
	 * @return Returns true if the type of the block equals Material.WATER or Material.STATIONARY_WATER
	 */
	private boolean isWater(Block block) {
		return (block.getType() == Material.WATER) || (block.getType() == Material.STATIONARY_WATER);
	}
	
	/**
	 * Checks if the block is lava.
	 * @param block The block to check
	 * @return Returns true if the type of the block equals Material.LAVA or Material.STATIONARY_LAVA
	 */
	private boolean isLava(Block block) {
		return (block.getType() == Material.LAVA) || (block.getType() == Material.STATIONARY_LAVA);
	}
	
	/*
	 * Credits go to md-5
	 */
	public static ItemStack[] compareInventories(ItemStack[] items1, ItemStack[] items2) {
		final ItemStackComparator comperator = new ItemStackComparator();
		final ArrayList<ItemStack> diff = new ArrayList<ItemStack>();
		final int l1 = items1.length, l2 = items2.length;
		int c1 = 0, c2 = 0;
		while (c1 < l1 || c2 < l2) {
			if (c1 >= l1) {
				diff.add(items2[c2]);
				c2++;
				continue;
			}
			if (c2 >= l2) {
				items1[c1].setAmount(items1[c1].getAmount() * -1);
				diff.add(items1[c1]);
				c1++;
				continue;
			}
			final int comp = comperator.compare(items1[c1], items2[c2]);
			if (comp < 0) {
				items1[c1].setAmount(items1[c1].getAmount() * -1);
				diff.add(items1[c1]);
				c1++;
			} else if (comp > 0) {
				diff.add(items2[c2]);
				c2++;
			} else {
				final int amount = items2[c2].getAmount() - items1[c1].getAmount();
				if (amount != 0) {
					items1[c1].setAmount(amount);
					diff.add(items1[c1]);
				}
				c1++;
				c2++;
			}
		}
		return diff.toArray(new ItemStack[diff.size()]);
	}

	public static ItemStack[] compressInventory(ItemStack[] items) {
		final ArrayList<ItemStack> compressed = new ArrayList<ItemStack>();
		for (final ItemStack item : items)
			if (item != null) {
				final int type = item.getTypeId();
				final byte data = rawData(item);
				boolean found = false;
				for (final ItemStack item2 : compressed)
					if (type == item2.getTypeId() && data == rawData(item2)) {
						item2.setAmount(item2.getAmount() + item.getAmount());
						found = true;
						break;
					}
				if (!found)
					compressed.add(new ItemStack(type, item.getAmount()));
			}
		Collections.sort(compressed, new ItemStackComparator());
		return compressed.toArray(new ItemStack[compressed.size()]);
	}
	
	public static byte rawData(ItemStack item) {
		return item.getType() != null ? item.getData() != null ? item.getData().getData() : 0 : 0;
	}
	
	public static class ItemStackComparator implements Comparator<ItemStack>
	{
		@Override
		public int compare(ItemStack a, ItemStack b) {
			final int aType = a.getTypeId(), bType = b.getTypeId();
			if (aType < bType)
				return -1;
			if (aType > bType)
				return 1;
			final byte aData = rawData(a), bData = rawData(b);
			if (aData < bData)
				return -1;
			if (aData > bData)
				return 1;
			return 0;
		}
	}
}
