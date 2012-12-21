package tk.blackwolf12333.grieflog.listeners.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryWrapper extends InventoryView {
	
	InventoryOpenEvent event;
	
	public InventoryWrapper(InventoryOpenEvent event) {
		this.event = event;
	}

	@Override
	public Inventory getBottomInventory() {
		Inventory bottom = Bukkit.createInventory(event.getPlayer(), event.getView().getType());
		bottom.addItem(event.getView().getBottomInventory().getContents());
		return bottom;
	}

	@Override
	public HumanEntity getPlayer() {
		if(!(event.getPlayer() instanceof Player)) {
			return null;
		}
		return event.getPlayer();
	}

	@Override
	public Inventory getTopInventory() {
		Inventory top = Bukkit.createInventory(event.getPlayer(), event.getView().getType());
		if(event.getView().getTopInventory().getContents() != null) {
			top.addItem(event.getView().getTopInventory().getContents());
		}
		return top;
	}

	@Override
	public InventoryType getType() {
		return this.event.getView().getType();
	}
	
	@Override
	public ItemStack getItem(int index) {
		return this.event.getView().getTopInventory().getItem(index);
	}
}
