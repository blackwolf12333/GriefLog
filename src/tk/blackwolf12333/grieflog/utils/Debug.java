package tk.blackwolf12333.grieflog.utils;

import java.util.logging.Logger;

import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class Debug {

	Logger log;
	
	public Debug(Logger log) {
		this.log = log;
	}
	
	public void info(String msg) {
		log.info(msg);
	}
	
	public void log(Object msg, boolean debug) {
		if(ConfigHandler.values.getDebug() && debug) {
			log.info(msg.toString());
		}
	}
	
	public void warning(String msg) {
		log.warning(msg);
	}
}
