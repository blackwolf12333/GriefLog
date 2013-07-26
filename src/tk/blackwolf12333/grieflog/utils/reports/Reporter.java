package tk.blackwolf12333.grieflog.utils.reports;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.PlayerSession;

public class Reporter {

	ArrayList<Report> reports = new ArrayList<Report>();
	
	public int countReports() {
		return reports.size();
	}
	
	public boolean createReport(PlayerSession session) {
		if(session.getPlayer() != null) {
			int x = session.getPlayer().getLocation().getBlockX();
			int y = session.getPlayer().getLocation().getBlockY();
			int z = session.getPlayer().getLocation().getBlockZ();
			String world = session.getPlayer().getWorld().getName();
			Report report = new Report(x, y, z, world);
			reports.add(report);
			return true;
		} else {
			return false;
		}
	}
	
	public void deleteReport(int index) {
		reports.remove(index);
	}
	
	public ArrayList<Report> getReports() {
		return reports;
	}
	
	public void setReports(ArrayList<Report> reports) {
		this.reports = reports;
	}
	
	public void saveReports() {
		File reportFile = new File(GriefLog.getGriefLog().getDataFolder(), "reports.dat");
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(reportFile));
			out.writeObject(reports);
			out.close();
		} catch (FileNotFoundException e) {
			try {
				reportFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadReports() {
		File reportFile = new File(GriefLog.getGriefLog().getDataFolder(), "reports.dat");
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(reportFile));
			Object obj = in.readObject();
			if(obj instanceof ArrayList<?>) {
				this.reports = (ArrayList<Report>) in.readObject();
			}
			in.close();
		} catch (FileNotFoundException e) {
			try {
				reportFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			if(e instanceof EOFException) {
				GriefLog.debug("No reports found");
			} else {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
