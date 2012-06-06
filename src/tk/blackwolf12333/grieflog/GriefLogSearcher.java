package tk.blackwolf12333.grieflog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

	public GriefLogSearcher() {
		files.add(GriefLog.file);
		
		File file = new File("logs/");
		String[] list = file.list();

		for (String element : list) {
			files.add(new File("logs" + File.separator + element));
		}
	}
	
	private String searchFile(String ...text)
	{
		String data = "";
		
		if(text.length == 1)
		{
			File[] searchFiles = new File[files.size()];
			searchFiles = files.toArray(searchFiles);
			
			FileReader fileReader = null;
			BufferedReader br = null;
			
			for(File searchFile : searchFiles)
			{
				try {
					fileReader = new FileReader(searchFile);
					br = new BufferedReader(fileReader);
					String line = "";

					while ((line = br.readLine()) != null) {
						if (line.indexOf(text[0]) >= 0) {
							data += line + System.getProperty("line.separator");
						}
					}

					

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if((br != null) && (fileReader != null))
					{
						try {
							br.close();
							fileReader.close();
						} catch(Exception e1) {
							e1.printStackTrace();
						}
						
					}
				}
			}
		}
		if(text.length == 2) {
			File[] searchFiles = new File[files.size()];
			searchFiles = files.toArray(searchFiles);
			
			FileReader fileReader = null;
			BufferedReader br = null;
			
			for(File searchFile : searchFiles)
			{
				try {
					fileReader = new FileReader(searchFile);
					br = new BufferedReader(fileReader);
					String line = "";

					while ((line = br.readLine()) != null) {
						if ((line.indexOf(text[0]) >= 0) && (line.indexOf(text[1]) > 0)) {
							data += line + System.getProperty("line.separator");
						}
					}

					br.close();
					fileReader.close();

				} catch (Exception e) {
					if((fileReader != null) && (br != null))
					{
						try {
							br.close();
							fileReader.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
					e.printStackTrace();
				} finally {
					if((br != null) && (fileReader != null))
					{
						try {
							br.close();
							fileReader.close();
						} catch(Exception e1) {
							e1.printStackTrace();
						}
						
					}
				}
			}
		}
		if(text.length == 3)
		{
			File[] searchFiles = new File[files.size()];
			searchFiles = files.toArray(searchFiles);
			
			FileReader fileReader = null;
			BufferedReader br = null;
			
			for(File searchFile : searchFiles)
			{
				try {
					fileReader = new FileReader(searchFile);
					br = new BufferedReader(fileReader);
					String line = "";

					while ((line = br.readLine()) != null) {
						if ((line.indexOf(text[0]) >= 0) && (line.indexOf(text[1]) > 0) && (line.indexOf(text[2]) > 0)) {
							data += line + System.getProperty("line.separator");
						}
					}

					br.close();
					fileReader.close();

				} catch (Exception e) {
					if((fileReader != null) && (br != null))
					{
						try {
							br.close();
							fileReader.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
					e.printStackTrace();
				} finally {
					if((br != null) && (fileReader != null))
					{
						try {
							br.close();
							fileReader.close();
						} catch(Exception e1) {
							e1.printStackTrace();
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
		return	searchFile(xyz);
	}

	/*@Override
	public boolean searchGriefLog(String text, String file, Player p) {

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String line = "";

			if (isAddExtraLines()) {
				p.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");

				while ((line = br.readLine()) != null) {
					if (line.contains(text)) {
						p.sendMessage(line);
					}
				}

				p.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");
			} else {
				while ((line = br.readLine()) != null) {
					if (line.contains(text)) {
						p.sendMessage(line);
					}
				}
			}

			br.close();
			fileReader.close();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}*/

	/*@Override
	public boolean searchGriefLog(String text, File file, Player p) {

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			String line = "";

			if (isAddExtraLines()) {
				p.sendMessage(ChatColor.BLUE + "+++++++++++GriefLog+++++++++++");

				while ((line = br.readLine()) != null) {
					if (line.contains(text)) {
						p.sendMessage(line);
					}
				}

				p.sendMessage(ChatColor.BLUE + "++++++++++GriefLogEnd+++++++++");
			} else {
				while ((line = br.readLine()) != null) {
					if (line.contains(text)) {
						p.sendMessage(line);
					}
				}
			}

			br.close();
			fileReader.close();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}*/

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
			e.printStackTrace();
		}
	}

}
