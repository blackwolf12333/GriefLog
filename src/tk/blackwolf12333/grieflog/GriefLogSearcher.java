package tk.blackwolf12333.grieflog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import tk.blackwolf12333.grieflog.api.IGriefLogSearcher;

public class GriefLogSearcher implements IGriefLogSearcher {

	private boolean addExtraLines = true;
	ArrayList<File> files = new ArrayList<File>();

	protected boolean isAddExtraLines() {
		return addExtraLines;
	}

	protected void setAddExtraLines(boolean addExtraLines) {
		this.addExtraLines = addExtraLines;
	}

	public GriefLogSearcher(String ...text) {
		files.add(GriefLog.file);
		
		File file = new File("logs/");
		String[] list = file.list();
		if(file.exists()) {
			for (String element : list) {
				files.add(new File("logs" + File.separator + element));
			}
		}
	}
	
	private String searchFile(String ...text)
	{
		String data = "";
		
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
					String line = "";

					while ((line = br.readLine()) != null) {
						if (line.contains(text[0])) {
							data += line + System.getProperty("line.separator");
							continue;
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
					String line = "";

					while ((line = br.readLine()) != null) {
						if ((line.contains(text[0])) && (line.contains(text[1]))) {
							data += line + System.getProperty("line.separator");
							continue;
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
					String line = "";

					while ((line = br.readLine()) != null) {
						if ((line.contains(text[0])) && (line.contains(text[1])) && (line.contains(text[2]))) {
							data += line + System.getProperty("line.separator");
							continue;
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
		
		return data;
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
	public void readReportFile(File file, CommandSender sender) {

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String line = "";

			sender.sendMessage(ChatColor.RED + "+++++++++ReportStart+++++++++");
			while ((line = br.readLine()) != null) {
				sender.sendMessage(line);
			}
			sender.sendMessage(ChatColor.RED + "++++++++++ReportEnd+++++++++");

			br.close();
			fileReader.close();

		} catch (Exception e) {
			sender.sendMessage(ChatColor.DARK_RED + "No Reports have been found!");
		}
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
					// TODO Auto-generated catch blockChest
					e.printStackTrace();
				}
			}
		}
	}
}
