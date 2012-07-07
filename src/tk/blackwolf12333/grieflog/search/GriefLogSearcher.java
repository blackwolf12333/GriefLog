package tk.blackwolf12333.grieflog.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.command.CommandSender;

import tk.blackwolf12333.grieflog.GriefLog;

public class GriefLogSearcher extends Searcher {

	public GriefLogSearcher() {
		files.add(GriefLog.file);
		
		File file = new File("logs" + File.separator);
		String[] list = file.list();
		if(file.exists()) {
			for (String element : list) {
				files.add(new File("logs" + File.separator + element));
			}
		}
	}

	@Override
	public String searchText(String arg0) {
		return searchFile(arg0);
	}

	@Override
	public String searchText(String arg0, String arg1) {
		return searchFile(arg0, arg1);
	}

	@Override
	public String searchText(String arg0, String arg1, String arg2) {
		return searchFile(arg0, arg1, arg2);
	}

	@Override
	public String searchPos(int x, int y, int z) {
		String xyz = x + ", " + y + ", " + z;
		return searchFile(xyz);
	}
	
	@Override
	/** 
	 * not implemented here
	 */
	public void readReportFile(File file, CommandSender sender) {}
	
	public String searchForBlockProtection(String ...text) {
		files.clear();
		files.add(GriefLog.file);
		
		File file = new File("logs" + File.separator);
		String[] list = file.list();
		if(file.exists()) {
			for (int i = 0; i < list.length; i++) {
				if(i == 10) {
					break;
				} else {
					files.add(new File("logs" + File.separator + list[i]));
				}
			}
		}
		
		return searchFile(text);
	}
	
	// just dumped this here, i'll need it once
	public void deleteLine(String line) {
		
		BufferedReader br = null;
		PrintWriter pw = null;
		
		File[] searchFiles = new File[files.size()];
		searchFiles = files.toArray(searchFiles);
		
		for(File searchFile : searchFiles) {
			try {
				
				File infile = searchFile;
				
				// Construct the new file that will later be renamed to the original
				// filename.
				File tempFile = new File(infile.getAbsolutePath() + ".tmp");
				br = new BufferedReader(new FileReader(infile));
				pw = new PrintWriter(new FileWriter(tempFile));
				
				String currentline = null;
					
				// Read from the original file and write to the new
				// unless content matches data to be removed.
				while ((currentline = br.readLine()) != null) {
						
					if (!currentline.trim().contains(line)) {
						
						pw.println(line);
						pw.flush();
					}
				}
				pw.close();
				br.close();

				// Delete the original file
				if (!infile.delete()) {
					System.out.println("Could not delete file");
					return;
				}
				
				// Rename the new file to the filename the original file had.
				if (!tempFile.renameTo(infile))
					System.out.println("Could not rename file");
				
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				pw.close();
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
