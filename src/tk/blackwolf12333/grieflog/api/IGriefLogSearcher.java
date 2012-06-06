package tk.blackwolf12333.grieflog.api;

import java.io.File;

import org.bukkit.command.CommandSender;

public interface IGriefLogSearcher {

	/**
	 * @param text
	 *            : text to search for
	 * @param file
	 *            : file to search in
	 * @return This function returns the line/lines on which the players name is
	 *         found.
	 */
	public String searchText(String name);

	/**
	 * @param name
	 *            : The players name.
	 * @param event
	 *            : The event to search for.
	 * @return This function returns the line/lines on which the players name
	 *         and the event is found.
	 */
	public String searchText(String name, String event);

	/**
	 * @param name
	 *            : The players name.
	 * @param event
	 *            : The event to search for.
	 * @param blockType
	 *            : The type of block that is part of the event.
	 * @return This function returns the line/lines on which the players name
	 *         and the event and the type of block is found.
	 */
	public String searchText(String name, String event, String blockType);

	/**
	 * @param pos
	 *            : something else you might want to search for.
	 * @return This function returns the line/lines on which the players name
	 *         and the event and the type of block and something else is found.
	 */
	public String searchPos(int x, int y, int z);

	/**
	 * @param file
	 *            : file to read
	 * @param p
	 *            : player to send the lines to
	 */
	public void readReportFile(File file, CommandSender sender);
}
