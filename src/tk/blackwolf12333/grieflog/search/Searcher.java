package tk.blackwolf12333.grieflog.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import tk.blackwolf12333.grieflog.api.ISearcher;

public abstract class Searcher  implements ISearcher {

	ArrayList<File> files = new ArrayList<File>();
	
	protected String searchFile(String ...text)
	{
		String data = null;
		
		if(text.length == 1)
		{
			File[] searchFiles = new File[files.size()];
			searchFiles = files.toArray(searchFiles);
			FileReader fr = null;
			BufferedReader br = null;
			
			for(File searchFile : searchFiles)
			{
				try {
					fr = new FileReader(searchFile);
					br = new BufferedReader(fr);
					String line = null;

					while ((line = br.readLine()) != null) {
						if (line.contains(text[0])) {
							data += line + System.getProperty("line.separator");
						} else {
							continue;
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if((fr != null) && (br != null)) {
						try {
							fr.close();
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		if(text.length == 2) {
			File[] searchFiles = new File[files.size()];
			searchFiles = files.toArray(searchFiles);
			FileReader fr = null;
			BufferedReader br = null;
			
			for(File searchFile : searchFiles)
			{
				try {
					fr = new FileReader(searchFile);
					br = new BufferedReader(fr);
					String line = null;

					while ((line = br.readLine()) != null) {
						if ((line.contains(text[0])) && (line.contains(text[1]))) {
							data += line + System.getProperty("line.separator");
						} else {
							continue;
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if((fr != null) && (br != null)) {
						try {
							fr.close();
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		if(text.length == 3)
		{
			File[] searchFiles = new File[files.size()];
			searchFiles = files.toArray(searchFiles);
			FileReader fr = null;
			BufferedReader br = null;
			
			for(File searchFile : searchFiles)
			{
				try {
					fr = new FileReader(searchFile);
					br = new BufferedReader(fr);
					String line = null;

					while ((line = br.readLine()) != null) {
						if ((line.contains(text[0])) && (line.contains(text[1])) && (line.contains(text[2]))) {
							data += line + System.getProperty("line.separator");
						} else {
							continue;
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if((fr != null) && (br != null)) {
						try {
							fr.close();
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		
		
		if(data != null) {
			return data.substring(4);
		} else {
			return null;
		}
	}
	
	@Override
	public abstract String searchText(String arg0);
	
	@Override
	public abstract String searchText(String arg0, String arg1);
	
	@Override
	public abstract String searchText(String arg0, String arg1, String arg2);
	
	@Override
	public abstract String searchPos(int x, int y, int z);
	
	@Override
	public abstract void readReportFile(File file, CommandSender sender);
}
