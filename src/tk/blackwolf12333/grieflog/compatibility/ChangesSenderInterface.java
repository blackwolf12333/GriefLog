package tk.blackwolf12333.grieflog.compatibility;

import java.util.HashSet;

import org.bukkit.Chunk;

import tk.blackwolf12333.grieflog.rollback.SendChangesTask;

public interface ChangesSenderInterface {
	void sendChanges(SendChangesTask task, HashSet<Chunk> chunks);
}
