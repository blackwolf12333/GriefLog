package tk.blackwolf12333.grieflog.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.entity.Player;

import tk.blackwolf12333.grieflog.GriefLog;


/**
 * I am not sure if the name of this class it right but i don't really care
 * about that:)
 */

public class FileUtils {

	/**
	 * Default constructor
	 * Doesn't do that much as you can see:)
	 */
	public FileUtils() {
		
	}
	
	
	/**
	 * @param text: text to search for
	 * @param file: file to search in
	 * @return This function returns the line/lines on which the searched text is found
	 */
	public String searchText(String text, File file)
	{
		String data = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			
			while((line = br.readLine()) != null)
			{
				if(line.indexOf(text) > 0)
				{
					data += line + System.getProperty("line.separator");
				}
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		if(data.length() > 0)
		{
			return data;
		}
		
		return data;
	}
	
	/**
	 * @param text: text to search for
	 * @param file: file to search in
	 * @param p: Player to tell the outcome of this search
	 * @return returns true if the message was send, if not, returns false
	 */
	public boolean searchText(String text, String file, Player p)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			
			p.sendMessage("+++++++++++GriefLog+++++++++++");
			while((line = br.readLine()) != null)
			{
			    if(line.indexOf(text) >= 0)
			    {
			    	p.sendMessage(line);
			    }
			}
		    p.sendMessage("++++++++++GriefLogEnd+++++++++");
			br.close();
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}
	
	/**
	 * @param text: text to search for
	 * @param file: file to search in
	 * @param p: Player to tell the outcome of this search
	 * @return returns true if the message was send, if not, returns false
	 */
	public boolean searchText(String text, File file, Player p)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			
		    p.sendMessage("+++++++++++GriefLog+++++++++++");
			while((line = br.readLine()) != null)
			{
			    if(line.indexOf(text) >= 0)
			    {
			    	p.sendMessage(line);
			    }
			}
			p.sendMessage("++++++++++GriefLogEnd+++++++++");
			
			br.close();
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}
	
	// pretty self explaining
	public double getFileSize(File file)
	{
		double bytes = file.length();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		
		return megabytes;
	}
	
	/**
	 * @param file: file to read
	 * @param p: player to send the lines to
	 */
	public void readFile(String file, Player p)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			
			p.sendMessage("+++++++++ReportStart+++++++++");
			while((line = br.readLine()) != null)
			{
			    	p.sendMessage(line);
			}
			p.sendMessage("++++++++++ReportEnd+++++++++");
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	/**
	 * @param file: file to read
	 * @param p: player to send the lines to
	 */
	public void readFile(File file, Player p)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			
			p.sendMessage("+++++++++ReportStart+++++++++");
			while((line = br.readLine()) != null)
			{
			    	p.sendMessage(line);
			}
			p.sendMessage("++++++++++ReportEnd+++++++++");
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	// also pretty self explaining
	public void writeFile(File file, String text)
	{
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(text);
			bw.close();
		}catch (Exception e) {
			GriefLog.log.warning("FileException! On GriefLog:FileUtils:writeFile(File,String)");
		}
	}
	
	// also pretty self explaining
	public void writeFile(String file, String text)
	{
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(text);
			bw.close();
		}catch (Exception e) {
			GriefLog.log.warning("FileException! On GriefLog:FileUtils:writeFile(File,String)");
		}
	}
	
	// same as the function above, but adds a newline if requested
	public void writeFile(String file, String text, boolean newLine)
	{
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(text);
			if(newLine)
				bw.newLine();
			bw.close();
		}catch (Exception e) {
			GriefLog.log.warning("FileException! On GriefLog:FileUtils:writeFile(File,String)");
		}
	}
	
	// same as above
	public void writeFile(File file, String text, boolean newLine)
	{
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(text);
			if(newLine)
				bw.newLine();
			bw.close();
		}catch (Exception e) {
			GriefLog.log.warning("FileException! On GriefLog:FileUtils:writeFile(File,String)");
		}
	}
	
	// pretty self explaining function
	public void copy(File from, File to) throws IOException {
	    
		if(!from.exists())
			return;
		if(!to.exists())
		{
			System.out.print("File \""+to.getName()+"\" does not exist!");
			return;
		}
		
		FileInputStream in = new FileInputStream(from);
	    FileOutputStream out = new FileOutputStream(to);

	    // Transfer bytes from in to out
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = in.read(buf)) > 0) {
	        out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	    from.delete();
	}
	
	// also pretty self explaining
	public void move(File from, File to)
	{
		if(!from.exists())
			return;
		if(!to.exists())
		{
			System.out.print("File \""+to.getName()+"\" does not exist!");
			return;
		}
		from.renameTo(to);
	}
}
