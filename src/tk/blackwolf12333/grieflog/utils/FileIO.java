package tk.blackwolf12333.grieflog.utils;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class FileIO {

	public synchronized void write(String data, File file) {
		try {
			if (getFileSize(file) >= ConfigHandler.values.getMb()) {
				System.out.print("backup at " + ConfigHandler.values.getMb());
				autoBackup(file);
				file.createNewFile();
			}

			FileWriter fw = null;
			BufferedWriter bw = null;
			try {
				fw = new FileWriter(file, true);
				bw = new BufferedWriter(fw);
				data = ChatColor.stripColor(data);
				bw.write(data);
				bw.close();
				fw.close();
			} catch (Exception e) {
				GriefLog.log.warning(e.getMessage());
			} finally {
				if((fw != null) && (bw != null))
				{
					bw.close();
					fw.close();
				}
			}

		} catch (IOException e) {
			GriefLog.log.warning(e.toString());
		}
	}
	
	/**
	 *  Reads a file storing intermediate data into a String. Fast method.
	 *  @param file the file to be read
	 *  @return a file data
	 */
	public String read2String(File file) throws Exception {
		InputStream in = null;
		byte[] buf = null; // output buffer
		int bufLen = 5100*1024;
		
		try{
			in = new BufferedInputStream(new FileInputStream(file));
			buf = new byte[bufLen];
			byte[] tmp = null;
			int len = 0;
			List<byte[]> data  = new ArrayList<byte[]>(24); // keeps peaces of data
			while((len = in.read(buf,0,bufLen)) != -1){
				tmp = new byte[len];
				System.arraycopy(buf,0,tmp,0,len); // still need to do copy
				data.add(tmp);
			}
			/*
	            This part is optional. This method could return a List data
	            for further processing, etc.
			*/
			len = 0;
			if (data.size() == 1) 
				return new String((byte[]) data.get(0));
			
			for (int i=0;i<data.size();i++)
				len += ((byte[]) data.get(i)).length;
			
			buf = new byte[len]; // final output buffer
			len = 0;
			for (int i=0;i<data.size();i++){ // fill with data
				tmp = (byte[]) data.get(i);
				System.arraycopy(tmp,0,buf,len,tmp.length);
				len += tmp.length;
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			if (in != null) {
				try {
					in.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return new String(buf);
	}
	
	public void copy(File from, File to) throws IOException {
		if (!from.exists())
			return;
		if (!to.exists()) {
			System.out.print("File \"" + to.getName() + "\" does not exist!");
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
	
	public double getFileSize(File file) {
		double bytes = file.length();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		
		return megabytes;
	}
	
	//
	// 		MISC FUNCTIONS, NOT REALLY FILEIO
	//
	
	private void autoBackup(File file) {
		String name = file.getName().substring(0, file.getName().indexOf(".")) + GriefLog.t.now() + ".txt";
		file.renameTo(new File(file.getParent(), name));
		GriefLog.debug("backup!!");
	}
}
