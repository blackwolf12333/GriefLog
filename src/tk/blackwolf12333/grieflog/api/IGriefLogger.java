package tk.blackwolf12333.grieflog.api;

import java.io.File;

public interface IGriefLogger {

	public void Log(String data);
	
	public void Log(String data, File file);
}
