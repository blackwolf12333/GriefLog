package blackwolf12333.maatcraft.grieflog.commands;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import blackwolf12333.maatcraft.grieflog.GriefLog;

public class GLog implements CommandExecutor {

	public GriefLog gl;
	
	public GLog(GriefLog plugin) {
		gl = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel,
			String[] args) {
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("glog"))
		{
			if (!(sender instanceof Player)) return true;
			
			if(args.length == 4) {
				if(args[0].equalsIgnoreCase("get"))
				{
					String x = args[1];
					String y = args[2];
					String z = args[3];
					
					sender.sendMessage(searchText(x+", "+y+", "+z));
					return true;
				}
			}
			
			if(args.length == 2)
			{
				if(args[0].equalsIgnoreCase("get"))
				{
					String coord = args[1];
					
					sender.sendMessage(searchText(coord));
				}
			}
			
			if(args.length == 2)
			{
				if(args[0].equalsIgnoreCase("get"))
				{
					if(args[1].equalsIgnoreCase("currentpos"))
					{
						String pos = p.getLocation().getBlockX() + ", " + p.getLocation().getBlockY() + ", " + p.getLocation().getBlockZ();
					
						sender.sendMessage(searchText(pos));
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public String searchText(String text)
	{
		try {
			int line = grepLineNumber(text);
			return showLines(line, line++);
		} catch (Exception e) {
			GriefLog.log.warning(e.getMessage());			
		}
		return "Not found!";
	}
	
	public int grepLineNumber(String word) throws Exception {
	    BufferedReader buf = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(GriefLog.file.getAbsolutePath()))));

	    String line;
	    int lineNumber = 0;
	    while ((line = buf.readLine()) != null)   {
	        lineNumber++;
	        if (word.equals(line)) {
	            return lineNumber;
	        }
	    }
	    return -1;
	}
	
	public String showLines(int startLine, int endLine)  {
		String line = null;
		int currentLineNo = 0;

		BufferedReader in = null;
		try {
			in = new BufferedReader (new FileReader(GriefLog.file));
			
			//read to startLine
			while(currentLineNo<startLine) {
				if (in.readLine()==null) {
					// oops, early end of file
					throw new IOException("File too small");
				}
				currentLineNo++;
			}
			
			//read until endLine
			while(currentLineNo<=endLine) {
				line = in.readLine();
				if (line==null) {
					// here, we'll forgive a short file
					// note finally still cleans up
					return null;
				}
				currentLineNo++;
				return line;
			}
			
		} catch (IOException ex) {
			return "Problem reading file.\n" + ex.getMessage();
		} finally {
			try { if (in!=null) in.close(); } catch(IOException ignore) {}
		}
		return "Lines Not Found!";
	}
}
