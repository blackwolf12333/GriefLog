package tk.blackwolf12333.grieflog.utils.searching;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import tk.blackwolf12333.grieflog.PlayerSession;
import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.utils.logging.Events;
import tk.blackwolf12333.grieflog.data.player.ChestAccessData;
import tk.blackwolf12333.grieflog.utils.InventoryStringDeSerializer;
import tk.blackwolf12333.grieflog.utils.SerializedItem;

public class ChestSearchResult implements Listener {
    static ArrayList<String> inventories = new ArrayList<String>();
    String playerInventory;
    PlayerSession player;

    public ChestSearchResult(PlayerSession player) {
        this.player = player;
        PluginManager man = Bukkit.getServer().getPluginManager();
        man.registerEvents(this, man.getPlugin("GriefLog"));
        Inventory inv = Bukkit.createInventory(player.getPlayer(), 54, "Taken");
        this.inventories.add(inv.getName());
        playerInventory = InventoryStringDeSerializer.InventoryToString(player.getPlayer().getInventory());

        SerializedItem[] itemsTaken = getItemsTaken(player.getSearchResult());
        SerializedItem[] itemsPut = getItemsPut(player.getSearchResult());
        if(itemsTaken != null) {
            for(int i = 0; i < itemsTaken.length; i++) {
                inv.setItem(i, itemsTaken[i].getItemStack());
            }
        }
        if(itemsPut != null) {
            for(int i = 0; i < itemsPut.length; i++) {
                player.getPlayer().getInventory().clear();
                player.getPlayer().getInventory().setItem(i, itemsPut[i].getItemStack());
            }
        }
        player.getPlayer().openInventory(inv);
    }

    private SerializedItem[] getItemsTaken(ArrayList<BaseData> searchResult) {
        for(BaseData data : searchResult) {
            if(data.getEvent().equals(Events.CHESTACCESS.getEventName())) {
                ChestAccessData chestAccess = (ChestAccessData) data;
                return chestAccess.getItems(chestAccess.getTaken());
            }
        }
        return null;
    }

    private SerializedItem[] getItemsPut(ArrayList<BaseData> searchResult) {
        for(BaseData data : searchResult) {
            if(data.getEvent().equals(Events.CHESTACCESS.getEventName())) {
                ChestAccessData chestAccess = (ChestAccessData) data;
                return chestAccess.getItems(chestAccess.getPut());
            }
        }
        return null;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(inventories.contains(event.getInventory().getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        //TODO: check if it's this inventory, close and remove the listener.
        if(inventories.contains(event.getInventory().getName())) {
            Inventory inv = InventoryStringDeSerializer.StringToInventory(playerInventory);
            this.player.getPlayer().getInventory().setContents(inv.getContents()); // buggy
        }
    }
}