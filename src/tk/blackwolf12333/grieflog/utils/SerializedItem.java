package tk.blackwolf12333.grieflog.utils;

import org.bukkit.inventory.ItemStack;

public class SerializedItem {

	String itemString;
	ItemStack itemStack;
	int slot;
	
	public SerializedItem(String itemString, ItemStack itemStack, int slot) {
		this.itemStack = itemStack;
		this.itemString = itemString;
		this.slot = slot;
	}

	public String getItemString() {
		return itemString;
	}

	public void setItemString(String itemString) {
		this.itemString = itemString;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}
}
