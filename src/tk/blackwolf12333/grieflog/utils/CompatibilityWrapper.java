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
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
		} catch(SecurityException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
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
