package blackwolf12333.maatcraft.grieflog.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.entity.Player;

import blackwolf12333.maatcraft.grieflog.GriefLog;

public class FileUtils {

	public FileUtils() {
		
	}
	
	public static String foundLine;
	
	public String searchText(String text, String file)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				if(line.indexOf(text)>= 0)
				{
					foundLine = line;
				}
				while(line.indexOf(text) < 0)
				{
					break;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return "Not found!";
	}
	
	public String searchText(String text, String file, Player p)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				if(line.indexOf(text)>= 0)
				{
					p.sendMessage(line);
				}
				while(line.indexOf(text) < 0)
				{
					break;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return "Not found!";
	}
	
	public String searchText(String text)
	{
		try {
			int line = grepLineNumber(text);
			return showLines(line, line+2);
		} catch (Exception e) {
			GriefLog.log.warning(e.getMessage());			
		}
		return "Not found!";
	}
	
	public int grepLineNumber(String word) throws Exception {
	    BufferedReader buf = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(GriefLog.file))));

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
	
	public double getFileSize(File file)
	{
		double bytes = file.length();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		
		return megabytes;
	}
}
