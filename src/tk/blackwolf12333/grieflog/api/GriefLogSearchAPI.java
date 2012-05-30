package tk.blackwolf12333.grieflog.api;

import java.io.File;

import org.bukkit.entity.Player;

public abstract class GriefLogSearchAPI {

	/**
	 * @param text: text to search for
	 * @param file: file to search in
	 * @return This function returns the line/lines on which the searched text is found
	 */
	public abstract String searchText(String text, File file);
	
	/**
	 * @param text: text to search for
	 * @param file: file to search in
	 * @param p: Player to tell the outcome of this search
	 * @return returns true if the message was send, if not, returns false
	 */
	public abstract boolean searchText(String text, String file, Player p);
	
	/**
	 * @param text: text to search for
	 * @param file: file to search in
	 * @param p: Player to tell the outcome of this search
	 * @return returns true if the message was send, if not, returns false
	 */
	public abstract boolean searchText(String text, File file, Player p);
	
	/**
	 * @param file: file to read
	 * @param p: player to send the lines to
	 */
	public abstract void readFile(String file, Player p);
}
