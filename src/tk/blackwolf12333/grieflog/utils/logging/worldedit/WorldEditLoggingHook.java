package tk.blackwolf12333.grieflog.utils.logging.worldedit;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.extent.AbstractDelegateExtent;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.util.eventbus.Subscribe;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.util.eventbus.EventHandler;
import com.sk89q.worldedit.LocalPlayer;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.data.block.BlockWorldEditChangeData;
import tk.blackwolf12333.grieflog.utils.logging.GriefLogger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

import java.util.UUID;

public class WorldEditLoggingHook {
    static GriefLog plugin;

    public WorldEditLoggingHook(GriefLog plugin) {
        this.plugin = plugin;
    }

    public void hook() {
        plugin.log.info("test");
        WorldEdit.getInstance().getEventBus().register(new Object() {
            @Subscribe(priority = EventHandler.Priority.EARLY)
            public void wrapToDestroyEverything(final EditSessionEvent event) {
                
                final Actor actor = event.getActor();
                if (actor == null || !(actor instanceof Player)) return;
                final UUID playerUUID = Bukkit.getPlayer(actor.getName()).getUniqueId();
                final String playerName = actor.getName();

                event.setExtent(new AbstractDelegateExtent(event.getExtent()) {
                    @Override
                    public boolean setBlock(Vector pt, BaseBlock b) throws WorldEditException {
                        Location location = new Location(Bukkit.getWorld(event.getWorld().getName()), pt.getBlockX(), pt.getBlockY(), pt.getBlockZ());
                        Block block = location.getBlock();
                        if (event.getStage() == EditSession.Stage.BEFORE_CHANGE) {
                            String changedFromType = block.getType().toString();
                            byte changedFromData = block.getData();
                            String changedTo = Material.getMaterial(b.getType()).toString();
                            byte changedToData = (byte)b.getData();
                            if(changedFromType.equalsIgnoreCase(changedTo) && changedFromData == changedToData) {
                                return super.setBlock(pt, b); // don't log if the blocks type and data haven't actually changed.
                                        // this only fills up the log with garbage that isn't used anyway
                            }
                            BlockWorldEditChangeData data = new BlockWorldEditChangeData(block, playerName, playerUUID, changedFromType, changedFromData, changedTo, changedToData);
                            new GriefLogger(data);
                        } 
                        return super.setBlock(pt, b);
                    }
                });
            }
        });
    }
}