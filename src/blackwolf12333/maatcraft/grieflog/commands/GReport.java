package blackwolf12333.maatcraft.grieflog.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import blackwolf12333.maatcraft.grieflog.GriefLog;

public class GReport implements CommandExecutor {

	public GriefLog gl;
	
	public GReport(GriefLog plugin) {
		gl = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("report"))
		{
			BufferedWriter writer;
			
			if(args[0] == "currentpos")
			{
				Player p = (Player) sender;
				
				int x = p.getLocation().getBlockX();
				int y = p.getLocation().getBlockY();
				int z = p.getLocation().getBlockZ();
				
				try {
					writer = new BufferedWriter(new FileWriter(GriefLog.reportFile.getAbsolutePath(),true));
					writer.write(searchText(x + ", " + y + ", " + z));
					writer.newLine();
					writer.write("Reported by: " + p.getName());
					writer.newLine();
					writer.close();
				} catch (IOException e) {
					GriefLog.log.warning(e.getMessage());
				}
			}
			
			if(args.length == 3)
			{
				Player p = (Player) sender;
				
				String x = args[0];
				String y = args[1];
				String z = args[2];
				
				try {
					writer = new BufferedWriter(new FileWriter(GriefLog.reportFile.getAbsolutePath(),true));
					writer.write(searchText(x + ", " + y + ", " + z));
					writer.newLine();
					writer.write("Reported by: " + p.getName());
					writer.newLine();
					writer.close();
				} catch (IOException e) {
					GriefLog.log.warning(e.getMessage());
				}
			}
		}
		
		return false;
	}

	public void createFile()
	{
		if(GriefLog.reportFile.exists())
		{
			return;
		}
		else
		{
			try {
				GriefLog.reportFile.createNewFile();
			} catch (IOException e) {
				GriefLog.log.warning(e.getMessage());
			}
		}
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
