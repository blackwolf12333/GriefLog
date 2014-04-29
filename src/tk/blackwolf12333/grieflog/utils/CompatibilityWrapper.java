package tk.blackwolf12333.grieflog.utils;

import tk.blackwolf12333.grieflog.compatibility.ChangesSenderInterface;
import tk.blackwolf12333.grieflog.compatibility.FastBlockSetterInterface;

public class CompatibilityWrapper {
	FastBlockSetterInterface fastBlockSetter;
	ChangesSenderInterface changesSender;
	
	public CompatibilityWrapper(String version) {
		try {
			fastBlockSetter = (FastBlockSetterInterface) Class.forName("tk.blackwolf12333.grieflog.compatibility." + version + ".FastBlockSetter").newInstance();
			changesSender = (ChangesSenderInterface) Class.forName("tk.blackwolf12333.grieflog.compatibility." + version + ".ChangesSender").newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | SecurityException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public FastBlockSetterInterface getFastBlockSetter() {
		return fastBlockSetter;
	}
	
	public ChangesSenderInterface getChangesSender() {
		return changesSender;
	}
}
