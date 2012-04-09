package blackwolf12333.maatcraft.grieflog.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
		boolean success = false;
		StringBuffer sb = new StringBuffer();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				if(line.indexOf(text) >= 0)
				{
					sb.append(line);
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
			return sb.toString();
		}
		else
		{
			return "Not Found!";
		}
	}
	
	public String searchText(String text, File file)
	{
		boolean success = false;
		StringBuffer sb = new StringBuffer();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = br.readLine()) != null)
			{
				if(line.indexOf(text) >= 0)
				{
					sb.append(line);
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
			return sb.toString();
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
					return "";
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
	
	public double getFileSize(File file)
	{
		double bytes = file.length();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		
		return megabytes;
	}
	
	public String readFile(String filename) throws IOException {
		
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
	
	public String readFile(File file) throws IOException {
		
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
	
	public void writeFile(File file, String text)
	{
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(text);
			bw.close();
		}catch (Exception e){//Catch exception if any
			GriefLog.log.warning("FileException! On GriefLog:FileUtils:writeFile(File,String)");
		}
	}
	
	public void writeFile(String file, String text)
	{
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(text);
			bw.close();
		}catch (Exception e){//Catch exception if any
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
		}catch (Exception e){//Catch exception if any
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
		}catch (Exception e){//Catch exception if any
			GriefLog.log.warning("FileException! On GriefLog:FileUtils:writeFile(File,String)");
		}
	}
	
	public String getLastLines(File file, int lines) {
	    try {
	        java.io.RandomAccessFile fileHandler = new java.io.RandomAccessFile( file, "r" );
	        long fileLength = file.length() - 1;
	        StringBuilder sb = new StringBuilder();
	        int line = 0;

	        for( long filePointer = fileLength; filePointer != -1; filePointer-- ) {
	            fileHandler.seek( filePointer );
	            int readByte = fileHandler.readByte();

	            if( readByte == 0xA ) {
	                if (line == lines) {
	                    if (filePointer == fileLength) {
	                        continue;
	                    } else {
	                        break;
	                    }
	                }
	            } else if( readByte == 0xD ) {
	                line = line + 1;
	                if (line == lines) {
	                    if (filePointer == fileLength - 1) {
	                        continue;
	                    } else {
	                        break;
	                    }
	                }
	            }
	           sb.append( ( char ) readByte );
	        }

	        sb.deleteCharAt(sb.length()-1);
	        String lastLine = sb.reverse().toString();
	        return lastLine;
	    } catch( java.io.FileNotFoundException e ) {
	        e.printStackTrace();
	        return null;
	    } catch( java.io.IOException e ) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public String getLastLines(String file, int lines) {
	    try {
	        java.io.RandomAccessFile fileHandler = new java.io.RandomAccessFile(file, "r");
	        long fileLength = file.length() - 1;
	        StringBuilder sb = new StringBuilder();
	        int line = 0;

	        for(long filePointer = fileLength; filePointer != -1; filePointer--) {
	            fileHandler.seek(filePointer);
	            int readByte = fileHandler.readByte();

	            if(readByte == 0xA) {
	                if (line == lines) {
	                    if (filePointer == fileLength) {
	                        continue;
	                    } else {
	                        break;
	                    }
	                }
	            } else if(readByte == 0xD) {
	                line = line + 1;
	                if (line == lines) {
	                    if (filePointer == fileLength - 1) {
	                        continue;
	                    } else {
	                        break;
	                    }
	                }
	            }
	           sb.append((char) readByte);
	        }

	        sb.deleteCharAt(sb.length()-1);
	        String lastLine = sb.reverse().toString();
	        return lastLine;
	    } catch(java.io.FileNotFoundException e) {
	        e.printStackTrace();
	        return null;
	    } catch(java.io.IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
