package tk.blackwolf12333.grieflog.utils.filters;

import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.Bukkit;

import tk.blackwolf12333.grieflog.data.BaseData;
import tk.blackwolf12333.grieflog.data.player.ChestAccessData;
import tk.blackwolf12333.grieflog.utils.logging.Events;

public class ChestAccessFilter implements Filter {
    int x,y,z;
    String world;

    public ChestAccessFilter(int x, int y, int z, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public boolean doFilter(BaseData data) {
        Location loc = new Location(Bukkit.getWorld(world), x, y, z);
        if(data.getEvent().equals(Events.CHESTACCESS.getEventName())) {
            ChestAccessData chestAccess = (ChestAccessData) data;
            return (loc.getX() == chestAccess.getChestX() && loc.getY() == chestAccess.getChestY() && loc.getZ() == chestAccess.getChestZ() && loc.getWorld().getName().equals(world));
        }
        return false;
    }
}