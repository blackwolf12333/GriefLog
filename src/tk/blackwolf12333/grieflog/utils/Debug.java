package tk.blackwolf12333.grieflog.utils;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import tk.blackwolf12333.grieflog.utils.config.ConfigHandler;

public class Debug {

	Logger log;
	
	public Debug(Logger log) {
		this.log = log;
		if(ConfigHandler.values.getDebugLogging()) {
			try {
				FileHandler fileHandler = new FileHandler("plugins/GriefLog/debug-log.txt", true);
				fileHandler.setLevel(Level.FINEST);
				LogFormatter formatter = new LogFormatter();
				fileHandler.setFormatter(formatter);
				this.log.addHandler(fileHandler);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void info(String msg) {
		log.info(msg);
	}
	
	public void log(Object msg, boolean debug) {
		if(ConfigHandler.values.getDebug() && debug) {
			log.warning(msg.toString());
		} else if(!ConfigHandler.values.getDebug() && debug) {
			if(ConfigHandler.values.getDebugLogging()) { // log debug output but don't put it in console
				log.getHandlers()[0].publish(new LogRecord(Level.WARNING, msg.toString()));
			}
			return; // do nothing if they don't want any debug output in their console
		} else {
			log.info(msg.toString());
		}
	}
	
	public void warning(String msg) {
		log.warning(msg);
	}

	public void stop() {
		for(Handler h : this.log.getHandlers()) {
			if(h instanceof FileHandler) {
				h.close();
			}
		}
	}

	private class LogFormatter extends Formatter {
		public String format(LogRecord record) {
			SimpleDateFormat date_format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
			return date_format.format(new Date(record.getMillis())) + " " + record.getLevel() + "\t" + record.getMessage() + "\n";
		}

		public String getHead(Handler h) { // yes please :)
			return "";
		}

		public String getTail(Handler h) {
			return "";
		}
	}
}
