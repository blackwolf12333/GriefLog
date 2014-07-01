package tk.blackwolf12333.grieflog.utils;

import java.lang.Exception;

import tk.blackwolf12333.grieflog.GriefLog;
/**
 * Don't use e.printStackTrace(), it's all handled here.
 */
public class GriefLogException extends Exception {
    public GriefLogException() {}

    public GriefLogException(String message) {
        GriefLog.debug(message);
    }

    public GriefLogException(String message, Throwable e) {
        GriefLog.debug(message);
        GriefLog.debug(e.toString() + ":");
        for(StackTraceElement element : e.getStackTrace()) {
            GriefLog.debug("\tat: " + element);
        }
    }
}