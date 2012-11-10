package tk.blackwolf12333.grieflog.utils.logging;

import java.io.File;
import java.io.IOException;

import tk.blackwolf12333.grieflog.GriefLog;
import tk.blackwolf12333.grieflog.data.BaseData;

public class GriefLogger implements Runnable {

	BaseData data;
	
	public GriefLogger(BaseData data) {
		this.data = data;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		log(this.data);
	}

	public synchronized void log(BaseData data) {
		try {
			File worldDir = new File(GriefLog.logsDir, data.getWorldName());
			File log = new File(worldDir, "GriefLog.txt");
			if (!log.exists()) {
				log.getParentFile().mkdirs();
				log.createNewFile();
			}
			
			String strData = data.toString();
			if(!strData.endsWith(System.getProperty("line.separator"))) {
				strData += System.getProperty("line.separator");
			}
			
			String ret = GriefLog.t.now() + strData;
			
			GriefLog.fileIO.write(ret, log);

		} catch (IOException e) {
			GriefLog.log.warning(e.toString());
		}
	}
}
