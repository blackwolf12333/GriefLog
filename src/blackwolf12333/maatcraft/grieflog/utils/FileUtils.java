package blackwolf12333.maatcraft.grieflog.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import blackwolf12333.maatcraft.grieflog.GriefLog;

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
	 * @param text: text to search for.
	 * @param file: filename of the file to search in.
	 * @return Returns the string you searched for.
	 */
	public String searchText(String text, String file)
	{
		String data = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				if(line.indexOf(text) > 0)
				{
					data += System.getProperty("line.separator") + line + System.getProperty("line.separator");
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
	
	public String searchText(String text, File file)
	{
		String data = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
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
	
	public void searchText(String text, String file, Player p)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			String data = "";
			while((line = br.readLine()) != null)
			{
			    if(line.indexOf(text) >= 0)
			    {
			        data += line + System.getProperty("line.separator");
			    }
			}
			if (data.length() > 0) {
			    p.sendMessage("+++++++++++GriefLog+++++++++++");
			    p.sendMessage(data);
			    p.sendMessage("++++++++++GriefLogEnd+++++++++");
			}
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	public String searchText(String text, File file, Player p)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(text);
		
		ArrayList<String> als = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				if(line.indexOf(text) >= 0)
				{
					als.add(line);
				}
			}
			
			br.close();
			
			for(Integer i = 0; i < als.size(); i++)
			{
				String ret = als.get(i);
				p.sendMessage(ret);
				if(i==1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7)
					p.sendMessage(System.getProperty("line.separator"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return "";
	}
	
	
	
	/**
	 * @param text The text to search for in @param file
	 * @param file The file to search through
	 * @return Returns true if the text is found on a line in @param file
	 */
	public boolean isInFile(String text, File file)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				if(line.indexOf(text) >= 0)
					return true;
				else
					return false;
			}
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return false;
	}

	/**
	 * @param text The text to search for in @param file
	 * @param file The file name to search through
	 * @return Returns true if the text is found on a line in @param file
	 */
	public boolean isInFile(String text, String file)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				if(line.indexOf(text) >= 0)
					return true;
				else
					return false;
			}
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return false;
	}

	
	public double getFileSize(File file)
	{
		double bytes = file.length();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		
		return megabytes;
	}
	
	public String readFile(String filename) {
		
		StringBuffer sb = new StringBuffer();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String nextLine = "";
		
			while ((nextLine = br.readLine()) != null) {
				return nextLine;
			}
			
			br.close();
		} catch (IOException e) {
			
		}
		return sb.toString();
	}
	
	public String readFile(File file) {
		
		StringBuffer sb = new StringBuffer();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String nextLine = "";
			
			while ((nextLine = br.readLine()) != null) {
				return nextLine;
			}
			
			br.close();
		} catch(IOException e) {
			
		}
		return sb.toString();
	}
	
	public String readf(File file)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.readFile(file));
		sb.append("\n");
		
		return sb.toString();
	}
	
	public String readf(String file)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.readFile(file));
		sb.append("\n");
		
		return sb.toString();
	}
	
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
