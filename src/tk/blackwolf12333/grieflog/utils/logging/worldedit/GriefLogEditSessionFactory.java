package tk.blackwolf12333.grieflog.utils.logging.worldedit;

import tk.blackwolf12333.grieflog.GriefLog;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EditSessionFactory;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.inventory.BlockBag;

public class GriefLogEditSessionFactory extends EditSessionFactory {

	GriefLog plugin;
	
	public GriefLogEditSessionFactory(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public EditSession getEditSession(LocalWorld world, int maxBlocks, LocalPlayer player) {
		return new GriefLogEditSession(world, maxBlocks, player, plugin);
	}
	
	@Override
	public EditSession getEditSession(LocalWorld world, int maxBlocks, BlockBag blockBag, LocalPlayer player) {
		return new GriefLogEditSession(world, maxBlocks, blockBag, player, plugin);
	}
	
	public boolean initialize() {
		/*try {
			Class.forName("com.sk89q.worldedit.EditSessionFactory").getDeclaredMethod("getEditSession", LocalWorld.class, int.class, BlockBag.class, LocalPlayer.class);
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}*/
		WorldEdit.getInstance().setEditSessionFactory(new GriefLogEditSessionFactory(plugin));
		return true;
	}
}
