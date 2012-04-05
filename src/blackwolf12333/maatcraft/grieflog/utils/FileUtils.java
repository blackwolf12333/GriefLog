package blackwolf12333.maatcraft.grieflog.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.entity.Player;

import blackwolf12333.maatcraft.grieflog.GriefLog;

/**
 * I am not sure if the name of this class it right but i don't really care
 * about that:)
 */

public class FileUtils {

	public FileUtils() {
		
	}
	
	
	/**
	 * @param text: text to search for.
	 * @param file: filename of the file to search in.
	 * @return Returns the string you searched for.
	 */
	public String searchText(String text, String file)
	{
		boolean success = false;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				if(line.indexOf(text) >= 0)
				{
					writeFile(GriefLog.reportFile, line);
					success = true;
				}
				while(line.indexOf(text) < 0)
				{
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		if(success)
		{
			return "";
		}
		else
		{
			return "Not Found!";
		}
	}
	
	public String searchText(String text, File file)
	{
		boolean success = false;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				if(line.indexOf(text) >= 0)
				{
					writeFile(GriefLog.reportFile, line);
					success = true;
				}
				while(line.indexOf(text) < 0)
				{
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		if(success)
		{
			return "";
		}
		else
		{
			return "Not Found!";
		}
	}
	
	public String searchText(String text, String file, Player p)
	{
		boolean success = false;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				if(line.indexOf(text)>= 0)
				{
					p.sendMessage(line);
					success = true;
				}
				while(line.indexOf(text) < 0)
				{
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		if(success)
		{
			return "";
		}
		else
		{
			return "Not Found!";
		}
	}
	
	public static double getFileSize(File file)
	{
		double bytes = file.length();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		
		return megabytes;
	}
	
	public static String readFile(String filename) throws IOException {
	   String lineSep = System.getProperty("line.separator");
	   BufferedReader br = new BufferedReader(new FileReader(filename));
	   String nextLine = "";
	   StringBuffer sb = new StringBuffer();
	   while ((nextLine = br.readLine()) != null) {
		   for(int i = 0; i < nextLine.length(); i++)
		   {
			   sb.append(nextLine);
			   //
			   // note:
			   //   BufferedReader strips the EOL character
			   //   so we add a new one!
			   //
			   sb.append(lineSep);
		   }
	   }
	   return sb.toString();
	}
	
	public static String readFile(File file) throws IOException {
		String lineSep = System.getProperty("line.separator");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String nextLine = "";
		StringBuffer sb = new StringBuffer();
		while ((nextLine = br.readLine()) != null) {
			for(int i = 0; i < nextLine.length(); i++)
			{
				sb.append(nextLine);
				//
				// note:
				//   BufferedReader strips the EOL character
				//   so we add a new one!
				//
				sb.append(lineSep);
			}
		}	
		return sb.toString();
	}
	
	public static String readFile(File inFile, File outFile) throws IOException {
		boolean success = false;
		
		try{
			// Open inFile
			FileInputStream fstream = new FileInputStream(inFile);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console
				writeFile(outFile,strLine);
				success = true;
			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			GriefLog.log.warning("Something went wrong in: GriefLog:FileUtils:readFile(File, File)");
		}
		if(success) {
			return "";
		}
		else
		{
			return "Something went wrong:(";
		}
	}
	
	public static void writeFile(File file, String text)
	{
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(text);
			bw.newLine();
			bw.close();
		}catch (Exception e){//Catch exception if any
			GriefLog.log.warning("FileException! On GriefLog:FileUtils:writeFile(File,String)");
		}
	}
	
	public static void writeFile(String file, String text)
	{
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(text);
			bw.newLine();
			bw.close();
		}catch (Exception e){//Catch exception if any
			GriefLog.log.warning("FileException! On GriefLog:FileUtils:writeFile(File,String)");
		}
	}
}
