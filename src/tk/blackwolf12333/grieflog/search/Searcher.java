package tk.blackwolf12333.grieflog.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.bukkit.command.CommandSender;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.api.ISearcher;

public abstract class Searcher implements ISearcher {

	ArrayList<File> files = new ArrayList<File>();
	ArrayList<String> data = new ArrayList<String>();
	
	GriefLog plugin;
	static String[] text;
	
	public Searcher(GriefLog plugin) {
		this.plugin = plugin;
	}
	
	public class SearchTask implements Callable<ArrayList<String>> {
		ArrayList<String> data = new ArrayList<String>();
		String[] text;
		
		public SearchTask(String ...text) {
			this.text = text;
		}
		
		public ArrayList<String> call() {
			System.out.print("Searching now...");
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
								data.add(line + System.getProperty("line.separator"));
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
								data.add(line + System.getProperty("line.separator"));
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
								data.add(line + System.getProperty("line.separator"));
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
	}
	
	protected ArrayList<String> searchFile(String ...text) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		FutureTask<ArrayList<String>> searchTask = new FutureTask<ArrayList<String>>(new SearchTask(text));
		executor.execute(searchTask);
		System.out.print("Searching task started");
		ArrayList<String> ret = new ArrayList<String>();
		
		try {
			ret = searchTask.get();
			System.out.print("Searching task ended");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	@Override
	public abstract ArrayList<String> searchText(String arg0);
	
	@Override
	public abstract ArrayList<String> searchText(String arg0, String arg1);
	
	@Override
	public abstract ArrayList<String> searchText(String arg0, String arg1, String arg2);
	
	@Override
	public abstract ArrayList<String> searchPos(int x, int y, int z);
	
	@Override
	public abstract void readReportFile(File file, CommandSender sender);
}
